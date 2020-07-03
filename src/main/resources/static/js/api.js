/*获取OEE相关参数*/
function getOEE() {
    return sendAjax("getOEE");
}
//获取时间开动率
function getAvailability() {
    return sendAjax("getAvailability");
}
//获取性能开动率
function getPerformance() {
    return sendAjax("getPerformance");
}
//获取质量合格率
function getQuality() {
    return sendAjax("getQuality");
}
/*获取产品数量相关参数*/
//产品总数
function getTotal_Count() {
    sendAjax("getTotal_Count");
}
//不合格产品数量
function getTotal_Reject_Count() {
    return sendAjax("getTotal_Reject_Count");
}
//合格品数量
function getTotal_Good_Count() {
    return sendAjax("getTotal_Good_Count");
}
/*获取计划停机时间*/
function getSchedule_Stop_Time() {
    return sendAjax("getSchedule_Stop_Time");
}
/*获取停机损失相关参数*/
//获取停机损失时间
function getAvailability_Loss_Time() {
    return sendAjax("getAvailability_Loss_Time");
}
//获取故障停机时间
function getEquipment_Failure_Time() {
    return sendAjax("getEquipment_Failure_Time");
}
//获取更换产品时间
function getChangeover_Time() {
    return sendAjax("getChangeover_Time");
}
//获取更换工具（设置调整）时间
function getSetup_Adjust_Time() {
    return sendAjax("getSetup_Adjust_Time");
}
/*获取性能损失相关时间*/
//获取性能损失时间
function getPerformance_Loss_Time() {
    return sendAjax("getPerformance_Loss_Time");
}
//获取速度损失时间
function getSpeed_LossTime() {
    return getPerformance_Loss_Time()-getIdling_Time()-getSmall_Stop_Time();
}
//获取空转时间
function getIdling_Time() {
    return sendAjax("getIdling_Time");
}
//获取间歇停机时间
function getSmall_Stop_Time() {
    return sendAjax("getSmall_Stop_Time");
}
/*获取质量损失时间*/
//获取质量损失时间
function getQuality_Loss_Time() {
    return sendAjax("getQuality_Loss_Time")*1000;
}
//获取生产残次品时间
function getDefectTime() {
    return sendAjax("getQuality_Loss_Time");
}
/*获取时间分类*/
//日历时间
function getAllTime() {
    return sendAjax("getAll_Time");
}
//负荷时间
function getPlanned_Production_Time() {
    return sendAjax("getPlanned_Production_Time");
}
//实际开动时间
function getRun_Time() {
    return sendAjax("getRun_Time");
}
//净开动时间
function getNet_Run_Time() {
    return sendAjax("getNet_Run_Time");
}
//有价值开动时间
function getFully_Productive_Time() {
    return sendAjax("getFully_Productive_Time");
}
/*发送ajax请求*/
function sendAjax(url) {
    var res;
    $.ajax({
        url:url,
        type:"post",
        async:false,//必须设置为同步，否则赋值失败
        success:function(result)
        {
            res=result;
        }
    });
    return res;
}