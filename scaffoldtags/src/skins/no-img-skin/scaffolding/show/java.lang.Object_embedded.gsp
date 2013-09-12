<div class="dialog">
<table>
    <tbody>
        <g:eachProperty type="${type}" name="${name}" value="${value}"
                        constraints="${constraints}"
                        widgets="${widgets}" style="${style}"
                        except="['class','metaClass','properties']">
            <tr class="prop">
                <td valign="top" class="name">${it.prop.name}:</td>
                <td valign="top" style="text-align:left;" class="value">
                    <g:renderType type="${it.prop.type}" template="show"
                                  name="${it.name}" value="${it.value}"
                                  widget="${it.widget}" style="${it.style}" />
                </td>
            </tr>
        </g:eachProperty>
    </tbody>
</table>
</div>