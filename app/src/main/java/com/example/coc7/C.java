package com.example.coc7;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class C extends AppCompatActivity {

    Button btn_dice;
    Button btn_C;
    EditText ip;
    EditText editText;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        ip = (EditText) findViewById(R.id.ip);
        editText = (EditText) findViewById(R.id.editText);
        text = (TextView) findViewById(R.id.text);


        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                connect();
            }
        });

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                send();
            }
        });

        btn_C=(Button) findViewById(R.id.btn_C);

        btn_C.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
    //......................................................................

        Socket socket =null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        public void connect(){


            System.out.println(ip.getText().toString());
                @SuppressLint("StaticFieldLeak") AsyncTask<Void,String,Void> read = new AsyncTask<Void, String, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {

                            socket = new Socket(ip.getText().toString(), 1234);
                            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            publishProgress("@success");
                        }catch (UnknownHostException e1){
                            Toast.makeText(C.this,"無法建立連接",Toast.LENGTH_SHORT).show();
                        }catch (IOException e1){
                            Toast.makeText(C.this,"無法建立連接",Toast.LENGTH_SHORT).show();
                        }
                        try { String line;
                            while ((line = reader.readLine())!=null) {
                                publishProgress(line);
                            }
                        }catch (IOException e){
                                e.printStackTrace();
                        }
                        return null;
                    }
                    protected void onProgressUpdate(String... values) {
                        if(values[0].equals("@success")) {
                            Toast.makeText(C.this, "連接成功", Toast.LENGTH_SHORT).show();
                        }
                        text.append("對方"+values[0]+"\n");
                        super.onProgressUpdate(values);
                    }
                };
                read.execute();


        }

        public void send() {
            try {
                text.append("我說:"+editText.getText().toString()+"\n");
                writer.write(editText.getText().toString()+"\n");
                writer.flush();
                editText.setText(" ");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



       /* btn_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_Show.setText((tv_Show.getText().toString() + et_Name.getText().toString() + "\r\n"));
                et_Name.setText("");
            }
        });

        btn_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random ran = new Random();
                int num = (ran.nextInt(100) + 1);
                tv_Show.setText((tv_Show.getText().toString() + "擲出的骰子為: \r\n" + num).toString() + "\r\n");

            }
        });*/









}