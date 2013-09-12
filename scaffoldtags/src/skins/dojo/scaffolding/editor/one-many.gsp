<g:if test="${style?.hidden}">
    <g:each var='item' in='${value?}'>
        <input type="hidden"
            name='${name}'
            value="${value?.id}" />
    </g:each>
</g:if>
<g:else>
    <ul>
        <g:each var='item' in='${value?}'>
            <li>
                <g:link controller='${referencedController}' action='show' id='${item.id}'>${item}</g:link>
                <input type="hidden"
                    name='${name}'
                    value="${item?.id}" />
            </li>
        </g:each>
    </ul>
    <g:if test="${style?.readonly}" />
    <g:elseif test="${domainId}">
       <g:link controller='${referencedController}' params='["${domainReference}.id":domainId]' action='create'>Add ${referencedDomain.name}</g:link>
    </g:elseif>
</g:else>