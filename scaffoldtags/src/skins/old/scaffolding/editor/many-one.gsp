<g:if test="${style?.hidden}">
    <input type="hidden"
           name='${name}.id'
           value="${value?.id}" />
</g:if>
<g:elseif test="${style?.readonly}">
    <input type="hidden"
           name='${name}.id'
           value="${value?.id}" />${value}
</g:elseif>
<g:else>
    <g:select optionKey="id"
              from="${referencedDomain.list()}"
              name='${name}.id'
              value="${value?.id}" />
</g:else>