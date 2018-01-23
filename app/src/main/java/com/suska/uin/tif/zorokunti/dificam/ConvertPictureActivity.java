package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ConvertPictureActivity extends AppCompatActivity {

    ImageView canvas , canvasKosong;
    Button resize , grayscale , lbp , bpnn;
    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
    String timeNow = s.format(new Date());
   // TextView tt;
    Bitmap newBitmap;
    //int [][] newMatrix = new int[newBitmap.getWidth()][newBitmap.getHeight()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_picture);


        canvas = (ImageView)findViewById(R.id.canvas);
        canvasKosong = (ImageView)findViewById(R.id.canvas_kosong);
        resize = (Button)findViewById(R.id.btn_resize);
       // tt = (TextView) findViewById(R.id.txt);
        grayscale = (Button)findViewById(R.id.btn_grayscale);
        lbp = (Button)findViewById(R.id.btn_lbp);
        bpnn = (Button)findViewById(R.id.btn_bpnn);

        //ImageData b = new ImageData();
        Intent x = getIntent();
        String uri = x.getStringExtra("url");
        Bitmap bt = BitmapFactory.decodeFile(uri);
        canvas.setImageBitmap(bt);
        setBitmap(bt);

        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent xx = getIntent();
                String awal = xx.getStringExtra("url");
                Bitmap btm = BitmapFactory.decodeFile(awal);
                Bitmap rp = Bitmap.createScaledBitmap(btm , 100 , 100, false);
                canvasKosong.setImageBitmap(rp);

                setBitmap(rp);

                int widthAwal=btm.getWidth();
                int widthResize=rp.getWidth();
                int heightAwal=btm.getHeight();
                int heightResize=rp.getHeight();

                ImageData id = new ImageData();

                int [] pixels = new int [widthResize*heightResize];
                rp.getPixels(pixels,0,widthResize,0,0,widthResize,heightResize);
                //id.setArrayImage(pixels);


                try {
                    id.saveFileToSDCard("LogDificam.txt", "Rezise Gambar"+awal+" "+timeNow);
                    id.saveFileToSDCard("LogDificam.txt", "Width Awal: "+widthAwal+" Height Awal: "+heightAwal);
                    id.saveFileToSDCard("LogDificam.txt", "Width Resize: "+widthResize+" Height Resize: "+heightResize);
            } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        grayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KonversiGrayscale(newBitmap);
                canvasKosong.setImageBitmap(newBitmap);

                int width = newBitmap.getWidth();
                int height = newBitmap.getHeight();
                ImageData id=new ImageData();

                 try {
                      id.saveFileToSDCard("LogDificam.txt", "Konversi Grayscale"+" "+timeNow);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        lbp.setOnClickListener(new View.OnClickListener() {
          //  @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

               KonversiLBP(newBitmap);

                ImageData id=new ImageData();
                try {
                    //id.saveFileToSDCard("LogDificam.txt", "Konversi Grayscale"+awal+" "+timeNow);
                    id.saveFileToSDCard("LogDificam.txt", "LBP"+" Width/Height"+newBitmap.getWidth()+"/"+newBitmap.getHeight());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });


        bpnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public Bitmap setBitmap(Bitmap newbitmap){

        newBitmap=newbitmap;
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


        Bitmap newLbp=Bitmap.createBitmap(btGray.getWidth(),btGray.getHeight(),btGray.getConfig());

        int width = btGray.getWidth();
        int height = btGray.getHeight();

        int matrixGrayPicture[] = new int[width*height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int pixel = btGray.getPixel(x, y);

                int px = Color.red(pixel);
                //int G = Color.green(pixel);
                //int B = Color.blue(pixel);

                matrixGrayPicture[x] = px;
            }
        }
        ImageData id= new ImageData();
        try {
            id.saveFileToSDCard("LogDificam.txt", "LBP"+" matrix gray 1 = "+matrixGrayPicture[1]);
            id.saveFileToSDCard("LogDificam.txt", "LBP"+" Panjang matrix Gray"+matrixGrayPicture.length);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        LbpAlgorithm LBP = new LbpAlgorithm();

        int widthheightArray  = (int) Math.sqrt(matrixGrayPicture.length);
        int [][]newMatrix2Dgray = new int[widthheightArray][widthheightArray];

        for(int a=0; a<newMatrix2Dgray.length; a++){
            for (int b=0; b<newMatrix2Dgray[a].length; b++){

                newMatrix2Dgray[a][b]=matrixGrayPicture[a];

            }
        }


        int [][] resultLBP2D = LBP.resultLBP(newMatrix2Dgray,widthheightArray,widthheightArray);

        int widthResultd2D=resultLBP2D.length;
        int heightResult2d=resultLBP2D[0].length;
        int length1D = widthResultd2D*heightResult2d;

        int []resultLBP1D = new int [length1D];

        for (int f=0; f<widthResultd2D; f++){
            int [] row = resultLBP2D[f];
            for(int g=0; g<row.length; g++){
                int matrix= resultLBP2D[f][g];
                resultLBP1D[f*row.length+g]=matrix;
            }
        }

        float[] newMatrikLBP = new float[width*height];

        for (int x=0; x<newMatrikLBP.length; x++ ){
            newMatrikLBP[x]=resultLBP1D[x];
        }

        Canvas canvas = new Canvas(btGray);
        Paint paint = new Paint();
        ColorMatrixColorFilter filter=new ColorMatrixColorFilter(newMatrikLBP);
        paint.setColorFilter(filter);
        canvas.drawBitmap(newLbp,0,0,paint);

        setBitmap(newLbp);


        try {
            id.saveFileToSDCard("LogDificam.txt", "LBP"+" matrix  25  = "+newMatrikLBP[1]);
            id.saveFileToSDCard("LogDificam.txt", "LBP"+" Panjang matrix LBP"+newMatrikLBP.length);
           } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newLbp;
    }
}
