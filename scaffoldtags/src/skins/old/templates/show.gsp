<%=packageName%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
          <meta name="layout" content="main" />
         <title>Show ${className}</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a href="\${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link action="list">${className} List</g:link></span>
            <span class="menuButton"><g:link action="create">New ${className}</g:link></span>
        </div>
        <div class="body">
           <h1>Show ${className}</h1>
           <g:if test="\${flash.message}">
                 <div class="message">\${flash.message}</div>
           </g:if>
           <g:renderDomain domain="\${${className}.class}"
                           template="show"
                           value="\${${propertyName}}" />
           <div class="buttons">
               <g:form>
                 <input type="hidden" name="id" value="\${${propertyName}?.id}" />
                 <span class="button"><g:actionSubmit value="Edit" /></span>
                 <span class="button"><g:actionSubmit value="Delete" /></span>
               </g:form>
           </div>
        </div>
    </body>
</html>
