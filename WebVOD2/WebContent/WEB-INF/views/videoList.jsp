
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String cssPath = basePath + "static/css";
session.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>WEB VOD APP DEMO</title>
    <link rel="stylesheet" href="<%=basePath%>static/css/grid-template.css" />
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.js"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.2/vue.js"></script>
<!-- component template -->
<script type="text/x-template" id="grid-template">
  <table>
    <thead>
      <tr>
        <th v-for="key in columns"
          @click="sortBy(key)"
          :class="{ active: sortKey == key }">
          {{ key | capitalize }}
          <span class="arrow" :class="sortOrders[key] > 0 ? 'asc' : 'dsc'">
          </span>
        </th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="entry in filteredData">
        <td v-for="key in columns">
          {{entry[key]}}
        </td>
      </tr>
    </tbody>
  </table>
</script>
</head>
<body>
<!-- <div align="center">Video List Page</div> -->

		<!-- demo root element -->
<!-- 				<div id="demo" align="center"> -->
<!-- 					<p>{{ message }}</p> -->
<!-- 					<form id="search"> -->
<!-- 						Search <input name="query" v-model="searchQuery"> -->
<!-- 					</form> -->
<!-- 					<p></p> -->
<!-- 					<demo-grid :data="gridData" :columns="gridColumns" -->
<!-- 						:filter-key="searchQuery"> </demo-grid> -->
<!-- 				</div> -->
<div class="panel-group">
	<div class="panel panel-default">
		<div  class="panel-heading" id = "showTime" align="center" ></div>
	</div>

<div class="panel panel-default">
	<div class="row">
		<div class="panel panel-default">
			<div class="row">
				<div class="col-md-3">
					<div class="panel-body" align="center">
						<div id="demo" align="center">
							<p>{{ message }}</p>
							<form id="search">
								Search 1 <input name="query" v-model="searchQuery">
							</form>
							<p></p>
							<demo-grid :data="gridData" :columns="gridColumns"
								:filter-key="searchQuery"> </demo-grid>
							<p></p>
							<form id="search2">
								Search 2 <input name="query" v-model="searchQuery2">
							</form>
							<p></p>
							<demo-grid :data="nmList" :columns="nmColumns"
								:filter-key="searchQuery3"> </demo-grid>
							<p></p>
							<form id="search3">
								Search 3 <input name="query" v-model="searchQuery3">
							</form>
							<p></p>
							<demo-grid :data="urlList" :columns="urlColumns"
								:filter-key="searchQuery3"> </demo-grid>
							<p></p>
							<form id="search4">
								Search 4 <input name="query" v-model="searchQuery4">
							</form>
							<p></p>
							<demo-grid :data="jpgList" :columns="jpgColumns"
								:filter-key="searchQuery4"> </demo-grid>
							<p></p>
<!-- 							<form id="search5"> -->
<!-- 								Search 5 <input name="query" v-model="searchQuery5"> -->
<!-- 							</form> -->
<!-- 							<p></p> -->
<!-- 							<demo-grid :data="docList" :columns="docColumns" -->
<!-- 								:filter-key="searchQuery5"> </demo-grid> -->
<!-- 							<p></p> -->
<!-- 									<p>solr/videos</p> -->
<!-- 									<ul> -->
<!-- 										<li v-for="doc in docs"> -->
<!-- 												<a>{{doc.id}}</a><span></span> -->
<!-- 												<a>{{doc.name}}</a><span></span> -->
<!-- 												<a>{{doc.link}}</a><span></span> -->
<!-- 												<a>{{doc.jpg}}</a><span></span> -->
<!-- 												<a>{{doc._version_}}</a><span></span> -->
<!-- 										</li> -->
<!-- 									</ul> -->
						</div>
						<div  id = "showVersions" align="center" ></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

	<div class="panel panel-default" >
		<div  class="panel-footer" align="center" >Designed by Tim.Zhang@2018</div>
	</div>
</div>
<script src="<%=basePath%>static/js/videoList.js" type="text/javascript"></script>
</body>
</html>