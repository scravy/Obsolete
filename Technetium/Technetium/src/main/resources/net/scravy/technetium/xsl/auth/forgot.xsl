<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml" version="1.0">

	<xsl:param name="userName" />

	<page title="Forgot password" heading="Recover your password">

		<section>
			<row>
				<span7 offset="2">
					<form action="forgot">

						<fieldset>
							<legend>Recover password</legend>

							<iText id="forgot.username" value="{$userName}"
								placeholder="login or email address">
								Who are you?
								<help>Please provide your login name or your
									email address in
									order to reset your password.
								</help>
							</iText>

							<div class="form-actions">
								<button type="submit" class="btn btn-primary">Reset password
								</button>
							</div>
						</fieldset>
					</form>
				</span7>
			</row>
		</section>

	</page>

</xsl:stylesheet>
