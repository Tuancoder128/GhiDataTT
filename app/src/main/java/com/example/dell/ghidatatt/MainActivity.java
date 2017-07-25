package com.example.dell.ghidatatt;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtNhapDaTa;
    Button btnGhiData;
    FileOutputStream ghidata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNhapDaTa = (EditText) findViewById(R.id.edt_data);
        btnGhiData = (Button) findViewById(R.id.btn_GhiData);
        btnGhiData.setOnClickListener(this);
    }

    public void GhiDaTa(String dulieu) {
        try {
            ghidata = openFileOutput("FileGhidata.txt", Context.MODE_PRIVATE);
            ghidata.write(dulieu.getBytes());
            ghidata.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "ghi data thanh cong", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        String savedata;
        savedata = edtNhapDaTa.getText().toString();
        Log.d("/duong dan file", getFilesDir().getAbsolutePath());
        GhiDaTa(savedata);
    }

    public void DocData(View v) {
        String docdata;
        try {
            String data;
            FileInputStream docfile = openFileInput("FileGhidata.txt");
            InputStreamReader reader = new InputStreamReader(docfile);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((data = bufferedReader.readLine()) != null) {
                stringBuffer.append(data);
            }
            bufferedReader.close();
            reader.close();
            docfile.close();
            Toast.makeText(MainActivity.this, stringBuffer, Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void LuuFileCache(View v) {
        File file = new File(getCacheDir().getPath(), "WriteCacheTT.txt");
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(edtNhapDaTa.getText().toString());
            Log.d("/duong dan file cache ", getFilesDir().getAbsolutePath());
            Toast.makeText(MainActivity.this, "luu Cache thanh cong", Toast.LENGTH_SHORT).show();

            bufferedWriter.close();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void DocFileCache(View v) {
        String data;
        File file = new File(getCacheDir().getPath(), "WriteCacheTT.txt");

        FileReader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        //StringBuffer = Stringbuilder
        StringBuffer stringBuffer = new StringBuffer();
        try {
            while ((data = bufferedReader.readLine()) != null) {
                stringBuffer.append(data);
                Toast.makeText(MainActivity.this, stringBuffer, Toast.LENGTH_SHORT).show();
                bufferedReader.close();
                reader.close();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean KiemTraTheNhoDaGan() {
        //tạo ra biên String để tìm tới kho thẻ nhớ
        String kiemtrathenho = Environment.getExternalStorageState();
        //gán biến String đó cho trạng thái đã gắn thẻ nhớ hay chưa
        if (Environment.MEDIA_MOUNTED.equals(kiemtrathenho)) {
            return true;
        }
        return false;
    }

    public boolean KiemTraTheNhoChiDoc() {
        String checkread = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(checkread)) {
            return true;
        }
        return false;
    }

    public void GhiFileVaoTheNho(View v) {
        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(MainActivity.this, "thẻ nhớ chưa được gắn", Toast.LENGTH_SHORT).show();
        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(MainActivity.this, "thẻ nhớ của bạn chỉ đọc", Toast.LENGTH_SHORT).show();
            } else {
                // nhờ Environment để tạo luồng mở tới file đó
                File folderthenho = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho");
                //mkdir() để kiểm tra xem file cần tạo để ghi đã được tạo chưa , nếu chưa thì hệ thống tự tạo
                folderthenho.mkdir();
                //tạo ra folder trong file Chính 
                File dgdanfile = new File(folderthenho, "Filethenho.txt");
                try {
                    FileOutputStream ghifile = new FileOutputStream(dgdanfile);
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(ghifile);
                    bufferedOutputStream.write(edtNhapDaTa.getText().toString().getBytes());
                    Toast.makeText(MainActivity.this, "thẻ nhớ của bạn đã được tạo để ghi file", Toast.LENGTH_SHORT).show();

                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    ghifile.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public void DocFileVaoTheNho(View v) {

        if (KiemTraTheNhoDaGan() == false) {
            Toast.makeText(MainActivity.this, "thẻ nhớ chưa được gắn", Toast.LENGTH_SHORT).show();
        } else {
            if (KiemTraTheNhoChiDoc() == true) {
                Toast.makeText(MainActivity.this, "thẻ nhớ của bạn chỉ đọc", Toast.LENGTH_SHORT).show();
            } else {
                File filemuondoc = new File(Environment.getExternalStorageDirectory().getPath() + "/TheNho", "Filethenho.txt");

                try {
                    FileInputStream docfile = new FileInputStream(filemuondoc);
                    InputStreamReader reader = new InputStreamReader(docfile);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    StringBuilder builder = new StringBuilder();
                    String dulieu = " ";
                    while ((dulieu = bufferedReader.readLine()) != null) {
                        builder.append(dulieu);
                    }
                    ;
                    bufferedReader.close();
                    reader.close();
                    docfile.close();
                    Toast.makeText(MainActivity.this, builder, Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}






