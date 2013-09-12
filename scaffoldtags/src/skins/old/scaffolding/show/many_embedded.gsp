<div>
    <g:each var="item" in="${value}">
        <g:renderDomain template="show"
                        domain="${referencedDomain}" name="${name}"
                        widgets="${widgets}" style="${style}"
                        value="${value}"
                        except="['id']"/>
    </g:each>
</div>