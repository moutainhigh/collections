<%!
public String[] allowImageScale(String sTempFile){
	try{
		int[] imgSize = CMyImage.getImageSize(sTempFile);//[image.width, image.height]
		String sImageScale = ConfigServer.getServer().getSysConfigValue("UPLOAD_IMAGE_LIMTED_SCALE","0,0,0,0");
		String sDoScaleValidErroMessage = "";
		/*1.解析参数*/
		int nImageWidth  = imgSize[0];
		int nImageHeight = imgSize[1];
		boolean bVaildScale = false;
		boolean bVaildRate = false;
		//解析体统配置的图片尺寸限制
		int nSetWidth  = Integer.parseInt(sImageScale.split(",")[0]);
		int nSetHeight = Integer.parseInt(sImageScale.split(",")[1]);
		if(nSetWidth>0 || nSetHeight>0) {
			bVaildScale = true;
		}
		double nSetRate = Double.parseDouble(sImageScale.split(",")[2]);
		if(nSetRate > 0) {
			bVaildRate = true;
		}
		boolean bIsConsiderRate = Integer.parseInt(sImageScale.split(",")[3])>0;
		/*2.判断逻辑*/
		//图片比例是否符合的优先级高于图片宽高是否符合，先判断比例
		if(bVaildRate) {
			/* 2.1.1 考虑横竖幅*/
			if(bIsConsiderRate) {
				System.out.println("图片宽度： "+nImageWidth+";\t图片高度： "+nImageHeight);
				double nImageRate = nImageWidth>nImageHeight ? (double)nImageWidth/nImageHeight : (double)nImageHeight/nImageWidth;
				if(nSetRate<1) {
					nSetRate = 1/nSetRate;
				}
				if(nSetWidth>nSetHeight) {
					sDoScaleValidErroMessage = "图片太宽了!\n\n不符合系统自动配置的(横幅)比例：[ 宽/高 = " + nSetRate + "]";
				}else {
					sDoScaleValidErroMessage = "图片太高了!\n\n不符合系统自动配置的(竖幅)比例：[ 高/宽 = " + nSetRate + "]";
				}
				System.out.println("[自动]图片比例： "+nImageRate+";\t设置比例： "+nSetRate);
				if(nImageRate>nSetRate) {
					return new String[]{"false",sDoScaleValidErroMessage};
				}
			/* 2.1.2 不考虑横竖幅*/
			}else {
				//若不考虑图片横竖幅，则默认为横幅：nImageWidth/nImageHeight
				double nImageDefaultRate = (double)nImageWidth/nImageHeight;
				System.out.println("[默认]图片比例： "+nImageDefaultRate+";\n设置比例： "+nSetRate);
				if(nImageDefaultRate>nSetRate) {
					sDoScaleValidErroMessage = "图片不符合系统默认配置的宽高比：[ 宽/高 = " + nSetRate + "]";
					return new String[]{"false",sDoScaleValidErroMessage};
				}
			}
		}
		/* 2.2 校验图片规格 */
		if((nImageHeight>nSetHeight || nImageWidth>nSetWidth) && bVaildScale) {
			sDoScaleValidErroMessage = "上传图片尺寸规格超过限制，支持规格为[" + nSetWidth + "x" + nSetHeight +"]";
			return new String[]{"false",sDoScaleValidErroMessage};
		}
		/*3.执行规则*/
		return new String[]{"true",""};
		
	}catch(Exception ex){
		return new String[]{"true",""};
	}
}
%>