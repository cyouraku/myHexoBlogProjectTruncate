var myDate;
setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

var names = [];
var numbers = [];
        // echarts路径配置
require.config({
	paths : {
		echarts : 'http://echarts.baidu.com/build/dist'
	}
});

// echarts使用
require([ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
], function(ec) {
	// 基于准备好的dom，初始化echarts图表
	var myChart = ec.init(document.getElementById('main'));

	var option = {
		tooltip : {
			show : true
		},
		legend : {
			data : [ 'Storage' ]
		},
		xAxis : [ {
			type : 'name',
			// data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
			data : names
		} ],
		yAxis : [ {
			type : 'number'
		} ],
		series : [ {
			"name" : "Storage",
			"type" : "bar",
			"data" : numbers
		} ]
	};

	// 为echarts对象加载数据
	myChart.setOption(option);
});

// {"seckillId":1,"name":"1000元秒杀iphone6s","number":1000,"startTime":1532012400000,"endTime":1532530800000,"createTime":1532066747000}
var datagrid_view = new Vue({
  el: '#demo',
// el: '#main',
  data: {
    message: 'This is an ECharts Bar Demo',
// names: [],
// numbers:[]
  },
  beforeMount: function() {
      this.getSecKill();
  },
  methods: {
      getSecKill: function() {
  		var self = this;
  		$.ajax({
              url: 'http://localhost:8081/seckill/list',
  			method:'get',
			statusCode:{
				404:function(){
					alert("Resource not found!")
				}
			},
			success:function(data) {
				this.names = data.name;
				console.log(data.name);
				this.numbers = data.number;
				console.log(data.number);
			}
  		});
        }
  }
})
