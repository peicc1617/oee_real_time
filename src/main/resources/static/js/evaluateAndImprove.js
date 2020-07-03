//设备状态评估
function evaluateLevel() {
    var data=[
        {
            'id':1,
            'oee':'大于75%',
            'description':'工业产品性能优良，经济效益良好',
            'level':'A'
        },
        {
            'id':2,
            'oee':'65%-75%',
            'description':'工业产品性能一般，经济效益一般',
            'level':'B'
        },
        {
            'id':3,
            'oee':'55%-65%',
            'description':'工业产品性能不稳，经济效益持平',
            'level':'C'
        },
        {
            'id':4,
            'oee':'40%-55%',
            'description':'工业产品性能低下，经济效益亏损',
            'level':'D'
        },
        {
            'id':5,
            'oee':'小于40%',
            'description':'工业产品性能严重异常，应立即停产进行整改',
            'level':'E'
        }
    ];
    var $table = $('#levelTable')
    $table.bootstrapTable('removeAll');
    $table.bootstrapTable('append',data);
    console.log("evaluateLevel()执行结束")
}
/*
function rowStyle(row, index) {
    var oee=getOEE()*100;
    var i;
    if(oee>75){
        i=1;
    }else if(oee>65){
        i=2;
    }else if(oee>55){
        i=3;
    }else if(oee>40){
        i=4;
    }else{
        i=5;
    }
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ]

    if (index==i) {
        return {
            classes: classes[0]
        }
    }
}*/
function rowStyle(row, index) {
    var oee=getOEE()*100;
    var i;
    if(oee>75){
        i=0;
    }else if(oee>65){
        i=1;
    }else if(oee>55){
        i=2;
    }else if(oee>40){
        i=3;
    }else{
        i=4;
    }
    var classes = [
        'bg-blue',
        'bg-green',
        'bg-orange',
        'bg-yellow',
        'bg-red'
    ]

    if (index ==i) {
        return {
            classes: classes[2]
        }
    }
    return {
        css: {
            color: 'grey'
        }
    }
}
