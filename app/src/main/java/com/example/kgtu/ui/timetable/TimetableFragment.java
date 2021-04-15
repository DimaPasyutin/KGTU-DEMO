package com.example.kgtu.ui.timetable;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kgtu.R;

public class TimetableFragment extends Fragment {

    public static WebView web;
    public static TimetableViewModel timetableViewModel;
    private Button saveLink;
    private String savedLink;
    private String savedLinkFromState;
    private String linkIsNow;
    public static final String SAVED_LINK_KEY = "SAVES_URL";
    public static final String LAST_LINK_KEY = "LAST_URL";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        timetableViewModel =
                new ViewModelProvider(this).get(TimetableViewModel.class);

        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveLink = view.findViewById(R.id.saveLink);
        web = (WebView) view.findViewById(R.id.webView);
        WebSettings ws = web.getSettings();
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        if (!getLinkFromPreferences(LAST_LINK_KEY).equals("empty")) {
            web.loadUrl(getLinkFromPreferences(LAST_LINK_KEY));
            web.setWebViewClient(new WebViewClient());
        } else {
            timetableViewModel.LoadLink();
        }

        saveLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedLink = web.getUrl();
                saveLinkToPreferences(web.getUrl(), SAVED_LINK_KEY);
                Toast.makeText(getContext(), "Страница сохранена", Toast.LENGTH_SHORT).show();
            }
        });

        timetableViewModel.getLinkLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String link) {
                web.loadUrl(link);
                web.setWebViewClient(new WebViewClient());
                linkIsNow = link;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.set_kgtu) {
            if(!web.getUrl().equals(linkIsNow)) {
                timetableViewModel.LoadLink();
            }
            return true;
        }
        if (id == R.id.set_оther) {
            if (!getLinkFromPreferences(SAVED_LINK_KEY).equals("empty")) {
                web.loadUrl(getLinkFromPreferences(SAVED_LINK_KEY));
                web.setWebViewClient(new WebViewClient());
            } else if (savedLink != null && savedLink.length() > 0) {
                web.loadUrl(savedLink);
                web.setWebViewClient(new WebViewClient());
            } else {
                Toast.makeText(getContext(), "Необходимо сначала запомнить страницу", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveLinkToPreferences(String str, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, str);
        editor.apply();
    }

    private String getLinkFromPreferences(String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString(key, "empty");
    }

    public static boolean goBack() {
        if (web.canGoBack()) {
            web.goBack();
            return  true;
        } else {
            timetableViewModel.LoadLink();
            return true;
        }
    }

    @Override
    public void onStop() {
        saveLinkToPreferences(web.getUrl(), LAST_LINK_KEY);
        super.onStop();
    }
}
