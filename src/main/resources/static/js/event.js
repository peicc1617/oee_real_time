/*开始生产*/
function produce() {
    sendAjax("produce");
    Get_Cycle_Time();
}
/*计划停机*/
//开始
function Schedule_Stop_Time_Start() {
    sendAjax("Schedule_Stop_Time_Start");
}
// 结束
function Schedule_Stop_Time_End() {
    sendAjax("Schedule_Stop_Time_End");
}
/*故障停机*/
//开始
function Equipment_Failure_Start() {
    sendAjax("Equipment_Failure_Start");
}
//结束
function Equipment_Failure_End() {
    sendAjax("Equipment_Failure_End");
}
/*更换产品*/
//开始
function Changeover_Time_Start() {
    sendAjax("Changeover_Time_Start");
}
//结束
function Changeover_Time_End() {
    sendAjax("Changeover_Time_End");
}
/*更换工具（设置调整）*/
//开始
function Setup_Adjust_Time_Start() {
    sendAjax("Setup_Adjust_Time_Start");
}
//结束
function Setup_Adjust_Time_End() {
    sendAjax("Setup_Adjust_Time_End");
}
/*空转*/
//开始
function Idling_Time_Start() {
    sendAjax("Idling_Time_Start");
}
//结束
function Idling_Time_End() {
    sendAjax("Idling_Time_End");
}
/*间歇停机*/
//开始
function Small_Stop_Time_Start() {
    sendAjax("Small_Stop_Time_Start");
}
//结束
function Small_Stop_Time_End() {
    sendAjax("Small_Stop_Time_End");
}
/*次品检测*/
function Set_Reject() {
    $.ajax({
        url:"Set_Reject",
        type:"get",
        data:{
            Reject_Count:$("#DefectValue").val()
        },
        async:false,//必须设置为同步，否则赋值失败
        success:function(result)
        {
            $("#setDefect").modal("hide");// 隐藏模态框
            alert(result)
        }
    });
}
/*速度调整*/
function Set_Speed() {
    $.ajax({
        url:"Set_Cycle_Time",
        type:"get",
        data:{
            Theoretical_Cycle_Time:$("#Theoretical_Cycle_Time").val(),
            Actual_Cycle_Time:$("#Actual_Cycle_Time").val(),
        },
        async:false,//必须设置为同步，否则赋值失败
        success:function(result)
        {
            $("#modifySpeed").modal("hide");// 隐藏模态框
            alert(result)
            Get_Cycle_Time()
        }
    });
}
/*更新理论循环时间、实际循环时间*/
function Get_Cycle_Time() {
    $.ajax({
        url:"Get_Cycle_Time",
        type:"get",
        async:false,//必须设置为同步，否则赋值失败
        success:function(result)
        {
            var time=result.split(",");

            $("#time1").val(time[0]);
            $("#time2").val(time[1]);
            var oee=getOEE()*100;
            var i;
            if(oee>75){
                $("#state").val("工业产品性能优良，经济效益良好");
            }else if(oee>65){
                $("#state").val("工业产品性能一般，经济效益一般");
            }else if(oee>55){
                $("#state").val("工业产品性能不稳，经济效益持平");
            }else if(oee>40){
                $("#state").val("工业产品性能低下，经济效益亏损");
            }else{
                $("#state").val("工业产品性能严重异常，应立即停产进行整改");
            }
        }
    });
}

