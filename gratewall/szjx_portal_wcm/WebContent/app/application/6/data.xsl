<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml"/>
	<xsl:output indent="yes"/>
	<xsl:output encoding="GB2312"/>
	<xsl:template match="/">
		<MetaDatas>
			<xsl:apply-templates select="/MetaDatas/MetaViewData"/>
		</MetaDatas>
	</xsl:template>
	<xsl:template match="MetaDatas/MetaViewData">
		<MetaViewData>
		<PROPERTIES>
		<xsl:for-each select="PROPERTIES/*">
			<xsl:variable name="tagName" select="name()"/>
			<xsl:choose>
				
				<xsl:when test="$tagName='索引'">
					<xsl:element name="idxID">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='名称'">
					<xsl:element name="Title">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='生成日期'">
					<xsl:element name="PubDate">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='著录日期'">
					<xsl:element name="mdUpdTime">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='正文内容'">
					<xsl:element name="Content">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='相关信息索引'">
					<xsl:element name="RidxId">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='相关信息名称'">
					<xsl:element name="Rtitle">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='文号'">
					<xsl:element name="fileNum">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='位置关键词'">
					<xsl:element name="placekey">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='所属主题'">
					<xsl:element name="subcat">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='所属体裁'">
					<xsl:element name="themecat">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='所属机构'">
					<xsl:element name="organcat">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='生效期'">
					<xsl:element name="efectdate">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='内容概述'">
					<xsl:element name="Description">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='检索的内容'">
					<xsl:element name="SearchContent">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='获取方式'">
					<xsl:element name="acesmthd">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='关键字'">
					<xsl:element name="Keywords">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='服务对象'">
					<xsl:element name="svobjcat">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='废止日期'">
					<xsl:element name="Abolidate">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				<xsl:when test="$tagName='发布机构'">
					<xsl:element name="Publisher">
						<xsl:value-of select="."/>
					</xsl:element>
				</xsl:when>
				
				
				<xsl:otherwise>
					
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		</PROPERTIES>
		<xsl:copy-of select="WCMAPPENDIXS"/>
		</MetaViewData>
	</xsl:template>

</xsl:transform>