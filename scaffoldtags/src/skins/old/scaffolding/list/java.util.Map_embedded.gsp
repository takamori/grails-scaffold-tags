<table>
    <tbody>
        <g:each var="entry" status="i" in="${value}">
            <tr class="prop">
                <td>${entry.key}</td>
                <td>
                    <g:renderType template="list"
                                  name="${name + '.' + entry.key}" value="${entry.value}"
                                  widget="${widgets[entry.key]}" style="${style[entry.key]}" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>