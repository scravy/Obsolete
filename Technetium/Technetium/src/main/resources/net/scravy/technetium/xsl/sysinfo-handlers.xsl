<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="query" />

	<xsl:include href="global-bricks.xsl" />

	<page title="Sysinfo : Sitemap" heading="Registered Request Handlers">
		<section>
			<row>
				<span12>
					<xsl:call-template name="table-handlers" />
				</span12>
			</row>
		</section>
	</page>


	<xsl:template name="table-handlers">
		<table class="table sortable" id="sysinfo-sitemap-table">
			<thead>
				<th>Path</th>
				<th>Handler Class</th>
			</thead>
			<tbody>
				<xsl:apply-templates select="/response/handlers/handler" />
			</tbody>
		</table>
	</xsl:template>

	<xsl:template match="handler">
		<tr>
			<td>
				<a href="{$root}/{@path}">
					<xsl:text>/</xsl:text>
					<xsl:value-of select="@path" />
				</a>
			</td>
			<td>
				<xsl:value-of select="." />
				<xsl:if test="@engine">
					<br />Engine:
					<xsl:value-of select="@engine" />
					<xsl:if test="@engineVersion">
						<xsl:text> (</xsl:text>
						<xsl:value-of select="@engineVersion" />
						<xsl:text>)</xsl:text>						
					</xsl:if>
				</xsl:if>
				<xsl:if test="@script">
					<br />Script:
					<xsl:value-of select="@script" />
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<xsl:template mode="page-head" match="*">
		<xsl:call-template name="sortable-script" />
	</xsl:template>

</xsl:stylesheet>
