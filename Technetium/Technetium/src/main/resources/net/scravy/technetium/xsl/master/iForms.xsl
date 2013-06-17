<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:x="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="lang.xsl" />

	<xsl:template match="x:iForm" mode="iForm">
		<xsl:variable name="formDefinition"
			select="document(concat('form://', @name))/formDefinition" />

		<form class="form-horizontal">
			<input type="hidden" name="!form">
				<xsl:attribute name="value">
					<xsl:value-of select="''" />
				</xsl:attribute>
			</input>

			<xsl:apply-templates select="$formDefinition"
				mode="iForm" />

		</form>

		<div
			style="border: 1px solid gray; background: whitesmoke color: black; padding: .5em;">
			<b>Debug</b>
			<xsl:apply-templates mode="dump" select="$formDefinition" />
		</div>
	</xsl:template>

	<xsl:template match="formField[@javaType = 'java.lang.String']" mode="iForm">
		<div class="control-group">
			<label class="control-label">
			
			</label>
			<div class="controls">
				<input type="text" class="input-xlarge" />
			</div>
		</div>
	</xsl:template>

	<xsl:template match="formField[@javaType = 'java.lang.Integer']" mode="iForm">
		<div class="control-group">
			<label class="control-label">
				
			</label>
			<div class="controls">
				<input type="number" />
			</div>
		</div>
	</xsl:template>



	<xsl:template match="formField" mode="iForm">
		<p>form field</p>
	</xsl:template>

	<xsl:template mode="dump" match="text()">
		<xsl:value-of select="." />
	</xsl:template>

	<xsl:template mode="dump" match="@*">
		<!-- <xsl:element name="text" namespace="http://www.w3.org/1999/XSL/Transform">
			<xsl:text> </xsl:text>
		</xsl:element> -->
		<br />
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
					<xsl:apply-templates select="@*" mode="dump">
						<xsl:sort select="name()" />
					</xsl:apply-templates>
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