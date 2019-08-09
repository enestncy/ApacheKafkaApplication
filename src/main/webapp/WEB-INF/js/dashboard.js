window.onload = function () {

    var dps_Istanbul = [];
    var dps_London = [];
    var dps_Tokyo = [];
    var dps_Moscow = [];
    var dps_Beijing = [];

    var chart = new CanvasJS.Chart("chartContainer",
        {
            zoomEnabled: true,

            title:{
                text: "LOG DASHBOARD"
            },

            data: [
                {
                    type: "line",
                    name:"Istanbul",
                    visible: true,
                    showInLegend: true,
                    xValueType: "dateTime",
                    dataPoints: dps_Istanbul
                },

                {
                    type: "line",
                    name:"London",
                    showInLegend: true,
                    visible: true,
                    xValueType: "dateTime",
                    dataPoints: dps_London
                },
                {
                    type: "line",
                    name:"Tokyo",
                    showInLegend: true,
                    visible: true,
                    xValueType: "dateTime",
                    dataPoints: dps_Tokyo
                },
                {
                    type: "line",
                    name:"Moscow",
                    showInLegend: true,
                    visible: true,
                    xValueType: "dateTime",
                    dataPoints: dps_Moscow
                },
                {
                    type: "line",
                    name:"Beijing",
                    showInLegend: true,
                    visible: true,
                    xValueType: "dateTime",
                    dataPoints: dps_Beijing
                }
            ]
        });

    chart.render();


    var yVal_Istanbul = 0;
    var yVal_London = 0;
    var yVal_Tokyo = 0;
    var yVal_Moscow = 0;
    var yVal_Beijing = 0;

    setInterval(function() {

        var xVal = getNow() - 7200000;

        $.ajax({

            type: 'POST',
            url: 'index',
            success: function (data) {
                var list = [];
                list = data;
                var count = 20;

                count = count || 1;

                for (var j = 0; j < count; j++) {
                    yVal_Istanbul = list[0];
                    yVal_London = list[1];
                    yVal_Tokyo = list[2];
                    yVal_Moscow = list[3];
                    yVal_Beijing = list[4];

                    dps_Istanbul.push({
                        x: xVal,
                        y: yVal_Istanbul
                    });
                    dps_London.push({
                        x: xVal,
                        y: yVal_London
                    });
                    dps_Tokyo.push({
                        x: xVal,
                        y: yVal_Tokyo
                    });
                    dps_Moscow.push({
                        x: xVal,
                        y: yVal_Moscow
                    });
                    dps_Beijing.push({
                        x: xVal,
                        y: yVal_Beijing
                    });
                }

                if (dps_Istanbul.length > 20) {
                    dps_Istanbul.shift();
                }
                if (dps_London.length > 20) {
                    dps_London.shift();
                }
                if (dps_Tokyo.length > 20) {
                    dps_Tokyo.shift();
                }
                if (dps_Moscow.length > 20) {
                    dps_Moscow.shift();
                }
                if (dps_Beijing.length > 20) {
                    dps_Beijing.shift();
                }

                chart.render();

            },
            error: function () {
                //alert("Bir Hata Oluştu");
            }

        });
    },1800000);
};



function getNow() {

    var d = new Date();

    var t = d.getHours() * 60 * 60 * 1000;
    t = t + d.getMinutes() * 60 * 1000;
    return t;
}


