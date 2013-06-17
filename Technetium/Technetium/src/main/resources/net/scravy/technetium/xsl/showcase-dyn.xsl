<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml" version="1.0">

  <page title="Showcase" heading="Technetium Technical Features Showcase">
    <section>
      <row>
        <span12>
          <xsl:apply-templates mode="dump" select="/response" />
        </span12>
      </row>
    </section>
  </page>

  <xsl:template mode="dump" match="text()">
    <xsl:value-of select="." />
  </xsl:template>

  <xsl:template mode="dump" match="@*">
    <xsl:text> </xsl:text>
    <span style="color: olive">
      <xsl:value-of select="name()" />
    </span>
    <span style="color: maroon">
      <xsl:text>="</xsl:text>
    </span>
    <span style="color: gray">
      <xsl:value-of select="." />
    </span>
    <span style="color: maroon">
      <xsl:text>"</xsl:text>
    </span>
  </xsl:template>

  <xsl:template mode="dump" match="*">

    <xsl:choose>
      <xsl:when test="count(child::node()) = 0">

        <div style="padding-left: 1em">
          <span style="color: maroon">
            <xsl:text>&lt;</xsl:text>
          </span>
          <span style="color: navy">
            <xsl:value-of select="name()" />
          </span>
          <xsl:apply-templates select="@*" mode="dump" />
          <span style="color: maroon">
            <xsl:text> /&gt;</xsl:text>
          </span>
        </div>

      </xsl:when>
      <xsl:otherwise>

        <div style="padding-left: 1em">
          <span style="color: maroon">
            <xsl:text>&lt;</xsl:text>
          </span>
          <span style="color: navy">
            <xsl:value-of select="name()" />
          </span>
          <xsl:apply-templates select="@*" mode="dump" />
          <span style="color: maroon">
            <xsl:text>&gt;</xsl:text>
          </span>

          <xsl:apply-templates mode="dump" select="* | text()" />

          <span style="color: maroon">
            <xsl:text>&lt;/</xsl:text>
          </span>
          <span style="color: navy">
            <xsl:value-of select="name()" />
          </span>
          <span style="color: maroon">
            <xsl:text>&gt;</xsl:text>
          </span>
        </div>

      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>

</xsl:stylesheet>
