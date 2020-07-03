package xjtu.oee_real_time.util;

public class DES {
    static double s[];//存储s的值(平滑值)
    static double b[];//存储b的值（趋势值）
    static double c[];//季节性因素
    static double F[];//存储预测值(因为会预测下一未知序列，所以数组长度需要大一点)
    static double distance[];//误差
    public static void main(String[] args) {
//        double data[]={0.5125,0.5514,0.5644,0.5513,0.5603,0.635};
        double data[]={51.25,55.14,56.44,55.13,56.03,63.5,62.28,63.87,67.45,64.88,65.22,70};
        //aerf=0.3,beta=0.3
        double result1[]=getTwiceExpect(data,0.3,0.3);
        for(int i=0;i<result1.length;i++){
            if(i<result1.length-1){
                System.out.println("aerf=0.3,beta=0.3"+"原始数据"+i+"的值为："+String.format("%.4f",data[i])+","+"平滑值s为："+String.format("%.4f",s[i])+","+"趋势值b为："+String.format("%.4f",b[i])+","+"预测结果为："+String.format("%.4f",result1[i])+","+"误差为："+String.format("%.4f",distance[i]));
            }else{
                System.out.println("aerf=0.3,beta=0.3"+"第"+i+"次预测结果为："+String.format("%.4f",result1[i]));
            }

        }
        //aerf=0.7,beta=0.3
        double result2[]=getTwiceExpect(data,0.7,0.3);
        for(int i=0;i<result2.length;i++){
            if(i<result2.length-1){
                System.out.println("aerf=0.7,beta=0.3"+"原始数据"+i+"的值为："+String.format("%.4f",data[i])+","+"平滑值s为："+String.format("%.4f",s[i])+","+"趋势值b为："+String.format("%.4f",b[i])+","+"预测结果为："+String.format("%.4f",result2[i])+","+"误差为："+String.format("%.4f",distance[i]));
            }else{
                System.out.println("aerf=0.7,beta=0.3"+"第"+i+"次预测结果为："+String.format("%.4f",result2[i]));
            }

        }
        //****************************三指数平滑曲线**********************
        double result3[]=getThreeExpect(data,0.3,0.3,0.5);
        for(int i=0;i<result3.length;i++){
            if(i<result3.length-1){
                System.out.println("三指数"+"原始数据"+i+"的值为："+String.format("%.4f",data[i])+","+"平滑值s为："+String.format("%.4f",s[i])+","+"趋势值b为："+String.format("%.4f",b[i])+","+"预测结果为："+String.format("%.4f",result3[i])+","+"误差为：");
            }else{
                System.out.println("三指数"+"第"+i+"次预测结果为："+String.format("%.4f",result3[i]));
            }

        }
    }
    public static  double[]  getTwiceExpect(double data[],double aerf,double beta){
        int len=data.length;
        s=new double[len];//存储s的值
        b=new double[len];//存储b的值
        F=new double[len+1];//存储预测值(因为会预测下一未知序列，所以数组长度需要大一点)
        distance=new double[len];//误差
        s[0]=(data[0]+data[1])/2;
        b[0]=(data[len-1]-data[0])/len;
        s[1]=data[1];
        b[1]=data[1]-data[0];
        F[0]=data[0];
        F[1]=s[0]+b[0];
        for(int t=1;t<len;t++){
            if(t==1){
                s[t]=data[1];
                b[t]=data[1]-data[0];
            }else{
                s[t]=aerf*data[t]+(1-aerf)*(s[t-1]+b[t-1]);
                b[t]=beta*(s[t]-s[t-1])+(1-beta)*b[t-1];
            }

            F[t+1]=s[t]+b[t];
        }
        for(int i=0;i<data.length;i++){
            distance[i]=(F[i]-data[i])/data[i];
        }

        return F;
    }
    public static double[] getThreeExpect(double data[],double aerf,double beta,double gama){
        int len=data.length;
        s=new double[len];//存储s的值
        b=new double[len];//存储b的值
        c=new double[len];//季节性值
        F=new double[len+1];//存储预测值(因为会预测下一未知序列，所以数组长度需要大一点)
        distance=new double[len];//误差
        int k=3;//k为周期(K<数据集的长度)
        //*********************s[0]初始化************************************
        //利用前一个周期内的平均值作为s[0]
        double sum=0;
        for(int i=0;i<k;i++){
            sum+=data[i];
        }
        s[0]=sum/k;
        //*********************b[0]初始化************************************
        double sum2=0;
        for(int i=k+1;i<2*k;i++){
            sum2+=data[i];
        }
        b[0]=(sum2-sum)/k*k;
        //*********************c[i]初始化************************************
        for(int i=0;i<len;i++){
            c[i]=data[i]-s[0];
        }
        //开始预测
        F[0]=data[0];
        F[1]=s[0]+b[0]+c[0];
        for(int t=1;t<len;t++){
            s[t]=aerf*(data[t-1]-c[t])+(1-aerf)*(s[t-1]+b[t-1]);//平滑值
            b[t]=beta*(s[t]-s[t-1])+(1-beta)*b[t-1];
            c[t]=gama*(data[t]-s[t-1]-b[t-1])+(1-gama)*c[t];
            F[t+1]=s[t]+b[t]+c[t];//到底加上C哪一时刻的值
        }
        return F;
    }
}
