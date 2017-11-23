package com.hi.raullanconi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        new asyncTask().execute("https://dev-hilab.azurewebsites.net/Hilab/rest/patients/emailFinder");

    }

    public static String getJson(String url){

        InputStream is = null;
        String jsonRetornado = "";

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            is = httpResponse.getEntity().getContent();

            if(is != null) {

                BufferedReader br = new BufferedReader( new InputStreamReader(is));
                String linha;
                while((linha = br.readLine()) != null)
                    jsonRetornado += linha;
                is.close();
            }
            else
                jsonRetornado = "ERRO";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return jsonRetornado;

    }

    private class asyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return getJson(urls[0]);

        }

        @Override
        protected void onPostExecute(String jsonRetornado) {

            textView.setText(jsonRetornado);

        }

    }

}
