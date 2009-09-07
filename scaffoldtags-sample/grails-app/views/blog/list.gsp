<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
         <meta name="layout" content="no-img-skin" />
         <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'sample.css')}" />
         <title>Blog List</title>
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
            <h1 class="title">Blog List</h1>
            <div class="pageActions">
            <span class="actionButton"><g:link action="create">Create</g:link></span></div>
            </div>
            <g:if test="${flash.message}">
                <div class="message">
                    ${flash.message}
                </div>
            </g:if>
            <g:renderDomain domain="${Blog.class}" 
                            template="list" 
                            value="${blogInstanceList}" 
                            order="['name','author']"
                            exceptWhen="${ { prop -> prop.type == Set.class } }"
                            style="[actions: [[show:'Show'], [edit: 'Edit'], [delete: 'Delete']],
                                    name: [link: [action: 'show']] ]" />
            <div class="paginateButtons">
                <g:paginate total="${Blog.count()}" />
            </div>
            <textarea class="codeShare"><g:showGSP view="list"/></textarea>
        </div>
    </body>
</html>
