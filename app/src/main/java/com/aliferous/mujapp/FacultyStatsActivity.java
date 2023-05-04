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

public class FacultyStatsActivity extends AppCompatActivity {

    ImageView back;
    TextView option,Np,Nap,Nasp;



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
        setContentView(R.layout.activity_faculty_stats);

        back = findViewById(R.id.faculty_stats_back);
        option = findViewById(R.id.faculty_stats_option);

        Np = findViewById(R.id.Np);
        Nap = findViewById(R.id.Nap);
        Nasp = findViewById(R.id.Nasp);



        pdf = findViewById(R.id.faculty_stats_pdf);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_header);
        scaledbmp = Bitmap.createScaledBitmap(bmp,1200,250,false);

        ActivityCompat.requestPermissions(FacultyStatsActivity.this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        i= getIntent().getStringExtra("i");
        reference = FirebaseDatabase.getInstance().getReference("Faculty Stats").child(i);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FacultyStatsActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("Name").getValue().toString();
                option.setText(name);

                name = dataSnapshot.child("Np").getValue().toString();
                Np.setText(name);
                name = dataSnapshot.child("Nap").getValue().toString();
                Nap.setText(name);
                name = dataSnapshot.child("Nasp").getValue().toString();
                Nasp.setText(name);

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
                canvas.drawText("Faculty Stats Information",pagewidth/2,180,titlepaint);

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


                canvas.drawText("Professors",40,500,mypaint);
                canvas.drawText("Assistant Professors",390,500,mypaint);
                canvas.drawText("Associate Professor",820,500,mypaint);



                canvas.drawLine(315,465,315,515,mypaint);
                canvas.drawLine(800,465,800,515,mypaint);


                //2020
                canvas.drawText(Np.getText().toString(),150,600,mypaint);
                canvas.drawText(Nap.getText().toString(),520,600,mypaint);
                canvas.drawText(Nasp.getText().toString(),950,600,mypaint);



                mypaint.setTextAlign(Paint.Align.LEFT);



                mypdfdocument.finishPage(mypage1);

                /*String path = Environment.getDataDirectory().getAbsolutePath()+"/MUJ";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                File file =  new File(dir,"Demo.pdf");*/

                String name = "/"+option.getText().toString()+" Faculty Stats Information.pdf";

                File file= new File (Environment.getExternalStorageDirectory(),name);

                // String myFilePath = Environment.getExternalStorageDirectory().getPath() +"/"+name+".pdf";
                // File file = new File(myFilePath);

                try {
                    mypdfdocument.writeTo(new FileOutputStream(file));
                    openpdf(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String s = file.toString();
                mypdfdocument.close();
                Toast.makeText(FacultyStatsActivity.this, ""+s, Toast.LENGTH_SHORT).show();






            }
        });
    }

    private void openpdf(File file) {

        //File file = new File(Environment.getExternalStorageDirectory() +"/Overview Placement Information.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(file));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        try {
            startActivity(intent);
            Log.e("IR", "No exception");
        } catch (ActivityNotFoundException e) {
            Log.e("IR", "error: " + e.getMessage());
            Toast.makeText(FacultyStatsActivity.this,
                    "No Application Available to View PDF",
                    Toast.LENGTH_SHORT).show();
        }

    }
}