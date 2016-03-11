<#-- @ftlvariable name="pager" type="me.geso.koblog.domain.Pager" -->
<#-- @ftlvariable name="entry" type="me.geso.koblog.domain.BlogEntry" -->

<#import "__wrapper.ftl" as wrapper>
<@wrapper.main title="tokuhirom's blog">
<h1><a href="/">tokuhirom's blog</a></h1>

<h2>${entry.title}</h2>
<div class="body">${entry.body}</div>
<a href="/entry/${entry.path}">${entry.created}</a>

</@wrapper.main>