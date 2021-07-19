define(function() {
	var TEMPLATE = {};
	TEMPLATE.header = {
		tpl : [
				'<!-- target: header -->',
				'<header id="person">',
				'<div class="wrap-header" >',
				'<div class="logo"></div>',
				'<nav>',
				'<ul class="nav-main">',
				'<li onclick="window.location.href=\'../person_account/home.html\'">首页</li>',
				'<li onclick="window.location.href=\'../../apply/security/index.html\'">个人中心</li>',
				//'<li>咨询服务</li>',
				'<li class="bgcolor">',
				'<div class="li-img-user">',
				'<span class="userName omit"></span>',
				'</div>',
				'<div class="li-img-exit" id="log-exit">',
				'<span class="exit">退出</span>', '</div>', '</li>',
				/*
				 * '<li class="bgcolor" onclick="window.location.href=\'/\';">', '<div
				 * class="li-img-exit">', '<span class="exit">退出</span>', '</div>', '</li>',
				 */
				'</ul>', '</nav>', '</div>', '</header>', '<!-- /target -->' ]
				.join('\n')
	};
	TEMPLATE.ent_header = {
			tpl : [
					'<!-- target: ent_header -->',
					'<header id="ent">',
					'<div class="wrap-header">',
					'<div class="logo"></div>',
					'<nav>',
					'<ul class="nav-main">',
					'<li onclick="window.location.href=\'/page/apply/${type}/home.html\';">首页</li>',
					'<li class="entName">',
					'<span id="entName" class="omit" title=""></span>',
					'</li>',
				
				/*	'<li>企业信息管理</li>',*/
					'<li class="entbgcolor">',
					'<div class="ent-li-img-exit" id="log-exit">',
					'<span class="exit">退出</span>', 
					'</div>',
					'</li>',
					'</ul>',
					'</nav>',
					'</div>', 
					'</header>', 
					'<!-- /target -->' ]
					.join('\n'),
					data : {
						type : "ent_account"
					}
		};
	TEMPLATE.footer = {
		tpl : [
				'<!-- target: footer -->',
				'<footer>',
				'<div class="wrap-footer">',
				'<span class="hint-words">推荐使用360极速浏览器,支持最低分辨率1024*768,请安装Adobe reader10.0以上版本.</span>',
				'<span class="company-name">技术支持:长城计算机软件与系统有限公司</span>',
				'</div>', '</footer>', '<!-- /target -->' ].join('\n')
	};

	TEMPLATE.card = {
		tpl : [
				'<!-- target: card -->',
				'<div class="cardBox">',
				'<!-- for: ${rows} as ${item},${index} -->',
				'<!--if:  ${item.modifySign} == 0 ||typeof(${item.modifySign})=="undefined"-->',

				'<div class="card btnEntDemoAdd" >',
				'<!-- else -->',
				'<div class="card card-color btnEntDemoAdd">',
				'<!-- /if -->',
				'<input type="hidden" value="${item.invType}">',
				'<input type="hidden" value="${item.investorId}">',
				'<input type="hidden" value="${gid}">',
				'<!--if: ${item.invType} == 20|| ${item.invType} ==35||${item.invType} ==36||${item.invType} ==91-->',
				'<p class="name-info omit">${item.inv}',
				'<!--if:${item.subConAm}==null-->',
				'<p class="subCon">0万元</p>',
				'<!--else-->',
				'<p class="subCon">${item.subConAm}万元</p>',
				'<!--/if-->',
				'<!--else-->',
				'<p class="enterprise-info" title="${item.inv}">${item.inv}',
				'<!--if:${item.subConAm}==null-->',
				'<p class="subCon">0万元</p>',
				'<!--else-->',
				'<p class="subCon">${item.subConAm}万元</p>',
				'<!--/if-->',
				'<!--/if-->',
				'<!-- if: ${item.ttt} & ${item.ttt} != "" -->',
				'<span class="main2-font4">${proxy}</span>',
				'<!-- /if -->',
				'</p>',
				/* '<div class="personInfo">', */
				/*
				 * '<!--if:${item.subConAm}==null-->', '<p class="subCon">0万元</p>', '<!--else-->', '<p class="subCon">${item.subConAm}万元</p>', '<!--/if-->',
				 */
				'<div class="info-tab">',
				'<span class="triangle"></span>',
				'<!-- if: ${item.modifySign} == 0||typeof(${item.modifySign})=="undefined" -->',
				'<span>信息未填写</span>', '<!-- else -->', '<span>信息已保存</span>',
				'<!-- /if -->',

				/* '<span class="select-cover">从现有人员中选择</span>', */
				'</div>',
				'</div>',
				/* '</div>', */
				'<!-- /for -->',
				'</div>', 
				'<!-- var: nextIndex = ${index}+1 -->',
				'<input name="total" type="hidden" value="${nextIndex}">',
				 '<!-- /target -->' ].join('\n'),
		data : {

		}
	};
	TEMPLATE.approve_msg = {
		data : {},
		tpl : [ '<!-- target: approve_msg -->', 
		        /*'<div>', '<table>',*/
				'<p><span class="adviceTitle">登记机关 : </span><span>${orgName}&nbsp;</span></p>',
				'<p><span class="adviceTitle">审查时间 : </span><span>${proEndDate}&nbsp;</span></p>',
				'<p><span class="adviceTitle">审查结果 : </span><span>${processResult}&nbsp;</span></p>',
				'<p><span class="adviceTitle">审查意见 : </span><span>${processNotion}&nbsp;</span></p>'
				/*'</table>',
				'</div>'*/

		].join('\n')
	};

	TEMPLATE.mbr_card = {
		/*
		 * 431A 董事长 432A 董事 432K 执行董事 436A 经理 408A 监事 408B 监事会主席
		 */

		tpl : [
				'<!-- target: mbr_card -->',

				'<!-- if:${positionType}=="1"-->',
				'<div class="directors">',
				'<!-- elif:${positionType}=="2"-->',
				'<div class="managers">',
				'<!-- elif:${positionType}=="3"-->',
				'<div class="conductors">',
				'<!-- else-->',
				'<!-- /if -->',

				'<!--if:${rows.length}!=0-->',

				'<!-- for: ${rows} as ${item},${index} -->',
				// begin of card
				'<div class="card" positionType="${item.positionType}" entmemberId="${item.entmemberId}" position="${item.position}">',

				// 显示姓名
				'<p class="name-info omit">',
				'<span title="${item.name}">${item.name}</span>',

				'<!--if:${item.leRepSign}==1-->',
				'<span class="main2-font4" title="法定代表人">法定代表人</span>',
				'<!--/if-->',
				'</p>',

				// 显示职务
				'<div class="info-tab">',
				'<span class="triangle"></span>',
				'<!-- if:${item.position}=="431A"-->',
				'<span">董事长</span>',
				'<!-- elif:${item.position}=="432A"-->',
				'<span>董事</span>',
				'<!-- elif:${item.position}=="432K"-->',
				'<span>执行董事</span>',
				'<!-- elif:${item.position}=="436A"-->',
				'<span>经理</span>',
				'<!-- elif:${item.position}=="408A"-->',
				'<span>监事</span>',
				'<!-- elif:${item.position}=="408B"-->',
				'<span>监事会主席</span>',
				'<!-- else-->',
				'<!-- /if -->',
				'</div>',

				// 操作
				'<div class="info-setup">',
				"<img src='../../../static/image/img/icon/deteleGreen.png' name='mbrDelBtn'>",
				'<!-- if: (${item.position}=="431A"||${item.position}=="432K"||${item.position}=="436A") && ${item.leRepSign}!=1-->',
				'<!-- elif:${item.position}=="432A"-->',
				'<span class="setup-president">设为董事长</span>',
				'<!-- elif:${item.position}=="408A"-->',
				'<span class="setup-chairman">设为监事会主席</span>', '<!-- else-->',
				'<!-- /if -->', '</div>',

				// end of card
				'</div>',

				'<!-- /for -->',
				'<input name="total" type="hidden" value="${index}">',

				'<!-- /if -->',// end of if:${rows.length}!=0

				// 添加
				'<div class="upload-card" positionType=${positionType} >',
				'<div class="location">',
				'<img src="../../../static/image/img/icon/cross.png" />',
				'</div>', '</div>',

				'</div>',

				'<!-- /target -->' ].join('\n'),
		data : {}
	};

	TEMPLATE.content = {
		tpl : [ '<!-- target: content -->', '<div>', '<div class="banner" >',
				'<a href="basic_info.html?gid=${gid}"class="blueactive" >',
				'<span class="icon-info-blue"></span>', '<span>企业基本信息</span>',
				'</a>', '<a href="inv.html?gid=${gid}">',
				'<span class="icon-inv"></span>', '<span>股东信息</span>', '</a>',
				'<a href="member.html?gid=${gid}">',
				'<span class="icon-mbr"></span>', '<span>主要人员信息</span>',
				'</a>', '<a href="contact.html?gid=${gid}">',
				'<span class="icon-connect"></span>', '<span>企业联系人</span>',
				'</a>', '<a href="upload.html?gid=${gid}">',
				'<span class="icon-upload"></span>', '<span>文件上传</span>',
				'</a>', '<a href="preview.html?gid=${gid}">',
				'<span class="icon-preview"></span>', '<span>预览页面</span>',
				'</a>', '</div>', '<div class="content">', '</div>', '</div>',
				'<!-- /target -->', ].join('\n'),
		data : {
			title : ""
		}
	};
	
	/*业务详情tab标签*/
	TEMPLATE.tab_content = {
			tpl : [ '<!-- target:tab_content -->', 
			        '<div class="tableheader">', 
			        '<div class="tabclass">',
					'<a href="id_confirm.html?gid=${gid}">',
					'<span class="tab-id"></span>', 
					'<span>身份认证</span>',
					'</a>',
					'<a href="app_detail.html?gid=${gid}" class="blueactive">',
					'<span class="tab-apply-blue"></span>',
					'<span>申请信息</span>', 
					'</a>',
					'<a href="business_confirm.html?gid=${gid}">',
					'<span class="tab-confirm"></span>', 
					'<span>业务确认</span>',
					'</a>', 
					'<a href="approval.html?gid=${gid}">',
					'<span class="tab-advice"></span>',
					'<span>审批意见</span>',
					'</a>', 
					'</div>', 
					'<div class="content">',
					'</div>',
					'</div>',
					'<!-- /target -->', ].join('\n'),
			data : {
				title : ""
			}
		};
	/*业务详情card模板*/
	TEMPLATE.tab_card={
			tpl:['<!--target:tab_card-->',
			     '<div class="tabbox">',
			     	'<!-- for: ${rows} as ${item}-->',
			     		'<!--if:${item.state}=="未通过"||${item.state}=="未提交"||${item.state}=="未确认"-->',
			     		'<div class="tabcard">',
			     		'<!--else-->',
			     		'<div class="tabcardgreen">',
			     		'<!--/if-->',
			     			'<span class="tabname">${item.name}</span>',
			     			'<span class="tabstate">${item.state}</span>',
			     		'</div>',
			     	'<!-- /for -->',
			     '</div>',
			     ].join('\n'),
			data:{
				title:""
			}
	};

	TEMPLATE.tab_list={
			tpl:['<!--target:tab_list-->',
			     '<div>',
			     '<!-- for: ${rows} as ${item}-->',
			     '<div class="tablistbg">',
			     	'<div class="box">',
			     		'<span class="gov" title="${item.regOrg}">审批机关：${item.regOrg}</span>',
			     		'<!--if:${item.processResult}=="核准"-->',
			     		'<span class="approvalstate" style="color:#a8d49f">审批状态：${item.processResult} </span>',
			     		'<!--else-->',
			     		'<span class="approvalstate" style="color:#fd2609">审批状态：${item.processResult} </span>',
			     		'<!--/if-->',
			     		'<span>审批日期：${item.timestamp}</span>',	
			     	'</div>',
			     	'<div class="advicebox">',
			     		'<!--if:${item.processResult}=="核准"-->',
			     		'<span></span>',
			     		'<!--else-->',
			     		'<span style="color:#fd2609">审批意见：${item.processNotion}</span>',
			     		'<!--/if-->',
			     	'</div>',   	
			     '</div>',
			     '<!--/for-->',
			     '</div>',
			     ].join('\n'),
			data:{
				title:''
			}
	};
	
	TEMPLATE.list_table = {
		tpl : [
				'<!-- target:list_table-->',
				'<section class="table-head">',
				'<section class="list-wrap">',
				'<section class="top">',
				'<span>${tableTitle}</span>',
				'<img id="add_bt" src="../../../static/image/img/icon/addicon.png">',
				'</section>',
				'<table class="list-table" id="list-table">',
				'<tr id="first-tr">',
				'<td style="display:none;"></td>',
				'<!--for:${col} as ${coltd}-->',
				'<td>${coltd.title}</td>',
				'<!--/for-->',
				'</tr>',
				'<!--if:${data.rows.length} !=0 -->',
				'<!--for:${data.rows} as ${item},${num}-->',
				/* '<input type="hidden" value="${item.stagespayId}">', */
				'<tr class="ivt-row" >',
				'<td style="display:none;">${item.stagespayId}</td>',
				'<td>',
				'<div vtype="textfield" name="money${num}" rule="must_number(16,4)_contrast;>=0" class="ivt-money" id="money${num}" defaultvalue=${item.curActConAm}>',
				'<input type="hidden" value="${item.curActConAm}">',
				'</div></td>',
				'<td>',
				'<div name="way${num}" id="way${num}" vtype="comboxfield" width="200" defaultvalue="${item.conForm}"  class="ivt-way" dataurl="../../../dictionary/queryData.do?dicId=CA22" rule="must"></div>',
				'</td>',
				'<td>',
				'<div name="time${num}" id="time${num}" vtype="datefield" width="200" editable=false defaultvalue="${item.conDate}" class="ivt-time" dataurl="../../../dictionary/queryData.do?dicId=CA22" rule="must;yyyy-MM-dd" isShowWeek="true" isshowdatelist="true"></div>',
				'</td>',
				'<td class="removeTr">',
				'</td>',
				'</tr>',
				'<!-- /for -->',
				'<!--else-->',
				'<tr class="ivt-row" >',
				'<td style="display:none;"></td>',
				'<td>',
				'<div vtype="textfield" name="money2016023" rule="must_number(18,6)_contrast;>0" class="ivt-money" id="money2016023">',
				// '<input type="hidden" value="${item.curActConAm}">',
				'</div></td>',
				'<td>',
				'<div name="way2016023" id="way2016023" vtype="comboxfield" width="200" defaultvalue=""  class="ivt-way" dataurl="../../../dictionary/queryData.do?dicId=CA22" rule="must"></div>',
				'</td>',
				'<td>',
				'<div name="time2016023" id="time2016023" vtype="datefield" width="200" editable=false defaultvalue="" class="ivt-time" dataurl="../../../dictionary/queryData.do?dicId=CA22" rule="must;yyyy-MM-dd" isShowWeek="true" isshowdatelist="true"></div>',
				'</td>',
				'<td class="removeTr">',
				'</td>',
				'</tr>',
				'<!--/if-->',
				'</table>',
				'<section id="capital">',
				'<span >出资总额:</span>',
				'<span id="subConAmSpan">0</span>',
				'<span>万元</span>',
				'<span>(币种：人民币)</span>',
				'</section>	',
				'</section>',
				'</section>',
				'<seciton class="fullLine">',
				'</section>',
				'<section name="applySetupInvPersonEditToolbar" class="btn-box">',
				'<section name="applySetupInvPersonEdit_query_button" id="applySetupInvPersonEdit_query_button" class="commonBt">保存',
				'</section>',
				'<section name="applySetupInvPersonEdit_reset_button" id="applySetupInvPersonEdit_reset_button" class="commonBt">取消',
				'</section>',
				'</section>', '<!-- /target -->' ].join("\n"),
		data : {

		}
	};
	TEMPLATE.upload_list = {
		tpl : [
				'<!-- target:upload_list-->',
				'<!-- for:${upload} as ${item}, ${itemindex} -->',
				    '<!-- if: ${itemindex} != "0" -->',
				        '<div class="dotted-line"></div>',
				    '<!-- /if -->',
					'<!-- var: max = ${item.rule | max} -->',
					'<!-- var: isAdd = ${item.rule |isAdd} -->',
					'<!-- var: min = ${item.rule | min} -->',
					'<div class="clearfix">',
					'<div class="container clearfix" categoryId="${item.categoryId}" max="${max}" min=${min} isAdd="${isAdd}">',
					'<div class="dotted-box">',
					'<div class="card-green">',
					'<span>${item.title}</span></br>',
					/*'<a href="">样例</a>',*/
					'</div>',
					'</div>',
					'<!-- if: ${item.categoryType} == "1" -->',
						'<!-- if:${item.fileArray} -->',
							'<!-- for:${item.fileArray} as ${file}, ${index}-->',
							'<!-- var: position = ${file.position} -->',
							'<!-- var: verifystate = ${file.verifystate} -->',
							
							'<!-- if:${verifystate} & ${position}!="0" -->',
								'<!-- var: uploadFileTip = "此人需要身份认证" -->',
								'<!-- var: uploadFileClass = "negative" -->',
							    '<!-- if:${verifystate} =="已通过" -->',
								    '<!-- var: uploadFileTip = "此人已通过身份认证" -->',
									'<!-- var: uploadFileClass = "positive" -->',
							    '<!-- /if -->',
							    '<!-- if:${verifystate} =="已提交" -->',
							    '<!-- var: uploadFileTip = "此人已提交身份认证" -->',
								'<!-- var: uploadFileClass = "positive" -->',
						    '<!-- /if -->',
							    '<div class="uploadfile">',
									'<div class="upload_container ${uploadFileClass}">',  
										'<div class="upload_touch">',
										'<div class="upload_tip">${uploadFileTip}</div>',
										'<div class="file-input hide" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" sn="${file.sn}"></div>',
									    '</div>',
									'</div>',
								'</div>',
								'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
								
							'<!-- else -->',
							
								'<!-- if:${file.cardType} == "1" -->',
									'<div class="uploadfile">',
									'<div class="uploadcontainer clearfix">',
									'<input type="file" uploadFileClass="upload_con" id="${index}0${item.categoryId}file" name="${index}0${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
									'<input type="file" uploadFileClass="upload_con" id="${index}1${item.categoryId}file" name="${index}1${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
										'<!-- if: ${file.fileId} -->',
										'<img src="" class="upload_img" approveMsg="${file.approveMsg}" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" >',
										'<div class="upload_state upload_state_${file.state}"></div>',
										    '<!-- if:${file.state} == "3" -->',
										    '<div class="upload_close hide"></div>',
										    '<!-- else -->',
										    	'<!-- if:${position}!="0" -->',
										    		'<div class="upload_close hide"></div>',
										    	'<!-- else -->',
								    				'<div class="upload_close"></div>',
								    			'<!-- /if -->',
										    '<!-- /if -->',
										'<!-- else -->',
										'<img src="" class="upload_img hide" approveMsg="" categoryId="${item.categoryId}" fId="" fileId="" thumbFileId="" refId="${file.refId}" refText="${file.refText}" state="" >',
										'<div class="upload_state hide"></div>',
										'<div class="upload_close hide"></div>',
										'<!-- /if -->',
									'</div>',
									'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
									'</div>',
								'<!-- else -->',
									'<div class="uploadfile">',
									'<input type="file" id="${index}${item.categoryId}file" name="${index}${item.categoryId}file" approveMsg="${file.approveMsg}" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" sn="${file.sn}" callback= "addUploadCase" >',
									'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
									'</div>',
								'<!-- /if -->',
							'<!-- /if -->',
							'<!-- /for -->',
							'<!-- if: ${isAdd} == "1" -->',
								'<!-- if: ${index} < ${max} -->',
								'<div class="uploadfile">',
								'<!-- var: nextIndex = ${index}+1 -->',
								'<input type="file" id="${nextIndex}${item.categoryId}file" name="${nextIndex}${item.categoryId}file" approveMsg="" categoryId="${item.categoryId}" fId="" fileId="" thumbFileId="" state="" callback= "addUploadCase" >',
								'</div>',
								'<!-- /if -->',
							'<!-- /if -->',
						'<!-- else -->',
						'<div class="uploadfile">',
						'<input type="file" id="0${item.categoryId}file" name="0${item.categoryId}file" approveMsg="" categoryId="${item.categoryId}" fId="" fileId="" thumbFileId="" state="" callback= "addUploadCase" >',
						'</div>',
						'<!-- /if -->',
					'<!-- elif: ${item.categoryType} == "2" -->',
						'<!-- if:${item.fileArray} -->',
							'<!-- for:${item.fileArray} as ${file}, ${index}-->',
							'<!-- var: position = ${file.position} -->',
							'<!-- var: verifystate = ${file.verifystate} -->',
							
							'<!-- if:${verifystate} & ${position}!="0" -->',
						
								'<!-- var: uploadFileTip = "此人需要身份认证" -->',
								'<!-- var: uploadFileClass = "negative" -->',
							    '<!-- if:${verifystate} == "已通过" -->',
								    '<!-- var: uploadFileTip = "此人已通过身份认证" -->',
									'<!-- var: uploadFileClass = "positive" -->',
							    '<!-- /if -->',
							    '<!-- if:${verifystate} =="已提交" -->',
							    	'<!-- var: uploadFileTip = "此人已提交身份认证" -->',
									'<!-- var: uploadFileClass = "positive" -->',
								'<!-- /if -->',
							    '<div class="uploadfile">',
									'<div class="upload_container ${uploadFileClass}">',  
										'<div class="upload_touch">',
										'<div class="upload_tip">${uploadFileTip}</div>',
										'<div class="file-input hide" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" sn="${file.sn}"></div>',
									    '</div>',
									'</div>',
									'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
								'</div>',
							'<!-- else -->',
								'<!-- if:${file.cardType} -->',
								    '<!-- if:${file.cardType} == "1" -->',
										'<div class="uploadfile">',
										'<div class="uploadcontainer clearfix">',
										'<input type="file" uploadFileClass="upload_con" id="${index}0${item.categoryId}file" name="${index}0${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
										'<input type="file" uploadFileClass="upload_con" id="${index}1${item.categoryId}file" name="${index}1${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
											'<!-- if: ${file.fileId} -->',
											'<img src="" class="upload_img" approveMsg="${file.approveMsg}" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" >',
											'<div class="upload_state upload_state_${file.state}"></div>',
												'<!-- if:${file.state} == "3" -->',
											    '<div class="upload_close hide"></div>',
											    '<!-- else -->',
											    	'<!-- if:${position}!="0" -->',
											    		'<div class="upload_close hide"></div>',
											    	'<!-- else -->',
											    		'<div class="upload_close"></div>',
											    		'<!-- /if -->',
											    '<!-- /if -->',
											'<!-- else -->',
											'<img src="" class="upload_img hide" approveMsg="" categoryId="${item.categoryId}" fId="" fileId="" thumbFileId="" refId="${file.refId}" refText="${file.refText}" state="" >',
											'<div class="upload_state hide"></div>',
											'<div class="upload_close hide"></div>',
											'<!-- /if -->',
										'</div>',
										'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
										'</div>',
									'<!-- else -->',
										'<div class="uploadfile">',
										'<input type="file" id="${index}${item.categoryId}file" name="${index}${item.categoryId}file" approveMsg="${file.approveMsg}" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" callback= "addUploadCase" >',
										'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
										'</div>',
									'<!-- /if -->',
								'<!-- else -->',
									'<div class="uploadfile">',
									'<div class="uploadcontainer clearfix">',
									'<input type="file" uploadFileClass="upload_con" id="${index}0${item.categoryId}file" name="${index}0${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
									'<input type="file" uploadFileClass="upload_con" id="${index}1${item.categoryId}file" name="${index}1${item.categoryId}file" fileId="" thumbFileId="" refId="" refText="" state="" callback="combiningUpload">',
										'<!-- if: ${file.fileId} -->',
										'<img src="" class="upload_img" approveMsg="${file.approveMsg}" categoryId="${item.categoryId}" fId="${file.fId}" fileId="${file.fileId}" thumbFileId="${file.thumbFileId}" refId="${file.refId}" refText="${file.refText}" state="${file.state}" >',
										'<div class="upload_state upload_state_${file.state}"></div>',
											'<!-- if:${file.state} == "3" -->',
										    '<div class="upload_close hide"></div>',
										    '<!-- else -->',
										    	'<!-- if:${position}!="0" -->',
										    		'<div class="upload_close hide"></div>',
										    	'<!-- else -->',
										    		'<div class="upload_close"></div>',
										    	'<!-- /if -->',
										    '<!-- /if -->',
										'<!-- else -->',
										'<img src="" class="upload_img hide" approveMsg="" categoryId="${item.categoryId}" fId="" fileId="" thumbFileId="" refId="${file.refId}" refText="${file.refText}" state="" >',
										'<div class="upload_state hide"></div>',
										'<div class="upload_close hide"></div>',
										'<!-- /if -->',
									'</div>',
									'<div class="upload_inf" title="${file.refText}">${file.refText}</div>',
									'</div>',
								'<!-- /if -->',
							'<!-- /if -->',
							'<!-- /for -->',
						'<!-- /if -->', 
					'<!-- /if -->', 
					'</div>',
					'</div>',
					
				'<!-- /for -->', 
				
				'<!-- /target -->' ].join("\n"),
		data : {

		}
	};

	TEMPLATE.scope_tags = {
		tpl : [
				'<!-- target:scope_tags-->',
				'<div class="tags_top">',
				'<!-- for:${groups} as ${group},${indexg} -->',
				'<span for="${group.code}" index="${indexg}">${group.name}</span>',
				'<!-- /for -->',
				'</div>',
				'<!-- for:${tags} as ${tag}, ${key} -->',
				'<div class="tags_content" id="${key}" >',
					'<!-- for:${tag} as ${item} -->',
					'<span code="${item.code}" group="${item.groupCode}" class="tag_content">${item.name}</span>',
					'<!-- /for -->', 
				'</div>', 
				'<!-- /for -->', 
				'<!-- /target -->' 
			].join("\n"),
		 data:{}
		
	};

	return TEMPLATE;
});

/*
 * 'master': { tpl: [ '<!-- target: myTpl(master = mainContent) -->', '<!--
 * block: content -->', ' this is master', '<!-- /block -->', '<!-- /target
 * -->', this.header.main-content ].join('\n') },
 * 
 * 'import-block': { tpl: [ '<!-- target: myTpl -->', '<!-- import:
 * mainContent -->', '<!-- block: content -->', 'this is import', '<!-- /block
 * -->', '<!-- /target -->' ].join('\n') },
 * 
 * 'use': { tpl: [ '<ul>', '<!-- for: ${persons} as ${p} -->', ' <!-- use:
 * item(main=${p.name}, sub=${p.email}) -->', '<!-- /for -->', '</ul>', '', '<!--
 * target: item --><li>${main}[${sub}]</li>' ].join('\n'), data:{ persons: [ {
 * name:"erik", email:"errorrik@gmail.com" }, { name:"otakustay",
 * email:"otakustay@gmail.com" } ] } },
 * 
 * 'var': { tpl: [ '<!-- var: name = "errorrik" -->', 'Hello ${name}!'
 * ].join('\n') }
 * 
 *  };
 */

