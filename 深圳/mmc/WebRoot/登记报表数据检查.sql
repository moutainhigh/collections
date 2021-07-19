--报表数据检查
select count(*) from e_baseinfo b where b.regorg is null and substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A')   --企业管辖区域为空数量     (0325)316   (0425)316  (0525)316
select * from e_baseinfo b where b.oldenttype not in (select d.old_code_value from dc_dc.codedata d where d.code_index='CA16')  --不存在的企业类型数据     (0325)2  (0425)2  (0525)0  ---高和锦  每月25号执行
select count(*) from e_baseinfo b where b.industryco is null   --企业行业类型为空数量    (0325)0  (0425)0 (0525)0
select count(*) from e_pb_baseinfo b where b.regorg is null  --个体管辖区域为空数量    (0325)0  (0425)0 (0525)0
select count(*) from e_pb_baseinfo b where b.industryco is null and b.regstate='1' --个体行业类型为空数量    (0325)3121  (0425)3121 (0525)3121
select b.regno "注册号",b.entname "企业名称",b.regcap "注册资本",b.regcapcur_cn "币种", b.enttype_cn "企业类型",b.industryco "行业分类",b.estdate "成立日期",b.apprdate "核准日期",b.dom "地址", b.regorg_cn "管辖区域" from e_baseinfo b where b.regcap is not null order by b.regcap desc  --企业注册资本排序
select * from dual
select count(*) from e_baseinfo b where b.regstate='2'   --企业中状态为吊销的户数 284203     (0325)276156  (0425)275900  (0525)275593
select count(*) from e_baseinfo b,e_revoke r where b.pripid=r.pripid(+) and  b.regstate='2' and r.revdate is not null --企业中状态为吊销并且吊销日期不为空的户数  (0325)275784  (0425)275528 (0525)275221
select count(*) from e_pb_baseinfo p where p.regstate='2'  --个体中状态为吊销的户数  (0325)251464  (0425)251409 (0525)251346
select count(*) from e_pb_baseinfo p,e_gt_revoke r where p.pripid=r.pripid(+) and  p.regstate='2' and r.revdate is not  null  --个体中状态为吊销并且吊销日期不为空的户数251891    (0325)251461   (0425)251407  (0525)251344

select count(*) from e_baseinfo b where substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A')
and b.estdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and b.regstate='1'     --企业期末实有户数  --1630916   (0325)1828293  (0425)1857977  (0525)1885262
and b.estdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and b.estdate>=to_date('2018-04-26','yyyy-mm-dd') and b.regstate='1'  --企业本期登记户数     (0325)23310  (0425)32197 (0525)28320

select count(*) from e_baseinfo b,e_revoke r where b.pripid=r.pripid(+) and  b.regstate='2' and r.revdate>=to_date('2018-04-26','yyyy-mm-dd') and r.revdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') --企业本期吊销户数   (0325)0  (0425)0 (0525)0
select count(*) from e_baseinfo b where substr(b.oldenttype,1,1) in ('1','2','3','4','5','6','Y','W','A') 
and b.regstate='4' and b.apprdate>=to_date('2018-04-26','yyyy-mm-dd') and b.apprdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') --企业本期注销户数     (0325)3105   (0425)2269 (0525)2349


select count(*) from e_pb_baseinfo p where p.estdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and p.regstate='1'  --个体期末实有户数   (0325)1312403  (0425)1328374 (0525)1342565
select count(*) from e_pb_baseinfo p where p.estdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and p.estdate>=to_date('2018-04-26','yyyy-mm-dd') and p.regstate='1'  --个体本期登记户数   (0325)14848   (0425)20978  (0525)19202
select count(*) from e_pb_baseinfo p,e_gt_revoke r where p.pripid=r.pripid(+) and  p.regstate='2' and r.revdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and r.revdate>=to_date('2018-04-26','yyyy-mm-dd')  --个体本期吊销户数 (1225)0  (0425)0  (0525)0
select count(*) from e_pb_baseinfo p where p.regstate='4' and p.apprdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss') and p.apprdate>=to_date('2018-04-26','yyyy-mm-dd')  --个体本期吊销户数  (0325) 3912   (0425)5078 (0525)5708


select count(*)
from e_baseinfo b
where ( (substr(b.oldenttype,1,2) in ('30','31','32','33','34','35','36','40','41','42','43','44','46','47','48','91','92'))
        or (b.oldenttype in ('1140','1213','1223','2140','2213','2223'))
        or (b.oldenttype in ('1000','1100','1120','1130','1152','1200','1210','1220','1230','1300','2000','2100','2120','2200','2210','2220','2300','A000') and substr(b.specflag,8,1)='0')
        or (b.oldenttype in ('1110','2110') and (substr(trim(b.specflag),8,1)='0' or trim(b.specflag) is null))  )
 and (b.estdate>=to_date('2018-04-26','yyyy-mm-dd') and b.estdate<=to_date('2018-05-25 23:59:59','yyyy-mm-dd hh24:mi:ss'))     --本期登记纯内资企业户数     (0325)6   (0425)10 (0525)8


