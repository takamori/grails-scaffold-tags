/* Copyright 2007 the original author or authors.
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

/**
 * A dynamic invocation tag lib that can be used to reduce API dependency 
 * introduced by use of GroovyPage.invokeTag() within GSP views.
 *
 * @author Daiji Takamori
 * @since 16-Mar-2007
 */
class InvokeTagLib {
    /**
     * Invokes a tag using specified params; useful when you want dynamic tags from a template
     * A body can either be specified as an attribute or as the nested body
     *
     * Example:
     * <g:invokeTag tag="datePicker" attrs="${attrs}"/>
     * <g:invokeTag tag="specialDisplayer" attrs="${attrs}" body="${content}"/>
     */
    def invokeTag = { attrs, body ->
    	def tag = attrs.tag
        if (!tag) {
            throwTagError("Tag [invokeTag] is missing required attribute [tag]")
        }
    	def tagAttrs = attrs.attrs
    	attrs.each {k, v ->
    		if (!["attrs", "body"].contains(k)) {
    		    tagAttrs[k] = v
    		}
    	}
        if (body) {
            def tagOut = invokeTagHelper(tag, tagAttrs, body)
            if (tagOut) out << tagOut
        } else {
            def tagOut = invokeTagHelper(tag, tagAttrs, attrs.body)
            if (tagOut) out << tagOut
        }
    }
 
    /**
     * Convenience closure.
     * Executes a tag with the specified tag name and attributes, 
     * and nesting the body if supported
     */
    private invokeTagHelper = { tagName, attrs, body ->
        def tagLib = grailsAttributes.getTagLibraryForTag(request,response,tagName)
        if (!tagLib) {
            throwTagError("Tag [invokeTag] cannot find taglib for tag ${tagName}")
        } else {
            def tag = tagLib[tagName]
            if (tag instanceof Closure) {
                tag = tag.clone()
                if (tag.getParameterTypes().length == 1) {
                    def tagOut = tag( attrs )
		            if (tagOut) out << tagOut
                    if (body != null) {
                        def bodyOut = body()
                        if (bodyOut) out << bodyOut
                    }
                } else if (tag.getParameterTypes().length == 2) {
                    def tagOut = tag( attrs, body )
                    if (tagOut) out << tagOut
                } else {
                    throwTagError("Tag [invokeTag] cannot execute ${tagName}; not implemented as a tag")
                }
            } else {
                throwTagError("Tag [invokeTag] cannot execute ${tagName}; not implemented as a tag")
            }
        }
    }

}