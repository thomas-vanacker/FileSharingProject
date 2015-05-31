package com.example.thomas.filesharingproject.ui;

import com.example.thomas.filesharingproject.R;
import com.example.thomas.filesharingproject.app.AppLog;
import com.example.thomas.filesharingproject.constants.Constants;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class AWSPreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference);
		
		setPreferenceChangeListener(Constants.PREF_DIRECTORY_LISTING, onPreferenceChange);
		setPreferenceChangeListener(Constants.PREF_DIRECTORY, onPreferenceChange);
	}
	
	private void setPreferenceChangeListener(String key, Preference.OnPreferenceChangeListener listener){
		Preference preference = findPreference(key);
		
		preference.setOnPreferenceChangeListener(onPreferenceChange);
	}
	
	private Preference.OnPreferenceChangeListener onPreferenceChange = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			AppLog.logString("Preference Change: " + preference.getKey() + ", " + newValue.toString());
			
			return true;
		}
	};
}
