<div class="dialog">
<table>
    <tbody>
        <g:each var="item" status="i" in="${value}">
            <tr class="prop">
                <td>
                    <g:renderType template="show"
                                  name="${name + '[' + i + ']'}" value="${item}"
                                  widget="${widgets ? widgets[0] : null}" style="${style ? style[0] : null}" />
                </td>
            </tr>
        </g:each>
    </tbody>
</table>
</div>