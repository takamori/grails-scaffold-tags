<ul>
    <g:each var="item" in="${value}">
        <li><g:link controller="${referencedController}" action="show" id="${item.id}">${item.title}</g:link></li>
    </g:each>
</ul>