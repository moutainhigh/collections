---created ls@2007-3-21
--- V6视频选件的数据库更新(Oracle版)，主要包括对模板置标, 属性面板操作等数据表的更新
--- 与SQLServer的主要区别：
--- 1. 将getdate()替换为sysdate
--- ...

-- 针对多个Video对象的操作
delete from XWCMPAGEOPERATOR where OprKey='preview' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  '预览这些视频', 'preview', '预览这些视频的发布效果', 
'videos', 38, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- 针对 videoInChannel 的操作
delete from XWCMPAGEOPERATOR where OprKey='upload' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '新建视频', 'upload', '上传新视频', 'videoInChannel', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='record' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '新建视频录制', 'record', '在线录制新视频', 'videoInChannel', 32, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='live' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '新建视频直播', 'live', '建立新的直播视频', 'videoInChannel', 32, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- 针对 videoInSite 的操作
/*
delete from XWCMPAGEOPERATOR where OprKey='upload' and OprType='videoInSite';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '上传新视频', 'upload', '上传新视频', 'videoInSite', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;
*/

-- ls@2007-5-14 10:56 调整Video置标
delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO', 'com.trs.components.video.publish.VideoTagParser', 
 '提取流媒体信息置标解析器', 'admin', sysdate, 1 from WCMTAGBEANS;

delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO_PLAY';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO_PLAY', 'com.trs.components.video.publish.VideoPlayTagParser', 
 '播放流媒体置标解析器', 'admin', sysdate, 1 from WCMTAGBEANS;

-- ls@2007-6-5 15:40 增加引用、改变状态等操作, 更新很多操作的权限和显示次序 (begin)
-- 针对单个Video对象的操作
delete from XWCMPAGEOPERATOR where OprKey='preview' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  '预览该视频', 'preview', '预览该视频的发布效果', 
'video', 38, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='publish' and OprType='video';
delete from XWCMPAGEOPERATOR where OprKey='basicpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '发布该视频', 'basicpublish', '发布该视频', 'video', 39, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='copy' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '复制该视频到...', 'copy', '复制该视频到...', 'video', 34, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='move' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '移动该视频到...', 'move', '移动该视频到...', 'video', 65, 1, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='quote' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '引用该视频到...', 'quote', '引用该视频到...', 'video', 34, 1, 5, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='changeStatus' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '改变状态', 'changeStatus', '改变该视频的状态', 'video', 32, 0, 6, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='detailpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '仅发布该视频的细览', 'detailpublish', '仅重新生成该视频的细览页面', 'video', 39, 0, 7, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='recallpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '撤销发布该视频', 'recallpublish', '撤回已发布目录或页面', 'video', 39, 0, 8, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='trash' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '将该视频放入回收站', 'trash', '将该视频放入回收站', 'video', 33, 0, 9, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='export' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '导出视频文档信息', 'export', '导出视频文档信息', 'video', 34, 0, 10, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='play' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  '播放该视频', 'play', '播放该视频', 
'video', 38, 0, 11, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- 针对多个Video对象的操作
delete from XWCMPAGEOPERATOR where OprKey='basicpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '发布这些视频', 'basicpublish', '发布这些视频', 'videos', 39, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='copy' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '复制这些视频到...', 'copy', '复制这些视频到...', 'videos', 34, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='move' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '移动这些视频到...', 'move', '移动这些视频到...', 'videos', 65, 1, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='quote' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '引用这些视频到...', 'quote', '引用这些视频到...', 'videos', 34, 1, 5, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='changeStatus' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '改变这些视频的状态', 'changeStatus', '改变这些视频的状态', 'videos', 32, 0, 6, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='detailpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '仅发布这些视频的细览', 'detailpublish', '仅重新生成这些视频的细览页面', 'videos', 39, 0, 7, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='recallpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '撤销发布这些视频', 'recallpublish', '撤回已发布目录或页面', 'videos', 39, 0, 8, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='trash' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '将视频放入回收站', 'trash', '将视频放入回收站', 'videos', 33, 0, 9, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='export' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '导出视频文档信息', 'export', '导出视频文档信息', 'videos', 34, 0, 10, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- 针对 videoInChannel 的操作
delete from XWCMPAGEOPERATOR where OprKey='import' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '导入WCM视频文档', 'import', '导入已有的WCM视频文档', 'videoInChannel', 32, 0, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;
-- ls@2007-6-5 15:40 增加引用、改变状态等操作, 更新很多操作的权限和显示次序 (end)

-- ls@2007-6-18 16:20 增加针对 videoInSite 的直播操作
delete from XWCMPAGEOPERATOR where OprKey='live' and OprType='videoInSite';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '新建视频直播', 'live', '建立新的直播视频', 'videoInSite', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ls@2007-6-18 16:20 增加便于视频直播展示的置标
delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO_LIVE';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO_LIVE', 'com.trs.components.video.publish.VideoLiveTagParser', 
 '视频直播展示的置标解析器', 'admin', sysdate, 1 from WCMTAGBEANS;