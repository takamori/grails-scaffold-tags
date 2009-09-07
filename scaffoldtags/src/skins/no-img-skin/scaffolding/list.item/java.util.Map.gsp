<table class="listItem">
    <tbody>
        <g:each var="entry" in="${value}">
            <tr class="mapEntry">
                <td class="mapEntryKey">${entry.key}</td>
                <td class="mapEntryValue">${entry.value}</td>
            </tr>
        </g:each>
    </tbody>
</table>