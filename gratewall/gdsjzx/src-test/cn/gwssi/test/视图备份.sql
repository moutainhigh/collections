USE datacenter
go
IF OBJECT_ID('dbo.V_COGNOS_USER') IS NOT NULL
BEGIN
    DROP VIEW dbo.V_COGNOS_USER
    IF OBJECT_ID('dbo.V_COGNOS_USER') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.V_COGNOS_USER >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.V_COGNOS_USER >>>'
END
go
IF OBJECT_ID('dbo.sysquerymetrics') IS NOT NULL
BEGIN
    DROP VIEW dbo.sysquerymetrics
    IF OBJECT_ID('dbo.sysquerymetrics') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.sysquerymetrics >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.sysquerymetrics >>>'
END
go
IF OBJECT_ID('dbo.SCZTVIEW') IS NOT NULL
BEGIN
    DROP VIEW dbo.SCZTVIEW
    IF OBJECT_ID('dbo.SCZTVIEW') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.SCZTVIEW >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.SCZTVIEW >>>'
END
go
IF OBJECT_ID('dbo.CASEINFOVIEW') IS NOT NULL
BEGIN
    DROP VIEW dbo.CASEINFOVIEW
    IF OBJECT_ID('dbo.CASEINFOVIEW') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.CASEINFOVIEW >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.CASEINFOVIEW >>>'
END
go
IF OBJECT_ID('dbo.Annual') IS NOT NULL
BEGIN
    DROP VIEW dbo.Annual
    IF OBJECT_ID('dbo.Annual') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.Annual >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.Annual >>>'
END
go
IF OBJECT_ID('dbo.ANJIANXX') IS NOT NULL
BEGIN
    DROP VIEW dbo.ANJIANXX
    IF OBJECT_ID('dbo.ANJIANXX') IS NOT NULL
        PRINT '<<< FAILED DROPPING VIEW dbo.ANJIANXX >>>'
    ELSE
        PRINT '<<< DROPPED VIEW dbo.ANJIANXX >>>'
END
go
create view ANJIANXX(AJID,applidique,ACCREGPER,REGTIME,ACCSCE,ACCTIME,KEYWORD,PNAME,PADDR,
PTEL,ENTADDR,ENTTEL,REGDEP,INVOPT,MDSENAME,atime,btime) as 
select a.contid AS AJID,f.applidique,f.ACCREGPER,convert(varchar, f.REGTIME,111) REGTIME,b.ACCSCE,convert(varchar, b.acctime,111) acctime,b.KEYWORD,f.name as PNAME,f.addr as PADDR,f.tel as PTEL,f.mainaddr as ENTADDR,f.maintel as ENTTEL,f.title,f.INVOPT,f.MDSENAME,
(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) atime,dbo.ajtimestamp(a.contid) btime
from 
T_12315_AJXXB a left join T_12315_DJXXB b on a.contid = b.regino  left join T_12315_XXLY f on a.contid = f.regino
go
IF OBJECT_ID('dbo.ANJIANXX') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.ANJIANXX >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.ANJIANXX >>>'
go
create view Annual(ANCHEID,EntName,RegNo,EntType,Tel,Addr,Email,BusSt,PRIPID,InvName,AnCheYears,url,atime,AnCheYear) as 
select (case a.ANCHEID when ' ' then '空' else a.ANCHEID end ) ANCHEID,a.EntName,a.RegNo,(select value from t_dm_qylxdm where code=a.e nttype) EntType,a.Tel,a.Addr,a.Email,(select value from  T_DM_JYZTDM where code=a.BusSt) BusSt,a.PRIPID,dbo.ndbggdxx(a.PRIPID,a.ancheid) invname,dbo.ancheyearall(a.PRIPID) AnCheYears,
('page/reg/regDetail.jsp?flag=0&priPid='||a.pripid||'&enttype='||a.enttype||'&year=') url,(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) atime,AnCheYear from t_ndbg_qybsjbxx a
go
IF OBJECT_ID('dbo.Annual') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.Annual >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.Annual >>>'
go
create view CASEINFOVIEW(caseid,caseno,casesrcid,casetype,casename,casescedistrict,casespot,casetime,casereason,caseval,appprocedure,caseinternetsign,caseforsign,casestate,casefiauth,casefidate,exedate,exesort,unexereasort,caseresult,casedep,clocaserea,clocasedate,sourceflag,timestamp,entname,entnameurl,url) as 
	select a.caseid,a.caseno,a.casesrcid,(case a.casetype when '1' then '一般案件' when '2' then '简易案件' when '3' then '特殊案件' else '其他案件' end) casetype,a.casename,a.casescedistrict,a.cases                pot,a.casetime,a.casereason,a.caseval,a.appprocedure,a.caseinternetsign,a.caseforsign,(case a.casestate when '0' then '销案' when '1' then '已结案' when '2' then '未结案' else '其他状态' end) casestate,a.casefiauth,a.casefidate,a.exedate,a.exesort,a.unexe            reasort,(case a.caseresult when '1' then '受理' when '2' then '不予受理' when '3' then '告知' when '9' then '其它' else a.caseresult end) caseresult,a.casedep,a.clocaserea,a.clocasedate,a.sourceflag,(case convert(datetime,a.timestamp) when null then conve          rt(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) timestamp,dbo.connactcaseobjname(a.caseid,a.casesrcid) entname,dbo.connactcaseobjurl(a.caseid,a.casesrcid) entnameurl,('caseid=' || a.caseid) url from t_aj_ajjbxx a where a.casestate='1'
go
IF OBJECT_ID('dbo.CASEINFOVIEW') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.CASEINFOVIEW >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.CASEINFOVIEW >>>'
go
create view SCZTVIEW(jbxxid,entname,regno,entstate,enttype,lerep,estdate,industryphy,industryco,regorg,inv,opstate,opscoandform,opscope,dom,bgsx,timestamp,url,uniscid,frxx,gjrxx,addr) as 
select (case a.scztjbxxid when '' then '8dd6c048-ca8c-4fb2-90f1-f00bbb6c85ef' else a.scztjbxxid end) scztjbxxid,a.entname,a.regno,
(select value from t_dm_old_jyztdm where code=a.servicestate) servicestate,
(select value from t_dm_qylxdm where code=a.enttype) enttype,a.lerep,
str_replace(substring(a.estdate,1,10),'-',null) estdate,
(select value from t_dm_hydm where code=a.industryphy) industryphy,(select value from t_dm_hydm where code=a.industryco) industryco,
(select value from T_DM_djgXjGdm where code=a.regorg) regorg,dbo.connactcernoinv(a.pripid,a.sourceflag) inv,
(select value from t_dm_jyztdm where code=a.opstate) opstate,a.opscoandform,a.opscope,a.dom,dbo.connactbgxx(a.pripid,a.sourceflag) bgsx,getdate() timestamp,
('flag=0&priPid='||a.pripid||'&jbxxid='||a.scztjbxxid||'&sourceflag='||a.sourceflag||'&enttype='||a.enttype||'&economicproperty='||economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=a.enttype)||'&type='||a.organizationmode||'&entname='||a.entname) url,a.uniscid,
dbo.connactfrxx(a.pripid,a.sourceflag) frxx,dbo.connactgjrxx(a.pripid,a.sourceflag) gjrxx,a.addr
from t_sczt_scztjbxx a
go
IF OBJECT_ID('dbo.SCZTVIEW') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.SCZTVIEW >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.SCZTVIEW >>>'
go
create view sysquerymetrics (uid, gid, hashkey, id, sequence, exec_min, exec_max, exec_avg, elap_min, elap_max, elap_avg, lio_min, lio_max, lio_avg, pio_min, pio_max, pio_avg, cnt, abort_cnt, qtext) as select  a.uid, -a.gid, a.hashkey, a.id, a.sequence, convert(int, substring(b.text, charindex('e1', b.text) + 3, charindex('e2', b.text) - charindex('e1', b.text) - 4)), convert(int, substring(b.text, charindex('e2', b.text) + 3, charindex('e3', b.text) - charindex('e2', b.text) - 4)), convert(int, substring(b.text, charindex('e3', b.text) + 3, charindex('t1', b.text) - charindex('e3', b.text) - 4)), convert(int, substring(b.text, charindex('t1', b.text) + 3, charindex('t2', b.text) - charindex('t1', b.text) - 4)), convert(int, substring(b.text, charindex('t2', b.text) + 3, charindex('t3', b.text) - charindex('t2', b.text) - 4)), convert(int, substring(b.text, charindex('t3', b.text) + 3, charindex('l1', b.text) - charindex('t3', b.text) - 4)), convert(int, substring(b.text, charindex('l1', b.text) + 3, charindex('l2', b.text) - charindex('l1', b.text) - 4)), convert(int, substring(b.text, charindex('l2', b.text) + 3, charindex('l3', b.text) - charindex('l2', b.text) - 4)), convert(int, substring(b.text, charindex('l3', b.text) + 3, charindex('p1', b.text) - charindex('l3', b.text) - 4)), convert(int, substring(b.text, charindex('p1', b.text) + 3, charindex('p2', b.text) - charindex('p1', b.text) - 4)), convert(int, substring(b.text, charindex('p2', b.text) + 3, charindex('p3', b.text) - charindex('p2', b.text) - 4)), convert(int, substring(b.text, charindex('p3', b.text) + 3, charindex('c', b.text) - charindex('p3', b.text) - 4)), convert(int, substring(b.text, charindex('c', b.text) + 2, charindex('ac', b.text) - charindex('c', b.text) - 3)), convert(int, substring(b.text, charindex('ac', b.text) + 3, char_length(b.text) - charindex('ac', b.text) - 2)), a.text from sysqueryplans a, sysqueryplans b where (a.type = 10) and (b.type =1000) and (a.id = b.id) and a.uid = b.uid and a.gid = b.gid
go
IF OBJECT_ID('dbo.sysquerymetrics') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.sysquerymetrics >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.sysquerymetrics >>>'
go
CREATE VIEW V_COGNOS_USER
AS
SELECT     RoleID AS 'uid', RoleName AS 'name', Description AS 'givenname', 0 AS issqluser, 1 AS issqlrole
FROM         T_COGNOS_ROLE
UNION ALL
SELECT     UserID AS 'uid', UserName AS 'name', ChineseName AS 'givenname', 1 AS issqluser, 0 AS issqlrole
FROM         T_COGNOS_USER
go
IF OBJECT_ID('dbo.V_COGNOS_USER') IS NOT NULL
    PRINT '<<< CREATED VIEW dbo.V_COGNOS_USER >>>'
ELSE
    PRINT '<<< FAILED CREATING VIEW dbo.V_COGNOS_USER >>>'
go
