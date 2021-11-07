var app = {
	el : "#app",
	data : {
	    joinEmail : "",
	    joinName : "",
	    joinPassword : ""

	},
	watch : {
	},
	mounted(){
	},
	methods : {
	    onSubmit : function(){
	        //alert("login process")
	    },
	    joinModal : function(){
	        $(".joinModal").modal("show");
	    },
	    join : function(){
            axios({
                method: 'post',
                url: '/users',
                data: {
                    email : app.joinEmail,
                    name : app.joinName,
                    password : app.joinPassword
                    //인자로 보낼 데이터
                }
            }).then(response => {
                $(".joinModal").modal("hide");
                alert("가입완료");
                console.log(response);
            }).catch(error => {
                alert("error");
                console.log(error);
            })
	    }
	}
};

$(function(){
	app = new Vue(app);
	Vue.nextTick(function(){
	});
});
