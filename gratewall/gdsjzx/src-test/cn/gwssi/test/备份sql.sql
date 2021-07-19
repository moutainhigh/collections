drop view ANJIANXX
create view ANJIANXX(AJID,applidique,ACCREGPER,REGTIME,ACCSCE,ACCTIME,KEYWORD,PNAME,PADDR,
PTEL,ENTADDR,ENTTEL,REGDEP,INVOPT,MDSENAME,atime,btime) as 
select a.contid AS AJID,f.applidique,f.ACCREGPER,convert(varchar, f.REGTIME,111) REGTIME,b.ACCSCE,convert(varchar, b.acctime,111) acctime,b.KEYWORD,f.name as PNAME,f.addr as PADDR,f.tel as PTEL,f.mainaddr as ENTADDR,f.maintel as ENTTEL,f.title,f.INVOPT,f.MDSENAME,
(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) atime,dbo.ajtimestamp(a.contid) btime
from 
T_12315_AJXXB a left join T_12315_DJXXB b on a.contid = b.regino  left join T_12315_XXLY f on a.contid = f.regino
go
drop view AnnualReport
go
create view AnnualReport(EntName,RegNo,EntType,Tel,Addr,Email,BusSt,PRIPID,InvName,AnCheYear,url,atime) as 
select a.EntName,a.RegNo,(select value from t_dm_qylxdm where code=a.enttype) EntType,a.Tel,a.Addr,a.Email,(select value from  T_DM_JYZTDM where code=a.BusSt) BusSt,a.PRIPID,dbo.ndbggdxx(a.PRIPID,a.ancheid) invname,dbo.ancheyearall(a.PRIPID) AnCheYear,('page/reg/regDetail.jsp?flag=0&priPid='||a.pripid||'&enttype='||a.enttype||'&year=') url,dbo.annualtimestamp(a.PRIPID) atimefrom t_ndbg_qybsjbxx a
go
drop view NDBGCZJJJ
go
--新建不了
create view NDBGCZJJJ(CapID,PRIPID,EntID,UNISCID,Inv,SubConAm,AcConAm,CapDate,ConForm,ConDate,CapShouldType,SOURCEFLAG,TIMESTAMP,AssetID,AssGro,AssetsIsShow,LiaGro,LiabilitiesIsShow,VendInc,SalesIsShow,ProGro,ProfitsIsShow,NetInc,NetProfitIsShow,RatGro,TaxIsShow,TotEqu,EquityIsShow,MaiBusInc,BusinessIsShow,InvestmentID,Department,InverstAmount,RegNo) as
select a.CapID,a.PRIPID,a.EntID,a.UNISCID,a.Inv,a.SubConAm,a.AcConAm,a.CapDate,a.ConForm,a.ConDate,a.CapShouldType,a.SOURCEFLAG,a.TIMESTAMP,b.AssetID,b.AssGro,b.AssetsIsShow,b.LiaGro,b.LiabilitiesIsShow,b.VendInc,b.SalesIsShow,b.ProGro,b.ProfitsIsShow,b.NetInc,b.NetProfitIsShow,b.RatGro,b.TaxIsShow,b.TotEqu,b.EquityIsShow,b.MaiBusInc,b.BusinessIsShow,c.InvestmentID,c.Department,c.InverstAmount,c.RegNo from T_NDBG_GDJCZXX a left join T_NDBG_QYZCZK b on a.pripid=b.pripid and a.SOURCEFLAG=b.SOURCEFLAG and a.EntID=b.EntID left join T_NDBG_DWTzXX c on a.PRIPID=c.pripid  and a.EntID=c.entid
go
--新建不了
go
create view NDBGJBXX(EntID,PRIPID,RegNo,UNISCID,EntName,EntType,OLD_EntType,EmpNum,EmpNumberIsShow,Tel,Addr,PostalCode,Email,BusSt,IsWeb,IsInveInfo,AnCheDate,AnCheYear,IssueDeptID,IsTransferStock,onAiccips,Relations,RelationsIsShow,SOURCEFLAG,TIMESTAMP,IndividualID,Name,Scope,ResParMSign,ResParSecSign,PartyMemberNum,PartyIsShow,ColGraNum,GraduatesIsShow,RetEmplNum,SoldiersIsShow,DisEmplNum,DisabilityIsShow,UneNum,UnemploymentIsShow,FundAm,OutputAmount,OutputIsShow,VendInc,SalesIsShow,RetailAmount,RetailIsShow,markupMode,reportIsShow,entNameissame,userissame,markupModeissame,scopeissame,addressissame,ColEmplNum,RetSolNum,DisPerNum,RatGro,RatGroIsShow,CoopID,TotalAmount,MemNum,AnnNewMemNum,AnnRedMemNum,Levelf,Certification,PriYeaSales,PriYeaProfit,SurplusIsShow,PriYeaSub,SubsidiesIsShow,TaxIsShow,PriYeaLoan,LoanIsShow,regCapitalissame,Farnum) as
select a.EntID,a.PRIPID,a.RegNo,a.UNISCID,a.EntName,a.EntType,a.OLD_EntType,a.EmpNum,a.EmpNumberIsShow,a.Tel,a.Addr,a.PostalCode,a.Email,a.BusSt,a.IsWeb,a.IsInveInfo,a.AnCheDate,a.AnCheYear,a.IssueDeptID,a.IsTransferStock,a.onAiccips,a.Relations,a.RelationsIsShow,a.SOURCEFLAG,a.TIMESTAMP,b.IndividualID,b.Name,b.Scope,b.ResParMSign,b.ResParSecSign,b.PartyMemberNum,b.PartyIsShow,b.ColGraNum,b.GraduatesIsShow,b.RetEmplNum,b.SoldiersIsShow,b.DisEmplNum,b.DisabilityIsShow,b.UneNum,b.UnemploymentIsShow,b.FundAm,b.OutputAmount,b.OutputIsShow,b.VendInc,b.SalesIsShow,b.RetailAmount,b.RetailIsShow,b.markupMode,b.reportIsShow,b.entNameissame,b.userissame,b.markupModeissame,b.scopeissame,b.addressissame,b.ColEmplNum,b.RetSolNum,b.DisPerNum,b.RatGro,b.RatGroIsShow,c.CoopID,c.TotalAmount,c.MemNum,c.AnnNewMemNum,c.AnnRedMemNum,c.Levelf,c.Certification,c.PriYeaSales,c.PriYeaProfit,c.SurplusIsShow,c.PriYeaSub,c.SubsidiesIsShow,c.TaxIsShow,c.PriYeaLoan,c.LoanIsShow,c.regCapitalissame,c.Farnum from T_NDBG_QYBSJBXX a left join T_NDBG_GTGSNDBB b ON a.priPid=b.pripid and a.SOURCEFLAG=b.SOURCEFLAG and a.entid=b.entid LEFT JOIN T_NDBG_NMZYHZSNDBB c ON a.priPid=c.pripid and a.SOURCEFLAG=c.SOURCEFLAG and a.entid=c.entid
go
drop view SCZTVIEW
go
create view SCZTVIEW(jbxxid,entname,regno,entstate,enttype,lerep,estdate,industryphy,industryco,regorg,inv,opstate,opscoandform,opscope,dom,bgsx,atime,btime,url,uniscid,frxx,gjrxx,addr) as 
select (case a.scztjbxxid when '' then '8dd6c048-ca8c-4fb2-90f1-f00bbb6c85ef' else a.scztjbxxid end) scztjbxxid,a.entname,a.regno,
(select value from t_dm_old_jyztdm where code=a.servicestate) servicestate,
(select value from t_dm_qylxdm where code=a.enttype) enttype,a.lerep,
str_replace(substring(a.estdate,1,10),'-',null) estdate,
(select value from t_dm_hydm where code=a.industryphy) industryphy,(select value from t_dm_hydm where code=a.industryco) industryco,
(select value from T_DM_djgXjGdm where code=a.regorg) regorg,dbo.connactcernoinv(a.pripid,a.sourceflag) inv,
(select value from t_dm_jyztdm where code=a.opstate) opstate,a.opscoandform,a.opscope,a.dom,dbo.connactbgxx(a.pripid,a.sourceflag) bgsx,
(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) atime,dbo.connacttimestamp(a.pripid,a.sourceflag) btime,
('flag=0&priPid='||a.pripid||'&jbxxid='||a.scztjbxxid||'&sourceflag='||a.sourceflag||'&enttype='||a.enttype||'&economicproperty='||economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=a.enttype)||'&type='||a.organizationmode||'&entname='||a.entname) url,a.uniscid,
dbo.connactfrxx(a.pripid,a.sourceflag) frxx,dbo.connactgjrxx(a.pripid,a.sourceflag) gjrxx,a.addr
from t_sczt_scztjbxx a
go

go
create view sysquerymetrics (uid, gid, hashkey, id, sequence, exec_min, exec_max, exec_avg, elap_min, elap_max, elap_avg, lio_min, lio_max, lio_avg, pio_min, pio_max, pio_avg, cnt, abort_cnt, qtext) as select  a.uid, -a.gid, a.hashkey, a.id, a.sequence, convert(int, substring(b.text, charindex('e1', b.text) + 3, charindex('e2', b.text) - charindex('e1', b.text) - 4)), convert(int, substring(b.text, charindex('e2', b.text) + 3, charindex('e3', b.text) - charindex('e2', b.text) - 4)), convert(int, substring(b.text, charindex('e3', b.text) + 3, charindex('t1', b.text) - charindex('e3', b.text) - 4)), convert(int, substring(b.text, charindex('t1', b.text) + 3, charindex('t2', b.text) - charindex('t1', b.text) - 4)), convert(int, substring(b.text, charindex('t2', b.text) + 3, charindex('t3', b.text) - charindex('t2', b.text) - 4)), convert(int, substring(b.text, charindex('t3', b.text) + 3, charindex('l1', b.text) - charindex('t3', b.text) - 4)), convert(int, substring(b.text, charindex('l1', b.text) + 3, charindex('l2', b.text) - charindex('l1', b.text) - 4)), convert(int, substring(b.text, charindex('l2', b.text) + 3, charindex('l3', b.text) - charindex('l2', b.text) - 4)), convert(int, substring(b.text, charindex('l3', b.text) + 3, charindex('p1', b.text) - charindex('l3', b.text) - 4)), convert(int, substring(b.text, charindex('p1', b.text) + 3, charindex('p2', b.text) - charindex('p1', b.text) - 4)), convert(int, substring(b.text, charindex('p2', b.text) + 3, charindex('p3', b.text) - charindex('p2', b.text) - 4)), convert(int, substring(b.text, charindex('p3', b.text) + 3, charindex('c', b.text) - charindex('p3', b.text) - 4)), convert(int, substring(b.text, charindex('c', b.text) + 2, charindex('ac', b.text) - charindex('c', b.text) - 3)), convert(int, substring(b.text, charindex('ac', b.text) + 3, char_length(b.text) - charindex('ac', b.text) - 2)), a.text from sysqueryplans a, sysqueryplans b where (a.type = 10) and (b.type =1000) and (a.id = b.id) and a.uid = b.uid and a.gid = b.gid
go

go
CREATE VIEW V_COGNOS_USER
AS
SELECT     RoleID AS 'uid', RoleName AS 'name', Description AS 'givenname', 0 AS issqluser, 1 AS issqlrole
FROM         T_COGNOS_ROLE
UNION ALL
SELECT     UserID AS 'uid', UserName AS 'name', ChineseName AS 'givenname', 1 AS issqluser, 0 AS issqlrole
FROM         T_COGNOS_USER
go

go
GRANT SELECT ON dbo.V_COGNOS_USER TO cognos01
go


---存储过程
CREATE PROCEDURE dbo.splitpage_sybase @qry varchar(16384),@ipage int,
                                   @num int,@maxpages int = 5000000 as
      begin
          declare @rcount int  --查询总数
          declare @execsql varchar(16384) --查询sql
              if @ipage > @maxpages --当前页和最大页
              begin
                select '输入页数[' || convert(varchar, @ipage) || ']大于最大查询页数[' ||
                       convert(varchar, @maxpages) || ']' return
              end
              select @rcount = @ipage * @num set rowcount @rcount--(当前页乘以每页条数)
                     --将$替换成''
                      set @qry = str_replace(@qry, '$', "'")
                      --将查询语句的select 替换成 select sybid=identity(1             2),
                       set @execsql = stuff(@qry,charindex('select',@qry),6,'select sybid=identity(12),')
                      --将查询语句的from替换成into #temptable1 from，先将查询结果存放到临时表#temptable1
                        set @execsql = stuff(@execsql, charindex('from ',@execsql),4,'into  #temptable1 from')
                          --重组sql语句，分页的核心控制处
                           set @execsql = @execsql || ' select * from #temptable1  where sybid >'|| convert(varchar,(@ipage-1)*@num) || ' and sybid <= '|| convert(varchar,@ipage*@num)
              execute (@execsql)
                  set rowcount 0
       end
--函数
--案件对主体名称的拼接 connactanxxobjname
select dbo.connactcaseobjname('0000f332-0116-1000-e001-359a0a160115','t_aj_ajjbxx') as xx
drop function connactcaseobjname
drop function connactanxxobjname

create function connactcaseobjurl(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384)  
as
begin   
declare @t varchar(16384) 
declare @r1 varchar(16384)  
declare @r2 varchar(16384)  
declare @countsum int 
select @countsum=count(c.entname) from t_aj_ajjbxx a left join t_aj_dsrxx b on a.caseid=b.caseid left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on b.pripid=c.pripid where a.casestate='1' and a.caseid=@id
if (@countsum>0)
begin
    declare mycurs cursor 
    for select '<a href="javascript:void(0);" onclick="marketObject('''||'flag=5&priPid='||c.pripid||'&jbxxid='||c.scztjbxxid||'&sourceflag='||c.sourceflag||'&enttype='||c.enttype||'&economicproperty='||c.economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=c.enttype)||'&type='||c.organizationmode||'&regno='||c.regno||'&entname='||c.entname ||''');">'||c.entname||'</a>' from t_aj_ajjbxx a left join t_aj_dsrxx b on a.caseid=b.caseid left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on b.pripid=c.pripid where a.casestate='1' and a.caseid=@id
    open mycurs
    fetch mycurs into @t
    while @@sqlstatus=0
       begin
          select @r1 = @r1 + @t+';'
          fetch mycurs into @t
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
----
--
select dbo.connactcaseobjname('0000f332-0116-1000-e001-359a0a160115','t_aj_ajjbxx') as xx
drop function connactcaseobjurl
create function connactcaseobjname(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384)  
as
begin   
declare @t varchar(16384) 
declare @r1 varchar(16384)  
declare @r2 varchar(16384)  
declare @countsum int 
select @countsum=count(c.entname) from t_aj_ajjbxx a left join t_aj_dsrxx b on a.caseid=b.caseid left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on b.pripid=c.pripid where a.casestate='1' and a.caseid=@id
if (@countsum>0)
begin
    declare mycurs cursor 
    for select c.entname from t_aj_ajjbxx a left join t_aj_dsrxx b on a.caseid=b.caseid left join t_sczt_scztjbxx c (index SCZTJBXXINDEX) on b.pripid=c.pripid where a.casestate='1' and a.caseid=@id
    open mycurs
    fetch mycurs into @t
    while @@sqlstatus=0
       begin
          select @r1 = @r1 + @t+';'
          fetch mycurs into @t
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--- 年度分支机构
drop function fzjgfunc
CREATE FUNCTION fzjgfunc(@entityno varchar(36),@ANCHEID char(36)) 
    RETURNS varchar(8000) 
AS
BEGIN    
DECLARE @r varchar(100)
DECLARE @r1 varchar(8000) 
DECLARE @r2 varchar(8000) 
DECLARE @countsum int 
select @countsum=count(*) from t_NDBG_FZJG WHERE entityno=@entityno and ANCHEID=@ANCHEID
if @countsum>0
begin
    declare mycurs cursor 
    for (select '('||BrName||" | "||FarSpeArtRegNO||')' from t_NDBG_FZJG WHERE entityno=@entityno and entid=@entid)
    open mycurs
    fetch mycurs into @r
    while @@sqlstatus=0
       begin
       select @r1 = @r1+ @r +','
       fetch mycurs into @r
       end
    close mycurs
    DEALLOCATE mycurs
    select @r2 = substring(@r1,1,datalength(@r1))	
end
   RETURN @r2
END

select dbo.fzjgfunc('ad26b527-0120-1000-e000-92d70a0a0115','664abca9-b5f6-405d-b63c-cec11bd37288')
--高级人信息
create function connactgjrxx(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384) 
as
begin    
declare @t varchar(8000)
declare @t1 varchar(8000)
declare @r1 varchar(16384) 
declare @r2 varchar(16384) 
declare @countsum int 
select @countsum=count(1) from (select c.personid,c.sourceflag from t_sczt_gjcyxx c where c.sourceflag=@sourceflag and c.PriPID=@id) c left join t_sczt_ryjbxx b on c.personid=b.ryjbxxid and c.sourceflag=b.sourceflag
if (@countsum>0)
begin
    declare mycurs cursor 
    for (select b.cerno,b.name from (select c.personid,c.sourceflag from t_sczt_gjcyxx c where c.sourceflag=@sourceflag and c.PriPID=@id) c left join t_sczt_ryjbxx b on c.personid=b.ryjbxxid and c.sourceflag=b.sourceflag)
    open mycurs
    fetch mycurs into @t,@t1
    while @@sqlstatus=0
       begin
        if (isnull(@t,'') <>'' or isnull(@t1,'') <>'')
            begin
                select @r1 = @r1+ @t1+'('+@t+')' +';'
            end
        fetch mycurs into @t,@t1
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--法人信息
create function connactfrxx(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384) 
as
begin    
declare @t varchar(8000)
declare @t1 varchar(8000)
declare @r1 varchar(16384) 
declare @r2 varchar(16384) 
declare @countsum int 
select @countsum=count(1) from (select c.personid,c.sourceflag from t_sczt_fddbr c where c.sourceflag=@sourceflag and c.PriPID=@id) c left join t_sczt_ryjbxx b on c.personid=b.ryjbxxid and c.sourceflag=b.sourceflag
if (@countsum>0)
begin
    declare mycurs cursor 
    for (select b.cerno,b.name from (select c.personid,c.sourceflag from t_sczt_fddbr c where c.sourceflag=@sourceflag and c.PriPID=@id) c left join t_sczt_ryjbxx b on c.personid=b.ryjbxxid and c.sourceflag=b.sourceflag)
    open mycurs
    fetch mycurs into @t,@t1
    while @@sqlstatus=0
       begin
        if (isnull(@t,'') <>'' or isnull(@t1,'') <>'')
            begin
                select @r1 = @r1+ @t1+'('+@t+')' +';'
            end
        fetch mycurs into @t,@t1
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--投资人证件号和名称
create function connactcernoinv(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384) 
as
begin    
declare @t varchar(8000)
declare @t1 varchar(8000)
declare @r1 varchar(16384) 
declare @r2 varchar(16384) 
declare @countsum int 
select @countsum=count(1) from (select TZRJCZXXID,SOURCEFLAG from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id union all select TZRJCZXXID,SOURCEFLAG from t_sczt_zrrtzrjczxx where sourceflag=@sourceflag and pripid=@id) a
if (@countsum>0)
begin
    declare mycurs cursor 
    for (select cerno,inv from (select cerno,inv from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id union all select cerno,inv from t_sczt_zrrtzrjczxx where sourceflag=@sourceflag and pripid=@id) a)
    open mycurs
    fetch mycurs into @t,@t1
    while @@sqlstatus=0
       begin
        --if (isnull(@r,'') <>'')
         --   begin
                select @r1 = @r1+ @t1+'('+@t+')' +';'
         --   end
        fetch mycurs into @t,@t1
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--投资人时间
create function connacttimestamp(@id varchar(36),@sourceflag varchar(6)) 
returns datetime 
as
begin    
declare @r datetime
declare @countsum int 
select @countsum=count(1) from (select tzrjczxxid,sourceflag from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id union all select tzrjczxxid,sourceflag from t_sczt_zrrtzrjczxx where sourceflag=@sourceflag and pripid=@id) a
if (@countsum>0)
begin
    select @r=max(convert(datetime,timestamp)) from (select timestamp from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id union all select timestamp from t_sczt_zrrtzrjczxx where sourceflag=@sourceflag and pripid=@id) a
end
if @r=null
begin
    select @r=convert(datetime,'1900-01-01 00:00:00')
end
   return @r
end
--变更
create function connactbgxx(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384)  
as
begin   
declare @t varchar(8192)
declare @t1 varchar(8192)
declare @t2 varchar(8192) 
declare @r1 varchar(16384)  
declare @r2 varchar(16384)  
declare @countsum int 
select @countsum=count(*) from t_sczt_bgxx where sourceflag=@sourceflag and entityno=@id
if (@countsum>0)
begin
    declare mycurs cursor 
    for select (select value from t_dm_bgbasx where code = a.altitem),altbe,altaf from t_sczt_bgxx a where sourceflag=@sourceflag and entityno=@id order by altdate
    open mycurs
    fetch mycurs into @t,@t1,@t2
    while @@sqlstatus=0
       begin
        --if (isnull(@r,'') <>'')
        --    begin
                select @r1 = @r1 + @t+'：'+@t1 +'-->>'+ @t2+ '||'
         --   end
        fetch mycurs into @t,@t1,@t2
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--
--建立索引
create unique/clustered index    on()--T_SCZT_DJGDXX/T_SCZT_FRTZRJCZQTXX/
create clustered index GDXXINDEX ON T_SCZT_GDXX(PriPID)--归档信息===（被删）
--create clustered index MCJBXXINDEX ON T_SCZT_MCJBXX(PriPID)--名称基本==（不存在）
CREATE CLUSTERED INDEX SCZTJBXXTAMPINDEX ON dbo.T_SCZT_SCZTJBXX(timestamp)

drop INDEX T_SCZT_BGXX.BGXXINDEX
drop INDEX T_SCZT_FDDBR.FDDBRINDEX
drop INDEX T_SCZT_FRTZRJCZXX.FRTZRJCZXXINDEX
drop INDEX T_SCZT_GDXX.GDXXINDEX
drop INDEX T_SCZT_GJCYXX.GJCYXXINDEX
drop INDEX T_SCZT_GQCZXX.GQCZXXINDEX
drop INDEX T_SCZT_GQDJXX.GQDJXXINDEX
drop INDEX T_SCZT_LS_CZXX.CZYWXXINDEX
drop INDEX T_SCZT_MCJBXX.MCJBXXINDEX
drop INDEX T_SCZT_QCXX.QCXXINDEX
drop INDEX T_SCZT_QRXX.QRXXINDEX
drop INDEX T_SCZT_QSXX.QSXXINDEX
drop INDEX T_SCZT_SCZTBCXX.SCZTBCXXINDEX
drop INDEX T_SCZT_SCZTBJXX.SCZTBJXXINDEX
drop INDEX T_SCZT_SCZTJBXX.SCZTJBXXINDEX
drop INDEX T_SCZT_SCZTLSBCXX.SCZTLSBCXXINDEX
drop INDEX T_SCZT_ZDXXX.ZDXXXINDEX
drop INDEX T_SCZT_ZRRTZRJCZXX.ZRRTZRJCZXXINDEX
drop INDEX T_SCZT_ZZXX.ZZXXINDEX
drop INDEX T_YWBL_DBSY.DBSYINDEX 

----测试
CREATE  FUNCTION test(
    @pripid     varchar(50) 
)
returns varchar(1000)
as         
begin
 declare  @CerNO varchar(1000)
 set @CerNO=''
     select @CerNO=@CerNO + ',' + enttype  from T_SCZT_scztjbxx where enttype='7300'
     return @CerNO 
end 
--
CREATE  FUNCTION test(
    @pripid     varchar(50) 
)
returns varchar(1000)
as         
begin
 declare  @CerNO varchar(1000)
 set @CerNO=''
     select @CerNO=@CerNO + ',' + enttype  from T_SCZT_scztjbxx where enttype='7300'
     return @CerNO 
end 


drop function test  
drop function f_str  
select dbo.test('1') as xx
select dbo.f_str(1) as xx
--
select * from T_AJ_AJJBXX a left join T_AJ_DSRXX b on a.CASEID=b.CASEID left join t_sczt_scztjbxx c on b.PRIPID=c.PriPID


select top 1 '<a href=''javascript:void(0);'' onclick=''marketObject(\'''||
'flag=0&priPid='||c.pripid||'&jbxxid='||c.scztjbxxid||'&sourceflag='||c.sourceflag||'&enttype='||c.enttype||'&economicproperty='||c.economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=c.enttype)||'&type='||c.organizationmode||'&entname='||c.entname
||'\'')''>'||c.entname||'</a>'  from t_sczt_scztjbxx c


<a href='javascript:void(0);' onclick='marketObject('flag=0&priPid=00000f97-b4c0-435e-a456-37412aa92041&jbxxid=00000f97-b4c0-435e-a456-37412aa92041&sourceflag=440606&enttype=9999&economicproperty=2&enttypeName=个体&type=0&entname= ')'>

<a href='javascript:void(0);' onclick='marketObject(\'flag=0&priPid=00000f97-b4c0-435e-a456-37412aa92041&jbxxid=00000f97-b4c0-435e-a456-37412aa92041&sourceflag=440606&enttype=9999&economicproperty=2&enttypeName=个体&type=0&entname= \')'>
<a href='javascript:void(0);' onclick='marketObject(\'flag=0&priPid=00000f97-b4c0-435e-a456-37412aa92041&jbxxid=00000f97-b4c0-435e-a456-37412aa92041&sourceflag=440606&enttype=9999&economicproperty=2&enttypeName=个体&type=0&entname= \')'> </a>

||'flag=0&priPid='||c.pripid||'&jbxxid='||c.scztjbxxid||'&sourceflag='||c.sourceflag||'&enttype='||c.enttype||'&economicproperty='||c.economicproperty||'&enttypeName='||(select value from t_dm_qylxdm where code=c.enttype)||'&type='||c.organizationmode||'&entname='||c.entname
--
select top 1 '<a href=''javascript:void(0);''>'  from t_sczt_scztjbxx c
--
drop function connactinv
drop function connactcerno
drop function connacttimestamp
drop function connactbgxx
--投资人名称
create function connactinv(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384) 
as
begin    
declare @r varchar(8000)
declare @r1 varchar(16384) 
declare @r2 varchar(16384) 
declare @countsum int 
--select @countsum=count(*) from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id
select @countsum=count(1) from (select TZRJCZXXID,SOURCEFLAG from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id union all select TZRJCZXXID,SOURCEFLAG from t_sczt_zrrtzrjczxx sourceflag=@sourceflag and pripid=@id) a
if (@countsum>0)
begin
    declare mycurs cursor 
    for (select Inv from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id)
    open mycurs
    fetch mycurs into @r
    while @@sqlstatus=0
       begin
        if (isnull(@r,'') <>'')
            begin
                select @r1 = @r1+ @r +';'
            end
        fetch mycurs into @r
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
---投资人证件号
create function connactcerno(@id varchar(36),@sourceflag varchar(6)) 
returns varchar(16384) 
as
begin    
declare @r varchar(8000)
declare @r1 varchar(16384) 
declare @r2 varchar(16384)
declare @countsum int 
select @countsum=count(*) from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id
if (@countsum>0)
begin
    declare mycurs cursor 
    for (select cerno from t_sczt_frtzrjczxx where sourceflag=@sourceflag and pripid=@id)
    open mycurs
    fetch mycurs into @r
    while @@sqlstatus=0
       begin
        if (isnull(@r,'') <>'')
            begin
                select @r1 = @r1+ @r +';'
            end
       fetch mycurs into @r
       end
    close mycurs
    deallocate mycurs
    select @r2 = substring(@r1,1,len(@r1)-1)
end
   return @r2
end
--
