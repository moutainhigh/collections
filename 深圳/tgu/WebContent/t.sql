CREATE OR REPLACE VIEW DC_SZQX_SYS_ROLE_VIEW AS
SELECT DISTINCT R.APP_ID       AS SYS_ID, --系统名称ID
                W.NAME         AS SYS_NAME, --系统名称
                R.ID           AS ROLE_ID, --权限ID
                R.NAME         AS ROLE_NAME, --权限名称
                R.ENNAME       AS ROLE_CODE, --权限CODE
                H.USER_ID      AS USER_ID, --用户ID
                U.NAME         AS USER_NAME, --用户名
                U.AREA_CODE    AS DEP_CODE, --组织代码
                (select a.name from SZQX.sys_area a where a.code = u.area_code) as deptName,
                --H.WORKFLOW_ID  AS WORKFLOW_ID, --流程ID
                WT.UPDATE_DATE AS UPDATETIME --审核时间
  FROM SZQX.SYS_ROLE              R, --权限表
       SZQX.SYS_WEBAPP            W, --系统表
       SZQX.SYS_USER_ROLE_HISTORY H, --权限申请历史表
       SZQX.SYS_WORKFLOW_TODO     WT,--权限审批过程表
       SZQX.SYS_USER              U  --用户表
 WHERE R.APP_ID = W.ID
   AND R.ID = H.ROLE_ID
   AND H.WORKFLOW_ID = WT.ID
   AND H.USER_ID = U.ID
   AND W.BUILDDEPT = '长软'
   AND W.NAME IN ('门户新闻投稿系统', '综合查询系统', '系统管理和报表')
   AND WT.ACT_TYPE = 'user_role_audit'
   AND WT.ONE_TEXT LIKE '[同意]%'
   --AND WT.UPDATE_DATE >= SYSDATE - 3 --调度条件
;