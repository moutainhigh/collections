//食品经营许可证信息
function temp1(lictype, entname,data) {
	var licenseNo = " ";
	var entName = " ";
	var entScCode = " ";
	var legalPresent = " ";
	var address = " ";
	var engagePlace = " ";
	var mainFormatName = " ";
	var engageProject = " ";
	var inspectOrganName = " ";
	var signerName = " ";
	var certificationDate = " ";
	var validityTo = " ";
	var licenseState = " ";
	
	
	if(isNotEmpty(data[0].licenseNo)){
		licenseNo= data[0].licenseNo;
	}
	if(isNotEmpty(data[0].entName)){
		entName= data[0].entName;
	}
	if(isNotEmpty(data[0].entScCode)){
		entScCode= data[0].entScCode;
	}
	if(isNotEmpty(data[0].legalPresent)){
		legalPresent= data[0].legalPresent;
	}
	if(isNotEmpty(data[0].address)){
		address= data[0].address;
	}
	if(isNotEmpty(data[0].engagePlace)){
		engagePlace= data[0].engagePlace;
	}
	
	if(isNotEmpty(data[0].mainFormatName)){
		mainFormatName= data[0].mainFormatName;
	}
	
	if(isNotEmpty(data[0].engageProject)){
		engageProject= data[0].engageProject;
	}
	if(isNotEmpty(data[0].inspectOrganName)){
		inspectOrganName= data[0].inspectOrganName;
	}
	
	
	if(isNotEmpty(data[0].signerName)){
		signerName= data[0].signerName;
	}
	if(isNotEmpty(data[0].certificationDate)){
		certificationDate= data[0].certificationDate;
	}
	
	if(isNotEmpty(data[0].validityTo)){
		validityTo= data[0].validityTo;
	}
	
	if(isNotEmpty(data[0].licenseState)){
		licenseState= data[0].licenseState;
	}
	
	//$('#openDialog').html(" ");
	$('#openDialog').empty();
	$('#openDialog').append("<div class='modal fade' id='"+ lictype+ "' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' data-backdrop='static' data-keyboard='true'>"
							+ "	<div class='modal-dialog' >"
							+ "		<div class='modal-content'>"
							+ "			<div class='modal-header'>"
							+ "				<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>"
							+ "				<h4 class='modal-title' id='myModalLabel'>"+ entname+ "的食品经营许可证</h4></div>"
							+ "			<div class='modal-body'>"
							+ "				<table class='table'><tr>"
							+ "					<td class='labelTitle'>许可证编号：</td>"
							+ "					<td>"+licenseNo+"</td>"
							+ "					<td class='labelTitle'>经营者名称：</td>"
							+ "					<td>"+entName+"</td></tr>"
							+ "				<tr><td class='labelTitle'>社会信用代码（身份证号）：</td>"
							+ "					<td>"+entScCode+"</td>"
							+ "					<td class='labelTitle'>法定代表人：</td>"
							+ "					<td>"+legalPresent+"</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td class='labelTitle'>住所：</td>"
							+ "					<td>"+address+"</td>"
							+ "					<td class='labelTitle'>经营场所：</td>"
							+ "					<td>"+engagePlace+"</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td class='labelTitle'>主体业态：</td>"
							+ "					<td>"+mainFormatName+"</td>"
							+ "					<td class='labelTitle'>经营项目：</td>"
							+ "					<td>"+engageProject+"</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td class='labelTitle'>日常监督管理机构：</td>"
							+ "					<td>"+inspectOrganName+"</td>"
							+ "					<td class='labelTitle'>签发人：</td>"
							+ "					<td>"+signerName+"</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td class='labelTitle'>发证日期：</td>"
							+ "					<td>"+certificationDate+"</td>"
							+ "					<td class='labelTitle'>有效期限至：</td>"
							+ "					<td>"+validityTo+"</td>"
							+ "				</tr>"
							+ "				<tr>"
							+ "					<td class='labelTitle'>证书状态：</td>"
							+ "					<td>"+licenseState+"</td>"
							+ "					<td>&nbsp;</td>"
							+ "					<td>&nbsp;</td>"
							+ "				</tr>"
							+ "				</table>"
							+ "			</div>"
							+ "			<div class='modal-footer'>"
							+ "				<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>"
							+ "			</div></div></div></div>");

}

// 食品生产许可证信息
function temp3(id,lictype,entname,data) {
	var licenseNo = " ";
	var entName= " ";
	var engagePlace= " ";
	var legalPresent= " ";
	var address= " ";
	var productName= " ";
	var entScCode = " ";
	var productTypeInfo =" ";
	var inspectOrganName =" ";
	var certificationDate =" ";
	var validityTo =" ";
	var licenseState =" ";
	
	var rownum = " ";
	var productCategoryCode=" ";
	var productCategoryName= " ";
	
	if(isNotEmpty(data[0].licenseNo)){
		licenseNo= data[0].licenseNo;
	}
	
	if(isNotEmpty(data[0].entName)){
		entName= data[0].entName;
	}
	
	if(isNotEmpty(data[0].legalPresent)){
		legalPresent= data[0].legalPresent;
	}
	
	if(isNotEmpty(data[0].address)){
		address= data[0].address;
	}
	
	
	
	if(isNotEmpty(data[0].entScCode)){
		entScCode= data[0].entScCode;
	}
	if(isNotEmpty(data[0].inspectOrganName)){
		inspectOrganName= data[0].inspectOrganName;
	}
	if(isNotEmpty(data[0].certificationDate)){
		certificationDate= data[0].certificationDate;
	}
	if(isNotEmpty(data[0].validityTo)){
		validityTo= data[0].validityTo;
	}
	if(isNotEmpty(data[0].licenseState)){
		licenseState= data[0].licenseState;
	}
	var tempHtml = " ";
	$.ajax({
		url : '../foodLic/foodDetail.do',
		type : 'POST',
		dataType : 'json',
		data : {
			"foodid" : id,
			"lictype" : "31"
		},
	    async:false, 
		success : function(data) {
			if(!$.isEmptyObject(data.data[0].data)){
				if(!$.isEmptyObject(data.data[0].data)){
					var data = data.data[0].data;
					
					for (var i = 0; i < data.length; i++) {
						if(i%2!=0){
							if(isNotEmpty(data[i].rownum)){
								rownum= data[i].rownum;
							}
							if(isNotEmpty(data[i].productName)){
								productName= data[i].productName;
							}
							
							if(isNotEmpty(data[i].productTypeInfo)){
								productTypeInfo= data[i].productTypeInfo;
							}
							if(isNotEmpty(data[i].productCategoryCode)){
								productCategoryCode= data[i].productCategoryCode;
							}
							
							if(isNotEmpty(data[i].productCategoryName)){
								productCategoryName= data[i].productCategoryName;
							}
							tempHtml+="<tr class='active'><td>"+rownum+"</td><td>"+productName+"</td><td>"+productCategoryName+"</td><td>"+productCategoryCode+"</td><td>"+productTypeInfo+"</td></tr>";
						}else{
							if(isNotEmpty(data[i].rownum)){
								rownum= data[i].rownum;
							}
							if(isNotEmpty(data[i].productName)){
								productName= data[i].productName;
							}
							
							if(isNotEmpty(data[i].productTypeInfo)){
								productTypeInfo= data[i].productTypeInfo;
							}
							if(isNotEmpty(data[i].productCategoryCode)){
								productCategoryCode= data[i].productCategoryCode;
							}
							
							if(isNotEmpty(data[i].productCategoryName)){
								productCategoryName= data[i].productCategoryName;
							}
							tempHtml+="<tr ><td>"+rownum+"</td><td>"+productName+"</td><td>"+productCategoryName+"</td><td>"+productCategoryCode+"</td><td>"+productTypeInfo+"</td></tr>";
							
						}
					}
				}
			}else{
				tempHtml+="<tr ><td colspan='5'>暂无明细</td></tr>";
			}
		},
		error : function(data) {

		}
	});
	
	$('#openDialog').empty();
	$('#openDialog').append("<div class='modal fade' id='"+ lictype+ "' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' data-backdrop='static'>"+
			"	<div class='modal-dialog'>"+
			"		<div class='modal-content'>"+
			"			<div class='modal-header'>"+
			"				<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>"+
			"				<h4 class='modal-title' id='myModalLabel'>"+entname+"的食品生产许可证</h4>"+
			"			</div>"+
			"			<div class='modal-body'>"+
			"				<table class='table'>"+
			"					<tr>"+
			"						<td class='labelTitle'>许可证号：</td>"+
			"						<td>"+licenseNo+"</td>"+
			"						<td class='labelTitle'>企业名称：</td>"+
			"						<td>"+entName+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>生产地址：</td>"+
			"						<td>"+engagePlace+"</td>"+
			"						<td class='labelTitle'>法定代表人（负责人）：</td>"+
			"						<td>"+legalPresent+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>注册地址：</td>"+
			"						<td>"+address+"</td>"+
			"						<td class='labelTitle'>食品类别：</td>"+
			"						<td>"+productName+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>社会信用代码：</td>"+
			"						<td>"+entScCode+"</td>"+
			"						<td class='labelTitle'>产品明细：</td>"+
			"						<td>"+productTypeInfo+"</td>"+
			"					</tr><tr>"+
			"						<td class='labelTitle'>发证机关：</td>"+
			"						<td>"+inspectOrganName+"</td>"+
			"						<td class='labelTitle'>发证日期：</td>"+
			"						<td>"+certificationDate+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>有效期至：</td>"+
			"						<td>"+validityTo+"</td>"+
			"						<td class='labelTitle'>证书状态：</td>"+
			"						<td>"+licenseState+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td colspan='4'><table class='table detailList'><tr class='success'><td width='5%'>序号</td><td width='17%'>食品、食品添加剂类别</td><td width='10%'>类别名称</td><td width='10%'>类别编号</td><td>品种明细</td></tr>"+tempHtml+"</table></td>"+
			"					</tr>"+
			"				</table>"+
			"			</div>"+
			"			<div class='modal-footer'>"+
			"				<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"</div>");
	
	
}

//食品生产许可证信息(食品类别)
function temp31() {
	
}

// 食品流通许可证信息
function temp5(lictype,entname,data) {
	var spltLicenseNo= " ";
	
	if(isNotEmpty(data[0].spltLicenseNo)){
		spltLicenseNo= data[0].spltLicenseNo;
	}
	
	var companyName = " ";
	if(isNotEmpty(data[0].companyName)){
		companyName= data[0].companyName;
	}
	var businessPlace = " ";
	if(isNotEmpty(data[0].businessPlace)){
		businessPlace= data[0].businessPlace;
	}
	var responsiblePerson = " ";
	var operationType = " ";
	var validUntil = " ";
	var applicantDate = " ";
	
	if(isNotEmpty(data[0].responsiblePerson)){
		responsiblePerson= data[0].responsiblePerson;
	}
	
	

	if(isNotEmpty(data[0].operationType)){
		operationType= data[0].operationType;
	}
	
	

	if(isNotEmpty(data[0].validUntil)){
		validUntil= data[0].validUntil;
	}
	if(isNotEmpty(data[0].applicantDate)){
		applicantDate= data[0].applicantDate;
	}
	
	$('#openDialog').empty();
	$('#openDialog').append("<div class='modal fade' id='"+ lictype+ "' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' data-backdrop='static'>"+
			"	<div class='modal-dialog'>"+
			"		<div class='modal-content'>"+
			"			<div class='modal-header'>"+
			"				<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>"+
			"				<h4 class='modal-title' id='myModalLabel'>"+entname+"的食品流通许可证信息</h4>"+
			"			</div>"+
			"			<div class='modal-body'>"+
			"				<table class='table'>"+
			"					<tr>"+
			"						<td class='labelTitle'>许可证号：</td>"+
			"						<td>"+spltLicenseNo+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>食品经营者名称：</td>"+
			"						<td>"+companyName+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>经营场所：</td>"+
			"						<td>"+businessPlace+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>法定代表人：</td>"+
			"						<td>"+responsiblePerson+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>许可范围：</td>"+
			"						<td>"+operationType+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>有效期限：</td>"+
			"						<td>"+applicantDate  + "至" +validUntil+"</td>"+
			"					</tr>"+
			"				</table>"+
			"			</div>"+
			"			<div class='modal-footer'>"+
			"				<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"</div>");
}

// 餐饮服务许可证信息
function temp6(lictype,entname,data) {
	var certificateNo= " ";
	if(isNotEmpty(data[0].certificateNo)){
		certificateNo= data[0].certificateNo;
	}
	var unitName= " ";
	if(isNotEmpty(data[0].unitName)){
		unitName= data[0].unitName;
	}
	var legalResponse= " ";
	if(isNotEmpty(data[0].legalResponse)){
		legalResponse= data[0].legalResponse;
	}
	
	var fullAddress= " ";
	if(isNotEmpty(data[0].fullAddress)){
		fullAddress= data[0].fullAddress;
	}
	
	var category= " ";
	if(isNotEmpty(data[0].category)){
		category= data[0].category;
	}
	var remark=" ";
	if(isNotEmpty(data[0].remark)){
		remark= data[0].remark;
	}
	var toEffectLimit= " ";
	if(isNotEmpty(data[0].toEffectLimit)){
		toEffectLimit= data[0].toEffectLimit;
	}
	
	$('#openDialog').empty();
	$('#openDialog').append("<div class='modal fade' id='"+ lictype+ "' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' data-backdrop='static'>"+
			"	<div class='modal-dialog'>"+
			"		<div class='modal-content'>"+
			"			<div class='modal-header'>"+
			"				<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>"+
			"				<h4 class='modal-title' id='myModalLabel'>的餐饮服务许可证信息</h4>"+
			"			</div>"+
			"			<div class='modal-body'>"+
			"				<table class='table'>"+
			"					<tr>"+
			"						<td class='labelTitle'>许可证号：</td>"+
			"						<td>"+certificateNo+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>单位名称：</td>"+
			"						<td>"+unitName+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>法定代表人：</td>"+
			"						<td>"+legalResponse+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>单位地址：</td>"+
			"						<td>"+fullAddress+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>类别：</td>"+
			"						<td>"+category+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>备注：</td>"+
			"						<td>"+remark+"</td>"+
			"					</tr>"+
			"					<tr>"+
			"						<td class='labelTitle'>有效期限至：</td>"+
			"						<td>"+toEffectLimit+"</td>"+
			"					</tr>"+
			"				</table>"+
			"			</div>"+
			"			<div class='modal-footer'>"+
			"				<button type='button' class='btn btn-default' data-dismiss='modal'>关闭</button>"+
			"			</div>"+
			"		</div>"+
			"	</div>"+
			"</div>");
}