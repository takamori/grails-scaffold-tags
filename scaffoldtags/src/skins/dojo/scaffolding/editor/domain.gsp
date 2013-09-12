<div class="dialog">
<table>
    <tbody>
        <g:eachDomainProperty domain="${domain}" name="${name}" value="${value}"
                              widgets="${widgets}" style="${style}"
                              except="${except}" exceptWhen="${exceptWhen}"
                              only="${only}"
                              order="${order}">
            <g:if test="${it.style?.hidden}">
                <g:renderProperty template="editor"
                                  domain="${domain}" domainId="${value?.id}"
                                  prop="${it.prop}" value="${it.value}"
                                  name="${it.name}"
                                  widget="${it.widget}" style="${it.style}"/>
            </g:if>
            <g:else>
                <tr class='prop'>
                <th class='name'><label for='${it.prop.name}'>${it.prop.naturalName}:</label></th>
                <td class='value ${hasErrors(bean:value,field:it.prop.name,'errors')}'>
                <g:renderProperty template="editor"
                                  domain="${domain}" domainId="${value?.id}"
                                  prop="${it.prop}" value="${it.value}"
                                  name="${it.name}"
                                  widget="${it.widget}" style="${it.style}"/>
                </td></tr>
            </g:else>
        </g:eachDomainProperty>
    </tbody>
</table>
    <g:if test="${style?.actions}">
        <div class="buttons">
            <g:renderActions template="editor" actions="${style.actions}" item="${value}" />
        </div>
    </g:if>
</div>
