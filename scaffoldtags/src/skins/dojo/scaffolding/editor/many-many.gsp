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
    <g:else>
        Add to ${prop.naturalName}:
        <g:select optionKey="id"
            from="${referencedDomain.list()}"
            name='${name}'
            noSelection="['': '---']" />
    </g:else>
</g:else>