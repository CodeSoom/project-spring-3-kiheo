var app = {
	el : "#app",
	data : {
	    airports : [],
	    fares : []
	},
	watch : {
	},
	mounted(){
	},
	methods : {
	    selectAirports : function(){
            axios({
                method: 'post',
                url: '/airport',
                data: {
                }
            }).then(response => {
                var airports = response.data;
                airports = _.orderBy(airports, ['airportNm'], ['asc']);
                app.airports = airports;
            }).catch(error => {
                alert("error");
                console.log(error);
            })
	    },
	    selectFares : function(){
            axios({
                method: 'post',
                url: '/airport/fares',
                data: {
                    startDt : $("#startDt").val(),
                    departureAirport : $("#departure-airports .active")[0].id,
                    arriveAirport : $("#arrive-airports .active")[0].id
                }
            }).then(response => {
                console.log(response.data);
                var fares = response.data;
                fares = _.orderBy(fares, ['economyCharge'], ['asc']);
                fares.forEach(o => {
                    o.deptInfo = o.depAirportNm + " " +o.depPlandTime.toString().substring(8,10)+":"+o.depPlandTime.toString().substring(10,12);
                    o.arrvInfo = o.arrAirportNm + " " +o.arrPlandTime.toString().substring(8,10)+":"+o.arrPlandTime.toString().substring(10,12);
                })
                app.fares = fares
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
	    app.selectAirports();

        $("#startDt").datepicker({
            format : "yyyymmdd",
            language : "kr",
            autoclose : true,
            todayHighlight : true
        });
        setTimeout(function() {
            $("#departure-airports a").click(function(e){
                e.preventDefault()
                $(this).tab('show')
            });

            $("#arrive-airports a").click(function(e){
                e.preventDefault()
                $(this).tab('show')
            });
        }, 500);

	});
});
