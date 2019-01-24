<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<!--实现文件上传  -->
	<body>
		<h1>实现文件上传</h1>
		<form action="http://localhost:8091/fileDemo" 
		method="post" 
		enctype="multipart/form-data">
			<input name="image" type="file"/>
			<input type="submit" value="提交"/>
		</form>	
	</body>
</html>




