var myDate;

//Vue.prototype.$axios = axios

setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

var player = new MediaElementPlayer('#player1', {
	loop: false,
	clickToPlayPause: true,
	alwaysShowControls: true,
	alwaysShowHours: true,
	success: function (media, ele, player) {
//		player.pause();
		player.play();
	}
});

function play() {
	player.play();
}


//var player1 = new Vue({
//    el: '#player1',
//    data: {
//    	currentVideo: 'index.m3u8',
//    },
//    ready:function(){
//        var self = this;
//        self.currentVideo = 'index.m3u8';
//        alert("player1 = " + self.currentVideo);
//    }
//})

//register the grid component
Vue.component('demo-videos', {
  template: '<div id="commonVideo" :style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="play">\
	  	  		<div class="articleListItemImage">\
					<img class="large" :src="http://localhost:8081/WebVOD2/{{videos.jpg}}" />\
				</div>\
				<p :id="\'commonVideo_\' + displayOrder" class="title">{{videos.name}}</p>\
	  		</div>',
	props: {
		videos: Object,
//		displayOrder: Number
	},
	mounted: function(){
	},
    methods: {
    	play:function(link) {
    		alert(link);
    		player.setSrc (link)
    		player.load();
    		play();
    	}
    }
})

var table_navi = new Vue({
    el: '#navi_tb',
    data: {
//    	urlList:[
// 	    		{id: '1', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
// 	    		{id: '2', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'},
// 	    		{id: '3', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
// 	    		{id: '4', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'}
// 	    	]
 	    	urlList:[]
    },
//    ready:function(){
//        var self = this;
        //Disable cache in order to refresh data when browser back button clicked.
//        $.ajaxSetup({cache:false});
//        $.ajax({
//        	url: 'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
//            method: 'GET',
//            statusCode:{
//                404: function () {
//                    alert("您还没有创建video");
//                }
//            },
//            success: function (data) {
//                self.urlList = data;
//            }
//        });
//    },
    mounted:  function(){
		this.getVideos();
	},
    methods: {
    	getVideos:function(){
    		var self = this;
    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
    		  .then(function (response) {
    			  self.urlList = data;
    		    console.log(response);
    		  });
    	}
    }
})

var table_main = new Vue({
	    el: '#url_tb',
	    data: {
//	    	urlList:[
//	    		{id: '1', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
//	    		{id: '2', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'},
//	    		{id: '3', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
//	    		{id: '4', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'}
//	    	]
	    	urlList:[],
	    },
//	    ready:function(){
//	        var self = this;
//	      //Default setting: enable cache in order not to refresh data when browser back button clicked.
//	        $.ajaxSetup({cache:true});
//	        $.ajax({
//	        	url: 'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
//	            method: 'GET',
//	            statusCode:{
//	                404: function () {
//	                    alert("您还没有创建video");
//	                }
//	            },
//	            success: function (data) {
//	                self.urlList = data;
//	            }
//	        });
//	    },
	    mounted:  function(){
			this.getVideos();
		},
	    methods: {
	    	getVideos:function(){
	    		var self = this;
	    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
	    		  .then(function (response) {
	    			  self.urlList = data;
	    		    console.log(response);
	    		  });
	    	},
	    	play:function(link) {
	    		alert(link);
	    		player.setSrc (link)
	    		player.load();
	    		play();
	    	}
	    }
})

var div_footer = new Vue({
    el: '#url_footer',
    data: {
    	videoList:[],
        base:'http://localhost:8081/WebVOD2/'
    },
    mounted:  function(){
		this.getVideos();
	},
    methods: {
    	getVideos:function(){
    		var self = this;
    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
    		  .then(function (response) {
    			self.videoList = data;
    		    console.log(response);
    		  });
    	},
    	play:function(link) {
    		alert(link);
    		player.setSrc (link)
    		player.load();
    		play();
    	}
    }
})
