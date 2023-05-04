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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class ResearchActivity extends AppCompatActivity {

    ImageView back;
    TextView option,Tnp,Tncp,Tnsp,Tnssp,Tnclp,Taf,Tni;

    Button pdf;

    DatabaseReference reference;
    String i;

    private Bitmap bmp, scaledbmp;

    private int pagewidth = 1200;
    private int x =0;
    private Date dateobj;
    private DateFormat dateFormat;

    Spinner year_spinner;

    String j = "2020";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        back = findViewById(R.id.research_back);
        option = findViewById(R.id.research_option);
        Tnp = findViewById(R.id.Tnp);
        Tncp = findViewById(R.id.Tncp);
        Tnsp = findViewById(R.id.Tnsp);
        Tnssp = findViewById(R.id.Tnssp);
        Tnclp = findViewById(R.id.Tnclp);
        Taf = findViewById(R.id.Taf);
        Tni = findViewById(R.id.Tni);
        pdf = findViewById(R.id.research_pdf);
        year_spinner = findViewById(R.id.research_year_spinner);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,250,false);

        ActivityCompat.requestPermissions(ResearchActivity.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String year = adapterView.getSelectedItem().toString();

                if (year.equals("2020")){
                    j="2020";
                    readUsers(j);
                }
                else if (year.equals("2019")){
                    j="2019";
                    readUsers(j);
                }
                else if (year.equals("2018")){
                    j="2018";
                    readUsers(j);
                }
                else if (year.equals("2017")){
                    j="2017";
                    readUsers(j);
                }
                else if (year.equals("2016")){
                    j="2016";
                    readUsers(j);
                }
                else if (year.equals("2015")){
                    j="2015";
                    readUsers(j);
                }
                else if (year.equals("2014")){
                    j="2014";
                    readUsers(j);
                }
                else if (year.equals("2013")){
                    j="2013";
                    readUsers(j);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResearchActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });

    }

    private void readUsers(String j) {

        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Research").child(j).child(i);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                option.setText(name);

                name = dataSnapshot.child("Tnp").getValue().toString();
                Tnp.setText(name);
                name = dataSnapshot.child("Tncp").getValue().toString();
                Tncp.setText(name);
                name = dataSnapshot.child("Tnsp").getValue().toString();
                Tnsp.setText(name);
                name = dataSnapshot.child("Taf").getValue().toString();
                Taf.setText(name);
                name = dataSnapshot.child("Tnssp").getValue().toString();
                Tnssp.setText(name);
                name = dataSnapshot.child("Tnclp").getValue().toString();
                Tnclp.setText(name);
                name = dataSnapshot.child("Tni").getValue().toString();
                Tni.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                canvas.drawText("Research Information",pagewidth/2,180,titlepaint);

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
                canvas.drawText("TNP",200,500,mypaint);
                canvas.drawText("TNCP",340,500,mypaint);
                canvas.drawText("TNSP",480,500,mypaint);
                canvas.drawText("TNSSP",640,500,mypaint);
                canvas.drawText("TNCLP",785,500,mypaint);
                canvas.drawText("TAF",940,500,mypaint);
                canvas.drawText("TNI",1070,500,mypaint);


                canvas.drawLine(170,465,170,515,mypaint);
                canvas.drawLine(310,465,310,515,mypaint);
                canvas.drawLine(450,465,450,515,mypaint);
                canvas.drawLine(600,465,600,515,mypaint);
                canvas.drawLine(760,465,760,515,mypaint);
                canvas.drawLine(900,465,900,515,mypaint);
                canvas.drawLine(1050,465,1050,515,mypaint);

                //2020
                canvas.drawText("2020",40,600,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,600,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,600,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,600,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,600,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,600,mypaint);
                canvas.drawText(Taf.getText().toString(),950,600,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,600,mypaint);

                //2019
                canvas.drawText("2019",40,660,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,660,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,660,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,660,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,660,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,660,mypaint);
                canvas.drawText(Taf.getText().toString(),950,660,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,660,mypaint);

                //2018
                canvas.drawText("2018",40,720,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,720,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,720,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,720,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,720,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,720,mypaint);
                canvas.drawText(Taf.getText().toString(),950,720,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,720,mypaint);

                //2017
                canvas.drawText("2017",40,780,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,780,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,780,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,780,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,780,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,780,mypaint);
                canvas.drawText(Taf.getText().toString(),950,780,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,780,mypaint);

                //2016
                canvas.drawText("2016",40,840,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,840,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,840,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,840,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,840,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,840,mypaint);
                canvas.drawText(Taf.getText().toString(),950,840,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,840,mypaint);

                //2015
                canvas.drawText("2015",40,900,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,900,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,900,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,900,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,900,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,900,mypaint);
                canvas.drawText(Taf.getText().toString(),950,900,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,900,mypaint);

                //2014
                canvas.drawText("2014",40,960,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,960,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,960,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,960,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,960,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,960,mypaint);
                canvas.drawText(Taf.getText().toString(),950,960,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,960,mypaint);

                //2014
                canvas.drawText("2013",40,1020,mypaint);
                canvas.drawText(Tnp.getText().toString(),190,1020,mypaint);
                canvas.drawText(Tncp.getText().toString(),340,1020,mypaint);
                canvas.drawText(Tnsp.getText().toString(),480,1020,mypaint);
                canvas.drawText(Tnssp.getText().toString(),640,1020,mypaint);
                canvas.drawText(Tnclp.getText().toString(),800,1020,mypaint);
                canvas.drawText(Taf.getText().toString(),950,1020,mypaint);
                canvas.drawText(Tni.getText().toString(),1080,1020,mypaint);

                canvas.drawText("TNP - Total number of Publications",40,1420,mypaint);
                canvas.drawText("TNCP - Total number of conference publication",40,1470,mypaint);
                canvas.drawText("TNSP - Total number of scopus publication",40,1520,mypaint);
                canvas.drawText("TNSSP - Total number of SCI/SCIE publication",40,1570,mypaint);
                canvas.drawText("TNCLP - Total number of UGC care listed journal publication",40,1620,mypaint);
                canvas.drawText("TAF - Total amount of funding (in Lacs)",40,1670,mypaint);
                canvas.drawText("TNI - Total number of IPRs",40,1720,mypaint);

                mypaint.setTextAlign(Paint.Align.LEFT);



                mypdfdocument.finishPage(mypage1);

                /*String path = Environment.getDataDirectory().getAbsolutePath()+"/MUJ";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                File file =  new File(dir,"Demo.pdf");*/

                String name = "/"+option.getText().toString()+" Research Information.pdf";

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
                Toast.makeText(ResearchActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                openpdf(name);


            }
        });
    }

    private void openpdf(String name) {

       /* File file = new File(Environment.getExternalStorageDirectory() +"/Overview Research Information.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory() +"/Overview Research Information.pdf"),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

       // try {
            startActivity(intent);
            Log.e("IR", "No exception");
        } catch (ActivityNotFoundException e) {
            Log.e("IR", "error: " + e.getMessage());
            Toast.makeText(ResearchActivity.this,
                    "No Application Available to View PDF",
                    Toast.LENGTH_SHORT).show();
        }*/

    }
}