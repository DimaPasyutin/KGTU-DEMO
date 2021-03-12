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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kgtu.R;

public class TimetableFragment extends Fragment {

    public static WebView web;
    private TimetableViewModel timetableViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timetableViewModel =
                new ViewModelProvider(this).get(TimetableViewModel.class);
        timetableViewModel.LoadLink();

        View root = inflater.inflate(R.layout.fragment_timetable, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        web = (WebView) view.findViewById(R.id.webView);
        WebSettings ws = web.getSettings();
        ws.setJavaScriptEnabled(true);

        timetableViewModel.getLinkLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String link) {
                web.loadUrl(link);
                web.setWebViewClient(new WebViewClient());
            }
        });
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
