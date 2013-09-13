<table class="listItem">
    <tbody>
        <g:each var="item" status="i" in="${value}">
            <tr>
                <td class="collectionItem">
                    <g:renderType template="list.item"
                                  name="${name + '[' + i + ']'}" value="${item}"
                                  widget="${widgets ? widgets[0] : null}" style="${style ? style[0] : null}" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>