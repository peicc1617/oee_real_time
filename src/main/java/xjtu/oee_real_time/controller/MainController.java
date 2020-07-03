package xjtu.oee_real_time.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @基本功能:
 * @program:OEE
 * @author:peicc
 * @create:2019-12-24 16:44:19
 **/
@RestController
public class MainController {
    private Object lock=new Object();
    private boolean isStoping=false;
    /*产品数量*/
    private int Total_Count;// 产品总数
    private int Total_Reject_Count;// 不合格品总数
    private int Total_Good_Count;// 合格品总数
    private double Theoretical_Cycle_Time=1.0;//理论循环时间
    private double Actual_Cycle_Time=1.2;//实际循环时间
    /*************************OEE计算指标**************************/
    /*日历时间*/
    private long All_Time;//日历时间
    private long Begin_Time=new Date().getTime();//设备启动时间(自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数)
    /*负荷时间*/
    private long Planned_Production_Time;//日历时间-计划停机时间
    /*计划停机*/
    private long Schedule_Stop_Time;//计划停机时间
    private long Schedule_Stop_Time_Start;//计划停机开始
    private long Schedule_Stop_Time_End;//计划停机结束
    private long Schedule_Stop_Count;//计划停机次数
    /*故障停机*/
    private long Equipment_Failure_Time;//故障停机时间
    private long Equipment_Failure_Time_Start;//故障停机开始
    private long Equipment_Failure_Time_End;//故障停机结束
    private long Equipment_Failure_Count;//计划停机次数
    /*更换产品事件*/
    private long Changeover_Time;//更换产品时间
    private long Changeover_Time_Start;//更换产品开始
    private long Changeover_Time_End;//更换产品结束
    private long Changeover_Count;//更换产品次数
    /*更换工具*/
    private long Setup_Adjust_Time;
    private long Setup_Adjust_Time_Start;
    private long Setup_Adjust_Time_End;
    private long Setup_Adjust_Count;
    /*空转事件*/
    private long Idling_Time;
    private long Idling_Time_Start;
    private long Idling_Time_End;
    private long Idling_Count;
    /*间歇停机*/
    private long Small_Stop_Time;
    private long Small_Stop_Time_Start;
    private long Small_Stop_Time_End;
    private long Small_Stop_Count;
    /*时间类型*/
    private long Availability_Loss_Time;//停机损失时间(故障停机+生产辅助停机)
    private long Performance_Loss_Time;//性能损失时间
    private long Run_Time;//实际开动时间
    private long Net_Run_Time;//净开动时间
    private long Quality_Loss_Time;//质量损失时间
    private long Total_Loss_Time;//损失总时间
    private long Fully_Productive_Time;//有价值生产时间
    /*OEE*/
    private double Availability;//时间开动率
    private double Performance;//性能开动率
    private double Quality;//质量合格率
    private double OEE;
    /*************************OEE计算指标**************************/
    //返回时间开动率
    @RequestMapping("/getAvailability")
    public double getAvailability(){
        DecimalFormat df=new DecimalFormat("0.00");
        Availability=Double.parseDouble(df.format((double) getRun_Time()/getPlanned_Production_Time()));
        return Availability;
    }
    //返回性能开动率
    @RequestMapping("/getPerformance")
    public double getPerformance(){
        DecimalFormat df=new DecimalFormat("0.00");
        Performance=Double.parseDouble(df.format((double)(Total_Count*Theoretical_Cycle_Time*1000)/getPlanned_Production_Time()));
        return Performance;
    }
    //返回质量合格率
    @RequestMapping("/getQuality")
    public double getQuality(){
        DecimalFormat df=new DecimalFormat("0.00");
        if(Total_Count==0){
            Quality=Double.parseDouble(df.format(0));
        }else{
            Quality=Double.parseDouble(df.format((double)Total_Good_Count/Total_Count));
        }
        return Quality;
    }
    //返回OEE
    @RequestMapping("/getOEE")
    public double getOEE(){
        DecimalFormat df=new DecimalFormat("0.00");
        OEE=Double.parseDouble(df.format(getAvailability()*getPerformance()*getQuality()));
        return OEE;
    }
    /*************************Setter/Getter**************************/
    public void setAll_Time(long all_Time) {
        All_Time = all_Time;
    }

    public long getBegin_Time() {
        return Begin_Time;
    }

    public void setBegin_Time(long begin_Time) {
        Begin_Time = begin_Time;
    }
    //产品
    @RequestMapping("/getTotal_Count")
    public int getTotal_Count() {
        return Total_Count;
    }

    public void setTotal_Count(int total_Count) {
        Total_Count = total_Count;
    }
    @RequestMapping("/getTotal_Reject_Count")
    public int getTotal_Reject_Count() {
        return Total_Reject_Count;
    }

    public void setTotal_Reject_Count(int total_Reject_Count) {
        Total_Reject_Count = total_Reject_Count;
    }
    @RequestMapping("/getTotal_Good_Count")
    public int getTotal_Good_Count() {
        return Total_Good_Count;
    }

    public void setTotal_Good_Count(int total_Good_Count) {
        Total_Good_Count = total_Good_Count;
    }

    /*************************Setter/Getter**************************/
    private static final Logger log= LoggerFactory.getLogger(MainController.class);
    /*----------------------------------时间统计--------------------------*/
    //返回日历时间
    @RequestMapping("/getAll_Time")
    public long getAll_Time(){
        Date date=new Date();
        All_Time=date.getTime()-getBegin_Time();
        return All_Time;
    }
    //返回负荷时间
    @RequestMapping("/getPlanned_Production_Time")
    public long getPlanned_Production_Time(){
        Planned_Production_Time=getAll_Time()-Schedule_Stop_Time;//日历时间-计划停机时间
        return Planned_Production_Time;
    }
    //返回停机损失时间
    @RequestMapping("/getAvailability_Loss_Time")
    public long getAvailability_Loss_Time(){
        Availability_Loss_Time=Equipment_Failure_Time+Changeover_Time+Setup_Adjust_Time;//故障停机时间+更换产品时间+更换工具时间
        return Availability_Loss_Time;
    }
    //返回实际开动时间
    @RequestMapping("/getRun_Time")
    public long getRun_Time(){
        Run_Time=getPlanned_Production_Time()-getAvailability_Loss_Time();//负荷时间-停机损失时间
        return Run_Time;
    }
    //返回性能损失时间
    @RequestMapping("/getPerformance_Loss_Time")
    public long getPerformance_Loss_Time(){
        Performance_Loss_Time=(long)(getRun_Time()-getPlanned_Production_Time()*getAvailability()*getPerformance());//实际开动时间-净开动时间
        return Performance_Loss_Time;
    }
    //返回净开动时间
    @RequestMapping("/getNet_Run_Time")
    public long getNet_Run_Time(){
        Net_Run_Time=getRun_Time()-getPerformance_Loss_Time();
        return Net_Run_Time;
    }
    //返回质量损失时间
    @RequestMapping("/getQuality_Loss_Time")
    public long getQuality_Loss_Time(){
        Quality_Loss_Time=(long)(Total_Reject_Count*Theoretical_Cycle_Time);//残次品数量*理论循环时间
        return Quality_Loss_Time;
    }
    //返回损失总时间
    @RequestMapping("/getTotal_Loss_Time")
    public long getTotal_Loss_Time(){
        Total_Loss_Time=getAvailability_Loss_Time()+getPerformance_Loss_Time()+getQuality_Loss_Time();//停机损失时间+性能损失时间+质量损失时间
        return Total_Loss_Time;
    }
    //返回有价值生产时间
    @RequestMapping("/getFully_Productive_Time")
    public long getFully_Productive_Time(){
        Fully_Productive_Time=getPlanned_Production_Time()-getTotal_Loss_Time();//负荷时间-损失总时间
        return Fully_Productive_Time;
    }
    /*------------------------------事件---------------------------------*/
    //设备启动
    @RequestMapping("/begin")
    public String begin(){
        log.info("设备启动........");
        Date date=new Date();
        Begin_Time=date.getTime();
        produce();
        return date.toString();
    }

    //生产零件
    @RequestMapping("/produce")
    public String produce(){
        Thread produce_Thread=new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("开始生产");
                while (true) {
                    synchronized (lock) {
                        while (isStoping) { //停机
                            try {
                                log.info("设备当前状态："+"生产停机");
                                lock.wait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //生产零件
                        try {
                            Thread.sleep((long)Actual_Cycle_Time*1000);
                            Total_Count++;
                            Total_Good_Count=Total_Count-Total_Reject_Count;
                            log.info("当前已生产"+Total_Count+"个零件","其中合格零件："+Total_Good_Count+"个","不合格零件："+Total_Reject_Count+"个");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        produce_Thread.start();
        return "当前已生产"+Total_Count+"个零件";
    }
    /*计划停机事件*/
    //计划停机开始
    @RequestMapping("/Schedule_Stop_Time_Start")
    public String Schedule_Stop_Time_Start(){
        //改变条件
        isStoping=true;//停机状态
        log.info("计划停机开始");
        Schedule_Stop_Count++;
        Date date=new Date();
        Schedule_Stop_Time_Start=date.getTime();
        return "计划停机开始";
    }
    //计划停机结束
    @RequestMapping("/Schedule_Stop_Time_End")
    public String Schedule_Stop_Time_End(){
        synchronized (lock) {
            //改变条件
            isStoping=false;//开机状态
            lock.notify();
            Date date=new Date();
            Schedule_Stop_Time_End=date.getTime();
            long temp=Schedule_Stop_Time_End-Schedule_Stop_Time_Start;
            Schedule_Stop_Time+=temp;
            log.info("此次计划停机时长"+temp);
        }
        return "计划停机结束";
    }
    //返回计划停机时间
    @RequestMapping("/getSchedule_Stop_Time")
    public String getSchedule_Stop_Time(){
        return String.valueOf(Schedule_Stop_Time);
    }
    //返回计划停机次数
    @RequestMapping("/get getSchedule_Stop__Count")
    public String getSchedule_Stop__Count(){
        return String.valueOf(Schedule_Stop_Count);
    }
    /*故障停机事件*/
    //故障停机开始
    @RequestMapping("/Equipment_Failure_Start")
    public String Equipment_Failure_Start(){
        Thread Euipment_Failure_Thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //改变条件
                isStoping=true;//停机状态
                log.info("故障停机开始");
                Equipment_Failure_Count++;
                Date date=new Date();
                Equipment_Failure_Time_Start=date.getTime();
            }
        });
        Euipment_Failure_Thread.start();
        return "故障停机";
    }
    //故障停机结束
    @RequestMapping("/Equipment_Failure_End")
    public String uipment_Failure_End(){
        Thread Euipment_Failure_End_Thread=new Thread(new Runnable() {
            @Override
            public void run() {
                //改变条件
                isStoping=false;//开机状态
                synchronized (lock) {
                    lock.notify();
                    Date date=new Date();
                    Equipment_Failure_Time_End=date.getTime();
                    long temp=Equipment_Failure_Time_End-Equipment_Failure_Time_Start;
                    Equipment_Failure_Time+=temp;
                    log.info("此次停机时长"+temp);
                }
            }
        });
        Euipment_Failure_End_Thread.start();
        return "故障停机结束";
    }
    //返回故障停机时间
    @RequestMapping("/getEquipment_Failure_Time")
    public String getEquipment_Failure_Time(){
        return String.valueOf(Equipment_Failure_Time);
    }
    //返回故障停机次数
    @RequestMapping("/getEquipment_Failure_Count")
    public String getEquipment_Failure_Counnt(){
        return String.valueOf(Equipment_Failure_Count);
    }
    /*更换产品事件*/
    //更换产品开始
    @RequestMapping("/Changeover_Time_Start")
    public String Changeover_Time_Start(){
        //改变条件
        isStoping=true;//停机状态
        log.info("更换产品开始");
        Changeover_Count++;
        Date date=new Date();
        Changeover_Time_Start=date.getTime();
        return "更换产品开始";
    }
    //更换产品结束
    @RequestMapping("/Changeover_Time_End")
    public String Changeover_Time_End(){
        synchronized (lock) {
            //改变条件
            isStoping=false;//开机状态
            lock.notify();
            Date date=new Date();
            Changeover_Time_End=date.getTime();
            long temp=Changeover_Time_End-Changeover_Time_Start;
            Changeover_Time+=temp;
            log.info("此次更换产品时长"+temp);
        }
        return "计划停机结束";
    }
    //返回更换产品时间
    @RequestMapping("/getChangeover_Time")
    public String getChangeover_Time(){
        return String.valueOf(Changeover_Time);
    }
    //返回更换产品次数
    @RequestMapping("/getChangeover_Count")
    public String getChangeover_Count(){
        return String.valueOf(Changeover_Count);
    }
    /*更换工具（设置调整）*/
    //更换工具开始
    @RequestMapping("/Setup_Adjust_Time_Start")
    public String Setup_Adjust_Time_Start(){
        //改变条件
        isStoping=true;//停机状态
        log.info("更换工具开始");
        Setup_Adjust_Count++;
        Date date=new Date();
        Setup_Adjust_Time_Start=date.getTime();
        return "更换工具开始";
    }
    //更换工具结束
    @RequestMapping("/Setup_Adjust_Time_End")
    public String Setup_Adjust_Time_End(){
        synchronized (lock) {
            //改变条件
            isStoping=false;//开机状态
            lock.notify();
            Date date=new Date();
            Setup_Adjust_Time_End=date.getTime();
            long temp=Setup_Adjust_Time_End-Setup_Adjust_Time_Start;
            Setup_Adjust_Time+=temp;
            log.info("此次更换工具时长"+temp);
        }
        return "更换结束";
    }
    //返回更换工具时间
    @RequestMapping("/getSetup_Adjust_Time")
    public String getSetup_Adjust_Time(){
        return String.valueOf(Setup_Adjust_Time);
    }
    //返回更换工具次数
    @RequestMapping("/getSetup_Adjust_Count")
    public String getSetup_Adjust_Count(){
        return String.valueOf(Setup_Adjust_Count);
    }
    /*空转事件*/
    //空转开始
    @RequestMapping("/Idling_Time_Start")
    public String Idling_Time_Start(){
        //改变条件
        isStoping=true;//停机状态
        log.info("空转开始");
        Idling_Count++;
        Date date=new Date();
        Idling_Time_Start=date.getTime();
        return "空转开始";
    }
    //空转结束
    @RequestMapping("/Idling_Time_End")
    public String Idling_Time_End(){
        synchronized (lock) {
            //改变条件
            isStoping=false;//开机状态
            lock.notify();
            Date date=new Date();
            Idling_Time_End=date.getTime();
            long temp=Idling_Time_End-Idling_Time_Start;
            Idling_Time+=temp;
            log.info("此次空转时长"+temp);
        }
        return "空转结束";
    }
    //返回空转时间
    @RequestMapping("/getIdling_Time")
    public String getIdling_Time(){
        return String.valueOf(Idling_Time);
    }
    //返回空转次数
    @RequestMapping("/getIdling_Count")
    public String getIdling_Count(){
        return String.valueOf(Idling_Count);
    }
    /*间歇停机*/
    //间歇停机开始
    @RequestMapping("/Small_Stop_Time_Start")
    public String Small_Stop_Time_Start(){
        //改变条件
        isStoping=true;//停机状态
        log.info("间歇开始");
        Small_Stop_Count++;
        Date date=new Date();
        Small_Stop_Time_Start=date.getTime();
        return "间歇停机开始";
    }
    //间歇停机结束
    @RequestMapping("/Small_Stop_Time_End")
    public String Small_Stop_Time_End(){
        synchronized (lock) {
            //改变条件
            isStoping=false;//开机状态
            lock.notify();
            Date date=new Date();
            Small_Stop_Time_End=date.getTime();
            long temp=Small_Stop_Time_End-Small_Stop_Time_Start;
            Small_Stop_Time+=temp;
            log.info("此次间歇停机时长"+temp);
        }
        return "间歇停机结束";
    }
    //返回间歇停机时间
    @RequestMapping("/getSmall_Stop_Time")
    public String getSmall_Stop_Time(){
        return String.valueOf(Small_Stop_Time);
    }
    //返回间歇停机次数
    @RequestMapping("/getSmall_Stop_Count")
    public String getSmall_Stop_Count(){
        return String.valueOf(Small_Stop_Count);
    }
    /*不合格品*/
    @RequestMapping(method = RequestMethod.GET,value = "/Set_Reject")
    public String Set_Reject(@RequestParam(value="Reject_Count") long Reject_Count){
        Total_Reject_Count+=Reject_Count;
        Total_Good_Count=Total_Count-Total_Reject_Count;
        return "不合格品总数为："+Total_Reject_Count+"合格品总数为："+Total_Good_Count+"产品总数为："+Total_Count;
    }
    //设置理论循环时间、实际循环时间
    @RequestMapping("/Set_Cycle_Time")
    public String Set_Cycle_Time(@RequestParam(value="Theoretical_Cycle_Time") double Theoretical_Cycle_Time, @RequestParam(value="Actual_Cycle_Time") double Actual_Cycle_Time){
        this.Theoretical_Cycle_Time=Theoretical_Cycle_Time;
        this.Actual_Cycle_Time=Actual_Cycle_Time;
        return "理论循环时间更改为:"+this.Theoretical_Cycle_Time+"实际循环时间更改为:"+this.Actual_Cycle_Time;
    }
    //获取理论循环时间、实际循环时间
    @RequestMapping("/Get_Cycle_Time")
    public String Get_Cycle_Time(){
        return Theoretical_Cycle_Time+","+Actual_Cycle_Time;
    }
}
