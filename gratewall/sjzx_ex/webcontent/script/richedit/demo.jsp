<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html XMLNS:XS>
<head>
	<title>HTML Editor v0.95 - Test Page</title>
</head>

<body style="margin: 0, 0, 0, 0;">
	<?import namespace="XS" implementation="XsDhtmlEditor.htc" />
	<XS:XsDhtmlEditor id="xsEditor"
		editorColumns="70"
		editorRows="25"
	/>
	
	<script language="javascript">
		xsEditor.fontList="Times New Roman,Arial,Georgia,????,????" 
		xsEditor.fontSizeDefault="small"
		xsEditor.showEmoticon="false"
		xsEditor.showMode="false"
		xsEditor.showPreview="false"
		xsEditor.showPrint="false"
		xsEditor.showCancel="false"
		xsEditor.showSave="false"
		xsEditor.onSave="alert(xsEditor.content);" 
		xsEditor.emoticonPath="images/emoticons/"
		xsEditor.emoticonList="emoticon-smile.gif,emoticon-wink.gif,emoticon-laugh.gif,emoticon-sad.gif,emoticon-ambivalent.gif,emoticon-tongue-in-cheek.gif,emoticon-surprised.gif,emoticon-unsure.gif,icon-error.gif,icon-info.gif,icon-warning.gif,emoticon-8.gif,emoticon-b.gif,emoticon-c.gif,emoticon-cat.gif,emoticon-d.gif,emoticon-e.gif,emoticon-f.gif,emoticon-g.gif,emoticon-h.gif,emoticon-i.gif,emoticon-k.gif,emoticon-l.gif,emoticon-u.gif,emoticon-n.gif,emoticon-y.gif,emoticon-s.gif,emoticon-star.gif,emoticon-t.gif,emoticon-m.gif,emoticon-p.gif,wiki-ftp.gif,wiki-mailto.gif,wiki-news.gif,wiki-wiki.gif,asp.gif,avi.gif,bmp.gif,chm.gif,doc.gif,gif.gif,gz.gif,html.gif,jpeg.gif,mov.gif,mp3.gif,mpeg.gif,pdf.gif,png.gif,ppt.gif,txt.gif,xls.gif,xml.gif,xsl.gif" 
		xsEditor.replaceTags="<strong>,<b>"
	</script>
</body>
</html>
