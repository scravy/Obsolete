<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="query" />

	<xsl:include href="global-bricks.xsl" />

	<page title="Sysinfo : Engines" heading="Available Script Engines">
		<section>
			<row>
				<span12>
					<xsl:apply-templates select="/response/engines/engine">
						<xsl:sort select="@name" />
					</xsl:apply-templates>
				</span12>
			</row>
		</section>
	</page>

	<xsl:template match="engine">
		<h2>
			<xsl:value-of select="@name" />
			<xsl:text> </xsl:text>
			<small>
				(
				<xsl:value-of select="@version" />
				)
			</small>
		</h2>
		<dl>
			<dt>Language:</dt>
			<dd>
				<xsl:value-of select="@language" />
				<xsl:text> (</xsl:text>
				<xsl:value-of select="@languageVersion" />
				<xsl:text>)</xsl:text>
			</dd>

			<dt>Threading:</dt>
			<dd>
				<xsl:choose>
					<xsl:when test="string-length(@threading) > 0">
						<xsl:value-of select="@threading" />
					</xsl:when>
					<xsl:otherwise>
						<i>No information</i>
					</xsl:otherwise>
				</xsl:choose>
			</dd>

			<dt>Compilable:</dt>
			<dd>
				<xsl:value-of select="@compilable" />
			</dd>

			<dt>Invocable:</dt>
			<dd>
				<xsl:value-of select="@invocable" />
			</dd>
			
			<dt>Aliases:</dt>
			<dd>
				<ul>
					<xsl:for-each select="name">
						<li>
							<xsl:value-of select="." />
						</li>
					</xsl:for-each>
				</ul>
			</dd>

			<dt>Filename Extensions:</dt>
			<dd>
				<ul>
					<xsl:for-each select="ext">
						<li>
							<xsl:value-of select="." />
						</li>
					</xsl:for-each>
				</ul>
			</dd>

			<dt>Mime-Types:</dt>
			<dd>
				<ul>
					<xsl:for-each select="mimeType">
						<li>
							<xsl:value-of select="." />
						</li>
					</xsl:for-each>
				</ul>
			</dd>
		</dl>
	</xsl:template>

</xsl:stylesheet>
