/* Copyright 2007-2009 Daiji Takamori
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

/**
 * Simplifies and standardizes code in Grails views
 *
 * @author Daiji Takamori
 * @since 08-Mar-2007
 */
class ScaffoldTagLib {
    private static final VERSION = "0.7.4"
    private static final Log LOG = LogFactory.getLog(ScaffoldTagLib)
    private static String PATH_TO_VIEWS = "/WEB-INF/grails-app/views"
    private static String PLUGIN_PATH_TO_VIEWS = "/WEB-INF/plugins/scaffold-tags-${VERSION}/grails-app/views"
    private static boolean initPaths = false

    /** Primitive class translation map */
    private static final classTranslations = [(Boolean.TYPE): Boolean,
                                              (Character.TYPE): Character,
                                              (Void.TYPE): Void,
                                              (Byte.TYPE): Byte,
                                              (Short.TYPE): Short,
                                              (Integer.TYPE): Integer,
                                              (Long.TYPE): Long,
                                              (Float.TYPE): Float,
                                              (Double.TYPE): Double ]

	/** Reference to the MetaClassRegistry */
    private static final MetaClassRegistry metaClassRegistry = GroovySystem.getMetaClassRegistry()

    /**
     *  performs looping across the properties of a domain. Examples:
     *
     *  <g:eachDomainProperty domain="User">${it.descriptor.name}</g:eachDomainProperty>
     *  <g:eachDomainProperty domain="User" value="${u}" except="${it.name = 'password'}">
     *      ${it.descriptor.name}: ${it.value}
     *  </g:eachDomainProperty>
     *  <g:eachDomainProperty domain="User" value="${u}" except="${it.name = 'password'}">
     *      <g:renderProperty prop="${it}" />
     *  </g:eachDomainProperty>
     */
    def eachDomainProperty = { attrs, body ->
        def domain = attrs.domain
        if (!domain) {
            throwTagError("Tag [eachDomainProperty] is missing required attribute [domain]")
        }
        def name = attrs.name // for inputs
        def except = attrs.except // to skip certain properties
        def exceptWhen = attrs.exceptWhen // to skip properties with certain conditions
        def only = attrs.only // to only display specific properties
        def value = attrs.value // for edit/show; not always create
        def widgets = attrs.widgets
        def style = attrs.style
        def order = attrs.order

        // Lookup domain by name if necessary
        def application = grailsAttributes.getGrailsApplication()
        def domainClass
        if (domain instanceof Class) {
            domainClass = application.getDomainClass(domain.name)
        } else {
            try {
                domainClass = application.getDomainClass(domain)
                domain = domainClass.clazz
            } catch (ClassNotFoundException e) {
                throwTagError("Tag [eachDomainProperty] could not find class ${domain}")
            }
        }

        def constraints = domainClass.constrainedProperties
        def props
        if (only) {
            props = domainClass.properties.findAll { only.contains(it.name) }
            if (!order) {
                order = only
            }
        } else {
            props = domainClass.properties.findAll {
                String propName = it.name
                return (propName != 'version' &&
                        (!constraints[propName] || constraints[propName]?.display) &&
                        (!except || !except.contains(propName)) &&
                        (!exceptWhen || !exceptWhen(it)))
            }
        }
        props = props.sort { o1, o2 ->
            String name1 = o1.name
            String name2 = o2.name
            if (name1 == "id") {
                return -1
            } else if (name2 == "id") {
                return 1
            } else {
                if (order) {
                    int i1 = order.indexOf(name1)
                    int i2 = order.indexOf(name2)
                    if (i1 != -1 && i2 != -1) {
                        if (i1 < i2) {
                            return -1
                        } else if (i1 > i2) {
                            return 1
                        }
                    } else if (i1 != -1) {
                        return -1
                    } else if (i2 != -1) {
                        return 1
                    }
                }
                return name1.compareTo(name2)
            }
        }
        props.each { prop ->
            def item = new DomainProperty()
            item.prop = prop
            item.name = name ? "${name}.${prop.name}" : prop.name
            item.value = value ? value[prop.name] : null
            item.style = style ? style[prop.name] : null
            item.widget = widgets ? widgets[prop.name] : null
            def bodyOut = body(item)
            if (bodyOut) out << bodyOut
        }
    }

    /**
     *  renders a domain using customized views. The search path is: (1) the current view directory;
     *  (2) a view directory for the domain class; (3) a generic view under the _domain directory.
     *
     *  Examples:
     *
     *  <g:renderDomain template="describe" domain="${User}" />
     *  <g:renderDomain template="show" domain="${User}" value="${u}" />
     *
     * It first searches for the template {template}/{domainClassName}.gsp
     * If not found, it defaults to the template {template}/domain.gsp
     */
    def renderDomain = { attrs ->
        def domain = attrs.domain
        if (!domain) {
            throwTagError("Tag [renderDomain] is missing required attribute [domain]")
        }
        def template = attrs.template
        if (!template) {
            throwTagError("Tag [renderDomain] is missing required attribute [template]")
        }
        def name = attrs.name
        def value = attrs.value
        def widgets = attrs.widgets
        def widget = attrs.widget
        def style = attrs.style
        def except = attrs.except
        def exceptWhen = attrs.exceptWhen
        def only = attrs.only
        def order = attrs.order

        // Lookup domain by name if necessary
        def application = grailsAttributes.getGrailsApplication()
        if (!(domain instanceof Class)) {
            try {
                def domainClass = application.getDomainClass(domain)
                domain = domainClass.clazz
            } catch (ClassNotFoundException e) {
                throwTagError("Tag [renderDomain] could not find class ${domain}")
            }
        }

        def beanName = domain.name
        String suffix = ""
        if (widget) {
            suffix = "_${widget}"
        }
        def uri = findView("${template}/${beanName}${suffix}.gsp")
        if (!uri) {
            uri = findView("${template}/domain${suffix}.gsp")
        }
        if (!uri) {
            throwTagError("No template ${template} found for domain ${beanName} in tag [renderDomain]")
        }

        def binding = [ domain: domain,
                        name: name,
                        value: value,
                        widgets: widgets,
                        style: style,
                        except: except,
                        exceptWhen: exceptWhen,
                        only: only,
                        order: order ]
        executeTemplate(uri, binding)
    }

    /**
     *  renders a domain property using customized views. Examples:
     *
     *  <g:renderProperty template="describe" domain="${User}" prop="p" />
     *  <g:renderProperty template="show" domain="${User}" value="${u}" />
     *
     * It first tries to use the template {template}/{domain}.{propertyName}.gsp
     * otherwise it invokes either g:renderRelation or g:renderType, as appropriate
     */
    def renderProperty = { attrs ->
        def template = attrs.template
        if (!template) {
            throwTagError("Tag [renderProperty] is missing required attribute [template]")
        }
        def prop = attrs.prop
        if (!prop) {
            throwTagError("Tag [renderProperty] is missing required attribute [prop]")
        }
        def domain = attrs.domain // for a search path
        def name = attrs.name // for inputs
        def value = attrs.value // for edit/show; not always create
        def style = attrs.style
        def domainId = attrs.domainId
        def widget = attrs.widget
        def widgets = attrs.widgets

        // Lookup property by name if necessary
        if (prop instanceof CharSequence) {
            if (!domain) {
                throwTagError("Tag [renderProperty] is missing required attribute [domain] when prop is a string")
            }
            def application = grailsAttributes.getGrailsApplication()
            def domainClass
            if (domain instanceof Class) {
                domainClass = application.getDomainClass(domain.name)
            } else {
                try {
                    domainClass = application.getDomainClass(domain)
                    domain = domainClass.clazz
                } catch (ClassNotFoundException e) {
                    throwTagError("Tag [renderProperty] could not find class ${domain}")
                }
            }
            prop = domainClass.getPropertyByName(prop)
        }

        // Obtain information about the property
        def domainClass = prop.domainClass
        def beanName = domainClass.propertyName
        def type = prop.type
        def propertyName = prop.name
        String suffix = ""
        if (widget) {
            suffix = "_${widget}"
        }

         // Execute the appropriate template for this property
        def uri
        if (domainClass) {
            uri = findView("${template}/${domainClass.name}.${propertyName}${suffix}.gsp")
        }
        while (!uri && prop.inherited) {
            domain = domain.superclass
            def application = grailsAttributes.getGrailsApplication()
            domainClass = application.getDomainClass(domain.name)
            prop = domainClass.getPropertyByName(propertyName)
            uri = findView("${template}/${domainClass.name}.${propertyName}${suffix}.gsp")
        }
        if (uri) {
            // Execute a property-specific template
            def constraints = domainClass.constrainedProperties[propertyName]
            def referencedDomain = prop.referencedDomainClass
            def binding = [ domain: domain,
                            name: name,
                            prop: prop,
                            domainId: domainId,
                            type: type,
                            value: value,
                            widgets: widgets,
                            style: style,
                            constraints: constraints,
                            referencedDomain: referencedDomain?.propertyName,
                            referencedDomainName: referencedDomain?.shortName ]
            executeTemplate(uri, binding)
        } else if (prop.association) {
            // Execute a relation template
            def tagOut = renderRelation([template: template,
                            domain: domain,
                            name: name,
                            prop: prop,
                            domainId: domainId,
                            value: value,
                            widget: widget,
                            widgets: widgets,
                            style: style])
            if (tagOut) out << tagOut
        } else {
            // Execute a type template
            def constraints = domainClass.constrainedProperties[prop.name]
            def tagOut = renderType([template: template,
                        domain: domain,
                        name: name,
                        type: type,
                        value: value,
                        constraints: constraints,
                        widget: widget,
                        widgets: widgets,
                        style: style])
            if (tagOut) out << tagOut
        }
    }

    /**
     *  renders a domain relation property using customized views. Examples:
     *
     *  <g:renderRelation template="edit" domain="${User}" propName="friends" />  - not supported yet
     *  <g:renderRelation template="show" domain="${User}" prop="${prop}" value="${u}" />
     *
     * If possible, it uses the template {template}/${relation}_{type}.gsp
     *  where relation is either "one" (special-cased as one-one or many-one)
     *  or "many" (special-cased as one-many and many-many)
        and type is a classname found in the property's class inheritance tree
     * If that's not found, it defaults to the template {template}/${relation}.gsp
     */
    def renderRelation = { attrs ->
        def template = attrs.template
        if (!template) {
            throwTagError("Tag [renderRelation] is missing required attribute [template]")
        }
        def prop = attrs.prop
        if (!prop) {
            throwTagError("Tag [renderRelation] is missing required attribute [prop]")
        }
        def domain = attrs.domain // for a search path
        def name = attrs.name // for inputs
        def value = attrs.value // for edit/show; not always create
        def style = attrs.style // in case there's any style information to pass along
        def domainId = attrs.domainId
        def widget = attrs.widget
        def widgets = attrs.widgets

        // Lookup property by name if necessary
        if (prop instanceof CharSequence) {
            if (!domain) {
                throwTagError("Tag [renderRelation] is missing required attribute [domain] when prop is a string")
            }
            def application = grailsAttributes.getGrailsApplication()
            def domainClass
            if (domain instanceof Class) {
                domainClass = application.getDomainClass(domain.name)
            } else {
                try {
                    domainClass = application.getDomainClass(domain)
                    domain = domainClass.clazz
                } catch (ClassNotFoundException e) {
                    throwTagError("Tag [renderRelation] could not find class ${domain}")
                }
            }
            prop = domainClass.getPropertyByName(prop)
        }

        // Obtain information about the property
        def domainClass = prop.domainClass
        def propertyName = prop.name
        Class clazz = prop.referencedPropertyType
        String relationType = "none"
        String simpleRelationType = "none"
        if (prop.oneToOne) {
            relationType = "one-one"
            simpleRelationType = "one"
        } else if (prop.manyToOne) {
            relationType = "many-one"
            simpleRelationType = "one"
        } else if (prop.oneToMany) {
            relationType = "one-many"
            simpleRelationType = "many"
        } else if (prop.manyToMany) {
            relationType = "many-many"
            simpleRelationType = "many"
        }
        String suffix = ""
        if (widget) {
            suffix = "_${widget}"
        }

        // Locate the template
        def domainName = domainClass?.propertyName
        def uri = findUriForType(template,
                				["${relationType}_", "${simpleRelationType}_"],
                				clazz,
                				suffix)
        if (!uri) {
            uri = findView(["${template}/${relationType}${suffix}.gsp",
                            "${template}/${simpleRelationType}${suffix}.gsp"])
        }
        if (!uri) {
            throwTagError("No template ${template} found for property ${propertyName} in domain ${domainName} in tag [renderRelation]")
        }

        // Execute it
        def constraints = domainClass.constrainedProperties[propertyName]
        def referencedDomain = prop.referencedDomainClass
        def backReference = referencedDomain.properties.find {
            (it.type == domainClass?.clazz)
        }?.name
        if (!backReference) {
            backReference = domainClass.propertyName
        }
        def binding = [ name: name,
                        prop: prop,
                        referencedDomain: clazz,
                        referencedController: referencedDomain.propertyName,
                        domainReference: backReference,
                        domainId: domainId,
                        value: value,
                        constraints: constraints,
                        widgets: widgets,
                        style: style ]
        executeTemplate(uri, binding)
    }

    /** [Placeholder for future development]
     * renders a bean using customized views.  This is like renderType except that it supports
     * introspection into bean properties.  This is also like renderDomain except that it supports
     * non-domain objects.
     * One consideration is whether to implement the 'embedded' style for java.lang.Object
     * and to have this be a convenience tag
     * /
    def renderBean = { attrs ->

    }*/

    /**
     * performs looping across the properties of a bean. This is like eachDomainProperty except that
     * it supports bean properties.
     * This is intended to be used by type renderers that represent a bean.
     */
    def eachProperty = { attrs, body ->
	    def type = attrs.type
	    if (!type) {
	        throwTagError("Tag [eachProperty] is missing required attribute [type]")
	    }
	    def name = attrs.name // for inputs
	    def except = attrs.except // to skip certain properties
	    def exceptWhen = attrs.exceptWhen // to skip properties with certain conditions
	    def only = attrs.only // to only display specific properties
	    def value = attrs.value // for edit/show; not always create
	    def widgets = attrs.widgets // custom views
	    def style = attrs.style // custom view attributes
	    def order = attrs.order

	    // Lookup domain by name if necessary
	    if (!(type instanceof Class)) {
	        try {
	            type = Class.forName(type)
	        } catch (ClassNotFoundException e) {
	            throwTagError("Tag [eachProperty] could not find class ${type}")
	        }
	    }

	    def application = grailsAttributes.getGrailsApplication()
	    def metaClass = metaClassRegistry.getMetaClass(type)
	    def props // = metaClass.properties.collect { k, v -> k }
	    if (only) {
	        props = metaClass.properties.findAll { prop -> only.contains( prop.name) }
	        if (!order) {
	            order = only
	        }
	    } else {
	        props = metaClass.properties.findAll { prop ->
	            String propName = prop.name
	            return ((!except || !except.contains(propName)) &&
	                    (!exceptWhen || !exceptWhen(prop)))
	        }
	    }
	    props = props.sort { o1, o2 ->
	        String name1 = o1.name
	        String name2 = o2.name
	        if (name1 == "id") {
	            return -1
	        } else if (name2 == "id") {
	            return 1
	        } else {
	            if (order) {
	                int i1 = order.indexOf(name1)
	                int i2 = order.indexOf(name2)
	                if (i1 != -1 && i2 != -1) {
	                    if (i1 < i2) {
	                        return -1
	                    } else if (i1 > i2) {
	                        return 1
	                    }
	                } else if (i1 != -1) {
	                    return -1
	                } else if (i2 != -1) {
	                    return 1
	                }
	            }
	            return name1.compareTo(name2)
	        }
	    }
	    props.each { prop ->
	        def item = [:]
	        item.prop = prop
	        item.name = name ? "${name}.${prop.name}" : prop.name
	        item.value = value ? value[prop.name] : null
	        item.style = style ? style[prop.name] : null
            item.widget = widgets ? widgets[prop.name] : null
	        def bodyOut = body(item)
            if (bodyOut) out << bodyOut
	    }
    }

    /**
     *  renders a data type using customized views. Examples:
     *
     *  <g:renderType template="describe" type="SomeClass" />
     *  <g:renderType template="show" type="SomeBean" value="${bean}" />
     *
     * It uses the template {template}/{type}.gsp
     * where type is either the classname or "array_{classname}" when an array of a given type
     * and where the type is looked up directly or through the superclasses hierarchy
     */
    def renderType = { attrs ->
        def template = attrs.template
        if (!template) {
            throwTagError("Tag [renderType] is missing required attribute [template]")
        }
        def type = attrs.type
        def name = attrs.name // for inputs
        def value = attrs.value // for edit/show; not always create
        if (!type) {
            if (!value) {
            	throwTagError("Tag [renderType] is missing required attribute [type] when [value] not specified")
            }
            type = value
        }
        def constraints = attrs.constraints // sometimes useful
        def widget = attrs.widget // for custom views
        def widgets = attrs.widgets // for custom views of fields when an embedded object
        def style = attrs.style // for custom view attributes
        if (type instanceof CharSequence) {
            type = Class.forName(type)
        }

        // Obtain info about the type
        Class clazz = type
        String prefix = ""
        if (clazz.isArray()) {
            clazz = clazz.componentType
            prefix = "array_"
        }
        if (clazz.isPrimitive()) {
            clazz = classTranslations[(clazz)]
        }
        String suffix = ""
        if (widget) {
            suffix = "_${widget}"
        }

        // Locate the template
        def uri = findUriForType(template, prefix, clazz, suffix)
        if (!uri) {
             throwTagError("No template ${template} found for type ${type} in tag [renderType]")
         }
//println "using ${uri} for ${name} ${type}"
        // Execute it
        def binding = [ name: name,
                        type: type,
                        value: value,
                        constraints: constraints,
                        widgets: widgets,
                        style: style ]
        executeTemplate(uri, binding)
    }

    def renderActions = { attrs ->
	    def template = attrs.template
	    if (!template) {
	        throwTagError("Tag [renderActions] is missing required attribute [template]")
	    }
	    def actions = attrs.actions
	    if (!actions) {
	        throwTagError("Tag [renderActions] is missing required attribute [actions]")
	    }
	    def controller = attrs.controller
	    def item = attrs.item
	    def widget = attrs.widget // for custom views
	    def style = attrs.style // for custom view attributes
	    actions.each { actionMapping ->
	        actionMapping.each { k, v ->
	        	def tagOut = renderAction([template: template,
	        	              action: k,
	        	              label: v,
	        	              controller: controller,
	        	              item: item,
	        	              widget: widget,
	        	              style: style], {} )
	            if (tagOut) out << tagOut
	        }
	    }
    }

    def renderAction = { attrs, body ->
	    def template = attrs.template
	    if (!template) {
	        throwTagError("Tag [renderAction] is missing required attribute [template]")
	    }
	    def action = attrs.action
	    if (!action) {
	        throwTagError("Tag [renderAction] is missing required attribute [action]")
	    }
	    def label = attrs.label
	    def controller = attrs.controller
	    def item = attrs.item
	    def widget = attrs.widget // for custom views
	    def style = attrs.style // for custom view attributes

	    String suffix = ""
	    if (widget) {
	        suffix = "_${widget}"
	    }

	    // Locate the template
	    def uri = findView("${template}/action${suffix}.gsp")
	    if (!uri) {
		    uri = findView("action${suffix}.gsp")
	    }
	    if (!uri) {
	         throwTagError("No action renderer found for template ${template} in tag [renderAction]")
	    }
	    // Execute it
	    def binding = [ controller: controller,
	                    action: action,
	                    label: label,
	                    item: item,
	                    style: style,
	                    body: body ]
	    executeTemplate(uri, binding)
	}

    /**
     * Searches for a template through the class inheritance tree.
     * Either a single prefix can be passed or multiple prefixes can be passed.
     * At the moment, only a single suffix can be passed.
     * The search path for each given class searched is:
     *        {controllerPath}/{viewType}/{prefix}{classname}${suffix}.gsp
     *        /scaffolding/{viewType}/{prefix}{classname}${suffix}.gsp
     *        {pluginPath}/scaffolding/{viewType}/{prefix}{classname}${suffix}.gsp
     * If the template is not found, null is returned.
     */
    private String findUriForType( viewType,  prefix,  clazz,  suffix) {
        String uri
        while (!uri && clazz) {
            uri = findUriForExactClass(viewType, prefix, clazz, suffix)
            if (!uri) {
//				println("Seeing interfaces ${clazz.interfaces?.name} for class ${clazz.name}")
                for (i in clazz.interfaces) {
                    uri = findUriForExactClass(viewType, prefix, i, suffix)
                    if (uri) {
                        break
                    }
                }
            }
            if (!uri) {
                clazz = clazz.superclass
            }
        }
        return uri
    }

    private String findUriForExactClass( viewType,  prefix,  clazz,  suffix) {
        String uri
        if (prefix instanceof CharSequence) {
            uri = findView("${viewType}/${prefix}${clazz.name}${suffix}.gsp")
        } else {
            for (p in prefix) {
                uri = findView("${viewType}/${p}${clazz.name}${suffix}.gsp")
                if (uri) {
                    break
                }
            }
        }
        return uri
    }


    /**
     * Returns the URI of the first template it can find
     * from either the standard views or plugin views.
     * Either a single view can be passed or multiple views can be passed
     *
     * The search path for a view is:
     *        {controllerPath}/{view}
     *        /scaffolding/{view}
     *        {pluginPath}/scaffolding/{view}
     */
   private findView(view) {
	   // There needs to be a better way to do the path lookup
	   String controllerUri = grailsAttributes.getControllerUri(request)

	   def ctx = grailsAttributes.applicationContext
	   def resourceLoader = ctx.containsBean('groovyPageResourceLoader') ? ctx.groovyPageResourceLoader : ctx

	   if (!initPaths) {
	       if (!resourceLoader.getResource("${PATH_TO_VIEWS}/error.gsp").exists())
		   {
			   PATH_TO_VIEWS = PATH_TO_VIEWS.substring(
				   PATH_TO_VIEWS.indexOf('/', 1) + 1)
			   PLUGIN_PATH_TO_VIEWS = PLUGIN_PATH_TO_VIEWS.substring(
				   PLUGIN_PATH_TO_VIEWS.indexOf('/', 1) + 1)
			   if (!resourceLoader.getResource("${PLUGIN_PATH_TO_VIEWS}/scaffolding/editor/domain.gsp").exists()) {
				   PLUGIN_PATH_TO_VIEWS = org.codehaus.groovy.grails.plugins.GrailsPluginUtils.pluginInfos.find { it.name == "scaffold-tags" }?.pluginDir.toString() + "/grails-app/views"
				   PLUGIN_PATH_TO_VIEWS = "file:" + PLUGIN_PATH_TO_VIEWS.replace("/./", "/")
			   }
		   }
           initPaths = true
	   }
	   def viewpaths = ["${PATH_TO_VIEWS}${controllerUri}",
						"${PATH_TO_VIEWS}/scaffolding",
						"${PLUGIN_PATH_TO_VIEWS}/scaffolding"]

        for (p in viewpaths) {
            if (view instanceof CharSequence) {
                def uri = "${p}/${view}"
                def resource = resourceLoader.getResource(uri)
                if (resource && resource.file && resource.file.exists()) {
                    // println "found-1 in ${uri} at ${resource}"
                    return uri
                }
            } else {
                for (v in view) {
                    def uri = "${p}/${v}"
					// println "searching for ${uri}"
                    def resource = resourceLoader.getResource(uri)
                    if (resource && resource.file && resource.file.exists()) {
                        // println "found-2 in ${uri} at ${resource}"
                        return uri
                    }
                }
            }
        }
		// println "none found"
        return null
    }

    /**
     * Convenience closure.
     * Executes a template located at a specific URI with the specified binding
     */
    private void executeTemplate(uri, binding) {
        def engine = grailsAttributes.getPagesTemplateEngine()
        engine.createTemplate(uri).make(binding).writeTo(out)
    }
}
