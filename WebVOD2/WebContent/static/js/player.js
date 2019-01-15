var myDate;

//Vue.prototype.$axios = axios

setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

var player = new MediaElementPlayer('#my-player', {
	features: ['playpause','loop','progress','current','duration','volume','fullscreen'],
	loop: false,
	clickToPlayPause: true,
	alwaysShowControls: true,
	alwaysShowHours: true,
//	success: function (media, ele, player) {
//		player.play();
//	}
    success: function(mediaElement, originalNode) {
    	player = mediaElement;
    }
});

function play(link) {
	alert(link);
	player.pause();
	player.setSrc(link);
	player.load();
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
//Vue.component('demo-videos', {
//  template: '<div id="commonVideo">\
//	  			<p id="videos-jpg"><input type="image" src="http://localhost:8081/WebVOD2/{{videos.jpg}}" alt="{{videos.name}}" v-on:click="play(videos.link);" style="margin-left: 2px;" /></p>\
//				<p id="videos-name">{{videos.name}}</p>\
//	  		</div>',
//	props: ['videos'],
//    methods: {
//    	play:function(link) {
//    		alert(link);
//    		player.setSrc (link)
//    		player.load();
//    		play();
//    	}
//    }
//})

Vue.component('blog-post', {
	template: '<div style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="">\
				<div v-bind:id="\'commonVideo_\' + post.id" ><input type="image" v-bind:src="post.jpg" v-on:click="this.play(post.link)" style="margin-left: 2px;" /><p>{{post.name}}</p>\</div>\
			</div>',
	props: ['post'],
})

Vue.component('blog-post-modified', {
	template: '<div style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="">\
				<div v-bind:id="\'commonVideo_\' + post.id" ><input type="image" v-bind:src="\'http://localhost:8080/WebVOD2/\' + post.jpg" v-on:click="this.play(\'http://localhost:8080/WebVOD2/\' + post.link)" style="margin-left: 2px;" /><p>{{post.name}}</p>\</div>\
			</div>',
	props: ['post'],
})

//var table_navi = new Vue({
//    el: '#navi_tb',
//    data: {
// 	    	urlList:[]
//    },
//    mounted:  function(){
//		this.getVideos();
//	},
//    methods: {
//    	getVideos:function(){
//    		var self = this;
//    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
//    		  .then(function (response) {
//    			  self.urlList = data;
//    		    console.log(response);
//    		  });
//    	}
//    }
//})

//var table_main = new Vue({
//	    el: '#url_tb',
//	    data: {
//	    	urlList:[],
//	    },
//	    mounted:  function(){
//			this.getVideos();
//		},
//	    methods: {
//	    	getVideos:function(){
//	    		var self = this;
//	    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
//	    		  .then(function (response) {
//	    			  self.urlList = data;
//	    		    console.log(response);
//	    		  });
//	    	},
//	    	play:function(link) {
//	    		alert(link);
//	    		player.setSrc (link)
//	    		player.load();
//	    		play();
//	    	}
//	    }
//})

//var div_footer = new Vue({
//    el: '#url_footer',
//    data: {
//    	videoList:[
//    	   		{id: '1', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
//    	   		{id: '2', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'},
//    	   		{id: '3', name : 'gear1', link : 'Gear1/prog_index.m3u8',jpg : 'images/Gear1.jpg'},
//    	   		{id: '4', name : 'gear2', link : 'Gear2/prog_index.m3u8',jpg : 'images/Gear2.jpg'}
//    	   	]
//    },
//    mounted:  function(){
//		this.getVideos();
//	},
//    methods: {
//    	getVideos:function(){
//    		var self = this;
//    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
//    		  .then(function (response) {
//    			self.videoList = data;
//    		    console.log(response);
//    		  });
//    	},
//    	play:function(link) {
//    		alert(link);
//    		player.setSrc (link)
//    		player.load();
//    		play();
//    	}
//    }
//})

var div_post_list = new Vue({
    el: '#post_list',
    data: {
//    	postList:[
//      	   		{id: '1', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
//      	   		{id: '2', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'},
//      	   		{id: '3', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
//      	   		{id: '4', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'}
//      	   		],
    	postList:[],
    },
    mounted:  function(){
		this.getVideos();
	},
    methods: {
    	getVideos:function(){
    		var self = this;
    		var config = { headers: {'Access-Control-Allow-Origin': '*', 'content-type': 'application/x-www-form-urlencoded'} };
    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos', config)
    		  .then(function (response) {
    			self.postList = response.data;
    		    console.log(response.data);
    		  });
    	}
    }
})

var div_post_list2 = new Vue({
    el: '#post_list2',
    data: {
    	postList:[
    	   		{id: '1', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '2', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'},
    	   		{id: '3', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '4', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'}
    	   		]
    },
    methods: {
    	getVideos:function(){
    		var self = this;
    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
    		  .then(function (response) {
    			self.postList = data;
    		    console.log(response);
    		  });
    	}
    }
})

var div_post_list3 = new Vue({
    el: '#post_list3',
    data: {
    	postList:[
    	   		{id: '1', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '2', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'},
    	   		{id: '3', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '4', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'}
    	   		]
    },
    methods: {
    	getVideos:function(){
    		var self = this;
    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
    		  .then(function (response) {
    			self.postList = data;
    		    console.log(response);
    		  });
    	}
    }
})
