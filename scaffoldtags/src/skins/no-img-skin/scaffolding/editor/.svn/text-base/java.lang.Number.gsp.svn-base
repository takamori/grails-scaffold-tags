<g:if test="${style?.hidden}">
    <input type='hidden' name='${name}' value="${value}" />
</g:if>
<g:elseif test="${style?.readonly}">
    <input type='hidden' name='${name}' value="${value}" />${value}
</g:elseif>
<g:elseif test="${!constraints}">
    <g:if test="${type == Byte.class}">
        <g:select from='${-128..127}' name='${name}' value="${value}" />
    </g:if>
    <g:else>
        <input type='text' name='${name}' value="${value}" />
    </g:else>
</g:elseif>
<g:else>
    <g:if test="${constraints.range}">
        <g:select from='${constraints.range.from .. constraints.range.to}' name='${name}' value="${value}" />
    </g:if>
    <g:elseif test="${constraints.size}">
        <g:select from='${constraints.size.from .. constraints.size.to}' name='${name}' value="${value}" />
    </g:elseif>
    <g:else>
        <input type='text' name='${name}' value="${value}" />
    </g:else>
</g:else>