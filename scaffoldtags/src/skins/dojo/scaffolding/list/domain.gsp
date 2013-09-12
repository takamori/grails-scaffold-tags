<div class="listScrollWindow">
<table id="${name}_list" class="list" dojoType="filteringTable"
       alternateRows="true" valueField="id"
       cellpadding="0" cellspacing="0" border="0">
    <thead>
        <tr>
            <th class="actionButtons"></th>
            <g:eachDomainProperty domain="${domain}" name="${name}"
                                  except="${except}" exceptWhen="${exceptWhen}"
                                  only="${only}"
                                  order="${order}">
                <%
                if (java.util.Calendar.class.isAssignableFrom(it.prop.type)) {
                    type = "Date"
                } else if (java.util.Date.class.isAssignableFrom(it.prop.type)) {
                    type = "DateTime"
                } else if (java.lang.Number.class.isAssignableFrom(it.prop.type) ||
                    [Byte.TYPE, Double.TYPE, Float.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE].contains(it.prop.type)) {
                    type = "Number"
                } else {
                    type = "String"
                }
                %>
                <th field="${it.prop.name}" dataType="${type}"
                    ${(it.prop.name == 'id') ? 'sort="asc"' : ''}>${it.prop.naturalName}</th>
            </g:eachDomainProperty>
        </tr>
    </thead>
    <tbody id="${name}_listBody">
        <g:each var="item" in="${value}">
            <tr value="${item.id}">
                <td class="actionButtons">
                    <g:if test="${style?.actions}">
                        <g:renderActions template="list" actions="${style.actions}" item="${item}" />
                    </g:if><g:else>
                        <g:renderAction template="list" action="show" label="Show" item="${item}" />
                    </g:else>
                </td>
                <g:eachDomainProperty domain="${domain}" value="${item}"
                                      widgets="${widgets}" style="${style}"
                                      except="${except}" exceptWhen="${exceptWhen}"
                                      only="${only}"
                                      order="${order}">
                    <td><%
                        %><g:if test="${it.style?.link}"><g:def var="p" value="${it}"/><%
                            %><g:link action="${p.style.link.action}" id="${item.id}"><%
                                %><g:renderProperty template="list.item"
                                                  domain="${domain}" domainId="${item?.id}"
                                                  prop="${p.prop}" value="${p.value}"
                                                  name="${p.name}"
                                                  widget="${p.widget}" style="${p.style}"/><%
                            %></g:link><%
                        %></g:if>
                        <g:else><%
                            %><g:renderProperty template="list.item"
                                              domain="${domain}" domainId="${item?.id}"
                                              prop="${it.prop}" value="${it.value}"
                                              name="${it.name}"
                                              widget="${it.widget}" style="${it.style}"/><%
                        %></g:else><%
                    %></td>
                </g:eachDomainProperty>
            </tr>
        </g:each>
    </tbody>
</table>
</div>
<g:if test="${style.pages}">
    <div class="paginateButtons" id="${name}_paginateButtons">
        <g:paginate total="${style.count}" action="list"/>
    </div>
    <div class="pageButtons" id="${name}_pageButtons" style="display:none;">
        <form method="get" action="" id="${name}_pageForm">
        <button dojoType="Button" id="${name}_pageFirst">First</button>
        <button dojoType="Button" id="${name}_pagePrev">Previous</button>
        <div>&nbsp;| Page&nbsp;</div>
        <input type="text" dojoType="ValidationTextBox" id="${name}_page1" digit="true" value="1"/>
        <div>&nbsp;of&nbsp;</div>
        <input type="hidden" id="${name}_pageCount" value="${style.count}" />
        <div class="numPages" id="${name}_numPages">${(style.count + params.max - 1).intdiv(params.max)}</div>
        <div>&nbsp;|&nbsp;</div>
        <button dojoType="Button" id="${name}_pageNext">Next</button>
        <button dojoType="Button" id="${name}_pageLast">Last</button>
        <div>&nbsp;|&nbsp;</div>
        <button dojoType="Button" id="${name}_pageRefresh">Refresh</button>
        <div>&nbsp;| Display&nbsp;</div>
        <input type="text" dojoType="ValidationTextBox" id="${name}_pagePer" digit="true" value="${params.max}"/>
        <div>&nbsp;per page.</div>
        <!--
        <textarea rows="5" cols="60" id="${name}_jsonDebug"></textarea>
         -->
        </form>
    </div>
</g:if>