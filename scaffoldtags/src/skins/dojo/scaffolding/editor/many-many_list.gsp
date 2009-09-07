<%/* g:select doesn't support multiple-item values properly */%>
<%
def disabledattr = (style?.readonly) ? "disabled" : ""
%>
<select multiple name="${name}" ${disabledattr}>
	<g:each var="item" in="${referencedDomain.list()}">
		<%
		def selectedAttr = (value.find { it.id == item.id }) ? "selected" : ""
		%>
		<option value='${item.id}' ${selectedAttr}>${item}</option>
	</g:each>
</select>