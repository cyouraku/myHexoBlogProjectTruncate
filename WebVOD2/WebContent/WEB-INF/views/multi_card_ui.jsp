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
	<title>Multi Card UI Sample Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<base href="/">
	<link rel="stylesheet" href="${basePath}static/css/reset.css" />
	<link rel="stylesheet" href="${basePath}static/css/default.css" />
	<link rel="stylesheet" href="${basePath}static/css/styles.css" />
    <link rel="stylesheet" href="${playerPath}/mediaelementplayer.css" />
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.5.0-alpha.1/jquery.mobile-1.5.0-alpha.1.min.css">
	<script src="//code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="//code.jquery.com/mobile/1.5.0-alpha.1/jquery.mobile-1.5.0-alpha.1.min.js"></script>
	<!-- import vue 2.5.16 -->
    <script src="${basePath}static/js/vue.js"></script>
    <script src="${playerPath}/mediaelement-and-player.js"></script>
<style>
.mejs-controls {
	display: none !important;
}
</style>
</head>
<body>
<div data-role="page" id="page1">
  <div data-role="header" align="center">
    <h1>jQuery Mobile Example</h1>
  </div>
    <div role="main" class="ui-content">
    <div data-role="tabs" id="dynamic-component-demo" class="demo">

      <div style="display:none">
	  	<audio id="my-player" preload="auto" hidden=true>
	  		<source src="${audioPath}/animal/baboon_monkey.mp3" type="audio/mpeg"/>
	  	</audio>
	  </div>

<!-- 	  <button -->
<!-- 	  	v-for="tab in tabs" -->
<!-- 	  	v-bind:key="tab" -->
<!-- 	  	v-bind:class="['tab-button',{active:currentTab === tab}]" -->
<!-- 	  	v-on:click="currentTab = tab" -->
<!-- 	  >{{tab}}</button> -->
	  <div data-role="navbar">
	   <ul>
	      <li v-for="tab in tabs" v-bind:key="tab">
<!--  <a v-bind:href="'#' + 'tab-' + currentTab.toLowerCase()" v-bind:class="['ui-btn-active',{active:currentTab === tab}]" v-on:click="currentTab = tab">{{tab}}</a> -->
	  	  <a v-bind:class="['ui-btn-active',{active:currentTab === tab}]" v-on:click="currentTab = tab" >{{tab}}</a>
	      </li>
<!--           <li><a href="#fragment-1" class="ui-btn-active">One</a></li> -->
        </ul>
       </div>

	  <component v-bind:is="currentTabComponent" class="tab" v-bind:animallist="animallist">
	  </component>
	</div>
	</div>


  <div data-role="footer" align="center">
    <p>HLS Server by Tim.Zhang@2018</p>
  </div>
</div>
	<script src="<%=basePath%>static/js/multi_common.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>static/js/multi_card_ui.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>