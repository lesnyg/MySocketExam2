package com.lesnyg.mysocketexam2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private static Socket mSocket;
    private BufferedReader mIn;
    private PrintWriter mOut;
    private String ip = "192.168.0.12";
    private int port = 8000;
    TextView tv_recevied;
    EditText et_msg;
    String msg;
//    String return_msg;
    Handler handler;
//    private SenderThread mSenderThread;
//    private ReceiverThread mReceiverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_msg = findViewById(R.id.et_msg);
        tv_recevied = findViewById(R.id.tv_recevied);

        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = et_msg.getText().toString();
                RequestThread thread = new RequestThread();
                thread.start();

            }
        });
        handler = new Handler();
    }

    class RequestThread extends Thread{
        public void run(){
            request();
        }
    }

    private void request() {

        try {

            mSocket = new Socket(ip, port);
            appendText("client started : "+ ip+":"+port);
//            mSenderThread = new SenderThread(mSocket, msg);
//            mReceiverThread = new ReceiverThread(mSocket);
//            mSenderThread.start();
//            mReceiverThread.start();
            ObjectOutputStream outputStream = new ObjectOutputStream(mSocket.getOutputStream());
            outputStream.writeUTF("Hello");
            outputStream.flush();

            appendText("Hello sent");

            ObjectInputStream inputStream = new ObjectInputStream(mSocket.getInputStream());
            String inStr = inputStream.readUTF();

            appendText("inStr from server : " + inStr);
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void appendText(String appmag){
        final String inMsg = appmag;
        handler.post(new Runnable() {
            @Override
            public void run() {
                tv_recevied.append(inMsg+'\n');
            }
        });

    }

}
//
///**
// * 메시지를 수신하는 스레드 입니다.
// */
//class ReceiverThread extends Thread {
//    private static final String TAG = "ReceiverThread";
//    Socket socket;
//
//    public ReceiverThread(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            // 서버로부터 인풋스트림이라는 통로를 통해 들어오는 문자열을 잠시 보관하는 객체입니다.
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            while (true) {
//                String str = reader.readLine();
//                if (str == null) {
//                    break;
//                }
//                Log.d(TAG, reader.readLine());
//
//            }
//        } catch (Exception e) {
//            Log.d(TAG, e.getMessage());
//        }
//
//    }
//
//}
//
///**
// * 메시지를 발신하는 스레드 입니다.
// */
//class SenderThread extends Thread {
//    Socket socket;
//
//    public SenderThread(Socket socket, String message) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//
//        try {
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//            PrintWriter writer = new PrintWriter(socket.getOutputStream());
//
//            while (true) {
//                String str = reader.readLine();
//
//                if (str.equals("bye")) {
//                    break;
//                }
//
//                // 입력한 문자열을 서버로 발신합니다.
//                writer.println(str);
//                writer.flush();
//            }
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//}
//
