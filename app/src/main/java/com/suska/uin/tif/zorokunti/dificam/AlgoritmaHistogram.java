package com.suska.uin.tif.zorokunti.dificam;

import java.text.DecimalFormat;

/**
 * Created by zorokunti on 02/02/2018.
 */

public class AlgoritmaHistogram {
    public AlgoritmaHistogram(){
/*
        DecimalFormat df= new DecimalFormat("#.####");

        //System.out.println("Matrix Awal 1 Dimensi");
        //printMatrix1D(matrixLBP);
        int ni[] = intensitasKeabuan(matrixLBP);
        double hi[] = nilaiHistogram(ni);
        double mean  = nilaiMean(hi);
        double variance = nilaiVariance(nilaiHistogram(ni),mean);
        double skewness = nilaiSkewness(nilaiHistogram(ni),mean,variance);
        double kurtosis = nilaiKurtosis(nilaiHistogram(ni),mean,variance);
        double entrophy = nilaiEntrophy(nilaiHistogram(ni));


        System.out.println("Nilai Matrix Intensitas Keabuan(ni)");
        printMatrix1D(ni);

        System.out.println("Nilai Matrix Histogram (hi)");
        printMatrix1Ddouble(hi);

        System.out.println("Nilai Mean ");
        System.out.println(df.format(mean));


        System.out.println("Nilai Variance ");
        System.out.println(df.format(variance));


        System.out.println("Nilai Skewness ");
        System.out.println(df.format(skewness));


        System.out.println("Nilai Kurtosis ");
        System.out.println(df.format(kurtosis));


        System.out.println("Nilai Entrophy ");
        System.out.println(df.format(entrophy));
*/
    }

    public int [] intensitasKeabuan(int []matrix){

        int newMatrix [] = new int[matrix.length];

        for(int a=0; a<matrix.length; a++){
            int awal;
            int nowValue = matrix[a];

            if(nowValue <= 255 ){
                int now=newMatrix[nowValue];
                newMatrix[nowValue] =now+1;
                //   newMatrix[nowValue]=oldValue+1;
            }

        }
        return newMatrix;
    }

    public double [] nilaiHistogram  (int [] intensitasKeabuan){

        double [] hi = new double[intensitasKeabuan.length];
        //int [] ni = new int[intensitasKeabuan];
        double n = intensitasKeabuan.length;


        for (int a=0; a<intensitasKeabuan.length; a++){
            int ni = intensitasKeabuan[a];
            double niDouble = (double) ni;
            hi[a]=niDouble/n;
        }
        return hi;
    }


    public double  nilaiMean(double [] nilaiHistogram){

        DecimalFormat df= new DecimalFormat("#.####");

        double meanValue=0;

        for (int x=0; x<nilaiHistogram.length; x++){
            double meann=x*nilaiHistogram[x];

            meanValue = meanValue+meann;
        }
        //double value = meanValue/nilaiHistogram.length;

        return meanValue;
    }

    public double nilaiVariance(double []nilaiHistogram , double mean){
        double [] imean2 = new double[nilaiHistogram.length];
        double varianceValue=0;

        for (int x=0; x<nilaiHistogram.length; x++){
            double i = x;
            double m= mean;
            double im = i-m;
            double im2= Math.pow(im, 2);
            varianceValue = varianceValue + ((im2*nilaiHistogram[x]));
            //System.out.println("pertma"+x+"value"+varianceValue);

        }

        return varianceValue;
    }


    public double nilaiSkewness(double []nilaiHistogram , double mean, double variance){
        double [] imean2 = new double[nilaiHistogram.length];
        double skewnwssValue=0;
        double v3 = Math.pow(variance, 3);

        for (int x=0; x<nilaiHistogram.length; x++){
            double i = x;
            double m= mean;
            double im = i-m;
            double im3= Math.pow(im, 3);
            skewnwssValue = skewnwssValue + ((im3*nilaiHistogram[x]));
            //System.out.println("pertma"+x+"value"+varianceValue);

        }

        return skewnwssValue/v3;
    }


    public double nilaiKurtosis(double []nilaiHistogram , double mean, double variance){

        double kurtosisValue=0;
        double v4 = Math.pow(variance, 4);

        for (int x=0; x<nilaiHistogram.length; x++){
            double i = x;
            double m= mean;
            double im = i-m;
            double im4= Math.pow(im, 4);
            kurtosisValue = kurtosisValue + ((im4*nilaiHistogram[x])-3);
            //System.out.println("pertma"+x+"value"+varianceValue);

        }

        return kurtosisValue/v4;
    }


    public double nilaiEntrophy(double []nilaiHistogram ){

        double entrophyValue=0;

        for (int x=0; x<nilaiHistogram.length; x++){

            if(x > 0){
                entrophyValue = entrophyValue + ((Math.log(2)/Math.log(nilaiHistogram[x])));
            }
        }

        return -1*entrophyValue;
    }





}
