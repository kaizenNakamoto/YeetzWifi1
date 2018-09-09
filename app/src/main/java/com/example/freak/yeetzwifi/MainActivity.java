package com.example.freak.yeetzwifi;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity {

    private EditText ipAddress;
    final Context context = this;
    String serverAdress2 = "192.168.43.94:80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = (EditText) findViewById(R.id.texty);
        Button up = findViewById(R.id.button);
        Button down = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.set);
        Button left = findViewById(R.id.button4);
        Button right= findViewById(R.id.button5);
        Button on = findViewById(R.id.button6);
        b3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(ipAddress.getText().toString().isEmpty()||ipAddress.getText().toString().length()<13)
                    Toast.makeText(MainActivity.this, "Wrong ip", Toast.LENGTH_SHORT).show();
                else {
                    serverAdress2 = ipAddress.getText().toString();
                    Toast.makeText(MainActivity.this,serverAdress2+" set",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        up.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                    sendat("forward");
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                    sendat("stop");
                return false;
            }
        });
        down.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                    sendat("back");
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                    sendat("stop");
                return false;
            }
        });
        left.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                    sendat("left");
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                    sendat("stop");
                return false;
            }
        });
        right.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                    sendat("right");
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                    sendat("stop");
                return false;
            }
        });
        on.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                    sendat("attack");
                return false;
            }
        });
    }

    private class HttpRequestTask extends AsyncTask<String, Void, String> {

        private String serverAdress;
        private String serverResponse = "";
        private AlertDialog dialog;

        public HttpRequestTask(String serverAdress) {
            this.serverAdress = serverAdress;
        }

        @Override
        protected String doInBackground(String... params) {
            String val = params[0];
            final String url = "http://" + serverAdress + "/" + val;

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet();
                getRequest.setURI(new URI(url));
                HttpResponse response = client.execute(getRequest);

                InputStream inputStream = null;
                inputStream = response.getEntity().getContent();
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream));

                serverResponse = bufferedReader.readLine();
                inputStream.close();

            } catch (Exception e) {
              e.printStackTrace();}
            return serverResponse;
        }
    }
    public void sendat(String dat){
        HttpRequestTask requestTask = new HttpRequestTask(serverAdress2);
        requestTask.execute(dat);
    }
}
