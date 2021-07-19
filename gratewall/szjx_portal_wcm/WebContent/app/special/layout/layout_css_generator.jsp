	<%!
	// 获取某布局的CSS样式
	public String getCssOfLayout(Layout _currLayout){
		String sClassName ="r_c"+_currLayout.getColumns()+"_"+_currLayout.getId();
		int nColumns = _currLayout.getColumns();
		int num = getNumOfRatio(_currLayout);
		String sCss ="";
		for(int i=1;i<=nColumns;i++){
			sCss+=getCssOfAColumn(i,num,sClassName,_currLayout.getRatio(),_currLayout.getRatioType());
		}
		return sCss;
	}
	// 获取布局某列的CSS样式
	public String getCssOfAColumn(int column,int num,String className,String _sRatio, int _nRatioType){
		String sCss = "."+className+" .c_"+column+"{";
		String[] sRatios = _sRatio.split(Layout.RATIO_SEPERATE);
		float percentage = (float)100.0;
		float  width = 0;
		
		//只有一列,则为100%
		if(sRatios.length==1 ){
			if(_nRatioType==Layout.RATIO_TYPE_PERCENTAGE && !sRatios[column-1].equals(Layout.ADAPTIVE_CHAR) && num<100  ){
				sCss+="width:"+num+"%;}";
				return sCss;
			}else{
				sCss+="width:100%;}";
				return sCss;
			}
		}
		// 如果有自适应列
		if(_sRatio.indexOf(Layout.ADAPTIVE_CHAR)>=0){
			percentage=(float)75.0;
		}
		// 如果当前列是自适应列，直接返回
		if(sRatios[column-1].equals(Layout.ADAPTIVE_CHAR)){
			if(_nRatioType==Layout.RATIO_TYPE_PERCENTAGE){
				if(num>=100){
					width=25;
				}else{
					width=100-num;
				}
			}else{
				width=25;
			}
			if(column==sRatios.length)
				width-=0.5;
			return sCss+="width:"+width+"%;}";
		}
		int nWidth =  Integer.parseInt(sRatios[column-1]);
		// 如果为固定比
		if(_nRatioType==Layout.RATIO_TYPE_FIXED){
			width=((float)nWidth*percentage)/num;
		}else{//百分比
			if(num>=100){
					width=((float)nWidth*percentage)/num;
			}else{
				width=nWidth;
			}
		}
		if(column==sRatios.length)
				width-=0.5;
		return sCss+="width:"+width+"%;}";
	}
	// 获取比例值总和
	public int getNumOfRatio(Layout _currLayout){
		String sRatio = _currLayout.getRatio();
		String[] sRatios = sRatio.split(Layout.RATIO_SEPERATE);
		int num=0;
		for(int i = 0;i<sRatios.length;i++){
			if(sRatios[i].equals(Layout.ADAPTIVE_CHAR)){
				continue;
			}
			num+=Integer.parseInt(sRatios[i]);
		}
		return num;
	}

	public String getRatioWidthPix(int _nRatioType,String  _sRatio){
		String[] sRatios = _sRatio.split(":");
		if( _nRatioType == Layout.RATIO_TYPE_FIXED){
			for(int k=0 ; k < sRatios.length ; k++){
				if(sRatios[k].equals(Layout.ADAPTIVE_CHAR))
					continue;
				sRatios[k]+=Layout.RATIO_FIXED_WIDTH;
			}
		}else{
			for(int k=0 ; k < sRatios.length ; k++){
				if(sRatios[k].equals(Layout.ADAPTIVE_CHAR))
					continue;
				sRatios[k]+=Layout.RATIO_PERCENTAGE_WIDTH;
			}
		}
		return CMyString.join(sRatios," : ");
	}
	%>