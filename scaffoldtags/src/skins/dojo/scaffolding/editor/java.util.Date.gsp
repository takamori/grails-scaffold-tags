<g:if test="${style?.hidden}">
<% /*
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />
*/ %>
</g:if>
<g:elseif test="${style?.readonly}">
<% /*
    <input type='hidden' name='${name}' value="${value?.encodeAsHTML()}" />${value}
*/ %>
    ${value?.encodeAsHTML()}
</g:elseif>
<g:else>
    <%
    if (!style) {
        style = [:]
    }
    if (!style.containerToggle) {
        style.containerToggle = "fade"
    }
    if (!style.containerToggleDuration) {
        style.containerToggleDuration = "200"
    }
    def c = new GregorianCalendar()
    if (value != null) {
        c.setTime(value)
    }
    def day = c.get(GregorianCalendar.DAY_OF_MONTH)
    def month = c.get(GregorianCalendar.MONTH)+1
    def year = c.get(GregorianCalendar.YEAR)
    def hour = c.get(GregorianCalendar.HOUR_OF_DAY)
    def minute = c.get(GregorianCalendar.MINUTE)
    %>
    <input type="hidden" name="${name}" value="struct" />
    <input type="hidden" name="${name}_day" id="${name}_day" value="${day}" />
    <input type="hidden" name="${name}_month" id="${name}_month" value="${month}" />
    <input type="hidden" name="${name}_year" id="${name}_year" value="${year}" />
    <input type="hidden" name="${name}_hour" id="${name}_hour" value="${hour}" />
    <input type="hidden" name="${name}_minute" id="${name}_minute" value="${minute}" />
    <input id="${name}_date" name="${name}_date" dojoType="dropdowndatepicker"
        value="${new java.text.SimpleDateFormat("yyyy-MM-dd").format(c.time)}"
        containerToggle="${style.containerToggle}" containerToggleDuration="${style.containerToggleDuration}" ${style} />
    <input id="${name}_time" name="${name}_time" dojoType="dropdowntimepicker"
        value="${new java.text.SimpleDateFormat("HH:mm").format(c.time)}"
        containerToggle="${style.containerToggle}" containerToggleDuration="${style.containerToggleDuration}" ${style} />
<%/* The following uses Dojo's TimeSpinner instead.
     Be sure to update the corresponding editor.script renderer as well.
    <input dojoType="TimeSpinner"
        value="${new java.text.SimpleDateFormat("HH:mm").format(value)}"
        delta="1:01" format="hh:mm"
        widgetId="${name}_timeSpinner">
*/ %>

</g:else>