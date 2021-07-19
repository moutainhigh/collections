/**
* 导航树上的右键菜单主要由三部分组成，
* 1)当前单击对象的可用操作
* 2)当前单击对象下面的子对象上的可用操作
* 3)除上两部分之外的其他操作
*/

/*
*是否和右边面板一直。意指右键菜单第一部分显示的菜单项数目是否
*为右边第二块操作面板的个数一样（除更多部分）
*/
var isSameToOperatorPanel = true;


/**
* 对右键菜单上的第一部分内容进行配置，默认添加当前对象的所有操作
* 单个对象名称由operType operKey的小写组成，如：channel_edit
* name	:菜单项上显示的名称
* desc	:菜单项上显示的title
* hide	:是否隐藏的标记。true表示隐藏，false表示显示
*/
var OperatorUpdateMapping = {
	channel_edit : {
		name	: '修改这个栏目', //菜单项上显示的文字
		desc	: '修改这个栏目', //菜单项上显示的title
		hide	: false //菜单是否显示的标记
	},
	channel_preview : {
		name	: '预览这个栏目',
		desc	: '预览这个栏目',
		hide	: false
	}
	//and so on...
};

////////////////////////////////////////////////////////////////////////////////
/**
* 对右键菜单上的第二部分内容进行配置，默认没有添加操作
* 单个对象名称由operType operKey的小写组成，如：channel_edit
* name	:菜单项上显示的名称
* desc	:菜单项上显示的title
* hide	:是否隐藏的标记。true表示隐藏，false表示显示
*/
var OperatorAddMapping = {
	Root : {//文字库右键菜单上要添加的操作
		WebSiteRoot_confvideolib : {
			name	: '视频库设置', //菜单项上显示的文字
			desc	: '视频库设置', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},
		WebSiteRoot_new : {
			name	: '创建一个新站点', //菜单项上显示的文字
			desc	: '创建一个新站点', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},
		WebSiteRoot_quicknew : {
			name	: '智能创建一个新站点', //菜单项上显示的文字
			desc	: '智能创建一个新站点', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},
		WebSiteRoot_import : {
			name	: '从外部导入站点', //菜单项上显示的文字
			desc	: '从外部导入站点', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},	
		WebSiteRoot_confphotolib : {
			name	: '图片库设置', //菜单项上显示的文字
			desc	: '图片库设置', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		}		
		//and so on...	
	},

	Site : {//站点右键菜单上要添加的操作
		websiteHost_new : {
			name	: '新建栏目', //菜单项上显示的文字
			desc	: '新建一个栏目', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},
		templateInSite_new : {
			name	: '新建模板',
			desc	: '新建一个模板',
			hide	: false
		},
		templateInSite_import : {
			name	: '导入模板',
			desc	: '导入模板',
			hide	: false
		},
		videoInSite_live : {
			name	: '新建视频直播',
			desc	: '建立新的直播视频',
			hide	: false,
			hideCondition : function(params){//true将隐藏,false将显示
				if(params["isVirtual"]){
					return true;
				}
				return ($nav().findSiteType($nav().$(params["prefix"]+"_"+params["id"])) != 2);
			}
		}
		//and so on...	
	},

	Channel : {//栏目右键菜单上要添加的操作
		documentInChannel_new : {
			name	: '新建文档', //菜单项上显示的文字
			desc	: '新建一篇文档', //菜单项上显示的title
			hide	: false, //菜单是否显示的标记	
			hideCondition : function(params){//true将隐藏,false将显示
				if(params["isVirtual"]){
					return true;
				}
				var siteType = $nav().findSiteType($nav().$(params["prefix"] + "_" + params["id"]));
				return siteType != 0;
			}
		},
		photoInChannel_upload : {
			name	: '上传新图片', //菜单项上显示的文字
			desc	: '上传一幅新图片', //菜单项上显示的title
			hide	: false, //菜单是否显示的标记	
			hideCondition : function(params){//true将隐藏,false将显示							
				if(params["isVirtual"]){
					return true;
				}				
				return ($nav().findSiteType($nav().$(params["prefix"]+"_"+params["id"])) != 1);
			}
		},
		videoInChannel_upload : {
			name	: '新建视频', //菜单项上显示的文字
			desc	: '上传新视频', //菜单项上显示的title
			hide	: false, //菜单是否显示的标记	
			hideCondition : function(params){//true将隐藏,false将显示							
				if(params["isVirtual"]){
					return true;
				}				
				return ($nav().findSiteType($nav().$(params["prefix"]+"_"+params["id"])) != 2);
			}
		},
		videoInChannel_record : {
			name	: '新建视频录制', //菜单项上显示的文字
			desc	: '在线录制新视频', //菜单项上显示的title
			hide	: false, //菜单是否显示的标记	
			hideCondition : function(params){//true将隐藏,false将显示							
				if(params["isVirtual"]){
					return true;
				}				
				return ($nav().findSiteType($nav().$(params["prefix"]+"_"+params["id"])) != 2);
			}
		},
		videoInChannel_multiupload : {
			name	: '批量新建视频', //菜单项上显示的文字
			desc	: '一次上传多个新视频', //菜单项上显示的title
			hide	: false, //菜单是否显示的标记	
			hideCondition : function(params){//true将隐藏,false将显示							
				if(params["isVirtual"]){
					return true;
				}				
				return ($nav().findSiteType($nav().$(params["prefix"]+"_"+params["id"])) != 2);
			}
		},
		channelHost_new : {
			name	: '新建栏目', //菜单项上显示的文字
			desc	: '新建一个子栏目', //菜单项上显示的title
			hide	: false //菜单是否显示的标记
		},
		templateInChannel_new : {
			name	: '新建模板',
			desc	: '新建一个模板',
			hide	: false,
			hideCondition : function(params){
				var excludeChannelTypes = [
					1,	//图片新闻
					2,	//头条新闻
					11	//链接栏目
				];
				if(params["channelType"] && excludeChannelTypes.include(params["channelType"])){
					return true;
				}
				return false;
			}
		},
		templateInChannel_import : {
			name	: '导入模板',
			desc	: '导入模板',
			hide	: false,
			hideCondition : function(params){
				var excludeChannelTypes = [
					1,	//图片新闻
					2,	//头条新闻
					11	//链接栏目
				];
				if(params["channelType"] && excludeChannelTypes.include(params["channelType"])){
					return true;
				}
				return false;
			}
		}
		//and so on...	
	}
};


//////////////////////////////////////////////////////////////////////////////
/**
*获得从operType到Mgr的隐射
*/
var ManagerMapping = {
	getManagerName : function(operType){
		var mgr = '';
		switch(operType){
			case "website":
			case "WebSiteRoot":
			case "websites":	
				mgr = 'WebSiteMgr';
				break;
			case "channel":
			case "websiteHost":
			case "channelHost":
			case "channelMaster":
			case "channels":
				mgr = 'ChannelMgr';
				break;
			case "template":
			case "templateInChannel":
			case "templateInSite":
			case "templates":
				mgr = 'TemplateMgr';
				break;
			case "extendfield":
			case "extendFieldInChannel":
			case "extendFieldInSite":
			case "extendFieldInSys":
			case "extendfields":
				mgr = 'ExtendFieldMgr';
				break;
			case "documentInChannel":
			case "documentInSite":
				mgr = 'ChnlDocMgr';
				break;
			//case "photo":
			case "photoInChannel":
			//case "photoInSite":
			//case "photos":
				mgr = "PhotoMgr";
				break;
			case "videoInChannel":
			case "videoInSite":
				mgr = "VideoMgr";
				break;
/*
 * 如果有必要，请将对应的case语句取消注释，并同时添加对应的mgr.
			case "chnldoc":
			case "chnldocs":

			case "chnlrecycle":
			case "chnlrecycleInChannel":
			case "chnlrecycleInSite":
			case "chnlrecycles":

			case "contentlinkInChannel":

			case "distribution":
			case "distributionInSite":
			case "distributions":

			case "docrecycle":
			case "docrecycleInChannel":
			case "docrecycleInSite":
			case "docrecycles":

			case "docSynCol":
			case "docSynColInChannel":
			case "docSynCols":

			case "docSynDis":
			case "docSynDisInChannel":
			case "docSynDiss":

			case "photo":
			case "photoInChannel":
			case "photoInSite":
			case "photos":

			case "replace":
			case "replaceInChannel":
			case "replaces":

			case "siterecycle":
			case "siterecycleHost":
			case "siterecycles":


			case "video":
			case "videoInChannel":
			case "videos":

			case "watermark":
			case "watermarkInSite":
			case "watermarks":
*/
			default : 
				alert('竟然没有找到对应的operType!' + "\noperType = [" + operType + "]");
		}
		return mgr;
	}
};