package com.lesnyg.mysocketexam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String html = "";
    private Socket socket;
    private BufferedReader mIn;
    private BufferedWriter mOut;
    private String ip = "172.30.1.47";
    private int port = 5000;
    TextView tv_recevied;

    private AsyncTask<String, Void, Void> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTask = new MySyncTask().execute();
        final EditText et_msg = findViewById(R.id.et_msg);
        tv_recevied = findViewById(R.id.tv_recevied);
        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_msg.getText().toString() != null || !et_msg.getText().toString().equals("")) {
                    PrintWriter out = new PrintWriter(mOut, true);
                    String return_msg = et_msg.getText().toString();
                    out.println(return_msg);
                }
            }
        });
    }

    public class MySyncTask extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(String... strings) {
            if (isCancelled())
                return null;
            query();
            return null;

        }

        protected void onPostExecute(String result) {
        }

        protected void onCancelled() {
            super.onCancelled();
        }

    }


    public void query() {
        try {
            socket = new Socket(ip, port);
            mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            mOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line;
            while (true) {
                line = mIn.readLine();
                html = line;

            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_recevied.setText(html);
            }
        });
    }
}

