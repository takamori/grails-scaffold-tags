<div class="dialog">
    <table>
        <tbody>
            <g:eachDomainProperty domain="${domain}" name="${name}" value="${value}"
                                  widgets="${widgets}" style="${style}"
                              	  except="${except}" exceptWhen="${exceptWhen}"
                                  only="${only}"
                                  order="${order}">
                <tr class="prop">
                    <td valign="top" class="name">${it.prop.naturalName}:</td>
                    <td valign="top" style="text-align:left;" class="value">
                    <g:renderProperty template="show"
                                      domain="${domain}" domainId="${value?.id}"
                                      prop="${it.prop}" value="${it.value}"
                                      name="${it.name}"
                                      widget="${it.widget}" style="${it.style}"/>
                    </td>
                </tr>
            </g:eachDomainProperty>
        </tbody>
    </table>
</div>
