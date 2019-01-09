var myDate;
setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

// register the grid component
Vue.component('demo-grid', {
  template: '#grid-template',
  props: {
    data: Array,
    columns: Array,
    filterKey: String
  },
  data: function () {
    var sortOrders = {}
    this.columns.forEach(function (key) {
      sortOrders[key] = 1
    })
    return {
      sortKey: '',
      sortOrders: sortOrders
    }
  },
  computed: {
    filteredData: function () {
      var sortKey = this.sortKey
      var filterKey = this.filterKey && this.filterKey.toLowerCase()
      var order = this.sortOrders[sortKey] || 1
      var data = this.data
      if (filterKey) {
        data = data.filter(function (row) {
          return Object.keys(row).some(function (key) {
            return String(row[key]).toLowerCase().indexOf(filterKey) > -1
          })
        })
      }
      if (sortKey) {
        data = data.slice().sort(function (a, b) {
          a = a[sortKey]
          b = b[sortKey]
          return (a === b ? 0 : a > b ? 1 : -1) * order
        })
      }
      return data
    }
  },
  filters: {
    capitalize: function (str) {
      return str.charAt(0).toUpperCase() + str.slice(1)
    }
  },
  methods: {
    sortBy: function (key) {
      this.sortKey = key
      this.sortOrders[key] = this.sortOrders[key] * -1
    }
  }
})

//{"seckillId":1,"name":"1000元秒杀iphone6s","number":1000,"startTime":1532012400000,"endTime":1532530800000,"createTime":1532066747000}
var datagrid_view = new Vue({
  el: '#demo',
  data: {
    message: 'Hello Mr.Cheung!',
    searchQuery: '',
    gridData: [],
    gridColumns: ['seckillId', 'name', 'number','startTime','endTime','createTime']
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
				self.gridData = data;
				console.log(data);
			}
  		});
        }
  }
})
