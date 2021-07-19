Object.extend(PageContext,{
	//为了使页面具有行为,定义Mgr对象
	ObjectMgr : ViewClsTemplateMgr,

	//为了右侧显示操作栏和属性栏,定义右侧面板的类型
	ObjectType	: 'ViewClsTemplate'
});
Object.extend(Grid,{
	draggable : false
});
