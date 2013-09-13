<html>
	<head>
		<title><g:layoutTitle default="Grails Dojo Skin" /></title>
		<link rel="stylesheet" href="${createLinkTo(dir:'css',file:'dojo.css')}"></link>
		<g:layoutHead />
        <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
        <META HTTP-EQUIV="Expires" CONTENT="-1">
	</head>
	<body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${createLinkTo(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div class="page">
            <div class="pageinner">
                <div class="header">
                    <h1 class="logo">Dojo Skin</h1>
                    <span class="skinDescription">A skin using the Dojo Ajax toolkit</span>
                </div>
                <div class="nav">
                    <span class="menuButton"><a href="${createLinkTo(dir:'')}">Home</a></span>
                    <g:each var="c" in="${grailsApplication.controllerClasses}">
                        <span class="menuButton"><g:link controller="${c.logicalPropertyName}">${c.name}</g:link></span>
                    </g:each>
                </div>
        		<g:layoutBody />
                <div class="signature">
                    Powered by
                    <a href="http://grails.org"><img class="signatureButton" src="${createLinkTo(dir:'images',file:'grails_logo.jpg')}" alt="Grails" /></a>
                    and
                    <a href="http://dojotoolkit.org"><img class="signatureButton" src="${createLinkTo(dir:'images',file:'dojo_logo.jpg')}" alt="Dojo" /></a>
                </div>
            </div>
        </div>
	</body>
</html>
