package com.example.thomas.filesharingproject.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.thomas.filesharingproject.quiz.Quizes;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		try {
			Gson gson = new Gson();
			InputStream inStream = openFileInput("quizes.json");
			Quizes quizes = new Quizes();
			if (inStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inStream);
				quizes = gson.fromJson(inputStreamReader, Quizes.class);
			}
			Quizes.setSingleton(quizes);
			saveQuizes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
				writer.write(gson.toJson(Quizes.getSingleton()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
