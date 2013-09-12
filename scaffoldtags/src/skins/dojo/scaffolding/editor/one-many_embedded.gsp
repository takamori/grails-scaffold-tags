<% int i = 0 %>
<div>
<g:each var='item' in='${value?}'>
    <g:renderDomain template="editor"
                    domain="${referencedDomain}" name="${name + '[' + i + ']'}"
                    widgets="${widgets}" style="${style}"
                    value="${item}"
                    except="['id']" />
    <% i++ %>
</g:each>
<g:renderDomain template="editor"
                domain="${referencedDomain}" name="${name + '[' + i + ']'}"
                widgets="${widgets}" style="${style}"
                except="['id']" />
</div>