/**
 * Created by Administrator on 2016/11/19.
 */
//这个javascript里所用的方法都是利用ajax向servlet提交一个request，对返回的结果进行处理
function findshortest() {//查找最短路线
    $.ajax({
        cache: false,
        type: "POST",
        url:'servlet/FindShortestServlet',
        data:$('#findway').serialize(),
        async: true,
        dataType: "json",
        error:function(msg){
            alert("failure");
        },
        success: function(msg) {
            var info = eval(msg).message;
            alert(info);
        }
    });
}
function arrive() {//有车辆到达
    $.ajax({
        cache: false,
        type: "POST",
        url: 'servlet/CarManage',
        data: $('#arrive').serialize(),
        async: true,
        dataType: "json",
        error: function (msg) {
            alert("failure");
        },
        success: function (msg) {
            var info = eval(msg).message;
            alert(info);
        }
    });
}

function leave(){//有车辆离开
    $.ajax({
        cache: false,
        type: "POST",
        url:'servlet/CarManage',
        data:$('#leave').serialize(),
        async: true,
        dataType: "json",
        error:function(msg){
            alert("failure");
        },
        success: function(msg) {
            var info = eval(msg).message;
            alert(info);
        }
    });
}
function query() {//查询停车场当前停放车辆
    $.ajax({
        cache: false,
        type: "POST",
        url: 'servlet/CarManage',
        data: "type=query",
        async: true,
        dataType: "json",
        error: function (msg) {
            alert("failure");
        },
        success: function (msg) {
            var info = eval(msg).message;
            alert(info);
        }
    });
}
function  searchInDatabase(){//查找功能
    $.ajax({
        cache: false,
        type: "POST",
        url: 'servlet/SearchServlet',
        data: $('#search').serialize(),
        async: true,
        dataType: "json",
        error: function (msg) {
            alert("failure");
        },
        success: function (msg) {
            var state=eval(msg).find;
            if (state==true){//如果返回信息该节点找到
                var info = eval(msg).message;
                window.location.href="detail.jsp?name="+info;//跳转到相应页面
            }else {//
                alert('不存在该景点');
            }


        }
    });
}

function action(){//用于生成力导向图
    var width = 600;
    var height = 400;

    var svg = d3.select("#chart")
        .append("svg")
        .attr("width",width)
        .attr("height",height);

    var force = d3.layout.force()
        .nodes(nodes)		//指定节点数组
        .links(edges)		//指定连线数组
        .size([width,height])	//指定范围
        .linkDistance(150)	//指定连线长度
        .charge(-400);	//相互之间的作用力

    force.start();	//开始作用

    //添加连线
    var svg_edges = svg.selectAll("line")
        .data(edges)
        .enter()
        .append("line")
        .style("stroke","#ccc")
        .style("stroke-width",1);

    var color = d3.scale.category20();

    //添加节点
    var svg_nodes = svg.selectAll("circle")
        .data(nodes)
        .enter()
        .append("circle")
        .attr("r",20)
        .style("fill",function(d,i){
            return color(i);
        })
        .call(force.drag);	//使得节点能够拖动

    //添加描述节点的文字
    var svg_texts = svg.selectAll("text")
        .data(nodes)
        .enter()
        .append("text")
        .style("fill", "black")
        .attr("dx", 20)
        .attr("dy", 8)
        .text(function(d){
            return d.name;
        });


    force.on("tick", function(){	//对于每一个时间间隔

        //更新连线坐标
        svg_edges.attr("x1",function(d){ return d.source.x; })
            .attr("y1",function(d){ return d.source.y; })
            .attr("x2",function(d){ return d.target.x; })
            .attr("y2",function(d){ return d.target.y; });

        //更新节点坐标
        svg_nodes.attr("cx",function(d){ return d.x; })
            .attr("cy",function(d){ return d.y; });

        //更新文字坐标
        svg_texts.attr("x", function(d){ return d.x; })
            .attr("y", function(d){ return d.y; });
    });
}