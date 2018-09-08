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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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
    double alfa, mean,variance,skewness,kurtosis,entrophy ;
    double min_m , max_m;
    double min_v , max_v;
    double min_s , max_s;
    double min_k , max_k;
    double min_e , max_e;

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

                    DecimalFormat df= new DecimalFormat("#.#####");

                    int ni[] = ah.intensitasKeabuan(matrixResultLBP);
                    double hi[] = ah.nilaiHistogram(ni);
                    double means  = ah.nilaiMean(hi);
                    double variances = ah.nilaiVariance(ah.nilaiHistogram(ni),means);
                    double skewnesss = ah.nilaiSkewness(ah.nilaiHistogram(ni),mean,variances);
                    double kurtosiss = ah.nilaiKurtosis(ah.nilaiHistogram(ni),mean,variances);
                    double entrophys = ah.nilaiEntrophy(ah.nilaiHistogram(ni));


                    //Log.e("mvske","m="+means+"v="+variances+"s="+skewnesss+"k="+kurtosiss+"e="+entrophys);


                    setOutput(means,variances,skewnesss,kurtosiss,entrophys);
                    getMinMax();
                    pengujian();

                    Log.d("Yeayy", "Selesai");



                }catch (Exception e){

                }

                Intent ma = new Intent(SimpleConvertPengujian.this , MainActivity.class);
                startActivity(ma);

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
                v0[cc] = cursor.getDouble(1);
                v1[cc] = cursor.getDouble(2);
                v2[cc] = cursor.getDouble(3);
                v3[cc] = cursor.getDouble(4);
                v4[cc] = cursor.getDouble(5);
                w[cc] = cursor.getDouble(6);

                dataBobot[cc][0] = v0[cc];
                dataBobot[cc][1] = v1[cc];
                dataBobot[cc][2] = v2[cc];
                dataBobot[cc][3] = v3[cc];
                dataBobot[cc][4] = v4[cc];
                dataBobot[cc][5] = w[cc];
            }

            Log.e("Get Data Bobot","true");

        }catch (Exception e){
            Log.e("Get Data Bobot",e.getMessage());


        }
        return  dataBobot;
    }

    public void getMinMax() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * from min_max",null);
        daftar = new String[cursor.getCount()];
        double dataMinMax [] = new double[10];

        try{
                cursor.moveToPosition(0);

                dataMinMax[0] = cursor.getDouble(1);
                dataMinMax[1] = cursor.getDouble(2);
                dataMinMax[2] = cursor.getDouble(3);
                dataMinMax[3] = cursor.getDouble(4);
                dataMinMax[4] = cursor.getDouble(5);
                dataMinMax[5] = cursor.getDouble(6);
                dataMinMax[6] = cursor.getDouble(7);
                dataMinMax[7] = cursor.getDouble(8);
                dataMinMax[8] = cursor.getDouble(9);
                dataMinMax[9] = cursor.getDouble(10);


                DecimalFormat df = new DecimalFormat("#.#####");
/*
                setMin_m(Double.parseDouble(df.format(dataMinMax[0])));
                setMax_m(Double.parseDouble(df.format(dataMinMax[1])));
                setMin_v(Double.parseDouble(df.format(dataMinMax[2])));
                setMax_v(Double.parseDouble(df.format(dataMinMax[3])));
                setMin_s(Double.parseDouble(df.format(dataMinMax[4])));
                setMax_s(Double.parseDouble(df.format(dataMinMax[5])));
                setMin_k(Double.parseDouble(df.format(dataMinMax[6])));
                setMax_k(Double.parseDouble(df.format(dataMinMax[7])));
                setMin_e(Double.parseDouble(df.format(dataMinMax[8])));
                setMax_e(Double.parseDouble(df.format(dataMinMax[9])));
*/
                setMin_m(dataMinMax[0]);
                setMax_m(dataMinMax[1]);
                setMin_v(dataMinMax[2]);
                setMax_v(dataMinMax[3]);
                setMin_s(dataMinMax[4]);
                setMax_s(dataMinMax[5]);
                setMin_k(dataMinMax[6]);
                setMax_k(dataMinMax[7]);
                setMin_e(dataMinMax[8]);
                setMax_e(dataMinMax[9]);

                Log.e("Nilai min x","min_m="+getMin_m());




        }catch (Exception e){
            Log.e("Gagal get Data min max",e.getMessage());


        }
    }

    public double[][] getCompareUji() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * from temp_y",null);
        daftar = new String[cursor.getCount()];
        double dataY [][] = new double[cursor.getCount()][cursor.getCount()];

        try{

            cursor.moveToFirst();
            double [] y = new double[cursor.getCount()];
            double [] t = new double[cursor.getCount()];

            for (int cc=0; cc < cursor.getCount(); cc++){
                cursor.moveToPosition(cc);
                y[cc] = cursor.getDouble(1);
                t[cc] = cursor.getDouble(2);

                dataY[cc][0] = y[cc];
                dataY[cc][1] = t[cc];
            }

            Log.e("Get Data Y temp","true");



        }catch (Exception e){
            Log.e("Get Data Y temp","Gagal");

        }
        return  dataY;
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


    public Double normalisasi(Double input, Double min, Double max) {

        return (input - min) / (max - min);
    }



    public double pengujian(){

        Bpnn bpnn = new Bpnn();

        double x1,x2,x3,x4,x5,nx1,nx2,nx3,nx4,nx5;

        x1 = mean;
        x2 = variance;
        x3 = skewness;
        x4 = kurtosis;
        x5 = entrophy;

        nx1 = normalisasi(x1 , getMin_m(),getMax_m());
        nx2 = normalisasi(x2 , getMin_v(),getMax_v());
        nx3 = normalisasi(x3 , getMin_s(),getMax_s());
        nx4 = normalisasi(x4 , getMin_k(),getMax_k());
        nx5 = normalisasi(x5 , getMin_e(),getMax_e());

        double [][] xx = new double[1][5];
        double [][] bobothidden = getBobotHidden();
        double [] bobotbias = getBias();
        double [] bobotoutput = getBobotOutput();

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j<1; j++) {
                xx[0][0]=nx1;
                xx[0][1]=nx2;
                xx[0][2]=nx3;
                xx[0][3]=nx4;
                xx[0][4]=nx5;
            }
        }

        double y = bpnn.feedForward(xx, 0, bobothidden, bobotbias, bobotoutput);

       // System.out.println(y);


        Log.e("nilai y = ","="+y);

        double [][] compareUji = getCompareUji();

        double start = y;


        List<Double> nilaiCompare = new ArrayList<>();

        for (int i = 0; i < compareUji.length; i++) {
            for (int j = 0; j < compareUji[0].length-1; j++) {
                nilaiCompare.add(compareUji[i][j]);
            }
        }

        Collections.sort(nilaiCompare);

        double nearest = 0;

        for (double i : nilaiCompare)
        {
            if (i <= start) {
                nearest = i;
            }
        }
        //System.out.println(nearest);
        Log.e("nilai y sidik jari="+nearest,"<===");
        getUser(nearest);
        return y;
    }

    private void getUser(double nearest) {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        String queNearest = "select b.nama from temp_y as a , tbl_pengguna as b where a.y = '"+nearest+"' and a.t=b.no";

        cursor = db.rawQuery(queNearest,null);
        daftar = new String[cursor.getCount()];
        double dataY [][] = new double[cursor.getCount()][6];

        try{
            cursor.moveToFirst();
            String HasilIdentifikasi = cursor.getString(0);

            Log.e("Identifikasi sidik jari","Sukses");
            Log.e("Identifikasi sidik jari","Dikenali sebagai"+HasilIdentifikasi);

            Toast.makeText(SimpleConvertPengujian.this , "Data Sidik Jari Dikenali sebagai "+HasilIdentifikasi,Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Log.e("Identifikasi sidik jari","Gagal");

        }
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

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return variance;
    }

    public double getSkewness() {
        return skewness;
    }

    public double getKurtosis() {
        return kurtosis;
    }

    public double getEntrophy() {
        return entrophy;
    }

    public double getMin_m() {
        return min_m;
    }

    public void setMin_m(double min_m) {
        this.min_m = min_m;
    }

    public double getMax_m() {
        return max_m;
    }

    public void setMax_m(double max_m) {
        this.max_m = max_m;
    }

    public double getMin_v() {
        return min_v;
    }

    public void setMin_v(double min_v) {
        this.min_v = min_v;
    }

    public double getMax_v() {
        return max_v;
    }

    public void setMax_v(double max_v) {
        this.max_v = max_v;
    }

    public double getMin_s() {
        return min_s;
    }

    public void setMin_s(double min_s) {
        this.min_s = min_s;
    }

    public double getMax_s() {
        return max_s;
    }

    public void setMax_s(double max_s) {
        this.max_s = max_s;
    }

    public double getMin_k() {
        return min_k;
    }

    public void setMin_k(double min_k) {
        this.min_k = min_k;
    }

    public double getMax_k() {
        return max_k;
    }

    public void setMax_k(double max_k) {
        this.max_k = max_k;
    }

    public double getMin_e() {
        return min_e;
    }

    public void setMin_e(double min_e) {
        this.min_e = min_e;
    }

    public double getMax_e() {
        return max_e;
    }

    public void setMax_e(double max_e) {
        this.max_e = max_e;
    }
}
