$package("com.trs.editor");

com.trs.editor.Config={
	htmlOn:false,
	basePath:com.trs.util.Common.BASE+'com.trs.editor/',
	toolPath:com.trs.util.Common.BASE+"com.trs.editor/img/",//相对路径即可
	facePath:com.trs.util.Common.BASE+'com.trs.editor/img/faces/',//绝对地址
	uploadPath:'http://localhost:8080/images/article',//上传前缀//绝对地址
	iframes:[],
	components:{},
	toolbarComponents:{},
	abstractLength:1000
};

$define('com.trs.editor.ImgUploader');
Object.extend(com.trs.editor.ImgUploader,{
	lastFormId:0,
	method:'local',//'remote'/'local'
	action:'imagePost.do',//img提交的action
	target:'iframe_for_img_upload',
	filePathPre:'file_path_',
	picShowPre:'picShow_',
	formPre:'form_',
	params:{
		filePath:'filePath',
		fileKey:'fileKey',
		uploadTime:'uploadTime'
	},
	values:{
		uploadTime:''
	}
});

$define('com.trs.editor.ArticleSubmiter');
Object.extend(com.trs.editor.ArticleSubmiter,{
	PREVIEW:1,//草稿or发布or预览标识字段对应的三个值
	PUBLISH:2,
	DRAFT:3,
	method:'local-ajax',//'remote'/'local'
	form:'ArticleForm',//article发布的表单名
	action:'articleAction.do',//article发布的action
	target:'iframe_for_article',
	previewAction:'articlePreview.do',//article预览的action
	params:{
		article:'content',//需要与页面对应下面三个字段名
		article_text:'contentText',
		article_abstract:'brief',
		image_url:'imageUrl',
		title:'title',
		album:'album',
		act:'actionType',//草稿or发布or预览标识字段
		date:'date',
		mode:'mode',
		ttype:'ttype',
		x_cms_flag:'x_cms_flag',
		blog_class:'blog_class',
		blog_info_quote:'blog_info_quote'
	}
});

Object.extend(com.trs.editor.Config,{
	styleInnerHtml:
		'<DIV id=style_h1 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H1 标题</DIV>'+
		'<DIV id=style_h2 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H2 标题</DIV>'+
		'<DIV id=style_h3 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H3 标题</DIV>'+
		'<DIV id=style_h4 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H4 标题</DIV>'+
		'<DIV id=style_h5 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H5 标题</DIV>'+
		'<DIV id=style_h6 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">H6 标题</DIV>'+
		'<DIV id=style_p style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">P 段落</DIV>'+
		'<DIV id=style_address style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">ADDRESS 地址</DIV>'+
		'<DIV id=style_pre style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">PRE 固定格式</DIV>',
	fontInnerHtml:
		'<DIV style="padding-left:10px;font-family:黑体;font-size:13px;line-height:150%;cursor:pointer">黑体</DIV>'+
		'<DIV style="padding-left:10px;font-family:隶书;font-size:13px;line-height:150%;cursor:pointer">隶书</DIV>'+
		'<DIV style="padding-left:10px;font-family:宋体;font-size:13px;line-height:150%;cursor:pointer">宋体</DIV>'+
		'<DIV style="padding-left:10px;font-family:楷体_GB2312;font-size:13px;line-height:150%;cursor:pointer">楷体_GB2312</DIV>'+
		'<DIV style="padding-left:10px;font-family:幼圆;font-size:13px;line-height:150%;cursor:pointer">幼圆</DIV>'+
		'<DIV style="padding-left:10px;font-family:Arial;font-size:13px;line-height:150%;cursor:pointer">Arial</DIV>'+
		'<DIV style="padding-left:10px;font-family:Courier New;font-size:13px;line-height:150%;cursor:pointer">Courier New</DIV>'+
		'<DIV style="padding-left:10px;font-family:Georgia;font-size:13px;line-height:150%;cursor:pointer">Georgia</DIV>'+
		'<DIV style="padding-left:10px;font-family:Times New Roman;font-size:13px;line-height:150%;cursor:pointer">Times New Roman</DIV>'+
		'<DIV style="padding-left:10px;font-family:Verdana;font-size:13px;line-height:150%;cursor:pointer">Verdana</DIV>'+
		'<DIV style="padding-left:10px;font-family:impact;font-size:13px;line-height:150%;cursor:pointer">impact</DIV>',
	fontSizeInnerHtml:
		'<DIV id=fontsize_1 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">10px</DIV>'+
		'<DIV id=fontsize_2 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">14px</DIV>'+
		'<DIV id=fontsize_3 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">16px</DIV>'+
		'<DIV id=fontsize_4 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">18px</DIV>'+
		'<DIV id=fontsize_5 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">24px</DIV>'+
		'<DIV id=fontsize_6 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">32px</DIV>'+
		'<DIV id=fontsize_7 style="padding-left:10px;font-size:13px;line-height:150%;cursor:pointer">48px</DIV>',
	foreColorInnerHtml:
		'<button class="C1"></button><button class="C2"></button><button class="C3"></button><button class="C4"></button><button class="C5"></button><button class="C6"></button><br>'+
		'<button class="C7"></button><button class="C8"></button><button class="C9"></button><button class="C10"></button><button class="C11"></button><button class="C12"></button><br>'+
		'<button class="C13"></button><button class="C14"></button><button class="C15"></button><button class="C16"></button><button class="C17"></button><button class="C18"></button><br>'+
		'<button class="C19"></button><button class="C20"></button><button class="C21"></button><button class="C22"></button><button class="C23"></button><button class="C24"></button><br>'+
		'<button class="C25"></button><button class="C26"></button><button class="C27"></button><button class="C28"></button><button class="C29"></button><button class="C30"></button><br>'+
		'<button class="C31"></button><button class="C32"></button><button class="C33"></button><button class="C34"></button><button class="C35"></button><button class="C36"></button><br>',
	bgColorInnerHtml:
		'<button class="C1"></button><button class="C2"></button><button class="C3"></button><button class="C4"></button><button class="C5"></button><button class="C6"></button><br>'+
		'<button class="C7"></button><button class="C8"></button><button class="C9"></button><button class="C10"></button><button class="C11"></button><button class="C12"></button><br>'+
		'<button class="C13"></button><button class="C14"></button><button class="C15"></button><button class="C16"></button><button class="C17"></button><button class="C18"></button><br>'+
		'<button class="C19"></button><button class="C20"></button><button class="C21"></button><button class="C22"></button><button class="C23"></button><button class="C24"></button><br>'+
		'<button class="C25"></button><button class="C26"></button><button class="C27"></button><button class="C28"></button><button class="C29"></button><button class="C30"></button><br>'+
		'<button class="C31"></button><button class="C32"></button><button class="C33"></button><button class="C34"></button><button class="C35"></button><button class="C36"></button><br>',
	faceInnerHtml:
		'<img border="0" src="'+com.trs.editor.Config.facePath+'ubb.gif" usemap="#Map" align="center">'+
		'<map name="Map" id=face_map>'+
		'  <area shape="rect" coords="246,83,272,107" id=040>'+
		'  <area shape="rect" coords="219,83,245,107" id=039>'+
		'  <area shape="rect" coords="192,83,218,107" id=038>'+
		'  <area shape="rect" coords="165,83,191,107" id=037>'+
		'  <area shape="rect" coords="138,83,164,107" id=036>'+
		'  <area shape="rect" coords="111,83,137,107" id=035>'+
		'  <area shape="rect" coords="84,83,110,107" id=034>'+
		'  <area shape="rect" coords="57,83,83,107" id=033>'+
		'  <area shape="rect" coords="30,83,56,107" id=032>'+
		'  <area shape="rect" coords="3,83,29,107" id=031>'+
		'  <area shape="rect" coords="246,56,272,80" id=030>'+
		'  <area shape="rect" coords="219,56,245,80" id=029>'+
		'  <area shape="rect" coords="192,56,218,80" id=028>'+
		'  <area shape="rect" coords="165,56,191,80" id=027>'+
		'  <area shape="rect" coords="138,56,164,80" id=026>'+
		'  <area shape="rect" coords="111,56,137,80" id=025>'+
		'  <area shape="rect" coords="84,56,110,80" id=024>'+
		'  <area shape="rect" coords="57,56,83,80" id=023>'+
		'  <area shape="rect" coords="30,56,56,80" id=022>'+
		'  <area shape="rect" coords="3,56,29,80" id=021>'+
		'  <area shape="rect" coords="246,30,272,54" id=020>'+
		'  <area shape="rect" coords="219,30,245,54" id=019>'+
		'  <area shape="rect" coords="192,30,218,54" id=018>'+
		'  <area shape="rect" coords="165,30,191,54" id=017>'+
		'  <area shape="rect" coords="138,30,164,54" id=016>'+
		'  <area shape="rect" coords="111,30,137,54" id=015>'+
		'  <area shape="rect" coords="84,30,110,54" id=014>'+
		'  <area shape="rect" coords="57,30,83,54" id=013>'+
		'  <area shape="rect" coords="30,30,56,54" id=012>'+
		'  <area shape="rect" coords="3,30,29,54" id=011>'+
		'  <area shape="rect" coords="246,4,272,28" id=010>'+
		'  <area shape="rect" coords="219,4,245,28" id=009>'+
		'  <area shape="rect" coords="192,4,218,28" id=008>'+
		'  <area shape="rect" coords="165,4,191,28" id=007>'+
		'  <area shape="rect" coords="138,4,164,28" id=006>'+
		'  <area shape="rect" coords="111,4,137,28" id=005>'+
		'  <area shape="rect" coords="84,4,110,28" id=004>'+
		'  <area shape="rect" coords="57,4,83,28" id=003>'+
		'  <area shape="rect" coords="30,4,56,28" id=002>'+
		'  <area shape="rect" coords="3,4,29,28" id=001>'+
		'</map>',
	insertAnchorInnerHtml:
		'<div style="font-size:9pt;padding-left:10px;padding-top:6px;line-height:120%">链接地址:&nbsp;&nbsp;<input id="wordEditer_Link_ADS" value="http://"><br>'+
		'链接文字:&nbsp;&nbsp;<input id="wordEditer_Link_TEXT">'+
		'<center><button style="width:40px">确定</button></center></div>',
	insertImgInnerHtml:
		'<div style="font-size:9pt;padding-left:10px;padding-top:6px;line-height:120%">远程图片链接地址:<INPUT value=http://><BUTTON style="WIDTH: 40px">确定</BUTTON></div><HR>'+
		'<div style="font-size:9pt;padding-left:10px;padding-top:6px;padding-bottom:6px;line-height:120%">本地图片:</div>',
	insertTableInnerHtml:
		'<div style="font-size:9pt;padding-left:20px;padding-right:20px;padding-top:6px;padding-bottom:6px;line-height:120%">'+
		'行数　　　:&nbsp;&nbsp;<input style="width:40px;"id="wordEditer_Table_ROWS" value="3" maxLength="2"><br>'+
		'列数　　　:&nbsp;&nbsp;<input style="width:40px;" id="wordEditer_Table_COLUMNS" value="3" maxLength="2"><br>'+
		'表格宽度　:&nbsp;&nbsp;<input style="width:40px;"id="wordEditer_Table_WIDTH" value="200">'+
		'<select id="wordEditer_Table_WIDTHTYPE">'+
        '<option selected value="pixels">象素</option>'+
        '<option>百分比</option>'+
		'</select><br>'+
		'边框粗细　:&nbsp;&nbsp;<input style="width:40px;" id="wordEditer_Table_BORDER" value="1"><br>'+
		'单元格边距:&nbsp;&nbsp;<input style="width:40px;"id="wordEditer_Table_PADDING" value="1"><br>'+
		'单元格间距:&nbsp;&nbsp;<input style="width:40px;"id="wordEditer_Table_SPACING" value="1"><br><hr>'+
		'<center><button style="width:40px">确定</button></center></div>',
	configInnerHtml:
		'<table width="152" height="224" cellspacing="0" cellpadding="0" border="0" background="'+com.trs.editor.Config.toolPath+'menu.gif">'+
		'<tr><td><input id="c_Component1" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component2" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component3" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component4" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component5" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component6" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component7" name="func" checked type="checkbox"></td></tr>'+
		'<tr><td><input id="c_Component8" name="func" checked type="checkbox"></td></tr>'+
		'</table>',
	load:function(){
		var cmp=new com.trs.editor.Component('Component1',0,0);
		this.components['Component1']=cmp;
		cmp.addCommand("Undo","tool_undo","撤销","Undo","button","tool_undo.gif");
		cmp.addCommand("Redo","tool_redo","重做","Redo","button","tool_redo.gif");
		this.toolbarComponents[cmp.id]=cmp.id;
		
		cmp=new com.trs.editor.Component('Component2',0,0);
		this.components['Component2']=cmp;
		cmp.addCommand("Cut","tool_cut","剪切","Cut","button","tool_cut.gif");
		cmp.addCommand("Copy","tool_copy","复制","Copy","button","tool_copy.gif");
		cmp.addCommand("Paste","tool_paste","粘贴","Paste","button","tool_paste.gif");
		this.toolbarComponents[cmp.id]=cmp.id;
		
		cmp=new com.trs.editor.Component('Component3',0,0);
		this.components['Component3']=cmp;
		cmp.addCommand("B","tool_b","加粗","B","button","tool_b.gif");
		cmp.addCommand("I","tool_i","斜体","I","button","tool_i.gif");
		cmp.addCommand("U","tool_u","下划线","U","button","tool_u.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component4',0,0);
		this.components['Component4']=cmp;
		cmp.addCommand("Left","tool_left","居左","Left","button","tool_left.gif");
		cmp.addCommand("Center","tool_center","居中","Center","button","tool_center.gif");
		cmp.addCommand("Right","tool_right","居右","Right","button","tool_right.gif");
		cmp.addCommand("NoAlign","tool_noalign","两端对齐","NoAlign","button","tool_noalign.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component5',0,0);
		this.components['Component5']=cmp;
		cmp.addCommand("InsertImg","tool_insertimg","插入图片","InsertImg","select","tool_img.gif");
		cmp.addCommand("InsertAnchor","tool_insertanchor","插入链接","InsertAnchor","select","tool_anchor.gif");
		cmp.addCommand("InsertTable","tool_inserttable","插入表格","InsertTable","select","tool_table.gif");
		cmp.addCommand("InsertFace","tool_insertface","插入表情","InsertFace","select","tool_face.gif");
		cmp.addCommand("InsertText","tool_inserttext","插入文本","InsertText","select","tool_text.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component6',0,0);
		this.components['Component6']=cmp;
		cmp.addCommand("InsertHr","tool_inserthr","横线","InsertHr","button","tool_hr.gif");
		cmp.addCommand("ForeColor","tool_forecolor","设置前景色","ForeColor","select","tool_forecolor.gif");
		cmp.addCommand("BgColor","tool_bgcolor","设置背景色","BgColor","select","tool_bgcolor.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component7',0,0);
		this.components['Component7']=cmp;
		cmp.addCommand("Style","tool_style","段落","Style","select","tool_style.gif");
		cmp.addCommand("Font","tool_font","字体","Font","select","tool_font.gif");
		cmp.addCommand("FontSize","tool_fontsize","字体大小","FontSize","select","tool_fontsize.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component8',0,0);
		this.components['Component8']=cmp;
		cmp.addCommand("OL","tool_ol","编号列表","OL","button","tool_ol.gif");
		cmp.addCommand("UL","tool_ul","项目符号列表","UL","button","tool_ul.gif");
		cmp.addCommand("Outdent","tool_outdent","减少缩进","Outdent","button","tool_outdent.gif");
		cmp.addCommand("Indent","tool_indent","增加缩进","Indent","button","tool_indent.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Component9',0,0);
		this.components['Component9']=cmp;
		cmp.addCommand("Config","tool_config","功能","Config","select","tool_config.gif");
		this.toolbarComponents[cmp.id]=cmp.id;

		cmp=new com.trs.editor.Component('Source',0,0);
		this.components['Source']=cmp;
		cmp.addCommand("Source","tool_source","源代码","Source","checkbox",false);

		cmp=new com.trs.editor.Component('UploadImg',0,0);
		this.components['UploadImg']=cmp;
		cmp.addCommand("UploadImg","1","上传图片","UploadImg","upload-img");
		cmp.addCommand("UploadImg","2","上传图片","UploadImg","upload-img");
		
		var uploadTime=$(com.trs.editor.ImgUploader.params["uploadTime"])
		if(uploadTime){
			com.trs.editor.ImgUploader.values["uploadTime"]=uploadTime.value;
		}
	},
	toolbar:function(_parentElement){
		var div=document.createElement("DIV");
		_parentElement.appendChild(div);
		var first=true;
		for(var i in this.toolbarComponents)
		{
			if(first){
				first=false;
			}
			else{
				var sep=new com.trs.editor.Separator(true);
				sep.toElement(div);
			}
			var cmp=this.toolbarComponents[i];
			if(cmp&&cmp!='')
			{
				this.components[cmp].toElement(div);
			}
		}
		return div;
	}
});
ClassName(com.trs.editor.Config,'editor.Config');