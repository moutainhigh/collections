create table AD_OU 
(
   XH                   int                            null,
   ORGAN_NAME           varchar2(200)                  null,
   ORGAN_ID             varchar2(100)                  null,
   PARENT_ID            varchar2(100)                  null,
   OU_STR               varchar2(2000)                 null
);

comment on table AD_OU is 
'新组织机构';

comment on column AD_OU.XH is 
'序号';

comment on column AD_OU.ORGAN_NAME is 
'机构名称';

comment on column AD_OU.ORGAN_ID is 
'机构ID';

comment on column AD_OU.PARENT_ID is 
'上级机构ID';

comment on column AD_OU.OU_STR is 
'机构字符串';

create table AD_OU_OLD
(
  ou_name VARCHAR2(200),
  ou_str     VARCHAR2(2000)
)

comment on table AD_OU_OLD
  is '原组织机构';
  
comment on column AD_OU_OLD.ou_name
  is '机构名称';
  
comment on column AD_OU_OLD.ou_str
  is '机构字符串';

create table AD_USER 
(
   XH                   int                            null,
   USER_NAME            varchar2(100)                  null,
   USER_ID              varchar2(100)                  null,
   ORGAN_NAME           varchar2(200)                  null,
   ORGAN_ID             varchar2(100)                  null,
   USER_STR             varchar2(2000)                 null
);

comment on table AD_USER is 
'新的用户';

comment on column AD_USER.XH is 
'序号';

comment on column AD_USER.USER_NAME is 
'人员名称';

comment on column AD_USER.USER_ID is 
'人员ID';

comment on column AD_USER.ORGAN_NAME is 
'所属机构名称';

comment on column AD_USER.ORGAN_ID is 
'所属机构ID';

comment on column AD_USER.USER_STR is 
'人员字符串';

create table AD_USER_OLD 
(
   用户名称                 varchar2(100)                  null,
   用户ID                 varchar2(100)                  null,
   用户字符串                varchar2(2000)                 null
);

comment on table AD_USER_OLD is 
'原始ad域中的用户信息';

comment on column AD_USER_OLD.用户名称 is 
'用户名称';

comment on column AD_USER_OLD.用户ID is 
'用户ID';

comment on column AD_USER_OLD.用户字符串 is 
'用户字符串';


create table AD_USER_OLD_TO_NEW 
(
   XH                   int                            null,
   USER_NAME            varchar2(100)                  null,
   USER_ID              varchar2(100)                  null,
   USER_OLD_STR         varchar2(2000)                 null,
   USER_NEW_STR         varchar2(2000)                 null
);

comment on table AD_USER_OLD_TO_NEW is 
'AD用户迁移对比表';

comment on column AD_USER_OLD_TO_NEW.XH is 
'序号';

comment on column AD_USER_OLD_TO_NEW.USER_NAME is 
'用户名称';

comment on column AD_USER_OLD_TO_NEW.USER_ID is 
'用户ID';

comment on column AD_USER_OLD_TO_NEW.USER_OLD_STR is 
'用户原DN字符串';

comment on column AD_USER_OLD_TO_NEW.USER_NEW_STR is 
'用户新DN字符串';



