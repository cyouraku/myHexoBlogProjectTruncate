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

Vue.component('vehicle-card-post', {
	template: '<div  v-bind:product-id="\'commonVehicle_\' + vehicle.id" product-color="#C4C8CB" class="product">\
		 <div class="thumbnail"><img v-bind:src="\'http://localhost:8080/WebVOD2/\' + vehicle.jpg" v-on:click="playAudio(\'http://localhost:8080/WebVOD2/\' + vehicle.effect)"/></div>\
		 <h1 class="title" v-on:click="playAudio(\'http://localhost:8080/WebVOD2/\' + vehicle.speak)">{{vehicle.name}}</h1>\
		 <p class="description">{{vehicle.description}}</p></div>',
	props: ['vehicle'],
	methods:{
		playAudio:function(link){
			play(link);
		}
	}
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
	    el: '#animal_card',
	    data: {
	    	animalList:[]
	    },
	    mounted:  function(){
			this.getAjaxAnimals();
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
	      				self.animalList = response;
	      				console.log(response);
	      			},
	      			error:function(){
	      				console.log('web service failure!');
	      			}
	      		});
	    	}
	    }
})

//var vm2 = new Vue({
//    el: '#vehicle_card',
//    data: {
//    	vehicleList:[]
//    },
//    mounted:  function(){
//		this.getAjaxVehicles();
//	},
//    methods: {
//    	//Tested:Chrome OK,IE re-rendering failed!
//    	getAjaxVehicles:function(){
//      		var self = this;
//      		$.ajax({
//      			url:'http://localhost:8080/WebServiceCXF-postgreSql/services/rest/animalService/getAllAnimals',
//      			method:'get',
//      			statusCode:{
//      				404:function(){
//      					alert("Resource not found!")
//      				}
//      			},
//      			success:function(response) {
//      				self.vehicleList = response;
//      				console.log(response);
//      			},
//      			error:function(){
//      				console.log('web service failure!');
//      			}
//      		});
//    	}
//    }
//})

vm.$watch('animalList',function(nval,oval){
	console.log('animalList oval size = ' + this.animalList.length);
	console.log('animalList nval size = ' + this.animalList.lenght);
	onLoad();
})

//vm2.$watch('vehicleList',function(nval,oval){
//	console.log('vehicleList oval size = ' + this.vehicleList.length);
//	console.log('vehicleList nval size = ' + this.vehicleList.lenght);
//	onLoad();
//})