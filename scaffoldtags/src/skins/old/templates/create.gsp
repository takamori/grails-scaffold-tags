<%=packageName%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="main" />
         <title>Create ${className}</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a href="\${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link action="list">${className} List</g:link></span>
        </div>
        <div class="body">
           <h1>Create ${className}</h1>
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
                               exceptWhen="\${ { prop -> prop.type == Set.class } }" />
               <div class="buttons">
                     <span class="formButton">
                        <input type="submit" value="Create"></input>
                     </span>
               </div>
            </g:form>
        </div>
    </body>
</html>
