<table>
    <thead>
        <tr>
            <g:eachProperty type="${type}" name="${name}"
                                  except="${['class','metaClass','properties']}" >
                <th>${it.prop.name}</th>
            </g:eachProperty>
        </tr>
    </thead>
    <tbody>
        <g:each var="item" in="${value}">
            <tr>
                <g:eachProperty type="${type}" name="${name}" value="${value}"
                                constraints="${constraints}"
                                widgets="${widgets}" style="${style}"
                                except="['class','metaClass','properties']">
                    <td valign="top" style="text-align:left;" class="value">
                        <g:renderType type="${it.prop.type}" template="list"
                                      name="${it.name}" value="${it.value}"
                                      widget="${it.widget}" style="${it.style}" />
                    </td>
                </g:eachProperty>
            </tr>
        </g:each>
    </tbody>
</table>