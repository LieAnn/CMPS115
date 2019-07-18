package com.cmps115.rinder;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.*;

public class PrefsFragment extends PreferenceFragmentCompat {

    public PrefsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);
    }
}
