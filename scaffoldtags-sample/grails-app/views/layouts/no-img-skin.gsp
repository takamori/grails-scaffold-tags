<html>
	<head>
		<title><g:layoutTitle default="Grails No-Images Skin" /></title>
		<link rel="stylesheet" href="${createLinkTo(dir:'css',file:'no-img-skin.css')}"></link>
		<g:layoutHead />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
        <META HTTP-EQUIV="Expires" CONTENT="-1">
	</head>
	<body>
        <div class="page">
            <div class="pageinner">
                <div class="header">
                    <h1 class="logo">No Images Skin</h1>
                    <span class="skinDescription">A skin using only HTML and CSS 
                    (except for the Grails 'powered-by' image at the bottom)</span>
                </div>
                <div class="nav">
                    <span class="menuButton"><a href="${createLinkTo(dir:'')}">Home</a></span>
                    <g:each var="c" in="${grailsApplication.controllerClasses}">
                        <span class="menuButton"><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></span>
                    </g:each>
                </div>
        		<g:layoutBody />
                <div class="signature">Powered by <a href="http://grails.org"><img class="signatureButton" src="${createLinkTo(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></a></div> 
            </div>
        </div>
	</body>	
</html>