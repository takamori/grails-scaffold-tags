<table class="listItem">
    <tbody>
        <g:eachDomainProperty domain="${domain}" value="${value}"
                              widgets="${widgets}" style="${style}"
                              except="${except}" exceptWhen="${exceptWhen}"
                              only="${only}"
                              order="${order}">
            <tr>
                <th class="property">${it.prop.naturalName}</th>
                <td class="property"><g:renderProperty template="list"
                                      domain="${domain}" domainId="${value?.id}"
                                      prop="${it.prop}" value="${it.value}"
                                      name="${it.name}"
                                      widget="${it.widget}" style="${it.style}"/></td>
            </tr>
        </g:eachDomainProperty>
        <g:if test="${style?.actions}">
            <tr>
                <th class="actionButtons"></th>
                <td class="actionButtons">
                    <g:each var="action" in="${style.actions}">
                        <g:renderAction template="list.item" action="${action.key}" item="${value}">${action.value}</g:renderAction>
                    </g:each>
                </td>
            </tr>
        </g:if>
    </tbody>
</table>