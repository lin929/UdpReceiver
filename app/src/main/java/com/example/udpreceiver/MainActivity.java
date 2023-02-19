package com.example.udpreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static String mLogTag = "UDPReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button receiveUdpButton = findViewById(R.id.receiveUdpButton);

        receiveUdpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Thread t1 = new Thread() {
                    public void run(){

                        Log.i(mLogTag, "t1 receive thread start ");
                        try
                        {
                            DatagramSocket sock = new DatagramSocket(5060);//5060ポートでUDP受信用ソケット構築
                            byte[] data = new byte[1024];//受信最大バッファ
                            DatagramPacket packet = new DatagramPacket(data, data.length);//受信用パケットを構築
                            Log.i(mLogTag, "start receiving");
                            sock.receive(packet);//受信
                            System.out.println("UDP受信:"+new String(Arrays.copyOf(packet.getData(),packet.getLength()),"UTF-8"));//受信データの表示
                            Log.i(mLogTag, "received");
                            sock.close();//ソケットのクローズ
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                            Log.i(mLogTag, "Exception");
                        }
                    }
                };
                t1.start();
            }
        });


    }
}
