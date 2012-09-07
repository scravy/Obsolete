<xsl:transform version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:p="http://technodrom.scravy.de/2011/magic-properties"
	xmlns:class="http://technodrom.scravy.de/2011/magic-class">

<xsl:output method="xml" indent="yes" />
	
<xsl:template match="/magic">
<magic>
	<xsl:apply-templates />
</magic>
</xsl:template>

<xsl:template name="container">
	<xsl:call-template name="component" />
</xsl:template>

<xsl:template match="vbox">
	<vbox>
		<xsl:call-template name="container" />
		<xsl:apply-templates />
	</vbox>
</xsl:template>

<xsl:template match="hbox">
	<hbox>
		<xsl:call-template name="container" />
		<xsl:apply-templates />
	</hbox>
</xsl:template>

<xsl:template name="component">
	<xsl:variable name="current" select="." />
	<xsl:variable name="kind" select="local-name()" />
	<xsl:variable name="class" select="@class" />
	<xsl:variable name="style" select="/magic/style" />
	
	<xsl:if test="@name">
		<xsl:attribute name="name">
			<xsl:value-of select="@name" />
		</xsl:attribute>
	</xsl:if>
	
	<xsl:for-each select="document('properties.xml')/p:properties/*">
		<xsl:variable name="name" select="local-name()" />
		<xsl:if test="concat(',', text(), ',') = ',,' or concat(',', text(), ',') != ',,' and contains(concat(',', text(), ','), concat(',', $kind, ','))">
		
		<xsl:attribute name="{$name}">
			<xsl:choose>
				<!-- First priority: local declaration -->
				<xsl:when test="$current/@*[local-name() = $name] and $name != 'group'">
					<xsl:value-of select="$current/@*[local-name() = $name]" />
				</xsl:when>
				<!-- Second priority: class declaration -->
				<xsl:when test="$name != 'name' and $style/class[@name = $class]/@*[local-name() = $name]">
					<xsl:value-of select="$style/class[@name = $class]/@*[local-name() = $name]" />
				</xsl:when>
				<!-- Third priority: common element style -->
				<xsl:when test="$style/*[local-name() = $kind]/@*[local-name() = $name]">
					<xsl:value-of select="$style/*[local-name() = $kind]/@*[local-name() = $name]" />
				</xsl:when>
				<!-- Fourth priority: common component styles -->
				<xsl:when test="$style/component/@*[local-name() = $name]">
					<xsl:value-of select="$style/component/@*[local-name() = $name]" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="@default" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
		</xsl:if>
	</xsl:for-each>
</xsl:template>

<xsl:template match="numeric">
	<numeric>
		<xsl:call-template name="component" />
	</numeric>
</xsl:template>

<xsl:template match="flexi-space">
	<label>
		<xsl:call-template name="component" />
	</label>
</xsl:template>

<xsl:template match="button | label | checkbox | numeric | textfield | textarea">
	<xsl:variable name="kind" select="local-name()" />
	<xsl:element name="{$kind}">
		<xsl:call-template name="component" />
		<xsl:value-of select="text()" />
	</xsl:element>
</xsl:template>

<xsl:template match="date | datepicker">
	<date>
		<xsl:call-template name="component" />
		<xsl:value-of select="text()" />
	</date>
</xsl:template>

<xsl:template match="combobox | select">
	<combobox>
		<xsl:call-template name="component" />
		<xsl:copy-of select="val" />
	</combobox>
</xsl:template>

<xsl:template match="table">
	<table>
		<xsl:call-template name="component" />
		<xsl:copy-of select="col" />
	</table>
</xsl:template>

<xsl:template match="tabs">
	<xsl:variable name="kind" select="local-name()" />
	
	<xsl:element name="tabs">
		<xsl:call-template name="component" />
		<xsl:for-each select="tab">
			<xsl:element name="tab">
				<xsl:element name="title">
					<xsl:value-of select="title/text()" />
				</xsl:element>
				<xsl:apply-templates select="hbox | vbox" />
			</xsl:element>
		</xsl:for-each>
	</xsl:element>
</xsl:template>

<xsl:template match="panes">
	<xsl:variable name="kind" select="local-name()" />
	
	<xsl:element name="panes">
		<xsl:call-template name="component" />
		<xsl:for-each select="pane">
			<xsl:element name="pane">
				<xsl:copy-of select="@*" />
				<xsl:element name="title">
					<xsl:value-of select="title/text()" />
				</xsl:element>
				<xsl:apply-templates select="hbox | vbox" />
			</xsl:element>
		</xsl:for-each>
	</xsl:element>
</xsl:template>
<xsl:template match="password">
	<xsl:variable name="kind" select="local-name()" />
	<xsl:element name="{$kind}">
		<xsl:call-template name="component" />
	</xsl:element>
</xsl:template>

<xsl:template match="style" />

<xsl:template match="text()|@*" />

</xsl:transform>