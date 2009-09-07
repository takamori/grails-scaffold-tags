<g:if test="${!style}">
    <g:timeZoneSelect name='${name}' value="${value}" />
</g:if>
<g:else>
    <g:invokeTag tag="timeZoneSelect" attrs="${style}" name="${name}" value="${value}"/>
</g:else>