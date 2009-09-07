<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
        <title>Show Blog</title>
    </head>
    <body>
        <div id="showBlogEntry" class="body">
            <g:if test="${flash.message}">
                 <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${blogInstance}">
                <div class="titleSection">
                    <div class="pageActions">
                        <span class="actionButton"><g:link action="list">Back to List</g:link></span>
                        <g:renderActions template="show" actions="[[edit: 'Edit'], [delete: 'Delete']]" item="${blogInstance}" />
                    </div>
                    <h1 class="title">${blogInstance.name}</h1>
                    <div class="author">by <g:renderProperty template="show" domain="Blog" prop="author" value="${blogInstance.author}" /></div>
                    <div class="createdOn">created on <g:renderType template="show" value="${blogInstance.createdOn}"/></div>
                </div>
                <div id="entries">
                    <g:each var="entry" in="${entries}">
                        <g:renderDomain template="show" domain="Entry" value="${entry}" widget="preview"/>
                    </g:each>
                </div>
            </g:if>
            <textarea class="codeShare"><g:showGSP view="show"/></textarea>
        </div>
    </body>
</html>
