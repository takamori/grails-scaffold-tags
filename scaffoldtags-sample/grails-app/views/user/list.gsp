<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
         <title>User List</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
            <h1 class="title">User List</h1>
            <div class="pageActions">
            <span class="actionButton"><g:link action="create">Create</g:link></span></div>
            </div>
            <g:if test="${flash.message}">
                <div class="message">
                    ${flash.message}
                </div>
            </g:if>
            <g:renderDomain domain="${User.class}" 
                            template="list" 
                            value="${userInstanceList}" 
                            exceptWhen="${ { prop -> prop.type == Set.class } }"
                            style="[actions:[[show:'Show'], [edit: 'Edit']] ]" />
            <div class="paginateButtons">
                <g:paginate total="${User.count()}" />
            </div>
            <textarea class="codeShare"><g:showGSP view="edit"/></textarea>
        </div>
    </body>
</html>
