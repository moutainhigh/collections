<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="../statics/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../statics/inner/uploader.css" rel="stylesheet" type="text/css" />
<style>
</style>
</head>
<body>

	<form style="width: 95%;margin-top: 15px;" class="form-horizontal">
		<div class="form-group">
			<label for="inputEmail3" class="col-sm-1 control-label">标题：</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="title" />
			</div>
		</div>
		<div class="form-group">
			<label for="inputPassword3" class="col-sm-1 control-label">内容简介</label>
			<div class="col-sm-10">
				<input type="test" class="form-control" id="contentmeta" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<div class="checkbox">
					<label>
						<input type="checkbox" />
						图
					</label>
				</div>
			</div>
		</div>
		
		<div class="form-group">
			<label for="inputPassword3" class="col-sm-1 control-label">内容简介</label>
			<div class="col-sm-10">
				<script id="editor" type="text/plain" name="gdesc" style="width: 100%; height: 350px;padding-top:10px"></script>
			</div>
		</div>
	</form>



	<!-- https://www.cnblogs.com/52lnamp/p/9232919.html -->
	<script type="text/javascript" charset="utf-8" src="../statics/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="../statics/ueditor/ueditor.all.js"></script>
	<script type="text/javascript" charset="utf-8" src="../statics/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	<script type="text/javascript">
		//https://www.jb51.net/article/43175.htm
		var url = parent.document.getElementById("mainContainer").contentWindow.location.href
		//alert(url)
		//实例化编辑器
		var ue = UE.getEditor('editor', {});
	</script>

	<div class="submit-footerbar">
		<label class="control-label"></label>
		<div class="controls">
			<input type="hidden" id="Remark" name="Remark" value="PageAdmin网站管理系统">
			<button type="button" class="btn btn-primary ui-pagePost" data-custom-params="url:'/admin/SiteSet/Edit/',callBack:'',beforeExecute:'BeforeExecute'" id="bt_submit">发布</button>
			<button type="button" class="btn btn-primary ui-pagePost" data-custom-params="url:'/admin/SiteSet/Edit/',callBack:'',beforeExecute:'BeforeExecute'" id="bt_submit">保存</button>
		</div>
	</div>
</body>
</html>