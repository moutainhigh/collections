<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template master-detail-3/frame-detail-update.jsp --%>

<freeze:html>
<head>

<style type="text/css">
#one {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: ����;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

#two {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: ����;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

#three {
	height: 180px;
	border-bottom: 1px solid #c2c2c2;
	border-right: 1px solid #c2c2c2;
	border-left: 1px solid #c2c2c2;
	width: 85%;
	padding: 5px;
	font-family: ����;
	line-height: 150%;
	font-size: 11.0pt;
	text-align: left;
	color: #555555;
}

.top {
	width: 95%;
	padding: 5px;
	color: #555555;
	background-color: #f2f2f2;
	font-family: ����;
	line-height: 150%;
	font-size: 11.0pt;
	height: 25%;
	text-align: left;
}

.item {
	color: #555555;
	border: 1px solid #c2c2c2;
	background-color: #93b9da;
	width: 85%;
	height: 40px;
	line-height: 135px;
}

</style>
<script type="text/javascript">
	
</script>
</head>
<freeze:body>
	<freeze:title caption="" />
	<freeze:errors />
	<table align="center" width="96%" height="90%">
		<tr style="height: 150px;">
			<td colspan=3 style="padding-top: 20px;" valign="top" align="center">
				<div class="top">
					<p style="font: 120%, ����; text-align: center; display: none;">����������</p>
					&nbsp&nbsp&nbsp&nbsp���������ָͨ���ɼ����񡢹������ɽ������ݽ���ƽ̨�ĵ�λ��ϵͳ��
					��Ҫ������������ڲ�ϵͳ�����ط־֡��ⲿϵͳ�����ݽ���ƽ̨�Է��������ί��ֵ����ƽ����˹淶��
					28��ί��ֵ����ƹ̶�Ϊ�����̾֡���˰�֡������֡��˱��֡�����֡��ʼ�֡������֡���˰�֡�������߾֡�
					�����֡������֡������֡���ͨί��֪ʶ��Ȩ�֡���ƾ֡����ų���֡�ˮ��֡��ǹ�ִ���֡������֡��߷�����ͨί��
					ס�����罨��ί���滮ί������ί����չ�ĸ�ί���Ļ�ִ���ܶӡ���������ί�����ں��ء�
				</div>
			</td>
		</tr>

		<tr>
			<td align="center" style="width: 32%; padding-top: 20px;"
				valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/nbxt.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">�ڲ�ϵͳ</b></td>
						 </tr>
						</Table>
					</div>


					<div id="one">&nbsp&nbsp&nbsp&nbsp�ڲ�ϵͳָ�������г��������ල����ίԱ���ڲ�Ϊ��ҵ���ſ�����ҵ��ϵͳ��
						Ŀǰ���ڲ�ϵͳ����13������Ҫ�������Ǽ�ϵͳ�����ϵǼ�ϵͳ�����ϵͳ������ϵͳ��12315ϵͳ��
						����ϵͳ������ϵͳ������ϵͳ�������ϵͳ,ũ�ʼ��ϵͳ��˽Ӫ���徭��������ҵE��ͨ�� ��</div>
				</div>
			</td>
			<td align="center" style="width: 32%; padding-top: 20px;"
				valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/wbxt.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">�ⲿϵͳ</b></td>
						 </tr>
						</Table>
					</div>
					<div id="two">
						&nbsp&nbsp&nbsp&nbsp�ⲿϵͳָͨ�����ݽ���ƽ̨���������г��������ල����ίԱ��������ݽ������ⲿ��λ���ⲿҵ��ϵͳ��
						ĿǰԼ13������Ҫ�����������о���ί��Դ���ġ������е�˰ϵͳ�������й�˰ϵͳ�������и߷��������������籣�֡�
						�������ʼ�֡�������ס��ί�������к�����Ϣ�졢������������Ϣ��ȡ�</div>
				</div>
			</td>


			<td align="center" style="width: 32%;padding-top: 20px;" valign="top">
				<div style="padding-bottom: 40px">
					<div class="item">
						<Table style="color: #fff;">
						 <tr>
						  <td><img src="/page/help/helpRes/qxfj.png" style="margin-top: 2px;"/></td>
						  <td><b style="margin-top: 10px;">���ط־�</b></td>
						 </tr>
						</Table>
					</div>
					<div id="three">
						&nbsp&nbsp&nbsp&nbsp���ط־�ָ�����������й��ֵ̾ĸ��־֣���18��������������־֡������־֡�
						���Ƿ־֡����Ƿ־֡���̨�־֡���ɽ�־֡����Ʒ־֡�˳��־֡���ͷ���־֡�ͨ�ݷ־֡�����־֡���ƽ�־֡�����־ֵȡ�</div>
				</div>
			</td>
		</tr>
	</table>

</freeze:body>
</freeze:html>


