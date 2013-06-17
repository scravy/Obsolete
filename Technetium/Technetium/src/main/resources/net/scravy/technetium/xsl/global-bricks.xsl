<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template name="sortable-script">
		<script type="text/javascript" src="sortable.js" />
		<style type="text/css">
			table.sortable > thead a:hover {
			text-decoration:
			none;
			}
		</style>
	</xsl:template>

</xsl:stylesheet>