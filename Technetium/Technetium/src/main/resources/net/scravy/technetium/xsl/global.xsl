<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="root" />
	<xsl:param name="path" />
	<xsl:param name="loggedIn" />

	<xsl:include href="global-navigation.xsl" />

	<xsl:template mode="page-head" match="*|@*" />
	<xsl:template mode="page-content" match="*|@*" />
	<xsl:template mode="page-foot" match="*|@*" />

	<xsl:template name="page">
		<xsl:param name="title" />
		<xsl:param name="heading" />

		<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
		<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
				<xsl:call-template name="head">
					<xsl:with-param name="title" select="$title" />
				</xsl:call-template>
				<xsl:apply-templates mode="page-head" select="/" />
			</head>
			<body>
				<xsl:call-template name="navigation" />

				<div class="container">
					<xsl:if test="$heading">
						<header>
							<div class="page-header">
								<h1>
									<xsl:value-of select="$heading" />
								</h1>
							</div>
						</header>
					</xsl:if>

					<xsl:apply-templates mode="page-content"
						select="/" />

					<xsl:call-template name="footer" />
				</div>

				<xsl:call-template name="foot" />

			</body>
		</html>

	</xsl:template>

	<xsl:template name="head">
		<xsl:param name="title" />

		<meta charset="utf-8" />

		<title>
			<xsl:choose>
				<xsl:when test="$title">
					<xsl:value-of select="$title" />
					<xsl:text> : Technetium</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>Technetium</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</title>

		<xsl:if test="$root = '/'">
			<base href="/" />
		</xsl:if>
		<xsl:if test="$root != '/'">
			<base href="{$root}/" />
		</xsl:if>
		
		<link rel="stylesheet" type="text/css" href="style.css" />
	</xsl:template>

	<xsl:template name="foot">
		<script type="text/javascript" src="app.js" />
		<xsl:apply-templates mode="page-foot" select="/" />
	</xsl:template>

	<xsl:template name="footer">
		<footer class="footer">
			<p class="pull-right">
				<a href="{$path}#">Back to top</a>
			</p>
			<p>
				&#x00A9; 2012 The Company
				&#183; <a href="sysinfo/handlers">(Technical) Sitemap</a>
			</p>
		</footer>
	</xsl:template>

</xsl:stylesheet>