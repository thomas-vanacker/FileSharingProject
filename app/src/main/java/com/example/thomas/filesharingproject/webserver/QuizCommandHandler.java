package com.example.thomas.filesharingproject.webserver;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.thomas.filesharingproject.R;
import com.example.thomas.filesharingproject.app.AppLog;
import com.example.thomas.filesharingproject.constants.Constants;
import com.example.thomas.filesharingproject.quiz.Answer;
import com.example.thomas.filesharingproject.quiz.Question;
import com.example.thomas.filesharingproject.quiz.Quiz;
import com.example.thomas.filesharingproject.quiz.Quizes;
import com.example.thomas.filesharingproject.utility.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class QuizCommandHandler implements HttpRequestHandler {
    private static final String EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory() + "";
    int serverPort = 0;
    private Context context = null;

    public QuizCommandHandler(Context context, int serverPort){
        this.context = context;
        this.serverPort = serverPort;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext httpContext) throws HttpException, IOException {
        HttpEntity entity = null;
        String uriString = request.getRequestLine().getUri();
        if (uriString.startsWith("/")) {
            uriString = uriString.substring(1);
        }
        final String localFileName = uriString;

            entity = new EntityTemplate(new ContentProducer() {
                public void writeTo(final OutputStream outstream) throws IOException {
                    OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
                    String resp;
                    if (localFileName.equals("quiz/Quiz.html")){
                        int quizNumber = (int) (Math.random() * Quizes.getSingleton().getQuizes().size());
                        if (quizNumber == Quizes.getSingleton().getQuizes().size()) {
                            resp = "<h2>Aucun quiz présent</h2>";
                        } else {
                            Quiz quiz = Quizes.getSingleton().getQuizes().get(quizNumber);
                            resp = "<html>";
                            resp += "<script src='quiz.js' type='text/javascript'></script>";
                            resp += "<head>";
                            resp += "</head>";
                            resp += "<body>";
                            resp += "<div class='multiplechoice-quiz' lang='fr'>";
                            resp += "<h2 id='multiplechoice-quiz'>" + quiz.getTitle() + "</h2>";
                            resp += "<p>Les questions suivantes peuvent avoir plusieurs réponses correctes!</p>";
                            for (Question question : quiz.getQuestion_list()) {
                                resp += "<p>" + question.getIntitulate();
                                for (Answer answer : question.getAnswer_list()) {
                                    resp += "(" + (answer.isGood_answer() ? "" : "!") + answer.getAnswer() + ")";
                                }
                                resp += "</p>";
                            }
                            resp += "</div>";
                            resp += "</body>";
                            resp += "</html>";
                        }
                    }
                    else {
                        resp = Utility.convertStreamToString(context.getAssets().open(localFileName));
                    }

                    writer.write(resp);
                    writer.flush();
                }
            });

            response.setHeader("Context-Type", "text");



        response.setEntity(entity);
    }
    private HttpEntity getEntityFromUri(String uri,HttpResponse response){
        String contentType = "text/html";
        String filepath = EXTERNAL_STORAGE_PATH;

        AppLog.logString("URI: " + uri);
        AppLog.logString(filepath);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        if(uri.equalsIgnoreCase("/") || uri.length() <= 0){
            filepath = EXTERNAL_STORAGE_PATH + "/" + pref.getString(Constants.PREF_DIRECTORY, "");
        }
        else{
            filepath = EXTERNAL_STORAGE_PATH + uri;
        }

        final File file = new File(filepath);

        HttpEntity entity = null;

        if(file.isDirectory()){
            entity = new EntityTemplate(new ContentProducer() {
                public void writeTo(final OutputStream outstream) throws IOException {
                    OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
                    String resp = getDirListingHTML(file);

                    writer.write(resp);
                    writer.flush();
                }
            });

            ((EntityTemplate)entity).setContentType(contentType);
        }
        else if(file.exists()){
            contentType = URLConnection.guessContentTypeFromName(file.getAbsolutePath());

            entity = new FileEntity(file, contentType);

            response.setHeader("Content-Type", contentType);
        }
        else{
            try {
                entity = new StringEntity(Utility.openHTMLString(context, R.raw.notfound));
                response.setHeader("Content-Type", "text/html");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        AppLog.logString("Content Type: " + contentType);

        return entity;
    }

    private String getDirListingHTML(File file) {
        ArrayList<String> fileList = getDirListing(file);
        String htmltemplate = Utility.openHTMLString(context, R.raw.dirlisting);
        String html = "";
        String fileinfo[];

        for (String fileName : fileList) {
            fileinfo = fileName.split("@");

            html += "<tr>";
            html += "<td><a href='http://" + Utility.getLocalIpAddress() + ":" + serverPort + "/dir/" + fileinfo[0] + "'>" + fileinfo[0] + "</a><br></td>";
            html += "<td>" + fileinfo[1] + "</td>";
            html += "</tr>";
        }

        html = htmltemplate.replace("%FOLDERLIST%", html);

        return html;
    }

    private ArrayList<String> getDirListing(File file) {
        if (file == null || !file.isDirectory()) return null;
        File[] files = file.listFiles();
        ArrayList<File> fileArrayList = new ArrayList<>();
        for (File f : files) {
            fileArrayList.add(f);
        }
        Collections.sort(fileArrayList);
        ArrayList<String> fileList = new ArrayList<>();
        DateFormat dateformat = DateFormat.getDateInstance();

        for (File f : fileArrayList) {
            fileList.add(f.getAbsolutePath().substring(EXTERNAL_STORAGE_PATH.length() + 1) + "@" + dateformat.format(new Date(f.lastModified())));
        }

        return fileList;
    }
}
