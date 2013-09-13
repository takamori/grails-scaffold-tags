<table>
    <thead>
        <tr>
            <g:eachDomainProperty domain="${domain}" name="${name}"
                                  except="${except}" exceptWhen="${exceptWhen}"
                                  only="${only}"
                                  order="${order}">
                <th>${it.prop.naturalName}</th>
            </g:eachDomainProperty>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <g:each var="item" in="${value}">
            <tr>
                <g:eachDomainProperty domain="${domain}" value="${item}"
                                      widgets="${widgets}" style="${style}"
                                      except="${except}" exceptWhen="${exceptWhen}"
                                      only="${only}"
                                      order="${order}">
                    <td><g:renderProperty template="list"
                                          domain="${domain}" domainId="${item?.id}"
                                          prop="${it.prop}" value="${it.value}"
                                          name="${it.name}"
                                          widget="${it.widget}" style="${it.style}"/></td>
                </g:eachDomainProperty>
                <td class="actionButtons">
                    <span class="actionButton"><g:link action="show" id="${item.id}">Show</g:link></span>
                </td>
            </tr>
        </g:each>
    </tbody>
</table>