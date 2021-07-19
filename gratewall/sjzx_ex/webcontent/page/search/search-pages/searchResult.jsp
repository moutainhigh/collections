<%@ page contentType="text/xml; charset=GBK" %><?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/Results">
    <xsl:if test="not(ResultItems/Item)">
      <div width="100%" bgcolor="#EEEEEE">
      		  <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="2px">小提示：无需单击“搜索”，按回车键可节省时间。</FONT>
              </LEFT>            
      		  <br/><br/><br/><br/>
              <LEFT>
                <FONT COLOR="RED" SIZE="4px">找不到和你的查询相符的内容。</FONT>
              </LEFT>            
      		  <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="4px">建议：</FONT>
              </LEFT>
              <br/><br/>  
              <LEFT>
	            <FONT COLOR="black" SIZE="4px">1.请检查输入字词有无错误。</FONT>
              </LEFT>
              <br/><br/>  
              <LEFT>
                <FONT COLOR="black" SIZE="4px">2.请换用另外的查询字词。</FONT>
              </LEFT>
              <br/><br/>
              <LEFT>
                <FONT COLOR="black" SIZE="4px">3.请改用较常用的字词。</FONT>
              </LEFT>         
      </div>          
    </xsl:if>
    <xsl:if test="ResultItems/Item">
      <xsl:variable name="StartIndex" select="ResultInfo/StartIndex" />
      <div  style="line-height:22px;width:100%;padding-left:10px;margin-bottom:2px;"><span>搜索结果 <xsl:value-of select="ResultInfo/ResultSize"/> 条</span></div>
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
          当前第&lt;<xsl:value-of select="ResultInfo/CurPage"/>&gt;页<![CDATA[ ]]>
          共&lt;<xsl:value-of select="ResultInfo/PageCount"/>&gt;页<![CDATA[ ]]>
          记录总数&lt;<xsl:value-of select="ResultInfo/ResultSize"/>&gt;条<![CDATA[ ]]>
            <xsl:if test="ResultInfo/CurPage > 1" >
              <A id="first" href="#">首页</A><![CDATA[ ]]>
              <A id="before" href="#">上一页</A><![CDATA[ ]]>
            </xsl:if>
            <xsl:if test="ResultInfo/CurPage &lt; ResultInfo/PageCount">
              <A id="after" href="#">下一页</A><![CDATA[ ]]>
              <A id="last" href="#">最后一页</A><![CDATA[ ]]>
            </xsl:if>
            <!-- 
            转到第
            <SELECT id="curPage">
                <xsl:for-each select="ResultItems/Item">
                    <OPTION>
                        <xsl:number value="position() + $StartIndex - 1" format="1"/>
                    </OPTION>                
                </xsl:for-each>
             </SELECT>
             页 
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