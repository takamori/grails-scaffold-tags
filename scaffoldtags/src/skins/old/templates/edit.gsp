<%=packageName%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="main" />
         <title>Edit ${className}</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a href="\${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link action="list">${className} List</g:link></span>
            <span class="menuButton"><g:link action="create">New ${className}</g:link></span>
        </div>
        <div class="body">
           <h1>Edit ${className}</h1>
           <g:if test="\${flash.message}">
                 <div class="message">\${flash.message}</div>
           </g:if>
           <g:hasErrors bean="\${${propertyName}}">
                <div class="errors">
                    <g:renderErrors bean="\${${propertyName}}" as="list" />
                </div>
           </g:hasErrors>
           <div class="prop">
	          <span class="name">Id:</span>
	          <span class="value">\${${propertyName}?.id}</span>
           </div>
           <g:form method="post" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
               <input type="hidden" name="id" value="\${${propertyName}?.id}" />
               <g:renderDomain domain="\${${className}.class}"
                               template="editor"
                               value="\${${propertyName}}"
                               except="['id']" />
               <div class="buttons">
                     <span class="button"><g:actionSubmit value="Update" /></span>
                     <span class="button"><g:actionSubmit value="Delete" /></span>
               </div>
            </g:form>
        </div>
    </body>
</html>
