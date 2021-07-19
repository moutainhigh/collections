<%@ page contentType="text/xml; charset=GBK" %><?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/Results">
    <xsl:if test="not(ResultItems/Item)">
      <div width="100%" bgcolor="#EEEEEE">
      		  <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="2px">С��ʾ�����赥���������������س����ɽ�ʡʱ�䡣</FONT>
              </LEFT>            
      		  <br/><br/><br/><br/>
              <LEFT>
                <FONT COLOR="RED" SIZE="4px">�Ҳ�������Ĳ�ѯ��������ݡ�</FONT>
              </LEFT>            
      		  <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="4px">���飺</FONT>
              </LEFT>
              <br/><br/>  
              <LEFT>
	            <FONT COLOR="black" SIZE="4px">1.���������ִ����޴���</FONT>
              </LEFT>
              <br/><br/>  
              <LEFT>
                <FONT COLOR="black" SIZE="4px">2.�뻻������Ĳ�ѯ�ִʡ�</FONT>
              </LEFT>
              <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="4px">3.����ýϳ��õ��ִʡ�</FONT>
              </LEFT>         
      </div>          
    </xsl:if>
    <xsl:if test="ResultItems/Item">
      <xsl:variable name="StartIndex" select="ResultInfo/StartIndex" />
      <div  style="line-height:22px;width:100%;padding-left:10px;margin-bottom:2px;"><span>������� <xsl:value-of select="ResultInfo/ResultSize"/> ��</span></div>
      <xsl:for-each select="ResultItems/Item">
        <!--<xsl:sort order="descending" select="Type" data-type="number"/>-->  
            <div style="margin-bottom:15px;">
              <xsl:if test="position() mod 2 = 0">
                <xsl:attribute name="class">b1</xsl:attribute>
              </xsl:if>
              <xsl:if test="position() mod 2 = 1">
                <xsl:attribute name="class">b2</xsl:attribute>
              </xsl:if>
              <xsl:number value="position()" format="1. " /> 
              <!-- <xsl:number value="position() + $StartIndex - 1" format="1. "/> -->
              <xsl:element name="A">
                <xsl:attribute name="href">
                  <xsl:value-of select="url" disable-output-escaping="yes"/>
                </xsl:attribute>
                <xsl:attribute name="class">resultItem</xsl:attribute>
                <xsl:attribute name="onclick">return checkLink('<xsl:value-of select="url" disable-output-escaping="yes"/>')</xsl:attribute>
                <xsl:attribute name="target">_blank</xsl:attribute>
                <xsl:value-of select="title" disable-output-escaping="yes" />
              </xsl:element>
              <xsl:element name="DIV">
                  <xsl:value-of select="content" disable-output-escaping="yes"/>
              </xsl:element>
            </div>
      </xsl:for-each>
	  <div class="f">
          ��ǰ��&lt;<xsl:value-of select="ResultInfo/CurPage"/>&gt;ҳ<![CDATA[ ]]>
          ��&lt;<xsl:value-of select="ResultInfo/PageCount"/>&gt;ҳ<![CDATA[ ]]>
          ��¼����&lt;<xsl:value-of select="ResultInfo/ResultSize"/>&gt;��<![CDATA[ ]]>
            <xsl:if test="ResultInfo/CurPage > 1" >
              <A id="first" href="#">��ҳ</A><![CDATA[ ]]>
              <A id="before" href="#">��һҳ</A><![CDATA[ ]]>
            </xsl:if>
            <xsl:if test="ResultInfo/CurPage &lt; ResultInfo/PageCount">
              <A id="after" href="#">��һҳ</A><![CDATA[ ]]>
              <A id="last" href="#">���һҳ</A><![CDATA[ ]]>
            </xsl:if>
            <!-- 
            ת����
            <SELECT id="curPage">
                <xsl:for-each select="ResultItems/Item">
                    <OPTION>
                        <xsl:number value="position() + $StartIndex - 1" format="1"/>
                    </OPTION>                
                </xsl:for-each>
             </SELECT>
             ҳ 
             -->                                         
      </div>
      <input type="hidden" id="collId">
        <xsl:attribute name="value">
          <xsl:value-of select="ResultInfo/CollId"/>
        </xsl:attribute>
      </input>
      <input type="hidden" id="rows">
        <xsl:attribute name="value">
          <xsl:value-of select="ResultInfo/Rows"/>
        </xsl:attribute>
      </input>
      <input type="hidden" id="page">
        <xsl:attribute name="value">
          <xsl:value-of select="ResultInfo/CurPage"/>
        </xsl:attribute>
      </input>
      <input type="hidden" id="query">
        <xsl:attribute name="value">
          <xsl:value-of select="ResultInfo/Query"/>
        </xsl:attribute>
      </input>
      <input type="hidden" id="maxPage">
        <xsl:attribute name="value">
          <xsl:value-of select="ResultInfo/PageCount"/>
        </xsl:attribute>
      </input>
    </xsl:if>
  </xsl:template>
</xsl:stylesheet>