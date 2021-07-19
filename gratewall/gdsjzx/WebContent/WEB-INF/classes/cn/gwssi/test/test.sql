select count(jbxxid) from T_SCZT_SCZTJBXX_TEST
CREATE TABLE dbo.T_SCZT_SCZTJBXX_TEST
(
    jbxxid          varchar(36)   NOT NULL,
    entname             varchar(100)  NULL,
    entstate        char(2)       NULL,
    regno               varchar(30)   NULL,
    enttype             varchar(6)    NULL,
    lerep               varchar(100)  NULL,
    estdate             varchar(20)   NULL,
    industryphy         varchar(15)   NULL,
    industryco          varchar(4)    NULL,
    regorg              varchar(9)    NULL,
    opstate             varchar(2)    NULL,
    opscoandform        varchar(100)  NULL,
    opscope             varchar(1000) NULL,
    dom                 varchar(200)  NULL,
    atime           datetime   NULL,
    btime           datetime   NULL,
    uniscid             varchar(36)   NULL,
    addr                varchar(100)  NULL,
    inv             varchar(1000) NULL,
    bgsx            varchar(1000) NULL, 
    url             varchar(500) NULL,
    frxx            varchar(1000) NULL,
    gjrxx           varchar(1000) NULL,
    CONSTRAINT PK_T_SCZT_SCZTJBXX_TEST
    PRIMARY KEY NONCLUSTERED (jbxxid)
)
LOCK DATAROWS
go
DROP TABLE T_SCZT_SCZTJBXX_TEST

create view SCZTTEST(jbxxid,entname,regno,entstate,enttype,lerep,estdate,industryphy,industryco,regorg,inv,opstate,opscoandform,opscope,dom,bgsx,atime,btime,url,uniscid,frxx,gjrxx,addr) as 
select top 1000 (case a.scztjbxxid when '' then '8dd6c048-ca8c-4fb2-90f1-f00bbb6c85ef' else a.scztjbxxid end) scztjbxxid,a.entname,a.regno,
a.servicestate servicestate,
a.enttype enttype,a.lerep,
str_replace(substring(a.estdate,1,10),'-',null) estdate,
a.industryphy industryphy,a.industryco industryco,
a.regorg regorg,a.pripid inv,
a.opstate opstate,a.opscoandform,a.opscope,a.dom,a.pripid bgsx,
(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) atime,(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) btime,
('page/reg/regDetail.jsp?flag=0&priPid='||a.pripid||'&jbxxid='||a.scztjbxxid||'&sourceflag='||a.sourceflag||'&enttype='||a.enttype||'&economicproperty='||economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=a.enttype)||'&type='||a.organizationmode) url,a.uniscid,
a.pripid frxx,a.pripid gjrxx,a.addr
from t_sczt_scztjbxx a 

--where a.SCZTJBXXID='00000f97-b4c0-435e-a456-37412aa92041' 


drop view SCZTTEST

select * from SCZTTEST

select timestamp from t_sczt_scztjbxx where timestamp is null
select timestamp from t_sczt_scztjbxx where timestamp=''



SELECT  jbxxid, entname, regno, entstate, enttype, lerep, estdate, industryphy, industryco, regorg, inv, opstate, opscoandform, opscope, dom, bgsx, atime, btime, url, uniscid, frxx, gjrxx, addr FROM SCZTVIEW WHERE 
(convert(varchar(14),atime,112)+convert(varchar(14),atime,108)) >= '19000101000000' AND (convert(varchar(14),atime,112)+convert(varchar(14),atime,108)) < '20160421094443' and jbxxid='000046c1-9b0c-4216-88fb-cc50e0add33e'



insert into T_SCZT_SCZTJBXX_TEST(jbxxid,entname,regno,entstate,enttype,lerep,estdate,industryphy,industryco,regorg,inv,opstate,opscoandform,opscope,dom,bgsx,atime,btime,url,uniscid,frxx,gjrxx,addr) select jbxxid,entname,regno,entstate,enttype,lerep,estdate,industryphy,industryco,regorg,inv,opstate,opscoandform,opscope,dom,bgsx,atime,btime,url,uniscid,frxx,gjrxx,addr from scztview