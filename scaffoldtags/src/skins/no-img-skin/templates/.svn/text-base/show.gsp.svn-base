<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="no-img-skin" />
        <title>Show ${className}</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
                <h1 class="title">Show ${className}</h1>
                <div class="pageActions">
                    <span class="actionButton"><g:link action="list">Back to List</g:link></span>
                </div>
            </div>
            <g:if test="\${flash.message}">
                 <div class="message">\${flash.message}</div>
            </g:if>
            <g:if test="\${${propertyName}}">
                <g:renderDomain domain="\${${className}.class}" 
                                template="show" 
                                value="\${${propertyName}}" 
                                style="[actions:[[edit: 'Edit'], [delete: 'Delete']] ]"/>
            </g:if>
        </div>
    </body>
</html>
