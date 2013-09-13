<ul class="listItem">
    <g:each var="item" in="${value}">
        <li>
            <g:renderDomain template="list.item"
                            domain="${referencedDomain}" name="${name}"
                            widgets="${widgets}" style="${style}"
                            value="${value}"
                            except="['id']"/>
        </li>
    </g:each>
</ul>