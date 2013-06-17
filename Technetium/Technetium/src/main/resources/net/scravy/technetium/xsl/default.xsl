<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml" version="1.0">

  <page title="Nothing" heading="There is simply nothing here.">
    <section>
      <p>Possible explanations:</p>
      <ul>
        <li>Maybe the content is only available if you're logged in?
        </li>
        <li>Maybe the developer forgot to set a template?</li>
      </ul>
      <p>Options:</p>
      <ul>
        <li>
          <a href="{$path}?!type=xml">View XML output</a>
        </li>
        <li>
          <a href="{$path}?!type=json">View JSON output</a>
        </li>
      </ul>
    </section>
  </page>

</xsl:stylesheet>