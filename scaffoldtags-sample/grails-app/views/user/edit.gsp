<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
         <title>Edit User</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
                <h1 class="title">Edit User</h1>
                <div class="pageActions">
                    <span class="actionButton"><g:link action="list">Back to List</g:link></span>
                </div>
            </div>
            <g:if test="${flash.message}">
                 <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
                <div class="errors">
                    <g:renderErrors bean="${userInstance}" as="list" />
                </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:renderDomain domain="${User.class}" 
                                template="editor" 
                                value="${userInstance}" 
                                style="[id: [readonly: true], actions:[[update: 'Update'], [delete: 'Delete']] ]" />
            </g:form>
            <textarea class="codeShare"><g:showGSP view="edit"/></textarea>
        </div>
    </body>
</html>
