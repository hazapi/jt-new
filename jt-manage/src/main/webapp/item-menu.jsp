<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>三级菜单联动</title>
		<script type="text/javascript"
			src="/js/jquery-easyui-1.4.1/jquery.min.js"></script>
		<script type="text/javascript"
			src="/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
		<link rel="stylesheet" type="text/css"
			href="/js/jquery-easyui-1.4.1/themes/icon.css" />
		<link rel="stylesheet" type="text/css"
			href="/js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	
	<!--定义样式  -->
	<style type="text/css">
		select {
			width : 200px;
			height : 80px;
			text-align :cneter;
		}
		
		option {
			text-align:center	
		}
		
	</style>
	<script type="text/javascript">
		//定义下拉框大小
		$(function(){
			
			//初始化一级标题
			jQuery.getJSON("/item/cat/list",{id:0}, function(data){
				var option = "";
				for(var i=0;i<data.length;i++){
					option += "<option value='"+data[i].id+"'>"+data[i].text+"</option>"
				}
				//追加数据
				$("#item_cat_1").append(option);
			});
			
			//添加一级标题选中改变事件,补全二级菜单数据
			$("#item_cat_1").change( function() {
				//清空全部下拉框数据
				$("#item_cat_2").empty();
				var parentId = $(this).val();
				$.getJSON("/item/cat/list",{id:parentId},function(data){
					var option = "";
					for(var i=0;i<data.length;i++){
						option += "<option value='"+data[i].id+"'>"+data[i].text+"</option>"
					}
					//追加2级标题
					$("#item_cat_2").append(option);
				});
			});
			
			//添加一级标题选中改变事件,补全二级菜单数据
			$("#item_cat_2").change( function() {
				$("#item_cat_3").empty();
				var parentId = $(this).val();
				$.getJSON("/item/cat/list",{id:parentId},function(data){
					var option = "";
					for(var i=0;i<data.length;i++){
						option += "<option value='"+data[i].id+"'>"+data[i].text+"</option>"
					}
					//追加2级标题
					$("#item_cat_3").append(option);
				});
			});

		})
	</script>
</head>
<body>
	<select id="item_cat_1" name="item_cat_1">
	</select>
	<select id="item_cat_2" name="item_cat_2">
	</select>
	<select id="item_cat_3" name="item_cat_3">
	</select>
</body>
</html>