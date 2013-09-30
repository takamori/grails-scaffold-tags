<table class="listItem">
    <tbody>
        <g:each var="entry" status="i" in="${value}">
            <tr class="mapEntry">
                <td class="mapEntryKey">
                    <g:renderType template="list.item" value="${entry.key}" />
                </td>
                <td class="mapEntryValue">
                    <g:renderType template="list.item"
                                  name="${name + '.' + entry.key}" value="${entry.value}"
                                  widget="${widgets[entry.key]}" style="${style[entry.key]}" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>