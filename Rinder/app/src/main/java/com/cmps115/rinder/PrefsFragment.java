package com.cmps115.rinder;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;


public class PrefsFragment extends PreferenceFragment {

    public PrefsFragment() {
        // Required empty public constructor
    }

    SharedPreferences prefs;

    ListPreference pricePreference;
    MultiSelectListPreference tagsPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        pricePreference = (ListPreference) findPreference("pricePref");
        tagsPreference = (MultiSelectListPreference) findPreference("tagsPref");


        if (!prefs.getString("pricePref", "").equals("")) {
            pricePreference.setSummary(prefs.getString("pricePref", "$"));
        }

        if (!prefs.getString("tagsPref", "").equals("")) {
            tagsPreference.setSummary(prefs.getString("tagsPref", "American"));
        }


        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }


    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("pricePref")) {
                pricePreference.setSummary(prefs.getString("sound_list", "카톡"));
            }

            if (key.equals("tagsPref")) {
                tagsPreference.setSummary(prefs.getString("keyword_sound_list", "카톡"));
            }


        }
    };

}