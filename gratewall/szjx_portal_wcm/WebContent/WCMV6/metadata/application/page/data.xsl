<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml"/>
	<xsl:output indent="yes"/>
	<xsl:output encoding="GB2312"/>

	<xsl:template match="/WCMMetaTableGovInfoS">
		<WCMDocuments>
			<xsl:apply-templates select="/WCMMetaTableGovInfoS/WCMMetaTableGovInfo"/>
		</WCMDocuments>
	</xsl:template>

	<xsl:template match="WCMMetaTableGovInfoS/WCMMetaTableGovInfo">
		<WCMDocument>
			<xsl:copy-of select="PROPERTIES"/>		
			<xsl:copy-of select="WCMAPPENDIXS"/>
		</WCMDocument>
	</xsl:template>	

</xsl:transform>