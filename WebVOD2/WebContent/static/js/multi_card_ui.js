Vue.component('animal-card-post', {
	template: '<div  v-bind:product-id="\'commonAnimal_\' + animal.id" product-color="#C4C8CB" class="product">\
		 <div class="thumbnail"><img v-bind:src="\'http://localhost:8080/WebVOD2/\' + animal.jpg" v-on:click="playAudio(\'http://localhost:8080/WebVOD2/\' + animal.effect)"/></div>\
		 <h1 class="title" v-on:click="playAudio(\'http://localhost:8080/WebVOD2/\' + animal.speak)">{{animal.name}}</h1>\
		 <p class="description">{{animal.description}}</p></div>',
	props: ['animal'],
	methods:{
		playAudio:function(link){
			play(link);
		}
	}
})

Vue.component('tab-animal',{
	template:'<keep-alive><div id="tab-animal"><article class="htmleaf-container"><p>This is animal tab.</p>\
			<div class="card">\
				<div class="products" align="center" style="border: 0px;float">\
					<div id="card_list">\
						<animal-card-post  v-for="animal in animallist" v-bind:key="animal.id" v-bind:animal="animal"></animal-card-post>\
					</div>\
				</div>\
				<div class="footer"><a id="prev" href="#" ripple="" ripple-color="#666666" class="btn">Prev</a><a id="next" href="#" ripple="" ripple-color="#666666" class="btn">Next</a></div>\
			</div>\
			</article></div></keep-alive>',
			props: ['animallist']
})

Vue.component('tab-vehicle',{
	template:'<keep-alive><div id="tab-vehicle"><article class="htmleaf-container"><p>This is vehicle tab.</p>\
			<div class="card">\
				<div class="products" align="center" style="border: 0px;float">\
					<div id="card_list">\
					<animal-card-post  v-for="animal in animallist" v-bind:key="animal.id" v-bind:animal="animal"></animal-card-post>\
					</div>\
				</div>\
				<div class="footer"><a id="prev" href="#" ripple="" ripple-color="#666666" class="btn">Prev</a><a id="next" href="#" ripple="" ripple-color="#666666" class="btn">Next</a></div>\
			</div>\
			</article></div></keep-alive>',
			props: ['animallist']
})

Vue.component('tab-fruit',{
	template:'<keep-alive><div id="tab-fruit"><article class="htmleaf-container"><p>This is fruit tab.</p>\
			<div class="card">\
				<div class="products" align="center" style="border: 0px;float">\
					<div id="card_list">\
					<animal-card-post  v-for="animal in animallist" v-bind:key="animal.id" v-bind:animal="animal"></animal-card-post>\
					</div>\
				</div>\
				<div class="footer"><a id="prev" href="#" ripple="" ripple-color="#666666" class="btn">Prev</a><a id="next" href="#" ripple="" ripple-color="#666666" class="btn">Next</a></div>\
			</div>\
			</article></div></keep-alive>',
			props: ['animallist']
})

function exit(){
	return
}

function onLoad(){
	    var productItem = $('.product');
	    var productCurrentItem = productItem.first();
	    productCurrentItem.addClass('active');
	    var getProductHeight = $('.product.active').height();
	    initial();
        function initial(){
        	console.log('product item amount = ' + productItem.length)
        	productItem.first().addClass('active');
            getProductHeight = 341;
            $('.products').css({ height: getProductHeight });
            animateContentColor();
            productCurrentItem = productItem.filter('.active');
        }

	    function calcProductHeight() {
	        getProductHeight = $('.product.active').height();
	        $('.products').css({ height: getProductHeight });
	    }
	    function animateContentColor() {
	        var getProductColor = $('.product.active').attr('product-color');
//	        alert(getProductColor);
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
}

var vm = new Vue({
	    el: '#dynamic-component-demo',
	    data: {
	    	currentTab:'Animal',
	    	tabs:['Animal','Vehicle','Fruit'],
	    	animallist:[]
	    },
	    mounted:  function(){
			this.getAjaxAnimals();
		},
	    computed:{
	    	currentTabComponent:function(){
	    		return 'tab-' + this.currentTab.toLowerCase()
	    	}
	    },
	    methods: {
	    	//Tested:Chrome OK,IE re-rendering failed!
	    	getAjaxAnimals:function(){
	      		var self = this;
	      		$.ajax({
	      			url:'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/animalService/getAllAnimals',
	      			method:'get',
	      			statusCode:{
	      				404:function(){
	      					alert("Resource not found!")
	      				}
	      			},
	      			success:function(response) {
	      				self.animallist = response;
	      				console.log(response);
	      			},
	      			error:function(){
	      				console.log('web service failure!');
	      			}
	      		});
	    	}
	    }
})


vm.$watch('animallist',function(nval,oval){
	console.log('animallist oval size = ' + this.animallist.length);
	console.log('animallist nval size = ' + this.animallist.lenght);
	onLoad();
})

vm.$watch('currentTab',function(nval,oval){
	console.log('currentTab oval size = ' + this.animallist.length);
	console.log('currentTab size = ' + this.animallist.lenght);
	onLoad();
})

