package com.example.thomas.filesharingproject.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.thomas.filesharingproject.quiz.Quizes;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class AWSApplication extends Application {
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		loadQuizes();
		super.onCreate();
	}

	private void loadQuizes() {
		Quizes quizes = new Quizes();
		try {
			Gson gson = new Gson();
			InputStream inStream = openFileInput("quizes.json");
			if (inStream != null) {
				byte[] b;
				b = new byte[inStream.available()];
				inStream.read(b);
				String json = new String(b);
				quizes = gson.fromJson(json, Quizes.class);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (quizes == null) {
			quizes = new Quizes();
		}
		Quizes.setSingleton(quizes);
		saveQuizes();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		saveQuizes();
		super.onTerminate();
	}

	private void saveQuizes() {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("quizes.json", Context.MODE_PRIVATE));
			try {
				Gson gson = new Gson();
				String json = gson.toJson(Quizes.getSingleton());
				writer.write(json);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
