<div class="listScrollWindow">
<table class="list">
    <thead>
        <tr>
            <th class="actionButtons"></th>
            <g:eachDomainProperty domain="${domain}" name="${name}"
                                  except="${except}" exceptWhen="${exceptWhen}"
                                  only="${only}"
                                  order="${order}">
                <th class="property">${it.prop.naturalName}</th>
            </g:eachDomainProperty>
        </tr>
    </thead>
    <tbody>
        <g:each var="item" in="${value}">
            <tr>
                <td class="actionButtons">
                    <g:if test="${style?.actions}">
                        <g:renderActions template="list" actions="${style.actions}" item="${item}" />
                    </g:if><g:else>
                        <g:renderAction template="list" action="show" label="Show" item="${item}" />
                    </g:else>
                </td>
                <g:eachDomainProperty domain="${domain}" value="${item}"
                                      widgets="${widgets}" style="${style}"
                                      except="${except}" exceptWhen="${exceptWhen}"
                                      only="${only}"
                                      order="${order}">
                    <td class="property"><%
                        %><g:if test="${it.style?.link}"><g:def var="p" value="${it}"/><%
                            %><g:link action="${p.style.link.action}" id="${item.id}"><%
                                %><g:renderProperty template="list.item"
                                                  domain="${domain}" domainId="${item?.id}"
                                                  prop="${p.prop}" value="${p.value}"
                                                  name="${p.name}"
                                                  widget="${p.widget}" style="${p.style}"/><%
                            %></g:link><%
                        %></g:if>
                        <g:else><%
                            %><g:renderProperty template="list.item"
                                              domain="${domain}" domainId="${item?.id}"
                                              prop="${it.prop}" value="${it.value}"
                                              name="${it.name}"
                                              widget="${it.widget}" style="${it.style}"/><%
                        %></g:else><%
                    %></td>
                </g:eachDomainProperty>
            </tr>
        </g:each>
    </tbody>
</table>
</div>