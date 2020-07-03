jQuery(function($) {
    //显示OEE
    showOEE();
    //显示三大指标
    showOtherThreeIndicators();
});
function init() {
    //显示六大损失、
    showAvailabilityLossTime();
    showPerformanceLossTime();
    showQualityossTime();
    showProduct();
    // showCount();
    showTime();
    // showTopLoss();
    showLossTime();
}