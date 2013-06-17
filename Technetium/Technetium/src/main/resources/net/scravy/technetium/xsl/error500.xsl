<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<page title="Internal Server Error" heading="That should not have happened.">
		<section>

			<xsl:apply-templates select="/response/exceptions/exception" />
		</section>
	</page>

	<xsl:template match="exception">

		<h2>
			<xsl:value-of select="@name" />
			<xsl:text> </xsl:text>
			<small>
				<xsl:value-of select="@type" />
			</small>
		</h2>

		<xsl:if test="message">
			<p>
				<xsl:value-of select="message" />
			</p>
		</xsl:if>
		<xsl:if test="not(message)">
			<p>
				No message was associated with this exception.
			</p>
		</xsl:if>

		<xsl:if test="cause">
			<h3>Chain of Causes</h3>
			<ul>
				<xsl:apply-templates select="cause" />
			</ul>
		</xsl:if>

		<h3>Stack Trace</h3>
		<ul>
			<xsl:apply-templates select="stackTrace/element" />
		</ul>

	</xsl:template>

	<xsl:template match="element">
		<li>
			<xsl:value-of select="@className" />
			<xsl:text>.</xsl:text>
			<b>
				<xsl:value-of select="@methodName" />
			</b>
			<xsl:text> (</xsl:text>
			<xsl:value-of select="@fileName" />
			<xsl:text>:</xsl:text>
			<xsl:value-of select="@lineNumber" />
			<xsl:text>)</xsl:text>
		</li>
	</xsl:template>

	<xsl:template match="cause">
		<li>
			Caused by:
			<b>
				<xsl:value-of select="@type" />
			</b>
			<xsl:if test="message">
				<xsl:text>: </xsl:text>
				<i>
					<xsl:value-of select="message" />
				</i>
			</xsl:if>
			<br />
			<ul>
				<xsl:apply-templates select="stackTrace/element[1]" />
			</ul>
		</li>
		<xsl:apply-templates select="cause" />
	</xsl:template>

</xsl:stylesheet>