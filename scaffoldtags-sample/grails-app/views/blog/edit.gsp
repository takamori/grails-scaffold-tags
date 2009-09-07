<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
         <title>Edit Blog</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
                <h1 class="title">Edit Blog</h1>
                <div class="pageActions">
                    <span class="actionButton"><g:link action="list">Back to List</g:link></span>
                </div>
            </div>
            <g:if test="${flash.message}">
                 <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${blogInstance}">
                <div class="errors">
                    <g:renderErrors bean="${blogInstance}" as="list" />
                </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:renderDomain domain="${Blog.class}" 
                                template="editor" 
                                value="${blogInstance}" 
                                style="[id: [readonly: true], actions:[[update: 'Update'], [delete: 'Delete']] ]" />
            </g:form>
            <textarea class="codeShare"><g:showGSP view="edit"/></textarea>
        </div>
    </body>
</html>
