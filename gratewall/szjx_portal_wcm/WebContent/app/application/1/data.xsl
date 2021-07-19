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
				
				<xsl:when test="$tagName='文件名'">
					<xsl:element name="fileName">
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