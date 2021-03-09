package com.example.kgtu.ui.timetable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kgtu.R;
import com.example.kgtu.ui.navigator.NavigatorViewModel;

public class TimetableFragment extends Fragment {

    private NavigatorViewModel navigatorViewModel;
    public static WebView web;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        web = (WebView) view.findViewById(R.id.webView);
        WebSettings ws = web.getSettings();
        ws.setJavaScriptEnabled(true);
        web.loadUrl("https://i-klgtu.ru/");
        web.setWebViewClient(new WebViewClient());


    }

    public static boolean goBack() {
        if (web.canGoBack()) {
            web.goBack();
            return  true;
        } else {
            return false;
        }
    }

}
