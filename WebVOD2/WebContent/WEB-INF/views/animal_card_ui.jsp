<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String playerPath = basePath + "player";
String audioPath  = basePath + "audio";
String imagePath = basePath + "images";
session.setAttribute("basePath", basePath);
session.setAttribute("playerPath", playerPath);
session.setAttribute("audioPath", audioPath);
session.setAttribute("imagePath", imagePath);
%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> -->
<!-- 	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Animal Card UI Sample Page</title>
	<link rel="stylesheet" href="${basePath}static/css/reset.css" />
	<link rel="stylesheet" href="${basePath}static/css/default.css" />
	<link rel="stylesheet" href="${basePath}static/css/styles.css" />
    <link rel="stylesheet" href="${playerPath}/mediaelementplayer.css" />
    <script src="//cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
    <script src="${basePath}static/js/jquery-1.11.0.min.js"></script>
	<!-- import vue 2.5.16 -->
    <script src="${basePath}static/js/vue.js"></script>
<!--     <script src="//cdn.bootcss.com/vue/1.0.26/vue.js"></script> -->
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
<%-- 	  		<audio id="my-player" src="${audioPath}" autostart="false" loop="false" type="audio/mp3" controls preload hidden=true> --%>
	  		<audio id="my-player" preload="auto" hidden=true>
	  			<source src="${audioPath}/animal/baboon_monkey.mp3" type="audio/mpeg"/>
				<source src="${audioPath}/animal_nm_en/baboon_monkey_en.wav" type="audio/wav"/>
<!-- 				<source src="" type="audio/ogg"/> -->
	  		</audio>
	  	</div>

	    <div class="card">
			<!-- 中央パネル:メディアプレイヤー -->
			<div class="products" align="center" style="border: 0px;float">
					<div id="animal_card">
						<animal-card-post  v-for="animal in animalList" v-bind:key="animal.id" v-bind:animal="animal"></animal-card-post>
					</div>
			</div>

	  		<div class="footer"><a id="prev" href="#" ripple="" ripple-color="#666666" class="btn">Prev</a><a id="next" href="#" ripple="" ripple-color="#666666" class="btn">Next</a></div>
        </div>

	<footer class="related">
		<p>HLS Server by Tim.Zhang@2018</p>
	</footer>
</article>
	<script src="<%=basePath%>static/js/animal_common.js" type="text/javascript" charset=”utf-8″></script>
	<script src="<%=basePath%>static/js/animal_card_ui.js" type="text/javascript" charset=”utf-8″></script>
	<script type="text/javascript">
	$(document).ready(function () {
		onLoad();
	});
	</script>
</body>
</html>