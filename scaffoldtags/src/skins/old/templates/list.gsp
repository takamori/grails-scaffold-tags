<%=packageName%>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="main" />
         <title>${className} List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a href="\${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link action="create">New ${className}</g:link></span>
        </div>
        <div class="body">
            <h1>${className} List</h1>
            <g:if test="\${flash.message}">
                <div class="message">
                    \${flash.message}
                </div>
            </g:if>
            <g:renderDomain domain="\${${className}.class}"
                            template="list"
                            value="\${${propertyName}List}"
                            exceptWhen="\${ { prop -> prop.type == Set.class } }" />
            <div class="paginateButtons">
                <g:paginate total="\${${className}.count()}" />
            </div>
        </div>
    </body>
</html>
