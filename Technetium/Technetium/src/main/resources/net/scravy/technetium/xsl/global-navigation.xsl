<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="loggedIn" />
	<xsl:param name="rand" />

	<xsl:template name="navigation">

		<div class="navbar">
			<div class="navbar-inner" style="border-radius: 0;">
				<div class="container" style="width: auto;">
					<a class="brand" href="{$root}">Technetium</a>

					<xsl:choose>
						<xsl:when test="$loggedIn">
							<ul class="nav pull-right">
								<li>
									<a href="admin">Admin</a>
								</li>
								<li class="divider-vertical"></li>
								<li class="dropdown">
									<a href="#" class="dropdown-toggle" data-toggle="dropdown">
										<xsl:text>Logged in as </xsl:text>
										<xsl:value-of select="/response/user/person/@displayName" />
										<b class="caret">
											<xsl:text> </xsl:text>
										</b>
									</a>
									<ul class="dropdown-menu">
										<li>
											<a href="start">Start</a>
										</li>
										<li class="divider">
											<xsl:text> </xsl:text>
										</li>
										<li>
											<a href="logout?{$rand}">Logout</a>
										</li>
									</ul>
								</li>
							</ul>
						</xsl:when>
						<xsl:otherwise>
							<ul class="nav pull-right">
								<li>
									<a href="login">You're not logged in.</a>
								</li>
							</ul>
						</xsl:otherwise>
					</xsl:choose>

				</div>
			</div>
		</div>

	</xsl:template>

</xsl:stylesheet>