var app = {
	el : "#app",
	data : {
	},
	watch : {
	},
	mounted(){
	},
	methods : {
	    init : function(){
	        axios({
	            method: 'post', //통신 방식
	            url: '/main', //통신할 페이지
	            data: {
                    //인자로 보낼 데이터
                }
	        }).then(response => {
                  //document.getElementById('boom').innerText=response.data.num;
                  console.log(response);
              }).catch(error => {
                  //document.getElementById('boom').innerText='error';
                  console.log(error);
              })

	    },
	    onSubmit : function(){
	        alert("login process")
	    }
	}
};

$(function(){
	app = new Vue(app);
	Vue.nextTick(function(){
	    //app.init();
		$(window).resize(function() {
		});

	});
});
