/**
 * 使用Echartjs绘制收益率曲线
 * @param id
 * 		渲染的Div元素id
 * @param date
 * 		日期列表 ['YYYY-MM-DD', 'YYYY-MM-DD']
 * @param roi
 * 		建议的Portfolio收益率列表 
 * @param benchmark
 * 		采用的Benchmark收益率列表
 * @returns
 * 		绘制完成后的Echart对象
 */
function drawRoiChart(id, date, roi, benchmark){
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
                restore : {show: true},
                saveAsImage : {show: true}
            },
            top: '2.5%',
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
            left: '20%',
            top: '3%',
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
                        backgroundColor: '#007500',
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
                        backgroundColor: '#CC0033',
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

/**
 * 使用Echartjs绘制风险曲线
 * @param id
 * 		渲染的Div的id
 * @param normal
 * 		常规板块的收益风险 [[risk,reward], ... ...]
 * @param chaorder_advice
 * 		推荐板块的收益风险 [[risk,reward], ... ...]
 * @param 
 * 		绘制完成后的Echarts对象
 */
function drawRiskRewardChart(id, normal, chaorder_advice){
    var rsk_rwd_chart = echarts.init(document.getElementById(id));
    var option = {
        title: {
            text: '收益风险散点图',
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
                restore : {show: true},
                saveAsImage : {show: true}
            },
            top: '3%',
            right: '5%',
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
              
                name:'累积收益率标准差波动(%)',
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
            left: '25%',
            top: '2.5%',
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

/**
 * 使用Echartjs绘制价格曲线
 * @param id
 * 		渲染的Div的id
 * @param price
 * 		产品价格
 * @param date
 * 		日期
 * @return 
 * 		绘制完成后的Echarts对象
 */
function drawPrice(price,date,id){
	var priceChart = echarts.init(document.getElementById(id));
	option = {
		    title: {
		        text: '价格曲线',
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:['价格序列'],
		    	left: '20%',
		    	top: '2%'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: date,
		    },
		    yAxis: {
		        type: 'value',
		        name: '价格（元/千克)',
		        min: 'dataMin',
		        max: 'dataMax'
		    },
		    series: [
		        {
		            name: '价格序列',
		            type: 'line',
		            data: price
		        },
		    ]
	};
	priceChart.setOption(option);
	return priceChart;		
}

/**
 * 绘制贝叶斯BarGraph
 * @param low
 * 		下跌概率
 * @param high
 * 		上涨概率
 * @param id
 * 		渲染的div的id
 * @return 
 * 		EchartJs对象
 */

function drawBayesNetworkBarChart(low, high, id){
	
	var bayesNetworkBarChart = echarts.init(document.getElementById(id));
	
	option = {
		tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    title: {
            text: '事件概率',
            left: '5%',
            top: '2%',
            textStyle: {
                color: 'black',
                fontWeight: 'bold'
            }
        },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis:  {
	        type: 'value'
	    },
	    yAxis: {
	        type: 'category',
	        data: ['黄金(GOLD)%',
	               '美元指数(USD)%',
	               '通胀指数(CPI)%',
	               '恐慌指数(VIX)%']
	    },
	    series: [
	        {
	            name: '上涨(高位)概率',
	            type: 'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'insideRight'
	                }
	            },
	            data: high
	        },
	        {
	            name: '下跌(低位)概率',
	            type: 'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'insideRight'
	                }
	            },
	             itemStyle:{
	                  normal:{color:'green'}
	            },
	            data: low
	        }
	    ]
	};
	
	bayesNetworkBarChart.setOption(option);
	return bayesNetworkBarChart;
}

