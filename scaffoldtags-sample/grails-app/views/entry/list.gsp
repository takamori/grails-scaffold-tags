<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
         <title>Entry List</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
            <h1 class="title">Entry List</h1>
            <div class="pageActions">
            <span class="actionButton"><g:link action="create">Create</g:link></span></div>
            </div>
            <g:if test="${flash.message}">
                <div class="message">
                    ${flash.message}
                </div>
            </g:if>
            <g:renderDomain domain="${Entry.class}" 
                            template="list" 
                            value="${entryInstanceList}" 
                            exceptWhen="${ { prop -> prop.type == Set.class } }"
                            style="[actions:[[show:'Show'], [edit: 'Edit']] ]" />
            <div class="paginateButtons">
                <g:paginate total="${Entry.count()}" />
            </div>
            <textarea class="codeShare"><g:showGSP view="list"/></textarea>
        </div>
    </body>
</html>
