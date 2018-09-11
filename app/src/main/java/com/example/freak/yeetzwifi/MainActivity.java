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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
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
    Boolean profileFlag=true;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = (EditText) findViewById(R.id.texty);
        final ImageView up = findViewById(R.id.button);
        final ImageView down = findViewById(R.id.button2);
        final ImageView b3 = findViewById(R.id.set);
        final ImageView left = findViewById(R.id.button4);
        final ImageView right= findViewById(R.id.button5);
        final ImageView on = findViewById(R.id.button6);
        t1=findViewById(R.id.textView);
        final ImageView profile=findViewById(R.id.profile);
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
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileFlag==true){
                sendat("p2");
                profileFlag=false;
                profile.setBackgroundResource(R.drawable.reverse);}
                else {
                sendat("p1");
                profileFlag=true;
                profile.setBackgroundResource(R.drawable.usual);
                }
                }
        });
        up.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    //up.setImageResource(R.drawable.uparrowpressed);
                    sendat("forward");
                   }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    //up.setImageResource(R.drawable.uparrow);
                    sendat("stop");
                   }
                return false;
            }
        });
        down.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    //down.setImageResource(R.drawable.downarrowpressed);
                    sendat("back");
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                { //down.setImageResource(R.drawable.downarrow);
                    sendat("stop");
                 }
                return false;
            }
        });
        left.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                {   //left.setImageResource(R.drawable.leftarrowpressed);
                    sendat("left");}
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                {   //left.setImageResource(R.drawable.leftarrow);
                    sendat("stop");}
                return false;
            }
        });
        right.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
                { //right.setImageResource(R.drawable.rightarrowpressed);
                    sendat("right");}
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP)
                { //right.setImageResource(R.drawable.rightarrow);
                    sendat("stop");}
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
