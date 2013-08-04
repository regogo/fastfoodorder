package com.rego.cashman.activities;


import com.rego.cashman.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class CMPreferenceActivity extends PreferenceActivity {
	

	@Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
}
}