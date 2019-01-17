//Vue.component('BlogPost', {
//	template: '<div style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="">\
//				<div v-bind:id="\'commonVideo1_\' + post1.id" ><input type="image" v-bind:src="post1.jpg" v-on:click="this.play(post1.link)" style="margin-left: 2px;" /><p>{{pos1t.name}}</p>\</div>\
//			</div>',
//	props: ['post1'],
//})

Vue.component('video-post', {
	template: '<div style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="">\
				<div v-bind:id="\'commonVideo_\' + video.id" ><input type="image" v-bind:src="\'http://localhost:8080/WebVOD2/\' + video.jpg" v-on:click="this.play(\'http://localhost:8080/WebVOD2/\' + video.link)" style="margin-left: 2px;" /><p>{{video.name}}</p>\</div>\
			</div>',
	props: ['video'],
})

//Vue.component('TvPost', {
//	template: '<div style="{float: displayOrder == 1 ? \'right\' : \'none\'}" @click="">\
//				<div v-bind:id="\'commonVideo3_\' + post3.id" ><input type="image" v-bind:src="\'http://localhost:8080/WebVOD2/\' + post3.jpg" v-on:click="this.play(\'http://localhost:8080/WebVOD2/\' + post3.link)" style="margin-left: 2px;" /><p>{{post3.name}}</p>\</div>\
//			</div>',
//	props: ['post3'],
//})



//var div_post_list = new Vue({
//    el: '#tv_list',
//    data: {
//    	postList1:[]
//    },
//    mounted:  function(){
//		this.getVideos();
//	},
//    methods: {
//    	getVideos:function(){
//    		var self = this;
//    		var config = { headers: {'Access-Control-Allow-Origin': '*', 'content-type': 'application/x-www-form-urlencoded'} };
//    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos', config)
//    		  .then(function (response) {
//    			self.postList1 = response.data;
//    		    console.log(response.data);
//    		  });
//    	}
//    }
//})

//var div_post_list2 = new Vue({
//    el: '#blog_list',
//    data: {
//    	postList2:[]
//    },
//    mounted:  function(){
//		this.getVideos();
//	},
//    methods: {
//    	getVideos:function(){
//		var self = this;
//		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos')
//		  .then(function (data) {
//			self.videoList = data;
//		    console.log(data);
//		  });
//	},
//	play:function(link) {
//		alert(link);
//		player.setSrc (link)
//		player.load();
//		play();
//	}
//}
//})

var div_post_list3 = new Vue({
    el: '#video_list',
    data: {
    	videoList:[
    	   		{id: '1', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '2', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'},
    	   		{id: '3', name : 'gear1', link : 'http://localhost:8080/WebVOD2/video/Gear1/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear1.jpg'},
    	   		{id: '4', name : 'gear2', link : 'http://localhost:8080/WebVOD2/video/Gear2/prog_index.m3u8',jpg : 'http://localhost:8080/WebVOD2/images/Gear2.jpg'}
    	   		],
    	testList:[]
    },
//    mounted:  function(){
//    	this.getVideos();
//    },
    beforeMount: function() {
        this.getVideos();
    },
    methods: {
  	  getVideos: function() {
  		var self = this;
  		$.ajax({
  			url:'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos',
  			method:'get',
  			statusCode:{
  				404:function(){
  					alert("Resource not found!")
  				}
  			},
  			success:function(data) {
  				self.testList = data;
  				console.log(data);
  			}
  		});
  		self.videoList = this.videoList;
      }
    }
})
