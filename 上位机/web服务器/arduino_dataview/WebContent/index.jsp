<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YiSoo ArduinoDataView </title>

<script src="echarts-all.js"></script>
<script src="jquery.js"></script>


</head>
<body>
<div style="text-align:center">
    <p><h1>Arduino环境数据监测</h1></p>
<div id="temDiv" style="height: 400px; width: 1000px;display:inline-block" ></div>
<div id="humDiv" style="height: 400px; width: 1000px;display:inline-block" ></div>
<div id="sunDiv" style="height: 400px; width: 1000px;display:inline-block" ></div>
</div>


<script type="text/javascript">
    function loadDataToTemToSun(option) {
        $.ajax({
            type : 'post',  //传输类型
            async : false,  
            url : 'sun.do', //web.xml中注册的Servlet的url-pattern
            data : {},
            dataType : 'json', //返回数据形式为json
            success : function(result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.xAxis[0].data.push(result[i].name);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.series[0].data.push(result[i].num);
                    }
                }
            },
            error : function(errorMsg) {
                alert("加载数据失败");
            }
        });//AJAX
    }//loadDataToTem()

    var myChart = echarts.init(document.getElementById('sunDiv'));
    var option = {
        title: {
            text: '光照强度数据可视化'
        },

        tooltip : {
            show : true
        },
        legend : {
            data : [ '光照' ]
        },
        xAxis : [ {
            type : 'category'

        } ],
        yAxis : [ {
            type : 'value'
        } ],
        series : [ {
            name : '光照',
            type : 'line'
        } ]
    };
    //加载数据到option
    loadDataToTemToSun(option);
    //设置option
    myChart.setOption(option);
</script>
<script type="text/javascript">
    function loadDataToTemToHum(option) {
        $.ajax({
            type : 'post',  //传输类型
            async : false,  
            url : 'hum.do', //web.xml中注册的Servlet的url-pattern
            data : {},
            dataType : 'json', //返回数据形式为json
            success : function(result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.xAxis[0].data.push(result[i].name);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.series[0].data.push(result[i].num);
                    }
                }
            },
            error : function(errorMsg) {
                alert("加载数据失败");
            }
        });//AJAX
    }//loadDataToTem()

    var myChart = echarts.init(document.getElementById('humDiv'));
    var option = {
        title: {
            text: '湿度数据可视化'
        },

        tooltip : {
            show : true
        },
        legend : {
            data : [ '湿度' ]
        },
        xAxis : [ {
            type : 'category'

        } ],
        yAxis : [ {
            type : 'value'
        } ],
        series : [ {
            name : '湿度',
            type : 'line'
        } ]
    };
    //加载数据到option
    loadDataToTemToHum(option);
    //设置option
    myChart.setOption(option);
</script>
<script type="text/javascript">
    function loadDataToTem(option) {
        $.ajax({
            type : 'post',  //传输类型
            async : false,  
            url : 'tem.do', //web.xml中注册的Servlet的url-pattern
            data : {},
            dataType : 'json', //返回数据形式为json
            success : function(result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.xAxis[0].data.push(result[i].name);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.series[0].data.push(result[i].num);
                    }
                }
            },
            error : function(errorMsg) {
                alert("加载数据失败");
            }
        });//AJAX
    }//loadDataToTem()

    var myChart = echarts.init(document.getElementById('temDiv'));
    var option = {
        title: {
            text: '温度数据可视化'
        },

        tooltip : {
            show : true
        },
        legend : {
            data : [ '温度' ]
        },
        xAxis : [ {
            type : 'category'

        } ],
        yAxis : [ {
            type : 'value'
        } ],
        series : [ {
            name : '温度',
            type : 'line'
        } ]
    };
    //加载数据到option
    loadDataToTem(option);
    //设置option
    myChart.setOption(option);
</script>
</body>
</html>