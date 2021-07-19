	
	delete from FUNCINFO_NEW;
	delete from OPERROLE_NEW;
	delete from FUNCTXN_NEW;
	
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000002', '使用说明', '7000000', null, '1', null, 'dctest2', '20130722', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2000000', '资源管理', null, null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '服务对象管理', '2000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000001', '平台介绍', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '数据源管理', '2000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '通知管理', '100000', null, '1', null, 'hjp', '20090727', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '采集资源管理', '2000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '共享资源查看', '2000000', null, '1', null, 'yuxinzhong', '20130314', 'dctest2', '20130716');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040000', '标准规范管理', '2000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '服务时间管理', '2000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000004', '服务对象的职能,角色介绍', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '数据标准', '2040000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '技术标准', '2040000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('54000000', '系统在线用户', '100000', null, '1', null, 'hjp', '20090224', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '命名规范', '2040000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3000000', '采集任务', null, null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '任务配置', '3000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4000000', '共享服务', null, null, '1', null, 'yuxinzhong', '20130314', 'dctest2', '20130718');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '接口管理', '4000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '服务配置', '4000000', null, '1', null, 'yuxinzhong', '20130314', 'dctest2', '20130716');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5000000', '运行监控', null, null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5010000', '实时监控', '5000000', null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5020000', '监控指标管理', '5000000', null, '1', null, 'yuxinzhong', '20130314', 'dctest2', '20130718');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5030000', '监控管理', '5000000', null, '1', null, 'yuxinzhong', '20130314', 'dctest', '20130820');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000000', '帮助中心', null, null, '1', null, 'yuxinzhong', '20130314', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000005', '采集服务流程', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000006', '共享服务流程', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000007', '交换平台部署', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000008', '交换流程', '7000000', null, '1', null, 'dctest', '20130719', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('100000', '系统管理', null, null, '1', null, 'system', '20121109', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '机构管理', '100000', null, '1', 's', 'system', '20070809', 'system', '20080813');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '用户管理', '100000', null, '1', null, 'system', '20070809', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '角色管理', '100000', null, '1', null, 'system', '20070809', 'system', '20080813');
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '功能权限', '100000', null, '1', null, 'yuxinzhong', '20121130', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6000000', '日志管理', null, null, '1', null, 'yuxinzhong', '20130312', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '日志查询', '6000000', null, '1', null, 'yuxinzhong', '20130312', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '日志统计', '6000000', null, '1', null, 'yuxinzhong', '20130312', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '使用报告', '6000000', null, '1', null, 'yuxinzhong', '20130312', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5400000', '监控事件', '5000000', null, '1', null, 'yuxinzhong', '20130801', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5500000', '警情发现与处理', '5000000', null, '1', null, 'yuxinzhong', '20130801', null, null);
	insert into FUNCINFO_NEW (FUNCCODE, FUNCNAME, PARENTCODE, GROUPNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '检索服务配置', '4000000', null, '1', null, 'dctest', '20130820', null, null);
	
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (102, 'user', '普通用户', null, null, null, null, null, null, null, '1', null, 'system', '20121126', 'system', '20080822', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (101, 'system', '用户范围：系统管理；权限范围：所有权限', '806000;807000;980320;54000000;60800000;980300;6020000;6030000;6010000;2010000;2010200;2030000;2050000;2040100;2040300;2040200;2020000;3010000;3010300;3010200;7000001;7000008;7000007;7000004;7000005;7000006;7000002;5030000;5020000;5010000;5400000;5500000;4020000;4010000;4010013', null, null, null, null, null, null, '1', null, 'system', '20121126', 'crcs', '20131213', 99999999);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000305, '资源管理', null, '2010000;2010200;2030000;2050000;2040100;2040300;2040200;2020000', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'crcs', '20131213', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000307, '采集任务', null, '3010000', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'yuxinzhong', '20130427', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000309, '共享服务', null, '4020000;4010000;4010013', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'crcs', '20131213', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000311, '运行监控', null, '5030000;5020000;5010000;5400000;5500000', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'crcs', '20131213', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000313, '帮助中心', '111224234', '7000001;7000008;7000007;7000004;7000005;7000006;7000002', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'crcs', '20131218', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (10000101, '日志管理', '日志管理', '6030000;6020000;6010000', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20130312', 'dctest3', '20130719', 0);
	insert into OPERROLE_NEW (ROLEID, ROLENAME, DESCRIPTION, FUNCLIST, ROLESCOPE, GROUPNAME, HOMEPAGE, LAYOUT, ROLETYPE, ROLETYPE2, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE, MAXCOUNT)
	values (1643400, '系统管理', '权限范围：具有用户管理，通知管理，查看系统在线用户的权限', '806000;807000;980320;54000000;60800000;980300', null, null, null, null, null, null, '1', null, 'yuxinzhong', '20121126', 'crcs', '20131213', 0);
	
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6011010', '查询采集日志列', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6011011', '查询采集日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6011006', '查看采集日志信息', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6011020', '查询日志列表(采集\共享)', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6010010', '查询共享日志', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6010011', '查询共享日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6010006', '查看共享日志信息', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6010007', '查看运行监控日志', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '981211', '查询业务日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '981214', '查询业务日志', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '601011', '查询系统日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '601016', '跳转系统日志信息', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6010001', '查询共享日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6010000', '6011001', '查询采集日志列表', '1', null, 'crcs', '20140127', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200107', '发布日志报告', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200108', '增加日志报告使用记录', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200109', '退回日志报告', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200110', '查询报告名称是否存在', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200111', '生成日志报告', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5400000', '5400000', '监控事件', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5500000', '5500000', '警情发现与管理', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('54000000', '981214', '查询业务日志', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('54000000', '81100001', '查询业务日志列表', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200201', '查询报告操作情况列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200101', '查询系统使用情况报告列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300011', '查询trs接口列表-框架页', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300001', '查询trs接口列表', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300002', '修改trs接口信息', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300003', '增加trs接口信息', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980301', '查询功能信息列表', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980302', '修改功能信息资料', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980303', '增加功能信息资料', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980304', '查询功能信息用于修改', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980305', '删除功能信息', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980307', '移动功能节点/修改上级节点的功能代码', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980308', '生成功能的交易列表文档', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980309', '导出功能信息', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '9803101', '查询树第一层节点', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '9803102', '查询子节点', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '9803103', '查询子节点', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980311', '查询功能交易列表', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980313', '增加功能交易资料', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980315', '删除功能交易信息', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980316', '批量查询交易列表', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '980317', '批量增加交易列表', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980300', '9803001', '功能权限', '1', null, 'dctest2', '20130717', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300004', '查询trs接口用于修改', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300005', '删除trs接口信息', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300006', '查询trs最大编号', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300007', '查询已有TRS库', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300008', 'trs共享服务(启用停用)', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010013', '40300009', '查询trs接口用于展示', '1', null, 'dctest', '20130820', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200102', '修改系统使用情况报告信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200103', '增加系统使用情况报告信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102015', '查询database数据源表用于修改', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102018', '查看数据库数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102019', '校验数据源是否已经被引用', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102021', '增加webService数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102022', '修改数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102023', '测试socket连接', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102024', '查询数据源表用于修改', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102026', '查看webservice数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102027', '增加webService数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102028', '修改数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102029', '查看JMS数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102030', '测试JMS连接', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102031', '查询数据源表用于修改', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807001', '查询用户列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807011', '机构内查询用户列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807004', '查询用户用于修改', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807014', '机构内查询用户用于修改', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807002', '修改用户资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807012', '机构内修改用户资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200104', '查询系统使用情况报告用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200105', '删除系统使用情况报告信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6030000', '620200106', '上传日志报告', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807003', '增加用户资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807013', '机构内增加用户资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807005', '停用用户', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807006', '修改用户密码信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807007', '初始化用户密码', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807008', '启用用户', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980301', '查询功能信息列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980321', '查询业务角色列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980322', '修改业务角色资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980323', '增加业务角色资料', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980324', '查询业务角色用于修改', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980325', '删除业务角色信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980326', '设置角色的状态', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980327', '设置角色的权限信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980328', '角色复制', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980329', '查询业务角色列表，不分页', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980330', '禁用角色', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('980320', '980331', '启用角色', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('807000', '807009', '校验账号', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205001', '查询例外日期', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205009', '查询例外日期列表用于日历', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205010', '修改例外日期信息', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205003', '增加例外日期信息', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205006', '增加例外日期', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205008', '批量删除例外日期信息', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205007', '删除例外日期', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205004', '查询例外日期用于修改', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2050000', '205005', '删除例外日期信息', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '201008', '共享资源查看', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5010000', '5100000', '跳转到实时监控', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5020000', '5200000', '跳转到监控指标管理', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('5030000', '5300000', '跳转到警情发现与管理', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301021', '查询共享数据项信息列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301022', '修改共享数据项信息信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301023', '增加共享数据项信息信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301024', '查询共享数据项信息用于修改', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301025', '删除共享数据项信息信息', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2030000', '20301026', '查询代码集列表', '1', null, 'dctest2', '20130718', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000001', '7100001', '跳转到平台介绍', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000002', '7100002', '跳转到使用说明', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000004', '7100004', '跳转到服务对象职能、角色介绍', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000005', '7100005', '跳转到采集服务流程', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000006', '7100006', '跳转到共享服务流程', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000007', '7100007', '跳转到交换平台部署', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('7000008', '7100008', '跳转到交换流程', '1', null, 'dctest2', '20130723', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201012', '同步内部采集表结构', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603016', '查看数据标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603904', '查询技术标准', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603905', '查询技术标准列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603006', '查询技术标准列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603007', '修改技术标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603008', '增加技术标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603009', '查询技术标准用于修改', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201013', '查询采集数据表信息表用于修改（内部系统）', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202000', '查询数据项名称是否重复', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202001', '查询采集数据项表列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202002', '修改采集数据项信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202003', '增加采集数据项信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202004', '查询采集数据项表用于修改', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202005', '删除采集数据项信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202006', '查看采集数据项信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202007', '查询采集数据项表用于新增', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202008', '保存采集数据项表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20202009', '保存采集数据项表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603901', '查询数据标准', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603010', '删除技术标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040200', '603017', '查看技术标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603907', '查询命名规范', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603908', '查询命名规范列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603011', '查询命名规范列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603012', '修改命名规范信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603013', '增加命名规范信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603014', '查询命名规范用于修改', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603015', '删除命名规范信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040300', '603018', '查看命名规范信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200001', '查询共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40209001', '查询共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40209002', '查询共享服务列表', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200002', '修改共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200003', '增加共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200004', '查询服务表用于修改', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200005', '删除共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200006', '原增加服务表信息', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200007', '根据接口ID获取数据表列表', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200008', '根据数据表ID获取数据项', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200009', '增加共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200010', '修改共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200011', '删除共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200012', '获取最大编号', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200013', '共享服务(启用停用)', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200014', '查询服务表用于展示', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200016', '导出共享服务', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4020000', '40200101', '查询服务号', '1', null, 'dctest2', '20130716', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806001', '查询机构列表', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806002', '修改机构资料', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806007', '增加机构资料', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806003', '增加机构资料', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806004', '查询机构用于修改', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806005', '删除机构信息', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '806006', '查询机构用于修改', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('806000', '807011', '查询用户列表', '1', null, 'system', '20080902', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102001', '查询数据源表列表', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102020', '查询数据源表列表-框架页', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102002', '修改数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102003', '增加webService数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102007', '增加FTP数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102008', '增加数据库数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102004', '查询数据源表用于修改', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102005', '删除数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102006', '查看webservice数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102010', '查看ftp数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102011', '查看数据库数据源', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102009', '测试FTP数据源连接', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102016', '测试数据库数据源连接', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102017', '测试webservice连接', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102012', '修改ftp数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102013', '修改database数据源表信息', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010200', '20102014', '查询ftp数据源表用于修改', '1', null, 'yuxinzhong', '20130805', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101031', '增加数据库采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101032', '修改数据库采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101034', '查询采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101035', '查询采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101135', '查询采集任务用于预览', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101036', '删除采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101051', '查询采集任务用于新增socket', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '40109001', '查询接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '60800001', '查询弹窗任务列表', '1', null, 'hjp', '20090727', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '60800002', '修改弹窗任务信息', '1', null, 'hjp', '20090727', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '60800003', '增加弹窗任务信息', '1', null, 'hjp', '20090727', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '60800004', '查询弹窗任务用于修改', '1', null, 'hjp', '20090727', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('60800000', '60800005', '删除弹窗任务信息', '1', null, 'hjp', '20090727', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '40109002', '查询接口列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401001', '查询接口列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401002', '修改共享接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401003', '增加共享接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401004', '去修改共享接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401005', '删除共享接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401010', '查看共享接口配置', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401006', '查询共享主题列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401007', '根据业务系统ID获取本业务系统下的所有主题', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401008', '根据业务主题ID获取本业务主题下的所有表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603902', '查询数据标准列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603001', '查询数据标准列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603002', '修改数据标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603003', '增加数据标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603004', '查询数据标准用于修改', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2040100', '603005', '删除数据标准信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401009', '根据表ID获取本表下的所有共享数据项', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401011', '删除共享接口', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401012', '查询此接口是否被服务调用', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('4010000', '401013', '接口(启用/停用)', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201000', '查询数据表名称是否重复', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20209001', '查询采集数据表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20209002', '查询采集数据表列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201001', '查询采集数据表列表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201002', '修改采集数据表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201003', '增加采集数据表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201004', '查询采集数据表信息表用于修改', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201005', '删除采集数据表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201006', '查看采集数据表信息', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201007', '查询采集数据表信息表用于新增', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201008', '生成采集数据表物理表', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201009', '查询采集库数据表名称是否重复', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201010', '导入excel格式采集表并入库', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2020000', '20201011', '查询数据表是否被采集任务引用', '1', null, 'dctest2', '20130731', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101000', '查询数据源名称是否使用', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30109001', '查询采集任务', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101001', '查询采集任务列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101002', '修改webservice采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101003', '增加webservice采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101004', '查询采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101005', '删除采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101006', '查看webservice采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101016', '查看socket/jms采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101106', '查看webservice采集任务信息用于预览', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101007', '查询采集任务用于新增webservice', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101008', '获取数据', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101009', '获取方法并入库', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101010', '修改服务启用/停用状态', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101011', '查询采集任务用于新增ftp', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101012', '增加ftp采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101013', '查询采集任务用于修改ftp', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101014', '修改ftp采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101015', '查看ftp采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101115', '查看ftp采集任务信息用于预览', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101017', '获取文件', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101021', '增加文件上传采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101022', '查询文件上传采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101023', '保存文件上传采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101024', '删除采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101025', '查询文件上传采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101125', '查询文件上传采集任务用于预览', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101026', '查询文件上传采集任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101030', '查询采集任务用于新增数据库', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101052', '查询采集任务用于修改socket', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101053', '修改socket采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101061', '查询采集任务用于新增jms', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101062', '查询采集任务用于修改jms', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30101063', '修改jms采集任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103003', '增加参数维护信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103004', '查询参数维护用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103005', '删除参数维护信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103006', '查询参数维护用于新增', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103007', '查询参数维护用于修改socket', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201001', '查询ftp任务列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201002', '修改ftp文件信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201003', '增加ftp任务信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201004', '查询ftp任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201005', '删除ftp文件信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30201006', '查看ftp文件信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102001', '查询webservice任务列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102002', '修改webservice方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102003', '增加webservice方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102004', '查询webservice任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102005', '删除webservice方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102006', '查看webservice方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102007', '查询webservice任务用于新增', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102008', '查询socket任务用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102009', '修改socket方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102010', '查看socket方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30102011', '删除socket方法信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501002', '修改采集数据库信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501003', '增加采集数据库信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501004', '查询采集数据库用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501005', '删除采集数据库信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501006', '查询采集数据库用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501007', '查询采集数据库用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501001', '查询采集数据库列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501008', '查询采集数据库用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30501009', '查询采集数据库用于修改', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103001', '查询参数维护列表', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('3010000', '30103002', '修改webservice方法的参数信息', '1', null, 'crcs', '20131213', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201012', '查询服务对象列表框架页', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201001', '查询服务对象列表', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201002', '修改服务对象信息', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201003', '增加服务对象信息', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201004', '查询服务对象用于修改', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201005', '删除服务对象信息', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201006', '删除服务对象信息--设置标志位', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201007', '检查服务对象代码或名称是否正被使用', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201008', '共享资源查看', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201009', '查看服务对象信息', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201010', '修改启用/停用状态', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201011', '校验服务对象是否被引用', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('2010000', '201014', '查询服务对性排序', '1', null, 'crcs', '20131216', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '30101065', '采集任务数据统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '53000112', '共享服务数据统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '53000115', '采集任务数据统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '53000216', '服务对象数据统计表格树', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '40200026', '查询共享服务用于预览页面', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020001', '共享服务指标统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020002', '单个对象日志统计图', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020005', '进入未用服务情况统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020006', '未用服务情况统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020007', '服务异常情况统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020008', '服务分布情况统计', '1', null, 'crcs', '20140123', null, null);
	insert into FUNCTXN_NEW (FUNCCODE, TXNCODE, TXNNAME, STATUS, MEMO, REGNAME, REGDATE, MODNAME, MODDATE)
	values ('6020000', '6020009', '共享服务情况统计', '1', null, 'crcs', '20140123', null, null);
	commit;
	
	
