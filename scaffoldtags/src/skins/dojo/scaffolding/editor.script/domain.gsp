<g:if test="${style?.actions}">
    <g:renderActions template="editor.script" actions="${style.actions}" item="${item}" />
</g:if><g:else>
    <g:renderAction template="editor.script" action="show" label="Show" item="${item}" />
</g:else>
<g:eachDomainProperty domain="${domain}"
                      widgets="${widgets}" style="${style}"
                      except="${except}" exceptWhen="${exceptWhen}"
                      only="${only}"
                      order="${order}">
    <g:renderProperty template="editor.script"
                      domain="${domain}" domainId="${item?.id}"
                      prop="${it.prop}" value="${it.value}"
                      name="${it.name}"
                      widget="${it.widget}" style="${it.style}"/>
</g:eachDomainProperty>
