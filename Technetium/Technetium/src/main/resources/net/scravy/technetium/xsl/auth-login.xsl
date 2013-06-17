<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="loginUser" />
	<xsl:param name="loginStatus" />

	<page title="Login" heading="Login to Technetium">

		<section>
			<row>
				<span7 offset="2">
					<form action="login">

						<xsl:choose>
							<xsl:when test="$loginStatus = 'unknownUser'">
								<warning>
									<strong>Unknown user!</strong>
									There is no such known user.
								</warning>
							</xsl:when>
							<xsl:when test="$loginStatus = 'denied'">
								<alert>
									<strong>Access denied!</strong>
									Wrong password.
								</alert>
							</xsl:when>
						</xsl:choose>

						<fieldset>
							<legend>Login</legend>

							<iText id="login.username" value="{$loginUser}"
								placeholder="login or email address">Username:</iText>
							<iPassword id="login.password" placeholder="your password">Password:
							</iPassword>

							<iActions>
								<iSubmit>Login</iSubmit>
								<iSubmit name="login.passwordLost">Don't remember?</iSubmit>
							</iActions>
						</fieldset>
					</form>
				</span7>
			</row>
		</section>

	</page>

</xsl:stylesheet>