var apiURL = 'http://localhost:8983/solr/sample_core_001/select?id:';
var jsonpResp = new Array();
var myDate;
setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

function myCallback(dataWeGotViaJsonp) {
	var text = '';
	var len = dataWeGotViaJsonp.response.docs.length;
//	alert(len);
	text = '<table><tr><th>id</th><th>version</th></tr>';
	for (var i = 0; i < len; i++) {
		var doc = dataWeGotViaJsonp.response.docs[i];
		text += '<tr><td>'
		text += doc.id;
		text += '</td>'
		text += '<td>'
		text += doc._version_;
		text += '</td></tr>'
	}
	text += '</table>';
	document.getElementById('showVersions').innerHTML = text;
	console.log(text);
}

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



var datagrid_view = new Vue({
  el: '#demo',
  data: {
    message: 'Hello Mr.Cheung!',
    searchQuery: '',
    searchQuery2: '',
    searchQuery3: '',
    searchQuery4: '',
    searchQuery5: '',
    gridColumns: ['id', 'name', 'link'],
//     gridData: [{"id":1,"name":"gear1","link":"Gear1/prog_index.m3u8","jpg":"images/Gear1.jpg"}
//               ,{"id":2,"name":"gear2","link":"Gear2/prog_index.m3u8","jpg":"images/Gear2.jpg"}],
    gridData: [],
    nmColumns: ['id', 'name', 'jpg'],
    nmList:[],
    urlColumns: ['id', 'link'],
    urlList:[],
    jpgColumns: ['id', 'jpg'],
    jpgList:[],
    docColumns: ['id', '_version_'],
    docList:[],
    id:['1','2','3','4'],
    currentId:'1',
    numFound:'',
    start:'',
    docs:[]
  },
  beforeMount: function() {
      this.getVideos();
      this.getVersions();
  },
  methods: {
	  getVideos: function() {
		var self = this;
		$.ajax({
			url:'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
//			url:'http://localhost/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
//			url:'http://192.168.56.1:7010/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
			method:'get',
			statusCode:{
				404:function(){
					alert("Resource not found!")
				}
			},
			success:function(data) {
				self.gridData = data;
				self.nmList = data;
				self.urlList = data;
				self.jpgList = data;
				console.log(data);
			}
		});
      },
      getVersions: function() {
		var self = this;
		$.ajax({
//            url: 'http://localhost:8983/solr/sample_core_001/select?q=*:*',
            url: 'http://localhost:8983/solr/sample_core_001/select?q=*:*',
            method:'get',
            jsonp:'json.wrf',
            dataType: 'jsonp',
			statusCode:{
				404:function(){
					alert("Resource not found!")
				}
			},
			success: function(data){
				myCallback(data);
			}
		});
      },
      fetchData: function () {
          var xhr = new XMLHttpRequest()
          var self = this
          xhr.open('GET', apiURL + self.currentId)
          xhr.onload = function () {
            self.docs = JSON.parse(xhr.responseText)
            console.log(self.docs[0].id + ';' + self.docs[0]._version_)
          }
          xhr.send()
      }
  }
})
