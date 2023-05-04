package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentActivity extends AppCompatActivity {

    ImageView back;
    TextView option,Tnsa,Tnsr,Tnw,Tnsp;


    Button pdf;

    DatabaseReference reference;
    String i;

    private Bitmap bmp, scaledbmp;

    private int pagewidth = 1200;
    private int x =0;
    private Date dateobj;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        back = findViewById(R.id.students_back);
        option = findViewById(R.id.students_option);

        Tnsa = findViewById(R.id.student_tnsa);
        Tnsr = findViewById(R.id.student_tnsr);
        Tnw = findViewById(R.id.student_tnw);
        Tnsp = findViewById(R.id.student_tnsp);

        pdf = findViewById(R.id.students_pdf);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,250,false);

        ActivityCompat.requestPermissions(StudentActivity.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Students").child(i);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                option.setText(name);

                name = dataSnapshot.child("Tnsa").getValue().toString();
                Tnsa.setText(name);
                name = dataSnapshot.child("Tnsr").getValue().toString();
                Tnsr.setText(name);
                name = dataSnapshot.child("Tnw").getValue().toString();
                Tnw.setText(name);
                name = dataSnapshot.child("Tnsp").getValue().toString();
                Tnsp.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });

    }

    public void createPDF () {

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dateobj = new Date();

                x++;
                PdfDocument mypdfdocument = new PdfDocument();
                Paint mypaint = new Paint();
                Paint titlepaint = new Paint();

                PdfDocument.PageInfo mypageinfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page mypage1 = mypdfdocument.startPage(mypageinfo1);
                Canvas canvas = mypage1.getCanvas();

                canvas.drawBitmap(scaledbmp,0,0,mypaint);


                titlepaint.setTextAlign(Paint.Align.CENTER);
                titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
                titlepaint.setTextSize(70);
                canvas.drawText("Student Information",pagewidth/2,180,titlepaint);

                mypaint.setTextAlign(Paint.Align.LEFT);
                mypaint.setTextSize(45);
                mypaint.setColor(Color.BLACK);
                canvas.drawText("Faculty: "+ option.getText().toString(),20,300,mypaint);


                mypaint.setTextAlign(Paint.Align.RIGHT);
                mypaint.setTextSize(35f);
                dateFormat = new SimpleDateFormat("dd/MM/yy");
                canvas.drawText("Date: "+ dateFormat.format(dateobj),pagewidth-20,300,mypaint);
                dateFormat = new SimpleDateFormat("HH:mm:ss");
                canvas.drawText("Time: "+ dateFormat.format(dateobj),pagewidth-20,350,mypaint);

                mypaint.setStyle(Paint.Style.STROKE);
                mypaint.setStrokeWidth(2);
                canvas.drawRect(20,450,pagewidth-20,530,mypaint);


                mypaint.setTextAlign(Paint.Align.LEFT);
                mypaint.setStyle(Paint.Style.FILL);
                canvas.drawText("Year",40,500,mypaint);
                canvas.drawText("TNSA",200,500,mypaint);
                canvas.drawText("TNSR",500,500,mypaint);
                canvas.drawText("TNW",800,500,mypaint);
                canvas.drawText("TNSP",1070,500,mypaint);


                canvas.drawLine(170,465,170,515,mypaint);
                canvas.drawLine(360,465,360,515,mypaint);
                canvas.drawLine(680,465,680,515,mypaint);
                canvas.drawLine(970,465,970,515,mypaint);

                //2020
                canvas.drawText("2020",40,600,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,600,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,600,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,600,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,600,mypaint);

                //2019
                canvas.drawText("2019",40,660,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,660,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,660,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,660,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,660,mypaint);

                //2018
                canvas.drawText("2018",40,720,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,720,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,720,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,720,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,720,mypaint);

                //2017
                canvas.drawText("2017",40,780,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,780,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,780,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,780,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,780,mypaint);

                //2016
                canvas.drawText("2016",40,840,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,840,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,840,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,840,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,840,mypaint);

                //2015
                canvas.drawText("2015",40,900,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,900,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,900,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,900,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,900,mypaint);

                //2014
                canvas.drawText("2014",40,960,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,960,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,960,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,960,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,960,mypaint);

                //2014
                canvas.drawText("2013",40,1020,mypaint);
                canvas.drawText(Tnsa.getText().toString(),190,1020,mypaint);
                canvas.drawText(Tnsr.getText().toString(),480,1020,mypaint);
                canvas.drawText(Tnw.getText().toString(),800,1020,mypaint);
                canvas.drawText(Tnsp.getText().toString(),1080,1020,mypaint);


                canvas.drawText("TNSA - Total number of students Admitted",40,1570,mypaint);
                canvas.drawText("TNSR - Total number of students registered",40,1620,mypaint);
                canvas.drawText("TNW - Total number of Withdrawals",40,1670,mypaint);
                canvas.drawText("TNSP - Total number of students passed out(Alumni)",40,1720,mypaint);

                mypaint.setTextAlign(Paint.Align.LEFT);



                mypdfdocument.finishPage(mypage1);

                /*String path = Environment.getDataDirectory().getAbsolutePath()+"/MUJ";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                File file =  new File(dir,"Demo.pdf");*/

                String name = "/"+option.getText().toString()+" Students Information.pdf";

                File file= new File (Environment.getExternalStorageDirectory(),name);

                // String myFilePath = Environment.getExternalStorageDirectory().getPath() +"/"+name+".pdf";
                // File file = new File(myFilePath);

                try {
                    mypdfdocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String s = file.toString();
                mypdfdocument.close();
                Toast.makeText(StudentActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                opetnsadf(name);


            }
        });
    }

    private void opetnsadf(String name) {

       /* File file = new File(Environment.getExternalStorageDirectory() +"/Overview Placement Information.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory() +"/Overview Placement Information.pdf"),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

       // try {
            startActivity(intent);
            Log.e("IR", "No exception");
        } catch (ActivityNotFoundException e) {
            Log.e("IR", "error: " + e.getMessage());
            Toast.makeText(PlacementActivity.this,
                    "No Application Available to View PDF",
                    Toast.LENGTH_SHORT).show();
        }*/

    }
}