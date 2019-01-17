<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String playerPath = basePath + "player";
String videoPath  = basePath + "video" + "/" + "index.m3u8";
session.setAttribute("basePath", basePath);
session.setAttribute("playerPath", playerPath);
session.setAttribute("videoPath", videoPath);
%>
<!DOCTYPE html>
<html>
<title>HLS Player</title>
<head>
<%--     <link rel="stylesheet" href="<%=basePath%>static/css/grid-template.css" /> --%>
	<link rel="stylesheet" href="${basePath}static/css/player.css" />
    <link rel="stylesheet" href="${playerPath}/mediaelementplayer.css" />
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
    <script src="//cdn.bootcss.com/vue/1.0.26/vue.js"></script>
<!--     <script src="//cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script> -->
    <script src="//cdn.bootcss.com/vue/2.4.2/vue.js"></script>
    <script src="${playerPath}/mediaelement-and-player.js"></script>
<style>
.mejs-controls {
	display: none !important;
}
</style>
</head>
<body>
<div class="panel-group">
	<div class="panel panel-default" >
		<div  class="panel-heading" id = "showTime" align="center" style="margin-top : 10px"></div>
	</div>

	<!-- Default panel contents -->
	<div class="panel panel-default" style="border: 0px;">
	    <div class="row">
			<!-- 左側パネル:メディアリンク -->
			<div class="col-sm-3" align="left" style="border: 0px;">
<!--          				<div id="post_list"  style="margin-top: 2px;"> -->
<!--          				<blog-post-modified  v-for="post in postList" v-bind:key="post.id" v-bind:post="post"></blog-post-modified> -->
<!--          				</div> -->
			</div>

			<!-- 中央パネル:メディアプレイヤー -->
			<div class="col-sm-6" align="center" style="border: 0px;float">
				<div class="row">
         			<video id="my-player" width="640" height="360" controls="controls" style="align: center">
						<source type="application/x-mpegURL" src="${videoPath}">
	  				</video>
	  			</div>
<!-- 	  			<div class="row"> -->
<!-- 	  					<div id="blog_list"  style="margin-top: 2px;"> -->
<!--          					<blog-post  v-for="post2 in postList2" v-bind:key="post2.id" v-bind:post="post2"></blog-post> -->
<!--          				</div> -->
<!-- 	  			</div> -->
	  			<div class="row">
	  					<div id="video_list"  style="margin-top: 2px;">
         					<video-post  v-for="video in videoList" v-bind:key="video.id" v-bind:post="video"></video-post>
         				</div>
	  			</div>
<!-- 	  			<div class="row"> -->
<!-- 	  					<div id="tv_list"  style="margin-top: 2px;"> -->
<!--          					<tv-post  v-for="post1 in postList1" v-bind:key="post1.id" v-bind:post="post1"></tv-post> -->
<!--          				</div> -->
<!-- 	  			</div> -->
			</div>

			<!-- 右側パネル:メディアリンク -->
			<div class="col-sm-3" align="right" style="border: 0px;">
<!--          				<div id="post_list2"  style="margin-top: 2px;"> -->
<!--          				<blog-post  v-for="post in postList" v-bind:key="post.id" v-bind:post="post"></blog-post> -->
<!--          				</div> -->
			</div>

        </div>
	</div>

	<div class="panel panel-default" >
		<div  class="panel-footer navbar-fixed-bottom" align="center" style="margin-bottom : 10px">HLS Server by Tim.Zhang@2018</div>
	</div>
</div>

<script src="<%=basePath%>static/js/player_common.js" type="text/javascript"></script>
<script src="<%=basePath%>static/js/player.js" type="text/javascript"></script>
</body>
</html>