var player = new MediaElementPlayer('#my-player', {
	features: ['playpause','loop','progress','current','duration','volume','fullscreen'],
	loop: false,
	clickToPlayPause: true,
	alwaysShowControls: true,
	alwaysShowHours: true,
    success: function(mediaElement, originalNode) {
    	player = mediaElement;
    }
});

function play(link) {
//	alert(link);
	player.pause();
	player.setSrc(link);
	player.load();
	player.play();
}