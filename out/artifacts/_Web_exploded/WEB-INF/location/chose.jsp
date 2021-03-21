<%--
  Created by IntelliJ IDEA.
  User: YanDan
  Date: 2020/11/23
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>选择位置</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.7.0/themes/icon.css">
    <script src="/static/jquery-1.9.1.min.js"></script>
    <script src="/static/jquery-easyui-1.7.0/jquery.easyui.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=EyKVsKKwi5GsVIxkUV1ZHQkXENUcU9Qj"></script>
</head>
<body>
<div id="allmap"></div>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    map.addControl(navigationControl);
    var geocoder=new BMap.Geocoder();
    // 添加定位控件
    var geolocationControl = new BMap.GeolocationControl();
    geolocationControl.addEventListener("locationSuccess", function(e){
        // 定位成功事件

    });
    geolocationControl.addEventListener("locationError",function(e){
        // 定位失败事件
        alert(e.message);
    });
    map.addControl(geolocationControl);
    //定义一个控件类
    function ZoomControl() {
        this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
        this.defaultOffset = new BMapGL.Size(100, 20)
    }
    //通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    //自定义控件必须实现自己的initialize方法，并且将控件的DOM元素返回
    //在本方法中创建个div元素作为控件的容器，并将其添加到地图容器中
    ZoomControl.prototype.initialize = function(map) {
        //创建一个dom元素
        var div=document.createElement('div');
        var input = document.createElement('input');
        var button=document.createElement('button');
        div.appendChild(input);
        div.appendChild(button);
        //添加文字说明
        // div.appendChild(document.(''));
        button.appendChild(document.createTextNode("搜索"));
        // 设置样式
        input.style.cursor = "pointer";
        input.style.padding = "7px 10px";
        input.style.boxShadow = "0 2px 6px 0 rgba(27, 142, 236, 0.5)";
        input.style.borderRadius = "5px";
        input.style.backgroundColor = "white";
        button.style.cursor = "pointer";
        button.style.padding = "7px 10px";
        button.style.boxShadow = "0 2px 6px 0 rgba(27, 142, 236, 0.5)";
        button.style.borderRadius = "5px";
        button.style.backgroundColor = "white";
        div.style.cursor = "pointer";
        div.style.border = "0px solid gray";
        div.style.backgroundColor = "white";
        // 绑定事件
        button.onclick = function(e){
            var place= input.value;
            var local = new BMap.LocalSearch(map, {
                renderOptions:{map: map}
            });
            local.search(""+place);
        }
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        // 将DOM元素返回
        return div;
    }
    //创建控件元素
    var myZoomCtrl = new ZoomControl();
    //添加到地图中
    map.addControl(myZoomCtrl);
    map.enableScrollWheelZoom(true);
    map.addEventListener('click', function (e) {
        geocoder.getLocation(e.point,function (rs) {
            var addComp = rs.addressComponents;
            var index=(addComp.province + addComp.city + addComp.district  + addComp.street  + addComp.streetNumber);
            alert(index);
            sessionStorage.setItem("index",index);
        });
    });
</script>
</body>
</html>
