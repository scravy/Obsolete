<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="template" />

	<page title="Nothing" heading="Missing template">
		<section>
			<p>
				<xsl:text>The specified template “</xsl:text>
				<xsl:value-of select="$template" />
				<xsl:text>” does not exist.</xsl:text>
			</p>
			<p><a href="{$path}?!type=xml">View XML data</a></p>
		</section>
	</page>

</xsl:stylesheet>
