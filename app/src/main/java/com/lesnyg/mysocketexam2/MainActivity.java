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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
    private static Socket socket;
    private static ServerSocket serverSocket;
    private BufferedReader mIn;
    private PrintWriter mOut;
    private String ip = "192.168.0.12";
    private int port = 8000;
    TextView tv_recevied;
    EditText et_msg;
    String msg;
    String return_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_msg = findViewById(R.id.et_msg);
        tv_recevied = findViewById(R.id.tv_recevied);
    }

    public void send_text(View v) {
        msg = et_msg.getText().toString();
        NetworkTask networkTask = new NetworkTask();
        networkTask.execute();

        Toast.makeText(this, "Data sent", Toast.LENGTH_SHORT).show();
    }


    public class NetworkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket(ip, port);

                    mOut = new PrintWriter(socket.getOutputStream());
                    mOut.write(msg);
                    mOut.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mOut.close();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}

