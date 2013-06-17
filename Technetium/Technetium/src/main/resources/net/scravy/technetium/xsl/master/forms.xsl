<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:x="http://www.w3.org/1999/xhtml" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="iForms.xsl"/>
	
	<xsl:template match="x:iForm">
		<xsl:apply-templates select="." mode="iForm" />
	</xsl:template>

	<xsl:template match="x:iText">
		<div>
			<xsl:call-template name="i-attributes">
				<xsl:with-param name="class" select="'control-group'" />
			</xsl:call-template>
			<label class="control-label" for="{@id}">
				<xsl:apply-templates select="*[name() != 'help']|text()" />
			</label>
			<div class="controls">
				<input type="text" id="{@id}" name="{@id}" class="input-large"
					placeholder="{@placeholder}" value="{@value}" />
				<xsl:if test="x:help">
					<p class="help-block">
						<xsl:apply-templates select="x:help" />
					</p>
				</xsl:if>
			</div>
		</div>
	</xsl:template>

	<xsl:template match="x:iTextArea">
		<div>
			<xsl:call-template name="i-attributes">
				<xsl:with-param name="class" select="'control-group'" />
			</xsl:call-template>
			<label class="control-label" for="{@id}">
				<xsl:apply-templates select="*[name() != 'help']|text()" />
			</label>
			<div class="controls">
				<textarea id="{@id}" name="{@id}" class="input-xlarge"
					placeholder="{@placeholder}" rows="{@rows}">
					<xsl:value-of select="@value" />
				</textarea>
				<xsl:if test="x:help">
					<p class="help-block">
						<xsl:apply-templates select="x:help" />
					</p>
				</xsl:if>
			</div>
		</div>
	</xsl:template>

	<xsl:template match="x:iPassword">
		<div>
			<xsl:call-template name="i-attributes">
				<xsl:with-param name="class" select="'control-group'" />
			</xsl:call-template>
			<label class="control-label" for="{@id}">
				<xsl:apply-templates select="*[name() != 'help']|text()" />
			</label>
			<div class="controls">
				<input type="password" id="{@id}" class="input-large" name="{@id}"
					placeholder="{@placeholder}" />
			</div>
		</div>
	</xsl:template>

	<xsl:template match="x:iCheckbox">
		<div>
			<xsl:call-template name="i-attributes">
				<xsl:with-param name="class" select="'control-group'" />
			</xsl:call-template>
			<label class="control-label" for="{@id}">
				<xsl:apply-templates select="*[name() != 'help']|text()" />
			</label>
			<div class="controls">
				<input type="checkbox" id="{@id}" class="input-large" name="{@id}"
					value="{@value}" />
			</div>
		</div>
	</xsl:template>

	<xsl:template match="x:iSelect">
		<div>
			<xsl:call-template name="i-attributes">
				<xsl:with-param name="class" select="'control-group'" />
			</xsl:call-template>
			<label class="control-label" for="{@id}">
				<xsl:apply-templates
					select="*[name() != 'help' and name() != 'option' and name() != 'optgroup']|text()" />
			</label>
			<div class="controls">
				<select id="{@id}" name="{@id}" value="{@value}">
					<xsl:apply-templates select="*[name() = 'option' or name() = 'optgroup']" />
				</select>
			</div>
		</div>
	</xsl:template>

	<xsl:template match="x:iCombobox">

	</xsl:template>

	<xsl:template match="x:iOptions">

	</xsl:template>

	<xsl:template match="x:iDate">

	</xsl:template>

	<xsl:template match="x:iSubmit">
		<xsl:choose>
			<xsl:when test="@name">
				<button type="submit" name="{@name}">
					<xsl:call-template name="i-attributes">
						<xsl:with-param name="class" select="'btn'" />
					</xsl:call-template>
					<xsl:apply-templates select="*|text()" />
				</button>
			</xsl:when>
			<xsl:otherwise>
				<button type="submit" class="btn btn-primary">
					<xsl:apply-templates select="*|text()" />
				</button>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="x:iActions">
		<div class="form-actions">
			<xsl:apply-templates select="*|text()" />
		</div>
	</xsl:template>

	<xsl:template name="i-attributes">
		<xsl:param name="class" />

		<xsl:attribute name="class">
			<xsl:choose>
				<xsl:when test="@class">
					<xsl:value-of select="concat($class, ' ', @class)" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$class" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>

		<xsl:apply-templates
			select="@*[name() != 'class' and name() != 'id' and name() != 'placeholder' and name() != 'value']" />
	</xsl:template>

</xsl:stylesheet>