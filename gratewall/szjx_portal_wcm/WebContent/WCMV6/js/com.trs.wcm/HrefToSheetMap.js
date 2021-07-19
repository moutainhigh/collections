$package('com.trs.wcm');

/*
 *定义从链接地址到页面底部sheet的映射
 */
var $HrefToSheetMap = com.trs.wcm.HrefToSheetMap = {
	$default : 'document',
	right_set : 'right',
	channel_thumbs_index : 'channel',
	chnl_recycle_list : 'channel',
	document_normal_index : 'document',
	document_readmode_index : 'document',
	extendfield_index : 'extendfield',
	template_args_index : 'templatearg',
	template_index : 'template',
	website_thumbs_index : 'website',
	site_recycle_list : 'website',
	replace_list : 'replace',
	docsyn_dis_list : 'documentsyn',
	docsyn_col_list : 'documentsyn',
	distribution_list : 'distribution',
	contentlink_index : 'contentlink',
	doc_recycle_list :'trashbox',
	video_list : 'document',
	document_list_4_linkchannel : 'document',
	photo_list : 'document',
	watermark_list : 'watermark',
    workflow_list : 'workflow',
    flowemployee_view : 'flowemployee'
};

var $VirtualSheetArray = [
	//'doc_search_result_list.html'
]