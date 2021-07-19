<%
	/*
	*分别按照链接和镜像引用发送请求，用以区别引用类型
	*/
	//链接引用
	processor.reset();
	processor.setEscapeParameters(new String[]{
		"DocumentId", "ObjectId"
	});
	processor.setAppendParameters(new String[]{
		"Modal", "" + ChnlDoc.MODAL_LINK
	});
	Channels quoteChannels = (Channels) processor.excute("document", "getQuotedChannels");
	int nQuoteChnlSize = quoteChannels.size();
	String sQuote = "quote";
	//镜像引用
	processor.reset();
	processor.setEscapeParameters(new String[]{
		"DocumentId", "ObjectId"
	});
	processor.setAppendParameters(new String[]{
		"Modal", "" + ChnlDoc.MODAL_MIRROR
	});
	Channels mirrorChannels = (Channels) processor.excute("document", "getQuotedChannels");
	int nMirrorChnlSize = mirrorChannels.size();
	int nTotalChnlSize = nMirrorChnlSize + nQuoteChnlSize;

	//构建table中的select值
	String sQuoteChannelIds = CMyString.showNull(quoteChannels.getIdListAsString());
	String sQuoteChannelDescs = "";
	String sMirrorChannelIds = CMyString.showNull(mirrorChannels.getIdListAsString());
	String sMirrorChannelDescs = "";
	if(nQuoteChnlSize > 0){
		for(int i=0; i<nQuoteChnlSize; i++){	
			Channel quoteChannel = (Channel)quoteChannels.getAt(i);
			if(quoteChannel == null)continue;
			sQuoteChannelDescs += quoteChannel.getDesc() + ",";
		}
		sQuoteChannelDescs = sQuoteChannelDescs.substring(0,sQuoteChannelDescs.length()-1);
	}
	if(nMirrorChnlSize > 0){
		for(int i=0; i<nMirrorChnlSize; i++){	
			Channel mirrorChannel = (Channel)mirrorChannels.getAt(i);
			if(mirrorChannel == null)continue;
			sMirrorChannelDescs += mirrorChannel.getDesc() + ",";
		}
		sMirrorChannelDescs = sMirrorChannelDescs.substring(0,sMirrorChannelDescs.length()-1);
	}
	String sTotalChannelIds = null;
	String sTotalChannelDescs = null;
	//链接or镜像引用不为空的时候，不会显示",1..."or"1...,"的形式
	if(sQuoteChannelIds == "") {
		sTotalChannelIds = sMirrorChannelIds;
		sTotalChannelDescs = sMirrorChannelDescs;
	}else if(sMirrorChannelIds == "") {
		sTotalChannelIds = sQuoteChannelIds;
		sTotalChannelDescs = sQuoteChannelDescs;
	}else{
		sTotalChannelIds = sQuoteChannelIds + "," + sMirrorChannelIds;
		sTotalChannelDescs = sQuoteChannelDescs + "," + sMirrorChannelDescs;
	}

	StringBuffer sbTableHtml = new StringBuffer();
	for(int i=0; i<nQuoteChnlSize; i++){	
		Channel quoteChannel = (Channel)quoteChannels.getAt(i);
		if(quoteChannel == null)continue;
		
		sbTableHtml.append("<tr bgcolor=\"#FFFFFF\" align=center valign=middle>");
		sbTableHtml.append("<td>"+(i+1)+"</td>");
		sbTableHtml.append("<td align=left title=\""+(quoteChannel.getId())+"-"+(CMyString.filterForHTMLValue(quoteChannel.getName()))+"\"><div><span class=\"_quoteTypeImg\">&nbsp;&nbsp;&nbsp;</span>");
		sbTableHtml.append(""+(CMyString.filterForHTMLValue(quoteChannel.getDesc()))+"</div></td>");
		sbTableHtml.append("<td><span><select _quoteType="+(sQuote)+" id=\"quote_"+(i+1)+"\" onchange=\"_onchangeQuoteTypeStyle();\">");
		sbTableHtml.append("<option value=\"quote\">链接引用</option>");
		sbTableHtml.append("<option value=\"mirror\">镜像引用</option>");
		sbTableHtml.append("</select></span></td>");
		sbTableHtml.append("<td _action=\"removeQuote\" _channelid=\""+(quoteChannel.getId())+"\" _channeldesc=\""+(CMyString.filterForHTMLValue(quoteChannel.getDesc()))+"\">");
		sbTableHtml.append("<span class=\"remove_quotechannel\" title=\"移除引用\" WCMAnt:paramattr=\"title:document_props.jsp.removeQueto\"></span>");
		sbTableHtml.append("</td>");
		sbTableHtml.append("</tr>");
	}
	String sMirror = "mirror";
	for(int i=0; i<nMirrorChnlSize; i++){	
		Channel quoteChannel = (Channel)mirrorChannels.getAt(i);
		if(quoteChannel == null)continue;

		sbTableHtml.append("<tr bgcolor=\"#FFFFFF\" align=center valign=middle>");
		sbTableHtml.append("<td>"+(i+1+nQuoteChnlSize)+"</td>");
		sbTableHtml.append("<td align=left title=\""+(quoteChannel.getId())+"-"+(CMyString.filterForHTMLValue(quoteChannel.getName()))+"\"><div><span class=\"_quoteTypeImg\">&nbsp;&nbsp;&nbsp;</span>");
		sbTableHtml.append((CMyString.filterForHTMLValue(quoteChannel.getDesc()))+"</div></td>");
		sbTableHtml.append("<td><span><select _quoteType="+(sMirror)+" id=\"quote_"+(i+1+nQuoteChnlSize)+"\" onchange=\"_onchangeQuoteTypeStyle();\">");
		sbTableHtml.append("<option value=\"quote\">链接引用</option>");
		sbTableHtml.append("<option value=\"mirror\">镜像引用</option>");
		sbTableHtml.append("</select></span></td>");
		sbTableHtml.append("<td _action=\"removeQuote\" _channelid=\""+(quoteChannel.getId())+"\" _channeldesc=\""+(CMyString.filterForHTMLValue(quoteChannel.getDesc()))+"\">");
		sbTableHtml.append("<span class=\"remove_quotechannel\" title=\"移除引用\" WCMAnt:paramattr=\"title:document_props.jsp.removeQueto\"></span>");
		sbTableHtml.append("</td>");
		sbTableHtml.append("</tr>");
	}
	if(nMirrorChnlSize==0 && nQuoteChnlSize==0){
		sbTableHtml.append("<tr bgcolor=\"#FFFFFF\" align=center valign=middle>");
		sbTableHtml.append("<td>&nbsp;</td>");
		sbTableHtml.append("<td align=left>&nbsp;</td>");
		sbTableHtml.append("<td>&nbsp;</td>");
		sbTableHtml.append("<td>&nbsp;</td>");
		sbTableHtml.append("</tr>");
	}
	String sTableHtml = sbTableHtml.toString();
%>
<%
Map docQuoteMap = new HashMap();
docQuoteMap.put("TOTALCHNLSIZE", String.valueOf(nTotalChnlSize));
docQuoteMap.put("TOTALCHANNELIDS", CMyString.filterForHTMLValue(sTotalChannelIds));
docQuoteMap.put("TOTALCHANNELDESCS",CMyString.filterForHTMLValue(sTotalChannelDescs));
docQuoteMap.put("TABLEQUOTECONTENT",sTableHtml);
builder.append(docQuoteMap);
%>
