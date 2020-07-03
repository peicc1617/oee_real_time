//显示OEE实时数据
function showOEE(){
    var dom = document.getElementById("OEE_container");
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    option = {
        title: {
            text: '设备综合效率实时监测',
            subtext: 'OEE'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#45a5ff'
                }
            }
        },
        legend: {
            data:['OEE实时值']
        },
        toolbox: {
            show: false,
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        dataZoom: {
            show: true,
            start: 0,
            end: 100
        },
        xAxis: [
            {
                type: 'category',
                boundaryGap: true,
                data: (function (){
                    var now = new Date();
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                        now = new Date(now - 2000);
                    }
                    return res;
                })()
            }
        ],
        yAxis: [
            {
                type: 'value',
                scale: true,
                name: 'OEE/百分比',
                max: 100,
                min: 0,
                axisLabel: {
                    formatter: '{value} %'
                },
                boundaryGap: [0.2, 0.2]
            }
        ],
        series: [
            {
                name:'OEE实时值',
                type:'line',
                data:(function (){
                    var res = [];
                    var len = 10;
                    while (len--) {
                        res.push(0);
                    }
                    return res;
                })()
            },
            /*{
                name:'OEE预测值',
                type:'line',
                data:(function (){// 数组初始化
                    var res = [];
                    var len = 0;
                    while (len < 10) {
                        res.push(0);
                        len++;
                    }
                    return res;
                })()
            }*/
        ]
    };

    app.count = 11;
    setInterval(function (){
        axisData = (new Date()).toLocaleTimeString().replace(/^\D*/,'');

        var data0 = option.series[0].data;
        // var data1 = option.series[1].data;
        data0.shift();
        data0.push(getOEE()*100);
        // console.log("data:"+option.series[0].data);
        // console.log("data0:"+data0);
        // data1.shift();
        // data1.push(getOEE()*100+10);

        option.xAxis[0].data.shift();
        option.xAxis[0].data.push(axisData);
        myChart.setOption(option);
    }, 1000);
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
//显示时间利用率、性能开动率以及质量合格率
function showOtherThreeIndicators() {
    var dom = document.getElementById("Gauge_container");
    var myChart = echarts.init(dom);
    var app = {};
    var option = null;
    option = {
        tooltip : {
            formatter: "{a} <br/>{c} {b}"
        },
        toolbox: {
            show: false,
            feature: {
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series : [
            {
                name: '时间开动率',
                type: 'gauge',
                z: 3,
                min: 0,
                max: 100,
                center:['40%','50%'],
                splitNumber: 10,
                radius: '60%',
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        fontStyle: 'italic'
                    }
                },
                detail : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    }
                },
                data:[{value: 0, name: '时间开动率'}]
            },
            {
                name: '时间开动率',
                type: 'gauge',
                z: 3,
                min: 0,
                max: 100,
                center:['60%','50%'],
                splitNumber: 10,
                radius: '60%',
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        fontStyle: 'italic'
                    }
                },
                detail : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    }
                },
                data:[{value: 0, name: '性能开动率'}]
            },
            {
                name: '时间开动率',
                type: 'gauge',
                z: 3,
                min: 0,
                max: 100,
                center: ['80%', '50%'],    // 默认全局居中
                splitNumber: 10,
                radius: '60%',
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        fontStyle: 'italic'
                    }
                },
                detail : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    }
                },
                data:[{value: 0, name: '质量合格率'}]
            },
            {
                name: '设备综合效率',
                type: 'gauge',
                z: 3,
                min: 0,
                max: 100,
                center:['20%','50%'],
                splitNumber: 10,
                radius: '60%',
                axisLine: {            // 坐标轴线
                    lineStyle: {       // 属性lineStyle控制线条样式
                        width: 10
                    }
                },
                axisTick: {            // 坐标轴小标记
                    length: 15,        // 属性length控制线长
                    lineStyle: {       // 属性lineStyle控制线条样式
                        color: 'auto'
                    }
                },
                splitLine: {           // 分隔线
                    length: 20,         // 属性length控制线长
                    lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                        color: 'auto'
                    }
                },
                title : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder',
                        fontSize: 20,
                        fontStyle: 'italic'
                    }
                },
                detail : {
                    textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                        fontWeight: 'bolder'
                    }
                },
                data:[{value: 0, name: '设备综合效率'}]
            }
        ]
    };
    setInterval(function (){
        option.series[0].data[0].value = (getAvailability()*100).toFixed(0) - 0;
        option.series[1].data[0].value = (getPerformance()*100).toFixed(0) - 0;
        option.series[2].data[0].value = (getQuality()*100).toFixed(0) - 0;
        option.series[3].data[0].value = (getOEE()*100).toFixed(0) - 0;
        myChart.setOption(option,true);
    },1000);
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}