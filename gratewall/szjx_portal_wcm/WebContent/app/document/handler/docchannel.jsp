<%
Map docChannelMap = new HashMap();
docChannelMap.put("DOCCHANNEL", String.valueOf(docChannel.getId()));
docChannelMap.put("CHANNEL-TRUN-HTML", CMyString.filterForHTMLValue(displayShow(docChannel.getDesc())));
docChannelMap.put("CHANNEL-HTMLVALUE", CMyString.filterForHTMLValue(docChannel.getDesc()));
builder.append(docChannelMap);
%>