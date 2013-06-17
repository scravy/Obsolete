<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:x="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="x:span1">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span1'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span2">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span2'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span3">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span3'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span4">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span4'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span5">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span5'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span6">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span6'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span7">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span7'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span8">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span8'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span9">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span9'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span10">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span10'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span11">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span11'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template match="x:span12">
		<div>
			<xsl:call-template name="span-attributes">
				<xsl:with-param name="class" select="'span12'" />
			</xsl:call-template>
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template name="span-attributes">
		<xsl:param name="class" />

		<xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="@class and @offset">
					<xsl:value-of select="concat($class, ' offset', @offset, ' ', @class)" />
				</xsl:when>
				<xsl:when test="@offset">
					<xsl:value-of select="concat($class, ' offset', @offset)" />
				</xsl:when>
				<xsl:when test="@class">
					<xsl:value-of select="concat($class, ' ', @class)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$class" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>

		<xsl:apply-templates select="@*[name() != 'class' and name() != 'offset']" />
	</xsl:template>

</xsl:stylesheet>