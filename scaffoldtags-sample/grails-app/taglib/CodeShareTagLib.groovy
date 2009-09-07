/*
 * A taglib used in the sample app to display the GSP used in a given view.
 */
class CodeShareTagLib {
    def showGSP = { attrs ->
        def url = new StringBuffer()
        if(attrs["path"]) {
            url << '/' << attrs["path"]
        }
	    if(attrs["controller"]) {
	        url << '/' << attrs["controller"]
	    } else {
	        url << grailsAttributes.getControllerUri(request)
	    }
	    if(attrs["view"]) {
	        url << '/' << attrs["view"]
	    }
	    if (!url) throwTagError("Invalid path for tag [showGSP]")
	    def resourceURL = "${grailsAttributes.PATH_TO_VIEWS}${url}.gsp"
	    def ctx = grailsAttributes.applicationContext
	    def resourceLoader = ctx.containsBean('groovyPageResourceLoader') ? ctx.groovyPageResourceLoader : ctx
	    def resource = resourceLoader.getResource(resourceURL)

	    if (resource && resource.file && resource.file.exists()) {
	        out << resource.getURL().text.encodeAsHTML()
	    } else {
	        throwTagError("Tag [showGSP] unable to find resource ${resourceURL} specified by ${attrs}")
	    }
    }
}