/**--metaview.js--**/
//多语种
Ext.apply(wcm.LANG,{
	METAVIEWDATA_1 : '确定',
	METAVIEWDATA_2 : '没选中任何模式',
	METAVIEWDATA_3 : '请选择当前记录要复制到的目标栏目!',
	METAVIEWDATA_4 : '执行进度',
	METAVIEWDATA_5 : '提交数据',
	METAVIEWDATA_6 : '成功执行完成',
	METAVIEWDATA_7 : '记录复制结果',
	METAVIEWDATA_8 : '栏目名称:',
	METAVIEWDATA_9 : ',视图名称:',
	METAVIEWDATA_10 : '源栏目和目标栏目使用的视图不一致,无法进行复制',
	METAVIEWDATA_11 : '导出',
	METAVIEWDATA_12 : '请选择要当前记录要移动到的目标栏目!',
	METAVIEWDATA_13 : '不能将当前记录从当前栏目移动到自身!',
	METAVIEWDATA_14 : '记录移动结果',
	METAVIEWDATA_15 : '源栏目和目标栏目使用的视图不一致,无法进行移动',
	METAVIEWDATA_16 : '请输入一个正整数.',
	METAVIEWDATA_17 : '请选择当前文档要引用到的目标栏目!',
	METAVIEWDATA_18 : '记录引用结果',
	METAVIEWDATA_19 : '源栏目和目标栏目使用的视图不一致,无法进行引用',
	METAVIEWDATA_20 : '导入',
	METAVIEWDATA_21 : '记录导入结果',
	METAVIEWDATA_22 : '尚未选择由TRS数据库导出的XML文件.',
	METAVIEWDATA_23 : '未选择其他XML文件.',
	METAVIEWDATA_24 : '管理TRS映射关系',
	METAVIEWDATA_25 : '记录—改变位置到',
	METAVIEWDATA_26 : '确实要将这',
	METAVIEWDATA_27 : '条记录放入废稿箱吗? ',
	METAVIEWDATA_28 : '删除进度',
	METAVIEWDATA_29 : '删除',
	METAVIEWDATA_30 : '删除数据结果',
	METAVIEWDATA_31 : '记录-改变状态',
	METAVIEWDATA_32 : '没有指定视图ID[VIEWID]',
	METAVIEWDATA_33 : '导出记录',
	METAVIEWDATA_34 : '设置数据同步到WCMDocument的规则',
	METAVIEWDATA_35 : '导入记录',
	METAVIEWDATA_36 : '引用记录',
	METAVIEWDATA_38 : '复制记录',
	METAVIEWDATA_39 : '移动记录',
	METAVIEWDATA_40 : '改变记录顺序到',
	METAVIEWDATA_41 : '修改这条记录',
	METAVIEWDATA_42 : '预览这条记录',
	METAVIEWDATA_43 : '预览这条记录发布效果',
	METAVIEWDATA_44 : '发布这条记录',
	METAVIEWDATA_45 : '发布这条记录，生成这条记录的细览页面以及更新相关概览页面',
	METAVIEWDATA_46 : '仅发布这条记录细览',
	METAVIEWDATA_47 : '仅发布这条记录细览，仅重新生成这条记录的细览页面',
	METAVIEWDATA_48 : '撤销发布这条记录',
	METAVIEWDATA_49 : '撤销发布这条记录，撤回已发布目录或页面',
	METAVIEWDATA_50 : '导出这条记录',
	METAVIEWDATA_51 : '将这条记录导出成zip文件',
	METAVIEWDATA_52 : '分隔线',
	METAVIEWDATA_53 : '改变这条记录状态',
	METAVIEWDATA_54 : '移动这条记录到',
	METAVIEWDATA_55 : '复制这条记录到',
	METAVIEWDATA_56 : '引用这条记录到',
	METAVIEWDATA_57 : '将记录放入废稿箱',
	METAVIEWDATA_58 : '新建一条记录',
	METAVIEWDATA_59 : '从外部导入记录',
	METAVIEWDATA_60 : '从Excel创建记录',
	METAVIEWDATA_61 : '设置同步规则',
	METAVIEWDATA_62 : '设置同步到文档的规则',
	METAVIEWDATA_63 : '预览这些记录',
	METAVIEWDATA_64 : '预览这些记录发布效果',
	METAVIEWDATA_65 : '发布这些记录',
	METAVIEWDATA_66 : '发布这些记录，生成这些记录的细览页面以及更新相关概览页面',
	METAVIEWDATA_67 : '仅发布这些记录细览',
	METAVIEWDATA_68 : '仅发布这些记录细览，仅重新生成这些记录的细览页面',
	METAVIEWDATA_69 : '撤销发布这些记录',
	METAVIEWDATA_70 : '撤销发布这些记录，撤回已发布目录或页面',
	METAVIEWDATA_71 : '导出这些记录',
	METAVIEWDATA_72 : '将这些记录导出成zip文件',
	METAVIEWDATA_73 : '改变这些记录状态',
	METAVIEWDATA_74 : '移动这些记录到',
	METAVIEWDATA_75 : '复制这些记录到',
	METAVIEWDATA_76 : '引用这些记录到',
	METAVIEWDATA_77 : '新建',
	METAVIEWDATA_78 : '删除',
	METAVIEWDATA_79 : '预览',
	METAVIEWDATA_80 : '快速发布',
	METAVIEWDATA_81 : '移动',
	METAVIEWDATA_82 : '复制',
	METAVIEWDATA_83 : '引用',
	METAVIEWDATA_84 : '刷新',
	METAVIEWDATA_85 : '刷新列表',
	METAVIEWDATA_86 : '资源列表管理',
	METAVIEWDATA_87 : '查看记录',
	METAVIEWDATA_88 : '已经复制到剪切板中！',
	METAVIEWDATA_89 : '您的浏览器不支持自动复制操作',
	METAVIEWDATA_90 : '没有设置字段的分类法信息！',
	METAVIEWDATA_91 : '未知的变量类型',
	METAVIEWDATA_92 : '未选择',
	METAVIEWDATA_93 : '检索',
	METAVIEWDATA_94 : '信息',
	METAVIEWDATA_95 : '附件管理',
	METAVIEWDATA_96 : '简易编辑器',
	METAVIEWDATA_97 : '选择数据',
	METAVIEWDATA_98 : '没有加载完成',
	METAVIEWDATA_99 : '非法数据',
	METAVIEWDATA_100 : '保存',
	METAVIEWDATA_101 : '关闭',
	METAVIEWDATA_102 : '个',
	METAVIEWDATA_103 : '资源',
	METAVIEWDATA_104 :'创建者',
	METAVIEWDATA_105 : '高级检索',
	METAVIEWDATA_106 : '修改',
	METAVIEWDATA_107 : '取消',
	METAVIEWDATA_108 : '记录',
	METAVIEWDATA_109 : '上传文件失败！',
	METAVIEWDATA_110 : '导出所有记录',
	METAVIEWDATA_111 : '此操作可能需要较长时间。确实要导出所有记录吗？',
	METAVIEWDATA_112 : '记录-导出所有记录',
	METAVIEWDATA_113 : '批量修改',
	METAVIEWDATA_114 : '提交数据',
	METAVIEWDATA_115 : '开始时间大于结束时间，请重新输入.',
	METAVIEWDATA_116 : '不能为空.',
	METAVIEWDATA_117 : '设置这条记录权限',
	METAVIEWDATA_118 : '序号',
	METAVIEWDATA_119 : '文档标题',
	METAVIEWDATA_120 : '排序',
	METAVIEWDATA_121 : '--当前文档--',
	METAVIEWDATA_122 : '上移',
	METAVIEWDATA_123 : '下移',
	METAVIEWDATA_124 : '请先在视图中设置分类法',
	METAVIEWDATA_125 : '分类法id可能被修改过，请注意',
	METAVIEWDATA_126 : '新建记录',
	METAVIEWDATA_127 : 'DocTitle和DocContent值加起来不要超过500字节.请尽量简短.',
	METAVIEWDATA_128 : '请选择导出字段.',
	METAVIEWDATA_129 : '删除记录',
	METAVIEWDATA_130 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选记录么？将<font color='red' style='font-size:14px;'>不可逆转</font>！",
	FILTER_METAVIEWDATA_ALL : '全部资源',
	METAVIEWDATA_131 : '直接发布这条记录',
	METAVIEWDATA_132 : '发布这条记录细览，同时发布此记录的所有引用记录',
	METAVIEWDATA_133 : '直接撤销发布这条记录',
	METAVIEWDATA_134 : '撤回当前记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
	METAVIEWDATA_135 : '直接发布这些记录',
	METAVIEWDATA_136 : '发布这些记录细览，同时发布这些记录的所有引用记录',
	METAVIEWDATA_137 : '直接撤销发布这些记录',
	METAVIEWDATA_138 : '撤回这些记录的发布页面，并同步撤销记录的所有引用以及原记录发布页面',
	METAVIEWDATA_139 : "确定要<font color='red' style='font-size:14px;'>撤销发布</font>所选记录及其所有引用记录么？将<font color='red' style='font-size:14px;'>不可逆转</font>！"
});