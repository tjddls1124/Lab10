package com.example.sungin.lab10;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btn_Save, btn_Enroll, btn_Cancel;
    DatePicker datePicker;
    LinearLayout linear1, linear2;
    EditText editText;
    ListView listView;
    ArrayList<File> fileArr;
    ArrayList<String> fileStrArr;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = getExternalPath();

        init();


        File file = new File(path + "/diary/");
        file.mkdir();


        String msg = "";
        if (file.isDirectory() == false) {
            msg = "디렉터리 생성 오류";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

        fileArr = new ArrayList<>();
        fileStrArr = new ArrayList<>();

        File[] files =
                new File(path + "/diary/").listFiles();

        for (int i = 0; i < files.length; i++) {
            fileArr.add(files[i]);
            fileStrArr.add(files[i].getName());
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileStrArr);
        listView.setAdapter(adapter);




//내부 메모리 읽기
//
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "text.txt", true));
//            bw.write("Hello");
//            bw.close();
//            Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "text.txt"));
//            String readStr ="";
//            String str = null;
//            while((str=br.readLine())!=null) readStr +=str +"\n";
//            br.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//    }
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        /*
        위험권한 부여
         */
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "SDCard 쓰기/읽기 권한 있음", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getApplicationContext(), "권한의 필요성 설명", Toast.LENGTH_SHORT).show();
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }




        /*
           diary폴더에서 파일 읽기
        */
//
//        try {
//            path = getExternalPath();
//            BufferedReader br = new BufferedReader(new FileReader(path + "/diary/" + "memo.txt"));
//            String readStr = "";
//            String str = null;
//            while( ( str=br.readLine() ) !=null ) readStr +=str +"\n";
//            br.close();
//            Toast.makeText(this, readStr.substring(0, readStr.length() - 1),
//                    Toast.LENGTH_SHORT).show();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(this,"File not Found",Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void listInit() {

        fileStrArr.add("");


    }


    public String getExternalPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else
            sdPath = getFilesDir() + "";

        Toast.makeText(getApplicationContext(), sdPath, Toast.LENGTH_SHORT).show();

        return sdPath;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            linear2.setVisibility(View.VISIBLE);
            linear1.setVisibility(View.INVISIBLE);
        }


        if (v.getId() == R.id.btnsave) {
            int year = datePicker.getYear() % 100;
            int month = datePicker.getMonth();
            String s_month = new String ();
            if(month<10) {
                s_month = "0"+month;
            }
            else
                s_month = ""+month;
            int day = datePicker.getDayOfMonth();

            String date = year + "-" + s_month + "-" + day;
        /*
            diary 폴더에 파일 쓰기
        */
            String path = getExternalPath();
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/diary/" + date + ".memo", true));
                if (editText.getText().toString() != null) bw.write(editText.getText().toString());
                bw.close();
                Toast.makeText(this, "저장완료" + getFilesDir(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage() + ":" + getFilesDir(), Toast.LENGTH_SHORT).show();
            }
            linear1.setVisibility(View.VISIBLE);
            linear2.setVisibility(View.INVISIBLE);
        }


    }

    void init() {
        btn_Enroll = (Button) findViewById(R.id.btn1);
        btn_Save = (Button) findViewById(R.id.btnsave);
        btn_Cancel = (Button) findViewById(R.id.btncancel);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        editText = (EditText) findViewById(R.id.EditText);
        listView = (ListView) findViewById(R.id.listview);

    }
}

