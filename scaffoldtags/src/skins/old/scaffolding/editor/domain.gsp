<div class="dialog">
<table>
    <tbody>
        <g:eachDomainProperty domain="${domain}" name="${name}" value="${value}"
                              widgets="${widgets}" style="${style}"
                              except="${except}" exceptWhen="${exceptWhen}"
                              only="${only}"
                              order="${order}">
            <tr class='prop'>
            <td valign='top' class='name'><label for='${it.prop.name}'>${it.prop.naturalName}:</label></td>
            <td valign='top' class='value ${hasErrors(bean:value,field:it.prop.name,'errors')}'>
                <g:renderProperty template="editor"
                                  domain="${domain}" domainId="${value?.id}"
                                  prop="${it.prop}" value="${it.value}"
                                  name="${it.name}"
                                  widget="${it.widget}" style="${it.style}"/>
            </td></tr>
        </g:eachDomainProperty>
    </tbody>
</table>
</div>

