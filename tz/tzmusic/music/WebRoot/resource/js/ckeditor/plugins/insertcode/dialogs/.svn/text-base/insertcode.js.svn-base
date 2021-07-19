CKEDITOR.dialog.add("insertcode",function(editor) {
	return {
		title : '代码高亮',
		minWidth : 700,
		minHeight : 480,
		//resizable: CKEDITOR.DIALOG_RESIZE_BOTH,  
		contents : [ {
			id : "cb",
			label : "cb",
			title : "cb",
			expand : !0,
			padding : 0,
			elements : [ {
				type : "select",
				id:"lang",
				label:"请选择语言：",
				default:"java",
				items: [['ActionScript3', 'as3'], ['Bash/shell', 'bash'], ['C#', 'csharp'], ['C++', 'cpp'], ['CSS', 'css'], ['Delphi', 'delphi'], ['Diff', 'diff'], ['Groovy', 'groovy'], ['Html', 'xhtml'], ['JavaScript', 'js'], ['Java', 'java'], ['JavaFX', 'jfx'], ['Perl', 'perl'], ['PHP', 'php'], ['Plain Text', 'plain'], ['PowerShell', 'ps'], ['Python', 'py'], ['Ruby', 'rails'], ['Scala', 'scala'], ['SQL', 'sql'], ['Visual Basic', 'vb'], ['XML', 'xml']]  
			},{  
                type: 'textarea',  
                style: 'width:700px;height:420px',  
                label: '请贴入代码',  
                id: 'code',  
                rows: 28
            }]
		}],
		onOk: function(){  
            lang = this.getValueOf('cb', 'lang');  
            code = this.getValueOf('cb', 'code');  
            editor.insertHtml("<pre class=\"brush:" + lang + ";\">"+code +"</pre>");
        }
	}
});