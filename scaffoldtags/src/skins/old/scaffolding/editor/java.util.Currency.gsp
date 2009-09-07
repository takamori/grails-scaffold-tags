<g:if test="${!style}">
    <g:currencySelect name='${name}' value="${value}"/>
</g:if>
<g:else>
    <g:invokeTag tag="currencySelect" attrs="${style}" name="${name}" value="${value}"/>
</g:else>