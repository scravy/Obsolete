<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
  xmlns:x="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:include href="spans.xsl" />
  <xsl:include href="forms.xsl" />
  <xsl:include href="lang.xsl" />

  <xsl:template match="/xsl:stylesheet | /xsl:transform">
    <xsl:copy>
      <xsl:apply-templates select="@*" />

      <xsl:element namespace="http://www.w3.org/1999/XSL/Transform"
        name="output">
        <!-- <xsl:attribute name="method">html</xsl:attribute> -->
      </xsl:element>

      <xsl:apply-templates select="xsl:*[local-name() != 'template']" />
      <xsl:apply-templates select="x:page" />
      <xsl:apply-templates select="xsl:template" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="@*">
    <xsl:attribute name="name()">
    	
    </xsl:attribute>
  </xsl:template>

  <xsl:template match="*">
    <xsl:copy>
      <xsl:apply-templates select="@*|*|text()" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="*|@*|text()" mode="text">
    <xsl:copy>
      <xsl:apply-templates mode="text" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="text()">
    <xsl:variable name="text" select="normalize-space(.)" />
    <xsl:choose>
      <xsl:when test="$text != ''">

        <xsl:if test="normalize-space(substring(., 1, 1)) = ''">
          <xsl:element name="text"
            namespace="http://www.w3.org/1999/XSL/Transform">
            <xsl:text> </xsl:text>
          </xsl:element>
        </xsl:if>

        <xsl:value-of select="$text" />

        <xsl:if test="normalize-space(substring(., string-length(.), 1)) = ''">
          <xsl:element name="text"
            namespace="http://www.w3.org/1999/XSL/Transform">
            <xsl:text> </xsl:text>
          </xsl:element>
        </xsl:if>
        
      </xsl:when>
      <xsl:otherwise>

      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="x:space">
    <xsl:element name="text" namespace="http://www.w3.org/1999/XSL/Transform">
      <xsl:text> </xsl:text>
    </xsl:element>
  </xsl:template>

  <xsl:template match="xsl:text">
    <xsl:copy>
      <xsl:apply-templates select="@*" />
      <xsl:apply-templates select="*|text()" mode="text" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="x:script">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
      <xsl:if test="count(child::node()) = 0">
        <xsl:element name="text"
          namespace="http://www.w3.org/1999/XSL/Transform">
          <xsl:text> </xsl:text>
        </xsl:element>
      </xsl:if>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="x:alert">
    <div>
      <xsl:call-template name="attributes">
        <xsl:with-param name="class" select="'alert alert-error'" />
      </xsl:call-template>
      <button class="close" data-dismiss="alert">×</button>
      <xsl:apply-templates select="*|text()" />
    </div>
  </xsl:template>

  <xsl:template match="x:warning">
    <div>
      <xsl:call-template name="attributes">
        <xsl:with-param name="class" select="'alert'" />
      </xsl:call-template>
      <button class="close" data-dismiss="alert">×</button>
      <xsl:apply-templates select="*|text()" />
    </div>
  </xsl:template>

  <xsl:template match="x:info">
    <div>
      <xsl:call-template name="attributes">
        <xsl:with-param name="class" select="'alert alert-info'" />
      </xsl:call-template>
      <button class="close" data-dismiss="alert">×</button>
      <xsl:apply-templates select="*|text()" />
    </div>
  </xsl:template>

  <xsl:template match="x:success">
    <div>
      <xsl:call-template name="attributes">
        <xsl:with-param name="class" select="'alert alert-success'" />
      </xsl:call-template>
      <button class="close" data-dismiss="alert">×</button>
      <xsl:apply-templates select="*|text()" />
    </div>
  </xsl:template>

  <xsl:template match="x:row">
    <div>
      <xsl:call-template name="attributes">
        <xsl:with-param name="class" select="'row'" />
      </xsl:call-template>
      <xsl:apply-templates select="*|text()" />
    </div>
  </xsl:template>

  <xsl:template match="x:form">
    <xsl:copy>
      <xsl:if test="not(@method)">
        <xsl:attribute name="method">
				<xsl:text>POST</xsl:text>
			</xsl:attribute>
      </xsl:if>
      <xsl:if test="not(@class)">
        <xsl:call-template name="attributes">
          <xsl:with-param name="class">
            <xsl:text>form-horizontal</xsl:text>
          </xsl:with-param>
        </xsl:call-template>
      </xsl:if>
      <xsl:if test="@class">
        <xsl:apply-templates select="@*" />
      </xsl:if>

      <xsl:apply-templates select="*|text()" />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="x:page">

    <xsl:element name="include" namespace="http://www.w3.org/1999/XSL/Transform">
      <xsl:attribute name="href">
				<xsl:value-of select="'global.xsl'" />
			</xsl:attribute>
    </xsl:element>

    <xsl:element name="template" namespace="http://www.w3.org/1999/XSL/Transform">
      <xsl:attribute name="mode">
				<xsl:text>page-content</xsl:text>
			</xsl:attribute>
      <xsl:attribute name="match">
				<xsl:text>*</xsl:text>
			</xsl:attribute>

      <xsl:apply-templates select="*|text()" />
    </xsl:element>

    <xsl:element name="template" namespace="http://www.w3.org/1999/XSL/Transform">
      <xsl:attribute name="match">
				<xsl:text>/response</xsl:text>
			</xsl:attribute>

      <xsl:element name="call-template"
        namespace="http://www.w3.org/1999/XSL/Transform">
        <xsl:attribute name="name">
					<xsl:text>page</xsl:text>
				</xsl:attribute>

        <xsl:element name="with-param"
          namespace="http://www.w3.org/1999/XSL/Transform">
          <xsl:attribute name="name">
					<xsl:text>title</xsl:text>
				</xsl:attribute>

          <xsl:value-of select="@title" />
        </xsl:element>

        <xsl:element name="with-param"
          namespace="http://www.w3.org/1999/XSL/Transform">
          <xsl:attribute name="name">
					<xsl:text>heading</xsl:text>
				</xsl:attribute>

          <xsl:value-of select="@heading" />
        </xsl:element>
      </xsl:element>
    </xsl:element>

  </xsl:template>


  <xsl:template name="attributes">
    <xsl:param name="class" />

    <xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="@class">
					<xsl:value-of select="concat($class, ' ', @class)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$class" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>

    <xsl:apply-templates select="@*[name() != 'class']" />
  </xsl:template>

</xsl:stylesheet>

