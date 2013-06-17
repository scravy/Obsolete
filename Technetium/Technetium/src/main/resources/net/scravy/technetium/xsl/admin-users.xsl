<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml" version="1.0">

  <xsl:param name="query" />

  <xsl:include href="global-bricks.xsl" />

  <page title="Users : Admin" heading="User administration">
    <section>
      <row>
        <span12>
          <xsl:call-template name="table-users" />
        </span12>
      </row>

      <form>
        <fieldset>
          <legend>Create a new user</legend>

          <iText id="firstName">First name:</iText>
          <iText id="lastName">Last name:</iText>
          <iText id="loginName">Login name:</iText>
          <iText id="email">Primary E-Mail:</iText>
          <iText id="displayName">Display name:</iText>
          <iPassword id="password">Password:</iPassword>

          <iActions>
            <iSubmit name="action.createNewUser">Create the new user</iSubmit>
          </iActions>
        </fieldset>
      </form>
      <!-- <form> <fieldset> <legend>Create a bunch of new users</legend> 
        <iTextArea
        id="users" rows="10" value="x"> Users specification: </iTextArea> 
        <iActions>
        <iSubmit>Create the new users</iSubmit> </iActions> </fieldset> </form> -->
    </section>

  </page>


  <xsl:template name="table-users">
    <form method="GET" action="{$path}" class="well form-inline">
      <input type="text" name="search.query" class="input-large search-query"
        value="{$query}" placeholder="part of name (any)" />
      <iSubmit>Search</iSubmit>

      <div class="btn-group pull-right">
        <a class="btn btn-inverse dropdown-toggle" data-toggle="dropdown"
          href="#">
          <i class="icon-wrench icon-white">
            <xsl:text> </xsl:text>
          </i>
          Options
          <span class="caret">
            <xsl:text> </xsl:text>
          </span>
        </a>

        <ul class="dropdown-menu">
          <li>
            <a href="#">Delete selected users</a>
          </li>
          <li>
            <a href="#">Export selected users</a>
          </li>
        </ul>
      </div>
    </form>
    <p>
      Showing
      <b>
        <xsl:value-of select="count(/response/users/person)" />
      </b>
      users, out of a total
      <b>
        <xsl:value-of select="/response/users/@count" />
      </b>
      .
    </p>

    <form id="admin-users-table-form">
      <input type="hidden" />
      <table class="table sortable" id="admin-users-table">
        <thead>
          <th>
            <xsl:text> </xsl:text>
          </th>
          <th>ID</th>
          <th>First name</th>
          <th>Last name</th>
          <th>Login</th>
          <th>E-Mail</th>
          <th>Display name</th>
        </thead>
        <tbody>
          <xsl:apply-templates select="/response/users/person" />
        </tbody>
      </table>
    </form>

  </xsl:template>

  <xsl:template match="person">
    <tr>
      <td>
        <input type="checkbox" id="box{position()}" />
      </td>
      <td>
        <xsl:value-of select="@id" />
      </td>
      <td ondblclick="$(this).">
        <xsl:value-of select="@firstName" />
      </td>
      <td>
        <xsl:value-of select="@lastName" />
      </td>
      <td>
        <xsl:value-of select="@loginName" />
      </td>
      <td>
        <xsl:value-of select="@email" />
      </td>
      <td>
        <xsl:value-of select="@displayName" />
      </td>
    </tr>
  </xsl:template>

  <xsl:template mode="page-head" match="*">
    <xsl:call-template name="sortable-script" />
    <style type="text/css">
      textarea.input-xlarge {
      width: 95%;
      }
    </style>
  </xsl:template>

  <xsl:template mode="page-foot" match="*">
    <script type="text/javascript">
      $("#admin-users-table tr").on('click', function(e)
      {
      if (e.target.nodeName.toLowerCase() != 'input') {
      var checkbox =
      $(this).find("input[type='checkbox']");
      checkbox.attr('checked',
      !checkbox.attr('checked'));
      }
      });
    </script>
  </xsl:template>

</xsl:stylesheet>
