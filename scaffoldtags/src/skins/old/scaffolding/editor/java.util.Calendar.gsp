<g:if test="${!style}">
    <g:datePicker name='${name}' value="${value}" />
</g:if>
<g:elseif test="${style.readonly}">
    ${value?.toString()}
</g:elseif>
<g:else>
    <g:invokeTag tag="datePicker" attrs="${style}" name="${name}" value="${value}"/>
</g:else>