/*------------绘制 Rsk Rwd 曲线------------------
id : 渲染的div id
normal : 基准数据 [[Rsk, Rwd,'Name']...] 数值
chaorder_advice : 推荐数据 [[Rsk, Rwd,'Name']...]  数值
返回: echartjs 对象
-----------------------------------------*/
function risk_reward_analysis(id, normal, chaorder_advice){
    var rsk_rwd_chart = echarts.init(document.getElementById(id));
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '收益风险分析: ',
            left: '5%',
            top: '2%',
            textStyle: {
                color: 'black',
                fontWeight: 'bold'
            }
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataZoom : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            },
            bottom: "2%",
            right: "5%",
        },
        tooltip: {
            showDelay : 0,    
            triggerOn: 'mousemove|click',
            axisPointer:{
                show: true,
                type : 'cross',
                lineStyle: {
                    type : 'dashed',
                    width : 1
                }
            },
            padding: 5,
            backgroundColor: '#222',
            borderColor: '#777',
            borderWidth: 1,
            formatter: function(obj){
            	return '资产名称: '+obj.value[2]+'<br/>'
            	+'风险: '+obj.value[0]+' 回报: '+obj.value[1];
            }
            
        },
        
        xAxis : [
            {

                type : 'value',
                splitLine:{show: false},//去除网格线
                scale:true,
              
                name:'收益率标准差波动(%)',
                nameTextStyle:{
                    color: 'black',
                    fontWeight: 'bold',
                    fontSize: 15
                },
                nameGap: '30',
                nameLocation:'middle',
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                }        
            }
        ],
        yAxis : [
            {
                type : 'value',
                splitLine:{show: false},//去除网格线              
                name:'累积回报率(%)',
                nameGap: 35,
                scale:true,
                
                nameTextStyle:{
                    color: 'black',
                    fontWeight: 'bold',
                    fontSize: 15

                },
                nameGap: '30',
                nameLocation:'middle',
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                }
            }
        ],
        legend: {
            data: ['常规', '建议'],
            right: '5%',
            top: '4%',
            textStyle: {
                color: 'black'
            }
        },
        series: [{
            name: '常规',
            type: 'scatter',
            data: normal,
            itemStyle: {
                normal: {
                    color: "green"
                }
            }
        },
        {
            name: '建议',
            type: 'scatter',
            data: chaorder_advice,
            itemStyle: {
                normal: {
                    color: "red"
                }
            }
        }]
    };
    rsk_rwd_chart.setOption(option);
    return rsk_rwd_chart;
}

/*------------绘制 Roi曲线------------------
id : 渲染的div id
date : 日期 list YYYY——MM-DD
roi : 组合收益率 数值
benchmark : 基准收益率 数值
返回: echartjs 对象
-----------------------------------------*/
function roi_curve(id, date, roi, benchmark){
    var Roi_Curve_Chart = echarts.init(document.getElementById(id));
    option = {
        title: {
            text: '回报率曲线',
            left: '5%',
            top: '2%',
            textStyle: {
                color: 'black',
                fontWeight: 'bold'
            }
        },
        toolbox: {
            show : true,
            feature : {
                mark : {show: true},
                dataZoom : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            },
        	bottom: "-2%",
        	right: "5%",
        },
        color: ['red', 'green', '#675bba'],
        tooltip: {
            trigger: 'none',
            axisPointer: {
                type: 'cross'
            }
        },
        legend: {
            data: ['建议组合', '基准收益'],
            right: '5%',
            top: '4%',
            textStyle: {
                color: 'black'
            }
        },
        grid: {
            top: 70,
            bottom: 50,
            borderColor: 'black',
        },
        xAxis: [
            {
                type: 'category',
                name: '日期',
                nameLocation: 'middle',
                nameGap: 35,
                nameTextStyle: {
                    color: 'black',
                    fontWeight: '400'
                },
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                },
                axisPointer: {
                    label: {
                        textStyle: {
                            color: 'white',
                            fontWeight: 'bold'
                        },
                        backgroundColor: '#004B97',
                        formatter: function (params) {
                            return '基准收益  ' + params.value
                                + (params.seriesData.length ? '：' + params.seriesData[0].data +'%': '');
                        }
                    }
                },
                data: date
            },
            {
                type: 'category',

                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                },
                axisPointer: {
                    label: {
                        textStyle: {
                            color: 'white',
                            fontWeight: 'bold'
                        },
                        backgroundColor: '#007500',
                        formatter: function (params) {
                            return '建议组合  ' + params.value
                                + (params.seriesData.length ? '：' + params.seriesData[0].data+'%' : '');
                        }
                    }
                },
                data: date
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '回报(%)',
                nameLocation: 'middle',
                nameGap: 35,
                nameTextStyle: {
                    color: 'black',
                    fontWeight: '400'
                },
                splitLine: false,
                axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                },
                axisPointer: {
                    label: {
                        formatter: function(value, index){
                            return value.value.toFixed(2) +"%";
                        }
                    }
                }
            }
        ],
        series: [
            {
                name:'建议组合',
                type:'line',
                xAxisIndex: 1,
                smooth: true,
                data: roi
            },
            {
                name:'基准收益',
                type:'line',
                smooth: true,
                data: benchmark
            }
        ]
    };
    Roi_Curve_Chart.setOption(option);
    return Roi_Curve_Chart;
}
/*------------绘制 Outlier Bar 曲线------------------
id : 渲染的div id
name : 名称 list
cumulative_volatility : 累积波动率 数值 list
outlier_probability : 异常几率 数值 list
返回: echartjs 对象
-----------------------------------------*/
function vol_outlier_bar(id, name, cumulative_volatility, outlier_probability){
    var vol_otl_bar = echarts.init(document.getElementById(id));
    option = {
    color: ['#CC3333','#003399'],
    title: {
        text: 'Volatility & Outlier Analysis',
        left: '5%',
        textStyle: {
            color: 'black',
            fontWeight: 'bold'
        }
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataZoom : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        },
        bottom: "-1%",
        right: "5%",
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data: ['cumulative volatility', 'outlier probability'],
        right: 'right',
        textStyle: {
            color: 'black'
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
            type : 'value',
            axisLine: {
                    onZero: false,
                    lineStyle: {
                        color: 'gray'
                    }
                }  
        }
    ],
    yAxis : [
        {
            type : 'category',
            nameGap: 35,
            axisTick : {show: false},
            data : name,
            axisLabel: {
                textStyle: {
                    color: 'black'
                }
            }
        }
    ],
    series : [
        {
            name:'cumulative volatility',
            type:'bar',
            label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:cumulative_volatility
        },
        {
            name:'outlier probability',
            type:'bar',
            stack: '总量',
            label: {
                normal: {
                    show: true
                }
            },
            data:outlier_probability
        }
    ]
    };
    vol_otl_bar.setOption(option);
    return vol_otl_bar;
}