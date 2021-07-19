alter table SHARE_SERVICE add IS_SINGLE VARCHAR2(1);
-- Add comments to the columns 
comment on column SHARE_SERVICE.IS_SINGLE
  is '是否批量服务 0是批量 1是单条';
  
    --批量更新状态
update share_service SET IS_SINGLE='1' where service_id='1a0f88cbeb844ab3a26e4b041258c2dd';
update share_service SET IS_SINGLE='1' where service_id='0840c7887a7847de98af8ea41519ee6f';
update share_service SET IS_SINGLE='1' where service_id='61473509b0924703b9e4c3d3a44a50e9';
update share_service SET IS_SINGLE='1' where service_id='b01415c2872a455c923eb7fc1dad2d31';
update share_service SET IS_SINGLE='1' where service_id='f007627c9e12417fb0187cd80529e183';
update share_service SET IS_SINGLE='1' where service_id='e17f958386e2463fa6368222727432cd';
update share_service SET IS_SINGLE='0' where service_id='d809520e94ab49e3a73d42626bb61565';
update share_service SET IS_SINGLE='0' where service_id='8db606dfbe284823bd8314c2f2d46c0b';
update share_service SET IS_SINGLE='0' where service_id='ad1eb386b8ea46c58b2abd66f7b677f0';
update share_service SET IS_SINGLE='0' where service_id='be85f06f10f74d6ea60221cd1841d909';
update share_service SET IS_SINGLE='0' where service_id='678b1a73797842329bf8e2355fb02fb1';
update share_service SET IS_SINGLE='0' where service_id='0eb33206d9a249dbb764eb5fdf29883e';
update share_service SET IS_SINGLE='1' where service_id='b6865a699e0d465886525417d42d165c';
update share_service SET IS_SINGLE='0' where service_id='860176e98ff14391bb6860bf63bd021b';
update share_service SET IS_SINGLE='0' where service_id='a1bc4e11b822408da46ded8781777db7';
update share_service SET IS_SINGLE='0' where service_id='ed6dc472665345908ffbe6159f2de503';
update share_service SET IS_SINGLE='0' where service_id='7b531a7c8982450499e7a4f7d3f9442e';
update share_service SET IS_SINGLE='0' where service_id='5a153ee4c16a47b0a1ff9d92201e820f';
update share_service SET IS_SINGLE='0' where service_id='9b7a3d581fff42cca7e01003e9b89ed5';
update share_service SET IS_SINGLE='0' where service_id='a594ea24c8c947549b50917047d41764';
update share_service SET IS_SINGLE='0' where service_id='5c6c1367f1c4404eb5dd7d06a9ff72ae';
update share_service SET IS_SINGLE='1' where service_id='4e55e377824c4429bb783bc899dadb36';
update share_service SET IS_SINGLE='0' where service_id='bbf4fc882ba0493c8f40fa9a385be58a';
update share_service SET IS_SINGLE='0' where service_id='3ee31e329aa845cb96da491b4ebdc569';
update share_service SET IS_SINGLE='0' where service_id='e111bf9c772c411bae13853dc4133942';
update share_service SET IS_SINGLE='0' where service_id='1f81f13abf3b470993672d408652425e';
update share_service SET IS_SINGLE='0' where service_id='b2c3818b342649de9000c1674e4c7095';
update share_service SET IS_SINGLE='1' where service_id='763b51c85e4441348f804bcf5f2cab3d';
update share_service SET IS_SINGLE='0' where service_id='643200e6fe99496cbf76137ea5ff117f';
update share_service SET IS_SINGLE='0' where service_id='a68a0542c2e54a86b837bea9efca7c4e';
update share_service SET IS_SINGLE='0' where service_id='2987349efedf47cc96a2b2bdb47807a0';
update share_service SET IS_SINGLE='0' where service_id='9e9c41341f654f23b68eba4e665134ae';
update share_service SET IS_SINGLE='0' where service_id='be256e807f11441490481b853caf6f73';
update share_service SET IS_SINGLE='0' where service_id='9bc2311d4e364660b86dac8fe4596c2c';
update share_service SET IS_SINGLE='0' where service_id='ae71b030814c4345b4246bde85ba8bcc';
update share_service SET IS_SINGLE='0' where service_id='a9798acd451c408485ba933aa3bf3245';
update share_service SET IS_SINGLE='0' where service_id='285a322d501b41ae80d4a6fdef7b9148';
update share_service SET IS_SINGLE='0' where service_id='869981c4f1cd4bd9b0a4b7b281b8319f';
update share_service SET IS_SINGLE='1' where service_id='784bffde93cf4cde866d20eeb33c5faf';
update share_service SET IS_SINGLE='0' where service_id='e14a6ad88986426983f3edf90dc2d65f';
update share_service SET IS_SINGLE='1' where service_id='fe8fcf546c3b49d697dae38468a00822';
update share_service SET IS_SINGLE='0' where service_id='218799ee79594259836606c8207f5706';
update share_service SET IS_SINGLE='1' where service_id='82586e95cbda4afd9d0523bf4bb59419';
update share_service SET IS_SINGLE='0' where service_id='2bf7c481246b48e38192e5a833e160bd';
update share_service SET IS_SINGLE='1' where service_id='d347854ad9954894983f63949ee56f7f';
update share_service SET IS_SINGLE='1' where service_id='421f685532394e7aa93606fbaa5dc591';
update share_service SET IS_SINGLE='0' where service_id='75772d7dffd24c5caedece63d5d1e754';
update share_service SET IS_SINGLE='0' where service_id='37826d68f2a74dd0bbbb42924d69a0f7';
update share_service SET IS_SINGLE='0' where service_id='092769d905e24485bb8dd33a83d74bcf';
update share_service SET IS_SINGLE='0' where service_id='1d6772d4d3b6483688f8f9010bed538d';
update share_service SET IS_SINGLE='0' where service_id='f62291ca287444a0bcf71a041534481a';
update share_service SET IS_SINGLE='1' where service_id='484d901f37c740a986c98032ea43f10f';
update share_service SET IS_SINGLE='1' where service_id='99a7477970ec448fb383284f14d24cf0';
update share_service SET IS_SINGLE='1' where service_id='c887c05751174a26ab2b98379afc21c4';
update share_service SET IS_SINGLE='0' where service_id='bcc3ff7caff64b07809961eb8aaa4d66';
update share_service SET IS_SINGLE='1' where service_id='20454ffd84a049f5a6ddeaf5dec30e09';
update share_service SET IS_SINGLE='1' where service_id='2167a406c38e4fd0ba9d8737012e9bec';
update share_service SET IS_SINGLE='1' where service_id='dee1968b2cc240e3be1fc9b188107173';
update share_service SET IS_SINGLE='0' where service_id='ad76268a90a54a36aa65f54fccca78e8';
update share_service SET IS_SINGLE='1' where service_id='7726a3aa9fd849a9ad0820e9ce367339';
update share_service SET IS_SINGLE='1' where service_id='8fb63db0945148eca1d48ac5ecb36986';
update share_service SET IS_SINGLE='1' where service_id='83444519d233466faefeacf7dee7a1be';
update share_service SET IS_SINGLE='1' where service_id='d6137baef14c4349b780a1d34784a35e';
update share_service SET IS_SINGLE='1' where service_id='c3811da6bc3a4513bffbbb9218df3387';
update share_service SET IS_SINGLE='1' where service_id='ebd081e16f494c17b65e9052906623d3';
update share_service SET IS_SINGLE='0' where service_id='530519f9fd0b455abb6af6c15a90034f';
update share_service SET IS_SINGLE='0' where service_id='1a2fe1b6d3e44b9bb36b14ab70ed1fcf';
update share_service SET IS_SINGLE='0' where service_id='d44846a3054e4920a8356a62d306e620';
update share_service SET IS_SINGLE='1' where service_id='78f9fee266d24e13b8b87ab591547a2f';
update share_service SET IS_SINGLE='1' where service_id='36830d4527cd464b98e2fdffc7a4783e';
update share_service SET IS_SINGLE='0' where service_id='5dbfffc031b0446497d9d9952fef4e93';
update share_service SET IS_SINGLE='0' where service_id='9005bdfa3f3c4fbfa8f1e9081d010ed2';
update share_service SET IS_SINGLE='0' where service_id='25df59075dcf41afbc8859f1b9e66dbd';
update share_service SET IS_SINGLE='0' where service_id='d0fa4f2162134cbb8bfb9cc5b2fe25cf';
update share_service SET IS_SINGLE='1' where service_id='29df515e2f664a1ea734661e22750eee';
update share_service SET IS_SINGLE='0' where service_id='ecbb6e7fe48d470ab85cef00fc5678a4';
update share_service SET IS_SINGLE='1' where service_id='f796dc29784442f6855df224b526d6f2';
update share_service SET IS_SINGLE='0' where service_id='170cb4a5cde0419480e8e09f7f434019';
update share_service SET IS_SINGLE='0' where service_id='9e34049165af4dd881a41156e36de158';
update share_service SET IS_SINGLE='0' where service_id='48939f7395f74d43ba1413614c5b77a7';
update share_service SET IS_SINGLE='0' where service_id='c0ec83e660654f3b9f6a7a4c7d88031f';
update share_service SET IS_SINGLE='1' where service_id='614fe3f98a304168a6a68eb213cb0ece';
update share_service SET IS_SINGLE='1' where service_id='031fcb6b23334de388ab9f8941556c4c';
update share_service SET IS_SINGLE='0' where service_id='b13c1527cf1e444ba3a9b783d2c08e53';
update share_service SET IS_SINGLE='0' where service_id='9d07a6e373954ede8158665361925898';
update share_service SET IS_SINGLE='1' where service_id='dad1011bc4dc4f99a71f30ea4b09f254';
update share_service SET IS_SINGLE='1' where service_id='e2803785fae0448d96339e2e7969b234';
update share_service SET IS_SINGLE='1' where service_id='c4549e4abf9e4a13b8155a2f3e1017b0';
update share_service SET IS_SINGLE='1' where service_id='d589902b29274d04898d1f97686394f8';
update share_service SET IS_SINGLE='1' where service_id='972ded7a00a94224bda8eeee5e53e595';
update share_service SET IS_SINGLE='1' where service_id='8926ed13f5254406aa6c6151dd922096';
update share_service SET IS_SINGLE='0' where service_id='d398f8ff249d4ef7b9691941b92c34a3';
update share_service SET IS_SINGLE='1' where service_id='bd0edda95b6d4bfda68d810b0132bb8a';
update share_service SET IS_SINGLE='1' where service_id='bdcceadf3ffd4cbabd3459faeace9b13';
update share_service SET IS_SINGLE='1' where service_id='7d250bdc1e0041ed8255cd271300fbb7';
update share_service SET IS_SINGLE='0' where service_id='44673aab04ea466382b6365f9275032f';
update share_service SET IS_SINGLE='0' where service_id='faf61325ac934a98a38382b419dbe393';
update share_service SET IS_SINGLE='1' where service_id='8e369d9b81e941b9aaf7d134d98c010b';
update share_service SET IS_SINGLE='1' where service_id='bdcfbd8efdb9460daa7c07ef031f39f1';
update share_service SET IS_SINGLE='0' where service_id='c2bb2abde323423bb6715554bc0a3029';
update share_service SET IS_SINGLE='0' where service_id='ae8f7d6f1cf74499ac1bcf9513faaaaa';
update share_service SET IS_SINGLE='1' where service_id='5018720c1f89479bae950a8a931f3b65';
update share_service SET IS_SINGLE='0' where service_id='a0b61d148ada4840864967ed75907ffe';
update share_service SET IS_SINGLE='0' where service_id='0bcf9977d3f04e7190e6df848b03f77b';
update share_service SET IS_SINGLE='1' where service_id='58becb128fde488c850ce4b8ec11a63c';
update share_service SET IS_SINGLE='1' where service_id='3e9929d426e34c69910c6dedf8b50365';
update share_service SET IS_SINGLE='1' where service_id='d630b677bde24fa2948afa02d9fd278b';
update share_service SET IS_SINGLE='1' where service_id='88db1ff5434f4cac9400e1d29d61fd0b';
update share_service SET IS_SINGLE='1' where service_id='17820d79b90740c8bdf8365323aeb81a';
update share_service SET IS_SINGLE='0' where service_id='44b5b3a880c44820a125b68b96079744';
update share_service SET IS_SINGLE='0' where service_id='c0f3c4b5c72d4b55b52d2e4775c690f2';
update share_service SET IS_SINGLE='0' where service_id='0f53af9413d943c48a5cc073504c3730';
update share_service SET IS_SINGLE='0' where service_id='6e7fce2987e44737b3463361834bfed4';
update share_service SET IS_SINGLE='0' where service_id='e60e9f9c9c044e42a6b0412a1d457914';
update share_service SET IS_SINGLE='0' where service_id='ea98e1d9a3cb405e877542990aa129de';
update share_service SET IS_SINGLE='0' where service_id='7c51bf3536704037a5da0593f265ed12';
update share_service SET IS_SINGLE='0' where service_id='cac37c5c52334f4aadf1a3d912ada7d1';
update share_service SET IS_SINGLE='0' where service_id='4bc0a24fa6694c17982f55aee671d2c9';
update share_service SET IS_SINGLE='1' where service_id='adfdc520deed41b9a3b2bafc6a5a4ad0';
update share_service SET IS_SINGLE='1' where service_id='2f4c3af2e3104e14b8f1dc6d0dc3129f';
update share_service SET IS_SINGLE='1' where service_id='b7c5b4b61809483cb453ecdb1da068ac';
update share_service SET IS_SINGLE='0' where service_id='59e132b30cbb4ef58e87933d616f2a15';
update share_service SET IS_SINGLE='0' where service_id='3859e9a6a6fb4963a3a994a92b762fb9';
update share_service SET IS_SINGLE='0' where service_id='4c0305256e0f4303af9688ae3c5f7389';
update share_service SET IS_SINGLE='0' where service_id='a6b9ae11e8c04e5291228aee0118d13b';
update share_service SET IS_SINGLE='0' where service_id='7b11bbc7e9a145a89528a4a4e13f3d9b';
update share_service SET IS_SINGLE='0' where service_id='09da21047e6e4d20b08af074011cb91c';
update share_service SET IS_SINGLE='0' where service_id='d067199caed04988bd9bad69ed75b3c8';
update share_service SET IS_SINGLE='0' where service_id='9d6721ce4c9747bcb3e2385ea2b93a12';
update share_service SET IS_SINGLE='0' where service_id='98076e51771a4dba9c77be81301404ce';
update share_service SET IS_SINGLE='0' where service_id='1a6132f4a01f4a1babb4df6cb8a68180';
update share_service SET IS_SINGLE='0' where service_id='4640a9e3f8fc4470acc14810e181b092';
update share_service SET IS_SINGLE='0' where service_id='53be1d11b83d4c7297b793d4a422b87b';
update share_service SET IS_SINGLE='0' where service_id='5be1f94521b54278946fd3e5d709747e';
update share_service SET IS_SINGLE='0' where service_id='c6f9220ae7924f13adefc0fc4228f480';
update share_service SET IS_SINGLE='0' where service_id='b41ce802a12a4ff2abe8b34bbf8cdf9d';
update share_service SET IS_SINGLE='0' where service_id='3c03af199ead4ec784cdefc819f0f461';
update share_service SET IS_SINGLE='0' where service_id='4f96d85bccab47acb9e147f2dbe14b43';
update share_service SET IS_SINGLE='0' where service_id='039c985ec6424be3a8d9738a789f5fbe';
update share_service SET IS_SINGLE='0' where service_id='bdea5ca871974db5b06ec69773d510dc';
update share_service SET IS_SINGLE='0' where service_id='ffd7bf7deca744b79108773f321c8cf8';
update share_service SET IS_SINGLE='0' where service_id='c5e1af9b8cfe4f9bbf6167cf00d55547';
update share_service SET IS_SINGLE='0' where service_id='021b2b599126424da4090898bdd6bfb8';
update share_service SET IS_SINGLE='0' where service_id='ecc90d7598a243b6a96149cbece8f949';
update share_service SET IS_SINGLE='0' where service_id='4832ba6a89904bee9af23d2b5e0e7913';
update share_service SET IS_SINGLE='0' where service_id='84b9b68b29f24d5e86b26e3ed10ce573';
update share_service SET IS_SINGLE='0' where service_id='f12c83ad9a3d4e73847e652af77ae2d8';
update share_service SET IS_SINGLE='0' where service_id='5355d64298c2473fb940624be3f50cda';
update share_service SET IS_SINGLE='0' where service_id='6af619967ee0414ab06140b25b51d9d7';
update share_service SET IS_SINGLE='0' where service_id='302a8fe577e947c09c1119921641de33';
update share_service SET IS_SINGLE='0' where service_id='da2c367b62a84899abd13e55cc63f979';
update share_service SET IS_SINGLE='0' where service_id='dce5a46debad43d5b1f694cd31625fa2';
update share_service SET IS_SINGLE='0' where service_id='628d703155e64f6c83d1d087228f15ea';
update share_service SET IS_SINGLE='0' where service_id='3e4e981038ae4be08c134af24db1b391';
update share_service SET IS_SINGLE='0' where service_id='99262180cbbe4846bf9eddb2eaf8882e';
update share_service SET IS_SINGLE='0' where service_id='4b3bd57c57e7439a914c94de2966da97';
update share_service SET IS_SINGLE='0' where service_id='c33d7fd0c7044bd79490b05fb79a09ff';
update share_service SET IS_SINGLE='0' where service_id='4878e49a5174474a82df4c69c6d5cf55';
update share_service SET IS_SINGLE='0' where service_id='6de61d9151f541f8bdfe51b95d555839';
update share_service SET IS_SINGLE='0' where service_id='1a41ba9b6b144f80babb743bb4a32905';
update share_service SET IS_SINGLE='1' where service_id='1fd1182bc2064cfa93f52fdd3f6a0597';
update share_service SET IS_SINGLE='1' where service_id='57ffbf66503142ae9f7913b27761dc3f';
update share_service SET IS_SINGLE='1' where service_id='472aa86f66c04cefa313096ec707493f';
update share_service SET IS_SINGLE='1' where service_id='8d85608335c74c6291e1b6536f4637cb';
update share_service SET IS_SINGLE='0' where service_id='20c89f89b1a642b79a117dcbbaa1bc08';
update share_service SET IS_SINGLE='1' where service_id='cee2b4d61cd44d179f25fe926a08f6c9';
update share_service SET IS_SINGLE='1' where service_id='65f43d20497743db90ccc8d2c30655f7';
update share_service SET IS_SINGLE='0' where service_id='f90243406881478baaf0a674ac86cd40';
update share_service SET IS_SINGLE='0' where service_id='fead35b75f17436c92ded02ebb35a708';
update share_service SET IS_SINGLE='0' where service_id='6d9e645c242b4838b61fbf195597052c';
update share_service SET IS_SINGLE='0' where service_id='052ff4c736a346a8a39c58755742091a';
update share_service SET IS_SINGLE='0' where service_id='80a04c86bd8c4f28bc26d851e4216764';
update share_service SET IS_SINGLE='0' where service_id='22aaf4164fe848c4846c975b64db479c';
update share_service SET IS_SINGLE='1' where service_id='a329abaf56814f8883d41791cc5a2bb6';
update share_service SET IS_SINGLE='0' where service_id='897448b929fa49ab9f79498fb7cac5af';
update share_service SET IS_SINGLE='1' where service_id='8d001d8caf4642929b977a1c460558b3';
update share_service SET IS_SINGLE='0' where service_id='3ae076310efa45fba09ebff8cfe70b4e';
update share_service SET IS_SINGLE='1' where service_id='e65691e612954fc9b002f4df5eba96d0';
update share_service SET IS_SINGLE='1' where service_id='36ca1190463d45c7a2794ddd1fec3054';
update share_service SET IS_SINGLE='0' where service_id='38604c79d6454f6f91b4fe424ab28ec4';
update share_service SET IS_SINGLE='0' where service_id='e06c729305e040e4b70ac009ba5beef1';
update share_service SET IS_SINGLE='0' where service_id='b2e897b62c9b49a8ae3df2133e80e792';
update share_service SET IS_SINGLE='0' where service_id='fb2f4e29d4c7479fbbccb13f3a807ba5';
update share_service SET IS_SINGLE='0' where service_id='222ed59bdb704cd394c75fbaa3a3cc70';
update share_service SET IS_SINGLE='0' where service_id='f62ab28cbdca448b9e7ce641bd37c2f5';
update share_service SET IS_SINGLE='0' where service_id='2bee242ae54e4c72a93dbccbc786647c';
update share_service SET IS_SINGLE='0' where service_id='606794f70a324978841bb244cf33f2c1';
update share_service SET IS_SINGLE='0' where service_id='8a05b41a9ec5419aaddba20afc55a67c';
update share_service SET IS_SINGLE='0' where service_id='c9c5abed4963446b8e03d5168134b2b0';
update share_service SET IS_SINGLE='0' where service_id='23613339cf574dd4bcf7e58ca7b7a74d';
update share_service SET IS_SINGLE='0' where service_id='0e2311dc631d4d24977656f01effb275';
COMMIT;

  
-- 服务对象增加附件字段，交换说明文件
alter table res_service_targets add fj_fk varchar2(100);
alter table res_service_targets add fjmc varchar2(200);
  

--ETL任务配置表 及初始化数据
drop table etl_subject;

create table ETL_SUBJECT
(
  ETL_ID        VARCHAR2(32) not null,
  RES_TARGET_ID VARCHAR2(32),
  SUBJ_NAME     VARCHAR2(240),
  SUBJ_DESC     VARCHAR2(2000),
  INTEVAL       VARCHAR2(240),
  START_TIME    VARCHAR2(240),
  ADD_TYPE      VARCHAR2(240),
  IS_MARKUP     VARCHAR2(1),
  SHOW_ORDER    NUMBER(10),
  SCHEDULE_JSON VARCHAR2(4000)
);

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A1', '24f9ecfdc9274db983ef0dae669943df', '信用数据采集', '信用数据采集', '周一到周五，每天一次', '1:00', '2', 'Y', 1, '{"scheduling_type" :"01","stime" :"14:00","etime" :"23:59","scheduling_week" :"","scheduling_day" :"undefined","scheduling_count" :"71","interval_time" :"20"}');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A2', '83fc7acc7afb4ffe80294c933cfbc888', '年检验照数据采集', '年检验照数据采集', '周一到周五，每天一次', '3:00', '1', 'Y', 2, '{"scheduling_type" :"02","stime" :"3:00","etime" :"4:00","scheduling_week" :"1,2,3,4,5","scheduling_day" :"undefined","scheduling_count" :"1","interval_time" :"60"}');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A3', 'b755451545d34c9498ffd1ecb7c23f58', '总局黑牌数据采集', '总局黑牌数据采集', '周一至周五，每天一次', '3:00', '2', 'Y', 3, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A4', '6f97c5a2208f40a89a13c18d5848202e', '法人库数据采集', '法人库数据采集', '周一至周五，每天一次', '7:50', '1', 'Y', 4, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A5', '14507195240e4bd0883956444171cfc6', '登记结果库数据采集', '登记结果库数据采集', '周一至周五，每半小时一次', '8:30', '1', 'Y', 5, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A6', '14507195240e4bd0883956444171cfc6', '登记历史库数据采集', '登记历史库数据采集', '周一至周五，每2小时一次', '10:00', '1', 'Y', 6, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A7', 'e6e78f7527fc4c83a322520343089014', '广告监管数据采集', '广告监管数据采集', '周一至周五，每天一次', '17:00', '1', 'Y', 7, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A8', '83fc7acc7afb4ffe80294c933cfbc888', '网格监管数据采集', '网格监管数据采集', '周一至周五，每天一次', '17:30', '2', 'Y', 8, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0030A9', 'e0e77ab9b14e45299c22d8091823e28b', '新案件数据采集', '新案件数据采集', '周一至周五，每天一次', '18:00', '1', 'Y', 9, '{"scheduling_type" :"01","stime" :"00:00","etime" :"23:59","scheduling_week" :"","scheduling_day" :"undefined","scheduling_count" :"719","interval_time" :"2"}');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0031A9', '6351425faafb43738030c549e571b215', '商品监管数据采集', '商品监管数据采集', '周一至周五，每天一次', '20:00', '2', 'N', 10, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0032A9', 'e3b5f70fb7e44a9696bd9880acdf7bfd', '食品许可证数据采集', '食品许可证数据采集', '周一至周五，每天一次', '20:00', '1', 'N', 11, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0033A9', '0db6958e5014477192846dcc8c472047', '12315数据采集', '12315数据采集', '周一至周五，每天一次', '21:00', '1', 'Y', 12, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0034A9', 'b6bb3eff22de40fea1d813a3e6c64c76', '住建委数据采集', '住建委数据采集', '周一至周五，每天一次', '23:00', '2', 'Y', 13, '');

insert into etl_subject (ETL_ID, RES_TARGET_ID, SUBJ_NAME, SUBJ_DESC, INTEVAL, START_TIME, ADD_TYPE, IS_MARKUP, SHOW_ORDER, SCHEDULE_JSON)
values ('E3CB8BDFCA6441FDE04064A06F0035A9', '14507195240e4bd0883956444171cfc6', '属地监管工商所数据更新', '属地监管工商所数据更新', '周一至周五，每天一次', '23:00', '2', 'Y', 14, '');

COMMIT;


--采集任务汇总视图
create or replace view view_collect_task as
select t.collect_task_id,
       service_targets_id,
       t.task_name,
       collect_type,
       scheduling_day1,
       start_time,
       collect_status,
       task_status,
       log_file_path from(
     select * from collect_task  where is_markup = 'Y')t ,
     (select collect_task_id,scheduling_day1,start_time from collect_task_scheduling c where is_markup = 'Y')c
 where   t.collect_task_id = c.collect_task_id(+)
   union all
select e.etl_id,e.res_target_id,e.subj_name,'06',e.inteval,e.start_time,'00','Y','' from etl_subject e;

  
  
----采集任务_采集类型代码集
delete from CODEINDEX where CODETYPE='采集任务_采集类型';
commit;
insert into CODEINDEX (CODETYPE, DESCRIPTION, STATUS, TABLENAME, LOADFUNC, MAINPAGE, RROLE, LOADSYCLE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '采集任务_采集类型', 1, null, null, null, null, 10, null, null);
commit;

--'采集任务_采集类型' 代码集修改
delete from CODEDATA where CODETYPE='采集任务_采集类型';
commit;
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '00', 'WebService', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '01', '文件上传', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '02', 'FTP', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '03', '数据库', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '04', 'JMS消息', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '05', 'SOCKET', null, 1, '20130624', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('采集任务_采集类型', '06', 'ETL', null, 1, '20130624', null, null);

----是否单户代码集
insert into CODEINDEX (CODETYPE, DESCRIPTION, STATUS, TABLENAME, LOADFUNC, MAINPAGE, RROLE, LOADSYCLE, STOPDATE, DELREASON)
values ('是否单户', '是否单户', 1, null, null, null, null, 10, null, null);

insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('是否单户', '0', '批量', null, 1, '20131205', null, null);
insert into CODEDATA (CODETYPE, CODEVALUE, CODENAME, SORTVALUE, STATUS, STARTDATE, STOPDATE, DELREASON)
values ('是否单户', '1', '单条', null, 1, '20131205', null, null);
commit;

---
alter table  etl_db_statistics  add is_markup varchar2(1);
-- Add comments to the columns 
comment on column etl_db_statistics.is_markup
  is 'Y:有效;N:无效;设置某些表是否有效';
alter table  etl_db_statistics  add collect_task_id varchar2(32);
-- Add comments to the columns 
comment on column etl_db_statistics.collect_task_id
  is '与etl_subject表中的etl_id进行关联';
---更改主题表中法人库对应的服务对象id
update res_business_topics set service_targets_id = '6f97c5a2208f40a89a13c18d5848202e' where business_topics_id='FRK';
update res_business_topics set service_targets_id = 'b6bb3eff22de40fea1d813a3e6c64c76' where business_topics_id='ZJW';

----更改etl_db_statistics表中字段长度
alter table etl_db_statistics modify  service_targets_name varchar2(100);
alter table etl_db_statistics modify  table_name_cn varchar2(100);
alter table etl_db_statistics modify  table_name_en varchar2(100);

