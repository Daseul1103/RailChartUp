<script>
console.log("도넛 차트");
//도넛 차트
var pieChart = c3.generate({
	bindto: '#pieChart',
	size: {
        height: 200,
        width: 600,
    },
    data: {
        columns: [
            ['data1', 30],
            ['data2', 120],
        ],
        type : 'donut',
        onclick: function (d, i) { console.log("onclick", d, i); },
        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    },
    donut: {
        title: "Iris Petal Width"
    }
});

</script>