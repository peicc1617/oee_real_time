/*显示损失时间*/
//时间统计
function showTime() {
    var dom = document.getElementById("Time_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        color: ['#3398DB'],
        /* title : {
             text: '时间分类',
             x:'center'
         },*/
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['日历时间','负荷时间', '实际开动时间', '净开动时间', '有价值开动时间'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                type:'bar',
                barWidth: '60%',
                data:[0, 0, 0, 0, 0]
            }
        ]
    };
    option.series[0].data[0]=getAllTime();
    option.series[0].data[1]=getPlanned_Production_Time();
    option.series[0].data[2]=getRun_Time();
    option.series[0].data[3]=getNet_Run_Time();
    option.series[0].data[4]=getFully_Productive_Time();
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//损失统计
function showLossTime() {
    var dom = document.getElementById("Loss_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        color: ['#3398DB'],
        /*title : {
            text: '时间分类',
            x:'center'
        },*/
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['计划停机时间','停机损失时间', '性能损失时间', '质量损失时间'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                type:'bar',
                barWidth: '60%',
                data:[0, 0, 0, 0]
            }
        ]
    };
    option.series[0].data[0]=getSchedule_Stop_Time();
    option.series[0].data[1]=getAvailability_Loss_Time();
    option.series[0].data[2]=getPerformance_Loss_Time();
    option.series[0].data[3]=getQuality_Loss_Time();
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//停机损失时间
function showAvailabilityLossTime() {
    var dom = document.getElementById("Availability_Lost_Time_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        title : {
            text: '停机损失',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['故障停机','更换产品','设置调整']
        },
        series : [
            {
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:0, name:'故障停机'},
                    {value:0, name:'更换产品'},
                    {value:0, name:'设置调整'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    option.series[0].data[0].value=getEquipment_Failure_Time();
    option.series[0].data[1].value=getChangeover_Time();
    option.series[0].data[2].value=getSetup_Adjust_Time();
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//性能损失时间
function showPerformanceLossTime() {
    var dom = document.getElementById("Performance_Lost_Time_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        title : {
            text: '性能损失',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['速度损失','空转','间歇停机']
        },
        series : [
            {
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:0, name:'速度损失'},
                    {value:0, name:'空转'},
                    {value:0, name:'间歇停机'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    option.series[0].data[0].value=getSpeed_LossTime();
    option.series[0].data[1].value=getIdling_Time();
    option.series[0].data[2].value=getSmall_Stop_Time();
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//质量损失时间
function showQualityossTime() {
    var dom = document.getElementById("Quality_Lost_Time_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        title : {
            text: '质量损失',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['生产次品','试加工次品']
        },
        series : [
            {
                name:'质量损失',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:0, name:'生产次品'},
                    {value:0, name:'试加工次品'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    option.series[0].data[0].value=Math.round(getQuality_Loss_Time()*0.8);
    option.series[0].data[1].value=Math.round(getQuality_Loss_Time()*0.2);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//产品统计
function showProduct() {
    var dom = document.getElementById("Product_Container");
    var myChart = echarts.init(dom);
    var option ;
    option = {
        title : {
            text: '产品统计',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['合格品','不合格品']
        },
        series : [
            {
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:0, name:'合格品'},
                    {value:0, name:'不合格品'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    option.series[0].data[0].value=getTotal_Good_Count();
    option.series[0].data[1].value=getTotal_Reject_Count();
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
/*
//次数统计
function showCount() {
    var dom = document.getElementById("Count_Container");
    var myChart = echarts.init(dom);
    var option;
    option = {
        title: {
            text: '次数统计',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['2011年', '2012年']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
        },
        series: [
            {
                name: '2011年',
                type: 'bar',
                data: [18203, 23489, 29034, 104970, 131744, 630230]
            },
            {
                name: '2012年',
                type: 'bar',
                data: [19325, 23438, 31000, 121594, 134141, 681807]
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//
function showTopLoss() {
    var dom = document.getElementById("Top_Loss_Container");
    var myChart = echarts.init(dom);
    var option;
    option = {
        title: {
            text: '主要损失',
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['2011年', '2012年']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
        },
        series: [
            {
                name: '2011年',
                type: 'bar',
                data: [18203, 23489, 29034, 104970, 131744, 630230]
            },
            {
                name: '2012年',
                type: 'bar',
                data: [19325, 23438, 31000, 121594, 134141, 681807]
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
function sortLossTime() {
    
}*/
