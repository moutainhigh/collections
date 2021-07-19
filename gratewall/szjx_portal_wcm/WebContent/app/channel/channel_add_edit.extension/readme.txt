一、说明：
该目录的存在是为了加强系统的扩展能力。


二、系统实现扩展的思路：
1.如果在某个目录Dir下存在jsp页面xxx.jsp;
2.如果由于选件或项目要求，需要对此xxx.jsp进行调整;
3.那么可以在xxx.jsp同级目录下新建目录xxx.extension;
4.可以在xxx.extension目录下定义自己将要实现定制功能的jsp；
5.如果xxx.jsp同目录下没有文件xxx_include_4_extension.jsp，那么请通知产品部门添加该include功能；
6.在产品部门添加了此include之后，运行指定工具【wcm/app/wcm_use/jsp_extension.jsp】输入指定的文件路径，如：xxx.jsp进行调整；
7.此工具将调整xxx_include_4_extension.jsp，在此jsp中include你之前新建的定制jsp；

说明：
	为了不让生成的xxx.jsp被产品后续更新包代码所覆盖，如果产品发布的xxx.jsp中没有引入xxx_include_4_extension.jsp，
	请通知产品部门对xxx.jsp添加xxx_include_4_extension.jsp功能。


三、对思路的简易描述：
A.jsp
	--->include:
		A_include_4_extension.jsp，此jsp由工具进行修改
			-->include:
				A.extension目录下的所有jsp文件
						