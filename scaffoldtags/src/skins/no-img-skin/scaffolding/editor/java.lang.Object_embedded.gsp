<div class="dialog">
<table>
    <tbody>
        <g:eachProperty type="${type}" name="${name}" value="${value}"
                        constraints="${constraints}"
                        widgets="${widgets}" style="${style}"
                        except="['class','metaClass','properties']">
            <tr class="prop">
                <td valign='top' class='name'><label for='${it.prop.name}'>${it.prop.name}:</label></td>
                <td valign='top' class='value ${hasErrors(bean:value,field:it.prop.name,'errors')}'>
                    <g:renderType type="${it.prop.type}" template="editor"
                                  name="${it.name}" value="${it.value}"
                                  widget="${it.widget}" style="${it.style}" />
                </td>
            </tr>
        </g:eachProperty>
    </tbody>
</table>
</div>
