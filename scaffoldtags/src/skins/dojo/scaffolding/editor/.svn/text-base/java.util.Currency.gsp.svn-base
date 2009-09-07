<g:if test="${style?.hidden}">
<% /*
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />
*/ %>
</g:if>
<g:elseif test="${style?.readonly}">
<% /*
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />${value}
*/ %>
    ${value?.encodeAsHTML()}
</g:elseif>
<g:elseif test="${!style}">
    <g:currencySelect name='${name}' value="${value}"/>
</g:elseif>
<g:else>
    <g:invokeTag tag="currencySelect" attrs="${style}" name="${name}" value="${value}"/>
</g:else>