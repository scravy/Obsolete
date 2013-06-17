<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="query" />

	<xsl:include href="global-bricks.xsl" />

	<page title="Sysinfo : Entities" heading="Domain Model">
		<section>
			<row>
				<span12>
					<xsl:apply-templates select="/response/domain/entity">
						<xsl:sort select="@name" />
					</xsl:apply-templates>
				</span12>
			</row>
		</section>
	</page>

	<xsl:template match="entity">
		<h2>
			<xsl:value-of select="@name" />
			<a id="entity-{@name}">
				<xsl:text> </xsl:text>
			</a>
		</h2>
		<table class="table sortable" id="sysinfo-domain-entity-{@name}">
			<thead>
				<th>Attribute</th>
				<th>Type</th>
				<th>Java-Type</th>
			</thead>
			<tbody>
				<xsl:for-each select="attribute">
					<xsl:sort select="@name" />
					<tr>
						<td>
							<xsl:value-of select="@name" />
						</td>
						<td>
							<xsl:value-of select="@type" />
						</td>
						<td>
							<xsl:choose>
								<xsl:when test="@type = 'BASIC'">
									<xsl:value-of select="@target" />
								</xsl:when>
								<xsl:when test="@mapKeyType">
									<xsl:value-of select="@collection" />
									<xsl:text>&lt;</xsl:text>
									<a href="{$path}#entity-{@target}">
										<xsl:value-of select="@mapKeyType" />
									</a>
									<xsl:text>, </xsl:text>
									<a href="{$path}#entity-{@target}">
										<xsl:value-of select="@target" />
									</a>
									<xsl:text>&gt;</xsl:text>
								</xsl:when>
								<xsl:when test="@collection">
									<xsl:value-of select="@collection" />
									<xsl:text>&lt;</xsl:text>
									<a href="{$path}#entity-{@target}">
										<xsl:value-of select="@target" />
									</a>
									<xsl:text>&gt;</xsl:text>
								</xsl:when>
								<xsl:otherwise>
									<a href="{$path}#entity-{@target}">
										<xsl:value-of select="@target" />
									</a>
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</xsl:for-each>
			</tbody>
		</table>
	</xsl:template>

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
					<br />
					Engine:
					<xsl:value-of select="@engine" />
					<xsl:if test="@engineVersion">
						<xsl:text> (</xsl:text>
						<xsl:value-of select="@engineVersion" />
						<xsl:text>)</xsl:text>
					</xsl:if>
				</xsl:if>
				<xsl:if test="@script">
					<br />
					Script:
					<xsl:value-of select="@script" />
				</xsl:if>
			</td>
		</tr>
	</xsl:template>

	<xsl:template mode="page-head" match="*">
		<xsl:call-template name="sortable-script" />
	</xsl:template>

</xsl:stylesheet>
