<g:if test="${style?.hidden}">
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />
</g:if>
<g:elseif test="${style?.readonly}">
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />${value}
</g:elseif>
<g:else>
    <input maxlength=1 name='${name}' value="${value?.encodeAsHTML()}" />
</g:else>