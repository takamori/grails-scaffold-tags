<div class="dialog">
    <table>
        <tbody>
            <g:eachDomainProperty domain="${domain}" name="${name}" value="${value}"
                                  widgets="${widgets}" style="${style}"
                              	  except="${except}" exceptWhen="${exceptWhen}"
                                  only="${only}"
                                  order="${order}">
                <tr class="prop">
                    <th class="name">${it.prop.naturalName}:</th>
                    <td class="value">
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
    <g:if test="${style?.actions}">
        <div class="buttons">
            <g:form>
                <g:if test="${value.id}"><input type="hidden" name="id" value="${value.id}" /></g:if>
                <g:renderActions template="show" actions="${style.actions}" item="${value}" />
            </g:form>
        </div>
    </g:if>
</div>
