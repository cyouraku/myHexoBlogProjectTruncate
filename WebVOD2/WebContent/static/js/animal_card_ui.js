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
//	    	this.getAnimals();
			this.getAjaxAnimals();
		},
	    methods: {
	    	//IE 11 does not support Promise response!Chrome OK!
//	    	getAnimals:function(){
//	    		var self = this;
//	    		var config = { headers: {'Access-Control-Allow-Origin': '*', 'content-type': 'application/x-www-form-urlencoded'} };
//	    		axios.get('http://localhost:8080/WebServiceCXF-postgreSql/services/rest/animalService/getAllAnimals', config)
//	    		.then(function (response) {
//	    		    // handle success
//	    			  self.animalList = response.data;
//		    		  console.log(response.data);
//	    		})
//	    		.catch(function (error) {
//	    		    // handle error
//	    		    console.log('web service failure! Error : ' + error);
//	    		}).then(function () {
//	    		    // always executed
//	    			console.log('axios finished!');
//	    		});
//	    	},
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
	      				self.animalList = this.testList;
	      				console.log('web service failure!');
	      			}
	      		});
	    	}
	    }
})

vm.$watch('animalList',function(nval,oval){
	console.log('oval size = ' + this.animalList.length);
	console.log('nval size = ' + this.animalList.lenght);
	onLoad();
})