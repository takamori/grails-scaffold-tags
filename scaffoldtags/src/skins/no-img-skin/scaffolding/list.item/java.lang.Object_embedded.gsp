<table class="listItem">
    <thead>
        <tr>
            <g:if test="${style?.actions}">
                <th class="actionButtons"></th>
            </g:if>
            <g:eachProperty type="${type}" name="${name}"
                                  except="${['class','metaClass','properties']}" >
                <th class="property">${it.prop.name}</th>
            </g:eachProperty>
        </tr>
    </thead>
    <tbody>
        <g:each var="item" in="${value}">
            <tr>
                <g:if test="${style?.actions}">
                    <td class="actionButtons">
                        <g:each var="action" in="${style.actions}">
                            <g:renderAction template="list.item" action="${action.key}" item="${item}">${action.value}</g:renderAction>
                        </g:each>
                    </td>
                </g:if>
                <g:eachProperty type="${type}" name="${name}" value="${value}"
                                constraints="${constraints}"
                                widgets="${widgets}" style="${style}"
                                except="['class','metaClass','properties']">
                    <td class="property">
                        <g:renderType type="${it.prop.type}" template="list"
                                      name="${it.name}" value="${it.value}"
                                      widget="${it.widget}" style="${it.style}" />
                    </td>
                </g:eachProperty>
            </tr>
        </g:each>
    </tbody>
</table>