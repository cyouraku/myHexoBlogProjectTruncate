var myDate;
setInterval(function() {
	myDate = new Date();
	showTime.innerHTML = myDate.toLocaleString();
}, 1000)

var player = new MediaElementPlayer('#music', {
	loop: false,
	clickToPlayPause: true,
	alwaysShowControls: true,
	alwaysShowHours: true,
	success: function (media, ele, player) {
		player.pause();
	}
});

function play() {
	player.play();
}

var table_main = new Vue({
	    el: '#animal_tb',
	    data: {
	    	urlList:[
	    		{id: '1', name : 'animal_1', link : 'animal/test.mp3',jpg : 'animal/Artboard 1.svg'},
	    		{id: '2', name : 'animal_10', link : 'animal/test.mp3',jpg : 'animal/Artboard 10.svg'},
	    		{id: '3', name : 'animal_11', link : 'animal/test.mp3',jpg : 'animal/Artboard 11.svg'},
	    		{id: '4', name : 'animal_12', link : 'animal/test.mp3',jpg : 'animal/Artboard 12.svg'},
	    		{id: '5', name : 'animal_13', link : 'animal/test.mp3',jpg : 'animal/Artboard 13.svg'},
	    		{id: '6', name : 'animal_16', link : 'animal/test.mp3',jpg : 'animal/Artboard 16.svg'}
	    	]
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

var player1 = new Vue({
    el: '#music',
    data: {
    	currentAudio: 'animal/test.mp3'
    }
})
