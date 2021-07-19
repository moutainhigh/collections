---created ls@2007-3-21
--- V6��Ƶѡ�������ݿ����(Oracle��)����Ҫ������ģ���ñ�, ���������������ݱ�ĸ���
--- ��SQLServer����Ҫ����
--- 1. ��getdate()�滻Ϊsysdate
--- ...

-- ��Զ��Video����Ĳ���
delete from XWCMPAGEOPERATOR where OprKey='preview' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  'Ԥ����Щ��Ƶ', 'preview', 'Ԥ����Щ��Ƶ�ķ���Ч��', 
'videos', 38, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ��� videoInChannel �Ĳ���
delete from XWCMPAGEOPERATOR where OprKey='upload' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�½���Ƶ', 'upload', '�ϴ�����Ƶ', 'videoInChannel', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='record' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�½���Ƶ¼��', 'record', '����¼������Ƶ', 'videoInChannel', 32, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='live' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�½���Ƶֱ��', 'live', '�����µ�ֱ����Ƶ', 'videoInChannel', 32, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ��� videoInSite �Ĳ���
/*
delete from XWCMPAGEOPERATOR where OprKey='upload' and OprType='videoInSite';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�ϴ�����Ƶ', 'upload', '�ϴ�����Ƶ', 'videoInSite', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;
*/

-- ls@2007-5-14 10:56 ����Video�ñ�
delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO', 'com.trs.components.video.publish.VideoTagParser', 
 '��ȡ��ý����Ϣ�ñ������', 'admin', sysdate, 1 from WCMTAGBEANS;

delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO_PLAY';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO_PLAY', 'com.trs.components.video.publish.VideoPlayTagParser', 
 '������ý���ñ������', 'admin', sysdate, 1 from WCMTAGBEANS;

-- ls@2007-6-5 15:40 �������á��ı�״̬�Ȳ���, ���ºܶ������Ȩ�޺���ʾ���� (begin)
-- ��Ե���Video����Ĳ���
delete from XWCMPAGEOPERATOR where OprKey='preview' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  'Ԥ������Ƶ', 'preview', 'Ԥ������Ƶ�ķ���Ч��', 
'video', 38, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='publish' and OprType='video';
delete from XWCMPAGEOPERATOR where OprKey='basicpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '��������Ƶ', 'basicpublish', '��������Ƶ', 'video', 39, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='copy' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '���Ƹ���Ƶ��...', 'copy', '���Ƹ���Ƶ��...', 'video', 34, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='move' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�ƶ�����Ƶ��...', 'move', '�ƶ�����Ƶ��...', 'video', 65, 1, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='quote' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '���ø���Ƶ��...', 'quote', '���ø���Ƶ��...', 'video', 34, 1, 5, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='changeStatus' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�ı�״̬', 'changeStatus', '�ı����Ƶ��״̬', 'video', 32, 0, 6, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='detailpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '����������Ƶ��ϸ��', 'detailpublish', '���������ɸ���Ƶ��ϸ��ҳ��', 'video', 39, 0, 7, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='recallpublish' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������������Ƶ', 'recallpublish', '�����ѷ���Ŀ¼��ҳ��', 'video', 39, 0, 8, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='trash' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Ƶ�������վ', 'trash', '������Ƶ�������վ', 'video', 33, 0, 9, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='export' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Ƶ�ĵ���Ϣ', 'export', '������Ƶ�ĵ���Ϣ', 'video', 34, 0, 10, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='play' and OprType='video';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1,  '���Ÿ���Ƶ', 'play', '���Ÿ���Ƶ', 
'video', 38, 0, 11, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ��Զ��Video����Ĳ���
delete from XWCMPAGEOPERATOR where OprKey='basicpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Щ��Ƶ', 'basicpublish', '������Щ��Ƶ', 'videos', 39, 1, 2, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='copy' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Щ��Ƶ��...', 'copy', '������Щ��Ƶ��...', 'videos', 34, 1, 3, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='move' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�ƶ���Щ��Ƶ��...', 'move', '�ƶ���Щ��Ƶ��...', 'videos', 65, 1, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='quote' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Щ��Ƶ��...', 'quote', '������Щ��Ƶ��...', 'videos', 34, 1, 5, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='changeStatus' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�ı���Щ��Ƶ��״̬', 'changeStatus', '�ı���Щ��Ƶ��״̬', 'videos', 32, 0, 6, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='detailpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '��������Щ��Ƶ��ϸ��', 'detailpublish', '������������Щ��Ƶ��ϸ��ҳ��', 'videos', 39, 0, 7, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='recallpublish' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '����������Щ��Ƶ', 'recallpublish', '�����ѷ���Ŀ¼��ҳ��', 'videos', 39, 0, 8, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='trash' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '����Ƶ�������վ', 'trash', '����Ƶ�������վ', 'videos', 33, 0, 9, 'admin', sysdate
from XWCMPAGEOPERATOR;

delete from XWCMPAGEOPERATOR where OprKey='export' and OprType='videos';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER, CrUser, CrTime)
select max(PAGEOPERATORID)+1, '������Ƶ�ĵ���Ϣ', 'export', '������Ƶ�ĵ���Ϣ', 'videos', 34, 0, 10, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ��� videoInChannel �Ĳ���
delete from XWCMPAGEOPERATOR where OprKey='import' and OprType='videoInChannel';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '����WCM��Ƶ�ĵ�', 'import', '�������е�WCM��Ƶ�ĵ�', 'videoInChannel', 32, 0, 4, 'admin', sysdate
from XWCMPAGEOPERATOR;
-- ls@2007-6-5 15:40 �������á��ı�״̬�Ȳ���, ���ºܶ������Ȩ�޺���ʾ���� (end)

-- ls@2007-6-18 16:20 ������� videoInSite ��ֱ������
delete from XWCMPAGEOPERATOR where OprKey='live' and OprType='videoInSite';
insert XWCMPAGEOPERATOR(PAGEOPERATORID, OPRNAME, OPRKEY, OPRDESC, OPRTYPE, RightIndex, ISDISPLAY, OPRORDER,CrUser, CrTime)
select max(PAGEOPERATORID)+1, '�½���Ƶֱ��', 'live', '�����µ�ֱ����Ƶ', 'videoInSite', 32, 1, 1, 'admin', sysdate
from XWCMPAGEOPERATOR;

-- ls@2007-6-18 16:20 ���ӱ�����Ƶֱ��չʾ���ñ�
delete from WCMTAGBEANS where TAGNAME='TRS_VIDEO_LIVE';
insert into WCMTAGBEANS(TAGBEANID, TAGNAME, TAGBEAN, TAGDESC, CRUSER, CRTIME, ENABLED) 
 select max(TAGBEANID)+1, 'TRS_VIDEO_LIVE', 'com.trs.components.video.publish.VideoLiveTagParser', 
 '��Ƶֱ��չʾ���ñ������', 'admin', sysdate, 1 from WCMTAGBEANS;