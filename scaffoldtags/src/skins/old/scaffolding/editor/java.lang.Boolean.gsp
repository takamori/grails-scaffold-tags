<g:if test="${!style}" >
    <g:checkBox name='${name}' value="${value}"></g:checkBox>
</g:if>
<g:else>
    <g:invokeTag tag="checkBox" attrs="${style}" name="${name}" value="${value}"/>
</g:else>