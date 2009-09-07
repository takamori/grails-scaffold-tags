<html>
    <head>
        <title>Welcome to Grails</title>
        <meta name="layout" content="dojo" />
    </head>
    <body>
        <div class="body">
            <div class="titleSection">
        <h1 class="title">Welcome to Grails</h1>
            </div>
        <p style="margin-left:20px;width:80%">Congratulations, you have successfully started your first Grails application! At the moment
        this is the default page, feel free to modify it to either redirect to a controller or display whatever
        content you may choose. Below is a list of controllers that are currently deployed in this application,
        click on each to execute its default action:</p>
        <div class="dialog">
            <ul>
              <g:each var="c" in="${grailsApplication.controllerClasses}">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link> </li>
              </g:each>
            </ul>
        </div>
        </div>
    </body>
</html>