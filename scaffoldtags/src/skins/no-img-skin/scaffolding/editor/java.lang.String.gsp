<g:if test="${style?.hidden}">
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />
</g:if>
<g:elseif test="${style?.readonly}">
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />${value}
</g:elseif>
<g:elseif test="${!constraints && !style}">
    <input type='text' name='${name}' value="${value?.encodeAsHTML()}" />
</g:elseif>
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
    %>
    <input ${attrStr} type="text" name='${name}' value="${value?.encodeAsHTML()}" />
</g:else>
