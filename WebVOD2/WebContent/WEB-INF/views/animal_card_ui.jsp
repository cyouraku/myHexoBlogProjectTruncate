<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String playerPath = basePath + "player";
String audioPath  = basePath + "audio" + "/animal/" + "test.mp3";;
String imagePath = basePath + "images";
session.setAttribute("basePath", basePath);
session.setAttribute("playerPath", playerPath);
session.setAttribute("audioPath", audioPath);
session.setAttribute("imagePath", imagePath);
%>
<!DOCTYPE html>
<html>
<head>
<!-- 	<meta charset="UTF-8"> -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Animal Card UI Sample Page</title>
	<link rel="stylesheet" href="${basePath}static/css/reset.css" />
	<link rel="stylesheet" href="${basePath}static/css/default.css" />
	<link rel="stylesheet" href="${basePath}static/css/styles.css" />
    <link rel="stylesheet" href="${playerPath}/mediaelementplayer.css" />
    <script src="${basePath}static/js/jquery-1.11.0.min.js"></script>
<%--     <script src="${basePath}static/js/vue.js"></script> --%>
    <script src="//cdn.bootcss.com/vue/1.0.26/vue.js"></script>
    <script src="${playerPath}/mediaelement-and-player.js"></script>
<style>
.mejs-controls {
	display: none !important;
}
</style>
</head>
<body>
<article class="htmleaf-container">
		<header class="htmleaf-header">
			<h1>Material Design Animal Card</h1>
<!-- 			<p><div  id = "showTime" align="center" ></div></p> -->
		</header>

		<!--${audioPath}-->
	  	<div style="display:none">
	  		<video id="my-player" src="${audioPath}" type="audio/mp3" autostart="false" loop="false" type="audio/mp3" controls preload hidden=true>
	  		</video>
	  	</div>

	    <div class="card">
			<!-- 中央パネル:メディアプレイヤー -->
			<div class="products" align="center" style="border: 0px;float">
					<div id="animal_card">
						<animal-card-post  v-for="animal in animalList" v-bind:key="animal.id" v-bind:animal="animal"></animal-post>
					</div>
			</div>

	  		<div class="footer"><a id="prev" href="#" ripple="" ripple-color="#666666" class="btn">Prev</a><a id="next" href="#" ripple="" ripple-color="#666666" class="btn">Next</a></div>
        </div>

	<footer class="related">
		<p>HLS Server by Tim.Zhang@2018</p>
	</footer>
</article>
	<script src="<%=basePath%>static/js/animal_common.js" type="text/javascript"></script>
	<script src="<%=basePath%>/static/js/animal_card_ui.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(document).ready(function () {
	    var getProductHeight = $('.product.active').height();
	    $('.products').css({ height: getProductHeight });

	    var productItem = $('.product'), productCurrentItem = productItem.filter('.active');
	    initial();
        function initial(){
            var nextItem = productCurrentItem.next();
            productCurrentItem.removeClass('active');
            if (nextItem.length) {
                productCurrentItem = nextItem.addClass('active');
            } else {
                productCurrentItem = productItem.first().addClass('active');
            }
            calcProductHeight();
            animateContentColor();
        }

	    function calcProductHeight() {
	        getProductHeight = $('.product.active').height();
	        $('.products').css({ height: getProductHeight });
	    }
	    function animateContentColor() {
	        var getProductColor = $('.product.active').attr('product-color');
	        $('body').css({ background: getProductColor });
	        $('.title').css({ color: getProductColor });
	        $('.btn').css({ color: getProductColor });
	    }

	    $('#next').on('click', function (e) {
	        e.preventDefault();
	        var nextItem = productCurrentItem.next();
	        productCurrentItem.removeClass('active');
	        if (nextItem.length) {
	            productCurrentItem = nextItem.addClass('active');
	        } else {
	            productCurrentItem = productItem.first().addClass('active');
	        }
	        calcProductHeight();
	        animateContentColor();
	        //stop play mp3
	        player.pause();
	    });
	    $('#prev').on('click', function (e) {
	        e.preventDefault();
	        var prevItem = productCurrentItem.prev();
	        productCurrentItem.removeClass('active');
	        if (prevItem.length) {
	            productCurrentItem = prevItem.addClass('active');
	        } else {
	            productCurrentItem = productItem.last().addClass('active');
	        }
	        calcProductHeight();
	        animateContentColor();
	        //stop play mp3
	        player.pause();
	    });
	    $('[ripple]').on('click', function (e) {
	        var rippleDiv = $('<div class="ripple" />'), rippleSize = 60, rippleOffset = $(this).offset(), rippleY = e.pageY - rippleOffset.top, rippleX = e.pageX - rippleOffset.left, ripple = $('.ripple');
	        rippleDiv.css({
	            top: rippleY - rippleSize / 2,
	            left: rippleX - rippleSize / 2,
	            background: $(this).attr('ripple-color')
	        }).appendTo($(this));
	        window.setTimeout(function () {
	            rippleDiv.remove();
	        }, 1900);
	    });
	});
	</script>
</body>
</html>