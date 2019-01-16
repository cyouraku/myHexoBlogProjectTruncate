//var myDate;

//setInterval(function() {
//	myDate = new Date();
//	showTime.innerHTML = myDate.toLocaleString();
//}, 1000)

//var player = new MediaElementPlayer('#my-player', {
//	features: ['playpause','loop','progress','current','duration','volume','fullscreen'],
//	loop: false,
//	clickToPlayPause: true,
//	alwaysShowControls: true,
//	alwaysShowHours: true,
//    success: function(mediaElement, originalNode) {
//    	player = mediaElement;
//    }
//});

//function play(link) {
//	alert(link);
//	player.pause();
//	player.setSrc(link);
//	player.load();
//	player.play();
//}


Vue.component('animal-card-post', {
	template: '<div  v-bind:product-id="\'commonAnimal_\' + animal.id" product-color="#C4C8CB" class="product">\
		 <div class="thumbnail"><img v-bind:src="\'http://localhost:8080/WebVOD2/\' + animal.jpg" v-on:click="playAudio(\'http://localhost:8080/WebVOD2/\' + animal.link)"/></div>\
		 <h1 class="title">{{animal.name}}</h1>\
		 <p class="description">This is {{animal.name}}</p></div>',
	props: ['animal'],
	methods:{
		playAudio:function(link){
			play(link);
		}
	}
})

var table_main = new Vue({
	    el: '#animal_card',
	    data: {
	    	animalList:[
	    		{id: '1', name : 'animal_1',  link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 1.svg'},
	    		{id: '2', name : 'animal_10', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 10.svg'},
	    		{id: '3', name : 'animal_11', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 11.svg'},
	    		{id: '4', name : 'animal_12', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 12.svg'},
	    		{id: '5', name : 'animal_13', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 13.svg'},
	    		{id: '6', name : 'animal_14', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 14.svg'},
	    		{id: '7', name : 'animal_15', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 15.svg'},
	    		{id: '8', name : 'animal_16', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 16.svg'},
	    		{id: '9', name : 'animal_17', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 17.svg'},
	    		{id: '10', name : 'animal_18', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 18.svg'},
	    		{id: '11', name : 'animal_19', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 19.svg'},
	    		{id: '12', name : 'animal_20', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 20.svg'},
	    		{id: '13', name : 'animal_21', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 21.svg'},
	    		{id: '14', name : 'animal_22', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 22.svg'},
	    		{id: '15', name : 'animal_23', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 23.svg'},
	    		{id: '16', name : 'animal_24', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 24.svg'},
	    		{id: '17', name : 'animal_25', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 25.svg'},
	    		{id: '18', name : 'animal_26', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 26.svg'},
	    		{id: '19', name : 'animal_27', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 27.svg'},
//	    		{id: '20', name : 'animal_28', link : 'audio/animal/baboon_monkey.mp3',jpg : 'images/animal/Artboard 28.svg'},
	    		{id: '20', name : 'baboon monkey', link : 'audio/animal/baboon_monkey.mp3',jpg : 'images/animal/baboon_monkey.jpg'},
	    		{id: '21', name : 'animal_29', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 29.svg'},
	    		{id: '22', name : 'animal_30', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 30.svg'},
	    		{id: '23', name : 'animal_31', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 31.svg'},
	    		{id: '24', name : 'animal_32', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 32.svg'},
	    		{id: '25', name : 'animal_33', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 33.svg'},
	    		{id: '26', name : 'animal_34', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 34.svg'},
	    		{id: '27', name : 'animal_35', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 35.svg'},
	    		{id: '28', name : 'animal_36', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 36.svg'},
	    		{id: '29', name : 'animal_37', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 37.svg'},
	    		{id: '30', name : 'animal_38', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 38.svg'},
	    		{id: '31', name : 'animal_39', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 39.svg'},
	    		{id: '32', name : 'animal_40', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 40.svg'},
	    		{id: '33', name : 'animal_41', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 41.svg'},
	    		{id: '34', name : 'animal_42', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 42.svg'},
	    		{id: '35', name : 'animal_43', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 43.svg'},
	    		{id: '36', name : 'animal_44', link : 'audio/animal/test.mp3',jpg : 'images/animal/Artboard 44.svg'}
	    	]
	    },
	    mounted:  function(){
			this.getAnimals();
		},
	    methods: {
	    	getAnimals:function(){
	    		var self = this;
//	    		var config = { headers: {'Access-Control-Allow-Origin': '*', 'content-type': 'application/x-www-form-urlencoded'} };
//	    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/videoService/getAllVideos', config)
//	    		  .then(function (response) {
//	    			self.animalList = response.data;
//	    		    console.log(response.data);
//	    		  });
	    		self.animalList = this.animalList;
	    	}
	    }
})