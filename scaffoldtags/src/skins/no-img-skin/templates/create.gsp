<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <title>Create ${className}</title>         
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
                <h1 class="title">Create ${className}</h1>
                <div class="pageActions">
                    <span class="actionButton"><g:link action="list">Back to List</g:link></span>
                </div>
            </div>
            <g:if test="\${flash.message}">
                 <div class="message">\${flash.message}</div>
            </g:if>
            <g:hasErrors bean="\${${propertyName}}">
                <div class="errors">
                    <g:renderErrors bean="\${${propertyName}}" as="list" />
                </div>
            </g:hasErrors>
            <g:form action="save" method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
                <g:renderDomain domain="\${${className}.class}" 
                                template="editor" 
                                value="\${${propertyName}}" 
                                except="['id']"
                                exceptWhen="\${ { prop -> prop.type == Set.class } }"
                                style="[actions:[[create: 'Create']] ]" />
            </g:form>
        </div>
    </body>
</html>
