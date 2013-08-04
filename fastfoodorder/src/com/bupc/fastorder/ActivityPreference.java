package com.bupc.fastorder;



import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.bupc.fastfoodorder.R;

public class ActivityPreference extends PreferenceActivity {

	@Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
}
	
}
