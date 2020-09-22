package com.example.asynchronetasks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);

    }

    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"https://www.hs-schmalkalden.de"});
//        task.execute(new String[]{"https://www.google.com"});
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                try {
                    URL myurl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) myurl.openConnection();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream in = connection.getInputStream();
                    int bytesRead = 0;
                    byte[] buffer = new byte[1024];
                    while ((bytesRead = in.read(buffer)) > 0) {
                        out.write(buffer, 0, bytesRead);
                        out.close();
                        response = new String(out.toByteArray());
                    }
                } catch (IOException e) {
                }
            }
            return response;
        }

        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
}