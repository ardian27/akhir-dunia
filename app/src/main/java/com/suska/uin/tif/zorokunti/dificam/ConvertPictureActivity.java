package com.suska.uin.tif.zorokunti.dificam;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ConvertPictureActivity extends AppCompatActivity {

    ImageView canvas , canvasKosong;
    Button resize , grayscale , lbp , bpnn,pengguna, histogram;
    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
    String timeNow = s.format(new Date());
    TextView  tv_mean,tv_variance,tv_skewness,tv_kurtosis,tv_entrophy;
    Bitmap newBitmap;
    ProgressBar progressBar;
    public int treshold;
    int [] arrayNew;
    String [] daftar, no;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    int nopengguna;

    double mean,variance,skewness,kurtosis,entrophy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_picture);


        progressBar = (ProgressBar)findViewById(R.id.pb);
        canvasKosong = (ImageView)findViewById(R.id.canvas_kosong);
        resize = (Button)findViewById(R.id.btn_resize);
        tv_mean = (TextView) findViewById(R.id.tv_mean);
        tv_variance = (TextView) findViewById(R.id.tv_variance);
        tv_skewness = (TextView) findViewById(R.id.tv_skewness);
        tv_kurtosis = (TextView) findViewById(R.id.tv_kurtosis);
        tv_entrophy = (TextView) findViewById(R.id.tv_entrophy);
        grayscale = (Button)findViewById(R.id.btn_grayscale);
        lbp = (Button)findViewById(R.id.btn_lbp);
        histogram = (Button)findViewById(R.id.btn_histogram);
        pengguna = (Button)findViewById(R.id.btn_pengguna);
        bpnn = (Button)findViewById(R.id.btn_bpnn);


        //ImageData b = new ImageData();
        Intent x = getIntent();
        String uri = x.getStringExtra("url"); 
        Bitmap bt = BitmapFactory.decodeFile(uri);
        canvasKosong.setImageBitmap(bt);
        setBitmap(bt);
        dbcenter = new DataHelper(this);



        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xx = getIntent();
                String awal = xx.getStringExtra("url");
                Bitmap btm = BitmapFactory.decodeFile(awal);
                Bitmap rp = Bitmap.createScaledBitmap(btm , 1000 ,1000, false);
                canvasKosong.setImageBitmap(rp);

                setBitmap(rp);

                int widthAwal=btm.getWidth();
                int widthResize=rp.getWidth();
                int heightAwal=btm.getHeight();
                int heightResize=rp.getHeight();

                ImageData id = new ImageData();



                int [] pixels = new int [widthResize*heightResize];
                rp.getPixels(pixels,0,widthResize,0,0,widthResize,heightResize);

               // int d = (rp.getPixel(8,8));
                //int r = Color.red(d);
                //int g = Color.green(d);
                //int b = Color.blue(d);
              try {
                    id.saveFileToSDCard("LogDificam.txt", "Rezise Gambar"+awal+" "+timeNow);
                    id.saveFileToSDCard("LogDificam.txt", "Width Awal: "+widthAwal+" Height Awal: "+heightAwal);
                    id.saveFileToSDCard("LogDificam.txt", "Width Resize: "+widthResize+" Height Resize: "+heightResize);
                   // id.saveFileToSDCard("LogDificam.txt", "Nilai Red"+r);
                    //id.saveFileToSDCard("LogDificam.txt", "Nilai Green"+g);
                    //id.saveFileToSDCard("LogDificam.txt", "Nilai blue"+b);
              } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        grayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   progressBar.setProgress(0);
                KonversiGrayscale(newBitmap);
                canvasKosong.setImageBitmap(newBitmap);

                int width = newBitmap.getWidth();
                int height = newBitmap.getHeight();
                ImageData id=new ImageData();

       //         int d = newBitmap.getPixel(9,9);
         //       int r = Color.red(d);
           //     int g = Color.green(d);
             //   int b = Color.blue(d);
               // int dd  = (byte) d;

                try {
                      id.saveFileToSDCard("LogDificam.txt", "Konversi Grayscale"+" "+timeNow);
                   //   id.saveFileToSDCard("LogDificam.txt", "Konversi Grayscale matrix"+" "+timeNow);
                 //     id.saveFileToSDCard("LogDificam.txt", "nilai r,g,b "+" "+r+","+g+","+b);
                   //   id.saveFileToSDCard("LogDificam.txt", "nilai gp "+dd);

                  } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


               // progressBar.setProgress(100);
            }
        });

        lbp.setOnClickListener(new View.OnClickListener() {
          //  @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

               Bitmap newB = KonversiLBP(newBitmap);
               canvasKosong.setImageBitmap(newB);



            }
        });

        histogram.setOnClickListener(new View.OnClickListener() {
          //  @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                int [] matrixResultLBP = arrayNew;

                ImageData id= new ImageData();
                int dd = matrixResultLBP[9];



                AlgoritmaHistogram ah = new AlgoritmaHistogram();

                DecimalFormat df= new DecimalFormat("#.####");

                int ni[] = ah.intensitasKeabuan(matrixResultLBP);
                double hi[] = ah.nilaiHistogram(ni);
                double means  = ah.nilaiMean(hi);
                double variances = ah.nilaiVariance(ah.nilaiHistogram(ni),means);
                double skewnesss = ah.nilaiSkewness(ah.nilaiHistogram(ni),mean,variances);
                double kurtosiss = ah.nilaiKurtosis(ah.nilaiHistogram(ni),mean,variances);
                double entrophys = ah.nilaiEntrophy(ah.nilaiHistogram(ni));


                tv_mean.setText(":"+means);
                tv_variance.setText(":"+variances);
                tv_skewness.setText(":"+skewnesss);
                tv_kurtosis.setText(":"+kurtosiss);
                tv_entrophy.setText(":"+entrophys);
                //int x = (byte) newBitmap.getPixel(800,800);
                //teksbutton.setText("Mean = "+mean+" "+"Variance = "+variance+""+"Skewness = "+skewness+" "+"Kurtosis "+kurtosis+" "+"Entrophy = "+entrophy );
                //teksbutton.setText("Nilai matrix 9 = "+dd);

                setOutput(means,variances,skewnesss,kurtosiss,entrophys);

                try{

                    id.saveFileToSDCardNoSpace("LogDificam.txt","Nilai Mean "+df.format(mean));
                    id.saveFileToSDCardNoSpace("LogDificam.txt","Nilai Variance "+df.format(variance));
                    id.saveFileToSDCardNoSpace("LogDificam.txt","Nilai Skewness "+df.format(skewness));
                    id.saveFileToSDCardNoSpace("LogDificam.txt","Nilai Kurtosis "+df.format(kurtosis));
                    id.saveFileToSDCardNoSpace("LogDificam.txt","Nilai Entrophy "+df.format(entrophy));
                    id.saveFileToSDCardNoSpace("LogDificam.txt","ID Pengguna "+nopengguna);

                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+df.format(mean));
                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+df.format(variance));
                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+df.format(skewness));
                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+df.format(kurtosis));
                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+df.format(entrophy));
                    id.saveFileToSDCardNoSpace("DataPelatihan.txt",""+nopengguna);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        pengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    Intent next = new Intent(getApplicationContext() , SimpanLBPActivity.class);
                    next.putExtra("m" ,mean );
                    next.putExtra("v" ,variance );
                    next.putExtra("s" ,skewness );
                    next.putExtra("k" ,kurtosis );
                    next.putExtra("e" ,entrophy );
                    startActivity(next);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "Mohon Lengkapi Proses Pengolahan Gambar",Toast.LENGTH_SHORT);
                }

            }
        });
    }



    public Bitmap setBitmap(Bitmap newbitmap){

        newBitmap=newbitmap;
        return newBitmap;
    }

    public Bitmap clearBitmap(){

        newBitmap=null;
        return newBitmap;
    }

    public Bitmap KonversiGrayscale(Bitmap bp){

        int w = bp.getWidth();
        int h = bp.getHeight();
        final double rr = 0.299;
        final double gg = 0.587;
        final double bb = 0.114;

        Bitmap newGrayscale = Bitmap.createBitmap(w,h, bp.getConfig());

        for (int x=0; x<w; x++){
            for (int y=0; y<h; y++){

                int pixel = bp.getPixel(x,y);

                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);

                R=G=B= (int)((rr*R)+(gg*G)+(bb*B));

                newGrayscale.setPixel(x,y, Color.rgb(R,G,B));


            }
        }

        setBitmap(newGrayscale);
        return newGrayscale;

    }



    public Bitmap KonversiLBP(Bitmap btGray) {


        Bitmap newLbp=Bitmap.createBitmap(btGray.getWidth(),btGray.getHeight(), Bitmap.Config.ALPHA_8 );
        int width = btGray.getWidth();
        int height = btGray.getHeight();




        int[] pixels = new int[btGray.getWidth()*btGray.getHeight()];
        int[][] pixels2 = new int[btGray.getWidth()][btGray.getHeight()];
        int [] matrixGrayPicture = new int[btGray.getWidth()*btGray.getHeight()];
        int k=0;
        for (int i = 0; i < btGray.getHeight(); i++)
        {
            for (int j = 0; j < btGray.getWidth(); j++)
            {
                pixels2[i][j]=(byte)(btGray.getPixel(j, i));

                //pixels[0]=(byte)(btGray.getPixel(999, 999));
               // pixels[1]=(byte)(btGray.getPixel(1000, 1000));

            }
        }



        ImageData id= new ImageData();
        LbpAlgorithm LBP = new LbpAlgorithm();

        int [][] newMatrix =LBP.resultLBP(pixels2,btGray.getWidth(), btGray.getHeight());

        int newMatrixReborn [] = new int [width*height];
        int kkk = 0;
        for (int g=0; g<width; g++){
            for (int h=0; h<height; h++) {
                newMatrixReborn[kkk] = (byte) newMatrix[g][h];
                pixels[kkk]= newMatrix[g][h];
                kkk++;
            }
        }

        setArray(pixels);

        newLbp.setPixels(newMatrixReborn,0,width,0,0,width,height);


        try {
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setBitmap(newLbp);
        return newLbp;
    }

    public int [] setArray(int [] newArray){
        arrayNew = newArray;
        return arrayNew;
    }


    public void setOutput(double tmean, double tvariance, double tskewness, double tkurtosis, double tentrophy){

        mean=tmean;
        variance=tvariance;
        skewness=tskewness;
        kurtosis=tkurtosis;
        entrophy=tentrophy;

    }

}
