package xjtu.oee_real_time.util;

import java.util.ArrayList;
import java.util.List;

public class Expones {


    public static  double[] get1_value(double data[][]) {
        double a = 0.76;//指数平滑系数
         List<Double> S_1 = new ArrayList<Double>();
        double[] pre_values = new double[data.length];
        double s1 = 0.0;
        double st=0.0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (j < 4) {
                    s1 += data[i][j];
                    if (data[0].length < 20)
                        st=s1/3;
                    else
                        st=data[i][0];
                }
            }
            S_1.add(st);
            for (int k = 0; k <data[0].length ; k++) {
                S_1.add(a*data[i][k]+(1-a)*S_1.get(k));
            }
            pre_values[i]=S_1.get(S_1.size()-1);
        }
        return pre_values;
    }
    public static double[] get2_value(double data[][],int t) {

        double a = 0.3;//指数平滑系数
        List<Double> S_1 = new ArrayList<Double>();
        List<Double> S2_1_new = new ArrayList<Double>();
        List<Double> S2_2_new = new ArrayList<Double>();
        double[] pre_values = new double[data.length];
        double s1 = 0.0;
        double st = 0.0;
        double at =0.0;
        double bt = 0.0;
        double xt = 0.0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 1; j < data[0].length; j++) {

                if (j < 4) {
                    s1 += data[i][j - 1];
                    if (data[0].length < 20)
                        st = s1 / 3;
                    else
                        st = data[i][0];
                }
            }
            S_1.add(st);//初始值
            for (int k = 0; k < data[0].length; k++) {
                if(k==0)
                    S2_1_new.add(a * data[i][k] + (1 - a) * S_1.get(k));
                else
                    S2_1_new.add(a*data[i][k]+(1-a)*S2_1_new.get(k-1));
            }
            for (int l = 0; l <data[0].length ; l++) {
                if(l==0)
                    S2_2_new.add(a * S2_1_new.get(l) + (1 - a) * S_1.get(l));
                else
                    S2_2_new.add(a*S2_1_new.get(l)+(1-a)*S2_2_new.get(l-1));
            }
            at=S2_1_new.get(S2_1_new.size()-1)*2-S2_2_new.get(S2_2_new.size()-1);
            bt=a/(1-a)*(S2_1_new.get(S2_1_new.size()-1)-S2_2_new.get(S2_2_new.size()-1));
            xt=at+bt*t;
            pre_values[i] = xt;
        }


        return pre_values;
    }

    public static double[] get3_value(double data[][] ,int t ) {
        double a = 0.25;//指数平滑系数
        List<Double> S_1 = new ArrayList<Double>();
        List<Double> S3_1_new = new ArrayList<Double>();
        List<Double> S3_2_new = new ArrayList<Double>();
        List<Double> S3_3_new = new ArrayList<Double>();
        double[] pre_values = new double[data.length];
        double s1 = 0.0;
        double st = 0.0;
        double at =0.0;
        double bt = 0.0;
        double xt = 0.0;
        double ct=0.0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 1; j < data[0].length; j++) {

                if (j < 4) {
                    s1 += data[i][j - 1];
                    if (data[0].length < 20)
                        st = s1 / 3;//小于20个数据，取前3个的平均值
                    else
                        st = data[i][0];//否则取第一个
                }
            }
            S_1.add(st);//初始值
            for (int k = 0; k < data[0].length; k++) {
                if(k==0)
                    S3_1_new.add(a * data[i][k] + (1 - a) * S_1.get(k));
                else
                    S3_1_new.add(a*data[i][k]+(1-a)*S3_1_new.get(k-1));
            }
            for (int l = 0; l <data[0].length ; l++) {
                if(l==0)
                    S3_2_new.add(a * S3_1_new.get(l) + (1 - a) * S_1.get(l));
                else
                    S3_2_new.add(a*S3_1_new.get(l)+(1-a)*S3_2_new.get(l-1));
            }
            for (int j = 0; j <data[0].length ; j++) {
                if(j==0)
                    S3_3_new.add(a * S3_2_new.get(j) + (1 - a) * S_1.get(j));
                else
                    S3_3_new.add(a*S3_2_new.get(j)+(1-a)*S3_3_new.get(j-1));
            }
            at=S3_1_new.get(S3_1_new.size()-1)*3-S3_2_new.get(S3_2_new.size()-1)*3+S3_3_new.get(S3_3_new.size()-1);
            bt=(a/(2*Math.pow((1-a),2)))*((6-5*a)*S3_1_new.get(S3_1_new.size()-1)-2*(5-4*a)*S3_2_new.get(S3_2_new.size()-1)+(4-3*a)*S3_3_new.get(S3_3_new.size()-1));
            ct=(Math.pow(a,2)/(2*Math.pow((1-a),2)))*(S3_1_new.get(S3_1_new.size()-1)-2*S3_2_new.get(S3_2_new.size()-1)+S3_3_new.get(S3_3_new.size()-1));
            xt=at+bt*t+ct*Math.pow(t,2);
            pre_values[i] = xt;
        }
        return  pre_values;
    }

}
