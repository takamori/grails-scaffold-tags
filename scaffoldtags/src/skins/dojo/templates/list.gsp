<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="dojo" />
        <title>${className} List</title>
        <g:javascript library="dojo"/>
        <script type="text/javascript">
            // Load Dojo's code relating to widget managing functions
            dojo.require("dojo.widget.*");
            <g:renderDomain domain="\${${className}.class}"
                            template="list.script"
                            value="\${${propertyName}List}"
                            exceptWhen="\${ { prop -> prop.type == Set.class } }"
                            style="[actions:[[show:'Show'], [edit: 'Edit']], pages: true ]" />
        </script>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
            <h1 class="title">${className} List</h1>
            <div class="pageActions">
            <span class="actionButton"><g:link action="create">Create</g:link></span></div>
            </div>
            <g:if test="\${flash.message}">
                <div class="message">
                    \${flash.message}
                </div>
            </g:if>
            <g:renderDomain domain="\${${className}.class}"
                            template="list"
                            value="\${${propertyName}List}"
                            exceptWhen="\${ { prop -> prop.type == Set.class } }"
                            style="[ actions:[[show:'Show'], [edit: 'Edit']],
                                    count: ${propertyName}Count, pages: true ]" />
        </div>
    </body>
</html>
