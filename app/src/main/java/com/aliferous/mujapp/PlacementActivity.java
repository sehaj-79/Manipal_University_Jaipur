package com.aliferous.mujapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class PlacementActivity extends AppCompatActivity {

    ImageView back;
    TextView option,Hp,Ap,Lp,F500,Tnsp,Psp,Tncv;
    ImageView stats;

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
        setContentView(R.layout.activity_placement);

        back = findViewById(R.id.placement_back);
        option = findViewById(R.id.placement_option);
        stats = findViewById(R.id.placement_stats);
        Hp = findViewById(R.id.Hp);
        Ap = findViewById(R.id.Ap);
        F500 = findViewById(R.id.F500);
        Tnsp = findViewById(R.id.Tnsp);
        Psp = findViewById(R.id.Psp);
        Lp = findViewById(R.id.Lp);
        Tncv = findViewById(R.id.Tncv);
        pdf = findViewById(R.id.placement_pdf);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,250,false);

        ActivityCompat.requestPermissions(PlacementActivity.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Placements").child(i);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlacementActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                option.setText(name);
                name = dataSnapshot.child("Stats").getValue().toString();
                Glide.with(PlacementActivity.this).load(name).into(stats);
                name = dataSnapshot.child("Hp").getValue().toString();
                Hp.setText(name);
                name = dataSnapshot.child("Lp").getValue().toString();
                Lp.setText(name);
                name = dataSnapshot.child("Ap").getValue().toString();
                Ap.setText(name);
                name = dataSnapshot.child("F500").getValue().toString();
                F500.setText(name);
                name = dataSnapshot.child("Tnsp").getValue().toString();
                Tnsp.setText(name);
                name = dataSnapshot.child("Psp").getValue().toString();
                Psp.setText(name);
                name = dataSnapshot.child("Tncv").getValue().toString();
                Tncv.setText(name);
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
                canvas.drawText("Placement Information",pagewidth/2,180,titlepaint);

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
                canvas.drawText("HP",200,500,mypaint);
                canvas.drawText("LP",350,500,mypaint);
                canvas.drawText("AP",500,500,mypaint);
                canvas.drawText("TNSP",640,500,mypaint);
                canvas.drawText("PSP",800,500,mypaint);
                canvas.drawText("F500",940,500,mypaint);
                canvas.drawText("TNCV",1070,500,mypaint);


                canvas.drawLine(170,465,170,515,mypaint);
                canvas.drawLine(310,465,310,515,mypaint);
                canvas.drawLine(450,465,450,515,mypaint);
                canvas.drawLine(600,465,600,515,mypaint);
                canvas.drawLine(760,465,760,515,mypaint);
                canvas.drawLine(900,465,900,515,mypaint);
                canvas.drawLine(1050,465,1050,515,mypaint);

                //2020
                canvas.drawText("2020",40,600,mypaint);
                canvas.drawText(Hp.getText().toString(),190,600,mypaint);
                canvas.drawText(Lp.getText().toString(),340,600,mypaint);
                canvas.drawText(Ap.getText().toString(),480,600,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,600,mypaint);
                canvas.drawText(Psp.getText().toString(),800,600,mypaint);
                canvas.drawText(F500.getText().toString(),950,600,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,600,mypaint);

                //2019
                canvas.drawText("2019",40,660,mypaint);
                canvas.drawText(Hp.getText().toString(),190,660,mypaint);
                canvas.drawText(Lp.getText().toString(),340,660,mypaint);
                canvas.drawText(Ap.getText().toString(),480,660,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,660,mypaint);
                canvas.drawText(Psp.getText().toString(),800,660,mypaint);
                canvas.drawText(F500.getText().toString(),950,660,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,660,mypaint);

                //2018
                canvas.drawText("2018",40,720,mypaint);
                canvas.drawText(Hp.getText().toString(),190,720,mypaint);
                canvas.drawText(Lp.getText().toString(),340,720,mypaint);
                canvas.drawText(Ap.getText().toString(),480,720,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,720,mypaint);
                canvas.drawText(Psp.getText().toString(),800,720,mypaint);
                canvas.drawText(F500.getText().toString(),950,720,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,720,mypaint);

                //2017
                canvas.drawText("2017",40,780,mypaint);
                canvas.drawText(Hp.getText().toString(),190,780,mypaint);
                canvas.drawText(Lp.getText().toString(),340,780,mypaint);
                canvas.drawText(Ap.getText().toString(),480,780,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,780,mypaint);
                canvas.drawText(Psp.getText().toString(),800,780,mypaint);
                canvas.drawText(F500.getText().toString(),950,780,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,780,mypaint);

                //2016
                canvas.drawText("2016",40,840,mypaint);
                canvas.drawText(Hp.getText().toString(),190,840,mypaint);
                canvas.drawText(Lp.getText().toString(),340,840,mypaint);
                canvas.drawText(Ap.getText().toString(),480,840,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,840,mypaint);
                canvas.drawText(Psp.getText().toString(),800,840,mypaint);
                canvas.drawText(F500.getText().toString(),950,840,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,840,mypaint);

                //2015
                canvas.drawText("2015",40,900,mypaint);
                canvas.drawText(Hp.getText().toString(),190,900,mypaint);
                canvas.drawText(Lp.getText().toString(),340,900,mypaint);
                canvas.drawText(Ap.getText().toString(),480,900,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,900,mypaint);
                canvas.drawText(Psp.getText().toString(),800,900,mypaint);
                canvas.drawText(F500.getText().toString(),950,900,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,900,mypaint);

                //2014
                canvas.drawText("2014",40,960,mypaint);
                canvas.drawText(Hp.getText().toString(),190,960,mypaint);
                canvas.drawText(Lp.getText().toString(),340,960,mypaint);
                canvas.drawText(Ap.getText().toString(),480,960,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,960,mypaint);
                canvas.drawText(Psp.getText().toString(),800,960,mypaint);
                canvas.drawText(F500.getText().toString(),950,960,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,960,mypaint);

                //2014
                canvas.drawText("2013",40,1020,mypaint);
                canvas.drawText(Hp.getText().toString(),190,1020,mypaint);
                canvas.drawText(Lp.getText().toString(),340,1020,mypaint);
                canvas.drawText(Ap.getText().toString(),480,1020,mypaint);
                canvas.drawText(Tnsp.getText().toString(),640,1020,mypaint);
                canvas.drawText(Psp.getText().toString(),800,1020,mypaint);
                canvas.drawText(F500.getText().toString(),950,1020,mypaint);
                canvas.drawText(Tncv.getText().toString(),1080,1020,mypaint);

                canvas.drawText("HP - Highest Package",40,1420,mypaint);
                canvas.drawText("LP - Lowest Package",40,1470,mypaint);
                canvas.drawText("AP - Average Package",40,1520,mypaint);
                canvas.drawText("TNSP - Total Number of Students Placed",40,1570,mypaint);
                canvas.drawText("PSP - Percentage of Students Placed",40,1620,mypaint);
                canvas.drawText("F500 - Fortune 500",40,1670,mypaint);
                canvas.drawText("TNCV - Total Number of Companies Visited",40,1720,mypaint);

                mypaint.setTextAlign(Paint.Align.LEFT);



                mypdfdocument.finishPage(mypage1);

                /*String path = Environment.getDataDirectory().getAbsolutePath()+"/MUJ";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                File file =  new File(dir,"Demo.pdf");*/

                String name = "/"+option.getText().toString()+" Placement Information.pdf";

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
                Toast.makeText(PlacementActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                openpdf(name);


            }
        });
    }

    private void openpdf(String name) {

       /* File file = new File(Environment.getExternalStorageDirectory() +"/Overview Placement Information.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory() +"/Overview Placement Information.pdf"),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

       *//* try {
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