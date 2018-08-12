package com.suska.uin.tif.zorokunti.dificam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SimpleConvertPengujian extends AppCompatActivity {

    DataHelper dbHelper;
    ImageView canvas , canvasKosongp;
    Button proses , pengguna;
    SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
    String timeNow = s.format(new Date());
     Bitmap newBitmap;
    ProgressBar progressBar;
    ListView lv_pengguna;
    public int treshold;
    int [] arrayNew;
    String [] daftar, no;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    int nopengguna;
    double mean,variance,skewness,kurtosis,entrophy;
    double [] bobot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_activity_convert_pengujian);


        canvasKosongp = (ImageView)findViewById(R.id.scanvas_kosongp);
        proses = (Button)findViewById(R.id.btn_proses_simplep);


        //ImageData b = new ImageData();
        Intent x = getIntent();
        String uri = x.getStringExtra("url");
       // String no = x.getStringExtra("nopengguna");
       // final int nopengguna = Integer.parseInt(no);
        Bitmap bt = BitmapFactory.decodeFile(uri);
        canvasKosongp.setImageBitmap(bt);
        setBitmap(bt);

        dbcenter = new DataHelper(this);



        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    Intent xx = getIntent();
                    String awal = xx.getStringExtra("url");
                    Bitmap btm = BitmapFactory.decodeFile(awal);
                    Bitmap rp = Bitmap.createScaledBitmap(btm , 1000 ,1000, false);
                    canvasKosongp.setImageBitmap(rp);

                    setBitmap(rp);

                    KonversiGrayscale(newBitmap);

                    Bitmap newB = KonversiLBP(newBitmap);
                    canvasKosongp.setImageBitmap(newB);

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


                    setOutput(means,variances,skewnesss,kurtosiss,entrophys);

                    Log.d("Yeayy", "Dah sampe sini");




                }catch (Exception e){

                }

            }

        });
    }

    public double[][] getBobotFromDB() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * from bobot",null);
        daftar = new String[cursor.getCount()];
        double dataBobot [][] = new double[cursor.getCount()][6];

        try{

            cursor.moveToFirst();
            double [] v0 = new double[cursor.getCount()];
            double [] v1 = new double[cursor.getCount()];
            double [] v2 = new double[cursor.getCount()];
            double [] v3 = new double[cursor.getCount()];
            double [] v4 = new double[cursor.getCount()];
            double [] w = new double[cursor.getCount()];

            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                v0[cc] = cursor.getDouble(2);
                v1[cc] = cursor.getDouble(3);
                v2[cc] = cursor.getDouble(4);
                v3[cc] = cursor.getDouble(5);
                v4[cc] = cursor.getDouble(6);
                w[cc] = cursor.getDouble(7);

                dataBobot[cc][0] = v0[cc];
                dataBobot[cc][1] = v1[cc];
                dataBobot[cc][2] = v2[cc];
                dataBobot[cc][3] = v3[cc];
                dataBobot[cc][4] = v4[cc];
                dataBobot[cc][5] = w[cc];
            }



        }catch (Exception e){

        }
        return  dataBobot;
    }



    public double [][] getBobotHidden(){
        double bobotDB[][] = getBobotFromDB();

        double[][] bobotHidden = new double[bobotDB.length - 1][bobotDB[0].length - 1];

        for (int ii = 0; ii < bobotHidden.length; ii++) {
            for (int j = 0; j < bobotHidden[0].length; j++) {
                bobotHidden[ii][j] = bobotDB[ii][j];
                //System.out.println(""+bobotHidden[ii][j]);
            }
        }
        return bobotHidden;
    }


    public double [] getBobotOutput(){
        double bobotDB[][] = getBobotFromDB();
        double[] bobotOutput = new double[bobotDB.length - 1];

        for (int m= 0; m < bobotOutput.length; m++) {
            bobotOutput[m] = bobotDB[m][5];
        }

        return bobotOutput;
    }

    public double [] getBias (){
        double bobotDB[][] = getBobotFromDB();
        double[] bias = bobotDB[bobotDB.length - 1];

        return bias;
    }

    public int setPengguna(int noPengguna){
        nopengguna=noPengguna;
        return nopengguna;
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
