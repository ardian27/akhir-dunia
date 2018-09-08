package com.suska.uin.tif.zorokunti.dificam;

/**
 * Created by angsakumisan on 14/08/18.
 */

public class Bpnn {



    //HITUNG Z_in
    public double[] z_in(double[][] normal, int a, double[][] bobotHidden, double[] bias){
        double[] z_in = new double[bobotHidden[0].length];
        for(int i=0;i<z_in.length;i++){
            for(int j=0;j<bobotHidden[0].length;j++){
                z_in[i] = z_in[i] + (normal[a][j]*bobotHidden[i][j]);
            }
            z_in[i] = z_in[i]+bias[i];
        }
        return z_in;
    }
    //HITUNG Z
    public double[] z(double[] z_in){
        double[] z = new double[z_in.length];
        for(int i=0;i<z.length;i++){
            z[i]=1/(1+Math.exp(-(z_in[i])));
        }
        return z;
    }
    //HITUNG Y_in
    public double y_in(double[] z, double[] bobotOutput, double[] bias){
        double y_in=0;
        for(int i=0;i<z.length;i++){
            y_in = y_in + (z[i]*bobotOutput[i]);
        }
        y_in = y_in+bias[bias.length-1];
        return y_in;
    }
    //HITUNG Y
    public double y(double y_in){
        double y=1/(1+Math.exp(-(y_in)));
        return y;
    }
    //FUNGSI FEEDFORWARD
    public double feedForward(double[][] data, int barisData, double[][] bobotHidden, double[] bias, double[] bobotOutput){
        double[][] normal = data;
        double[] z_in = z_in(normal, barisData, bobotHidden, bias);
        double[] z = z(z_in);
        double y_in = y_in(z, bobotOutput, bias);
        double y = y(y_in);
        return y;
    }

}
