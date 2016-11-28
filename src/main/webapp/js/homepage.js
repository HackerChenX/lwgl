$(function() {
	pageInitModule.setWidth();
	pageInitModule.setSidebar();
	pageInitModule.setCarousel();
})
$(window).resize(function() {
	pageInitModule.setWidth();
})
$(window).scroll(function() {
	pageInitModule.setScrollToTop();
});
/*
 * init page when page load
 */
var pageInitModule = (function(mod) {
	mod.setCarousel = function(){
		try {
			$('.carousel').hammer().on('swipeleft', function() {
				$(this).carousel('next');
			});
			$('.carousel').hammer().on('swiperight', function() {
				$(this).carousel('prev');
			});
		} catch(e) {
			console.log("you mush import hammer.js and jquery.hammer.js to let the carousel can be touched on mobile");
		}
	};
	mod.setWidth = function() {
		if($(window).width() < 768) {
			$(".sidebar").stop().animate({
				left: -220
			});
			$(".maincontent").stop().animate({
				paddingLeft: 0				
			});
		} else {
			$(".sidebar").stop().animate({
				left: 0
			});
			$(".maincontent").stop().animate({
				paddingLeft: 220
			});
		}
	};
	mod.setScrollToTop = function() {
		var top = $(window).scrollTop();
		if(top < 60) {
			$('#goTop').fadeOut("slow");
		} else {
			$('#goTop').fadeIn("slow");
		}
	};
	mod.setSidebar = function(){
		$('[data-target="sidebar"]').click(function() {
			var asideleft = $(".sidebar").offset().left;
			if(asideleft == 0) {
				$(".sidebar").stop().animate({
					left: -220
					
				});
				$(".maincontent").stop().animate({
					paddingLeft: 0		
				});
			}else{
				$(".sidebar").stop().animate({
					left: 0
				});
				$(".maincontent").stop().animate({
					paddingLeft: 220		
				});
			}
		});	
		$(function(){
			$("ul.collapse > li").on("click",function(){
			   $(this).parent().addClass("in");
			   $(this).addClass("active");             
               $(this).parent().parent().addClass("active");
               $(this).siblings().removeClass("active");
               $(this).parent().parent().siblings().find("li").removeClass("active");
			});			
		});
		var _strcurrenturl = window.location.href;
		$("ul.collapse > li").each(function (){
			var lastPoint = $(this).find("a").prop("href").lastIndexOf(".");
			var menuLink =$(this).find("a").prop("href").substring(0,lastPoint);        
			if (_strcurrenturl.indexOf(menuLink) > -1&&menuLink!=""){
	           $(this).addClass("active");
	           $(this).parent().addClass("in");
	           $(this).parent().parent().addClass("active");
	           $(this).siblings().removeClass("active");
	           $(this).parent().parent().siblings().find("li").removeClass("active");
	           return false;
	        }										
	   });
	 };
	return mod;

})(window.pageInitModule || {});
//jquery回车键表单提交禁用
$("#form").keypress(function(e) {
  if (e.which == 13) {
    return false;
  }
});
