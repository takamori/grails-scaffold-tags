<g:if test="${!constraints && !style}">
    <input type='text' name='${name}' value="${value?.encodeAsHTML()}" />
</g:if>
<g:elseif test="${constraints?.inList}">
    <g:select name='${name}'
        from='${constraints.inList.collect{it.encodeAsHTML()}}'
        value="${value?.encodeAsHTML()}" />
</g:elseif>
<g:else>
    <%
    attrs = [:]
    if (constraints?.maxSize) attrs.maxlength=constraints.maxSize
    attrStr = new StringBuilder()
    attrs.each { k, v ->
        attrStr << "${k}='${v}' "
    }
    if (style?.readonly) attrStr << "readonly "
    %>
    <input ${attrStr} type="text" name='${name}' value="${value?.encodeAsHTML()}" />
</g:else>
