<div class="blogEntryPreview">
    <h2 class="blogEntryTitle"><g:link controller="entry" action="show" id="${value.id}">${value.title}</g:link></h2>
    <div class="writtenOn"><g:renderType template="show" value="${value.createdOn}"/></div>
    <g:if test="${value.lastUpdated}">
        <div class="lastUpdate">updated on <g:renderType template="show" value="${value.lastUpdated}"/></div>
    </g:if>
    <div class="blogEntryText">
        <g:if test="${value.body.length() < 400}">
            ${value.body.encodeAsHTML().replace('\n', '<br/>')}
        </g:if>
        <g:else>
            ${value.body[0..400].encodeAsHTML().replace('\n', '<br/>')}... <g:render template="/more" model="[value: value]"/>
        </g:else>
    </div>
</div>
