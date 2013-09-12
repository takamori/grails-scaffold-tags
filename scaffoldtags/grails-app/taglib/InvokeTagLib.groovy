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

/**
 * A dynamic invocation tag lib that can be used to reduce API dependency
 * introduced by use of GroovyPage.invokeTag() within GSP views.
 *
 * @author Daiji Takamori
 * @since 16-Mar-2007
 */
class InvokeTagLib {
    /**
     * <p>
     * Invokes a tag using specified params; useful when you want dynamic tags from a template
     * A body can be specified as the nested body.   Attributes can either be specified directly
     * or in a map passed in as attrs (see examples below).
     * </p>
     *
     * <p>
     * Examples:
     * <pre>
     *  &lt;g:invokeTag tag="datePicker" name="fieldname" value="${value}" /&gt;
     *  &lt;g:invokeTag tag="datePicker" attrs="${attrs}" /&gt;
     *  &lt;g:invokeTag tag="specialDisplayer" attrs="${attrs}" &gt; ....[body]... &lt;/g:invokeTag&gt;
     * </pre>
     * </p>
     */
    def invokeTag = { attrs, body ->
        def tagName = attrs.tag
        if (!tagName) {
            throwTagError("Tag [invokeTag] is missing required attribute [tag]")
        }
        def tagAttrs = attrs.attrs ?: [:]
        attrs.each {k, v ->
            if (!["tag", "attrs"].contains(k)) {
                tagAttrs[k] = v
            }
        }

        // Identify and execute the tag
        def tagLib = grailsAttributes.getTagLibraryForTag(request,response,tagName)
        if (!tagLib) {
            throwTagError("Tag [invokeTag] cannot find taglib for tag ${tagName}")
        }

        def tag = tagLib[tagName]
        if (!(tag instanceof Closure)) {
            throwTagError("Tag [invokeTag] cannot execute ${tagName}; not implemented as a closure")
        }

        tag = tag.clone()
        if (tag.getParameterTypes().length == 1) {
            // Execute the tag with attributes, and ignore the body
            def tagOut = tag( tagAttrs )
            if (tagOut && !(tagOut instanceof PrintWriter)) out << tagOut
        } else if (tag.getParameterTypes().length == 2) {
            // Execute the tag with attributes, passing the nested body
            def tagOut = tag( tagAttrs, body )
            if (tagOut && !(tagOut instanceof PrintWriter)) out << tagOut
        } else {
            throwTagError("Tag [invokeTag] cannot execute ${tagName}; not implemented as a 1 or 2 parameter closure")
        }
    }
}
