<g:if test="${style?.actions}">
    <g:renderActions template="list.script" actions="${style.actions}" item="${item}" />
</g:if><g:else>
    <g:renderAction template="list.script" action="show" label="Show" item="${item}" />
</g:else>
function ${name}_renderProperty(domainType, property, domain)
{
<g:eachDomainProperty domain="${domain}"
                      widgets="${widgets}" style="${style}"
                      except="${except}" exceptWhen="${exceptWhen}"
                      only="${only}"
                      order="${order}">
    if (property == "${it.name}") {
    <g:renderProperty template="list.item.script"
                      domain="${domain}" domainId="${item?.id}"
                      prop="${it.prop}" value="${it.value}"
                      name="${it.name}"
                      widget="${it.widget}" style="${it.style}"/>
    }
</g:eachDomainProperty>
}
dojo.require("dojo.event.*");
dojo.require("dojo.io.*");
dojo.require("dojo.widget.FilteringTable");
dojo.require("dojo.json");
function ${name}_init()
{
  var table = dojo.widget.byId('${name}_list');
<g:if test="${style?.onDataSelect}">
<g:if test="${style.onDataSelect instanceof Map}">
  dojo.event.connect(table, 'onDataSelect', '${name}_onDataSelect');
</g:if>
<g:else>
  dojo.event.connect(table, 'onDataSelect', '${style.onDataSelect}');
</g:else>
</g:if>
<g:if test="${style.pages}">
    var params = {caption: "Previous"};
    var widget = dojo.widget.createWidget("Button", params, document.getElementById("${name}_paginatorPrev"));
    params = {caption: "Next"};
    widget = dojo.widget.createWidget("Button", params, document.getElementById("${name}_paginatorNext"));

    // Hide the normal paginate links and show the JS buttons
    var pageLinksDiv = document.getElementById("${name}_paginateButtons")
    pageLinksDiv.style.display = "none";
    var pageLinksDiv = document.getElementById("${name}_pageButtons")
    pageLinksDiv.style.display = "block";
    var pageLinksPage = dojo.widget.byId("${name}_page1")
    dojo.event.connect(pageLinksPage, 'onblur', '${name}_onBlur');
    var pageLinksPrev = dojo.widget.byId("${name}_pagePrev")
    dojo.event.connect(pageLinksPrev, 'onClick', '${name}_onPrev');
    var pageLinksNext = dojo.widget.byId("${name}_pageNext")
    dojo.event.connect(pageLinksNext, 'onClick', '${name}_onNext');
    var pageLinksFirst = dojo.widget.byId("${name}_pageFirst")
    dojo.event.connect(pageLinksFirst, 'onClick', '${name}_onFirst');
    var pageLinksLast = dojo.widget.byId("${name}_pageLast")
    dojo.event.connect(pageLinksLast, 'onClick', '${name}_onLast');
    var pageLinksRefresh = dojo.widget.byId("${name}_pageRefresh")
    dojo.event.connect(pageLinksRefresh, 'onClick', '${name}_onRefresh');
</g:if>
<g:else>
//  alert(dojo.json.serialize(table.store.getData()));
  var offset = 0;
  var requestCount = table.store.getData().length;
  var bindargs = {
      url: "list",
      mimetype: "text/json",
      handler: ${name}_fetchData,
      content: {json:true,offset:offset + requestCount}
  }
  dojo.io.bind(bindargs);
</g:else>
}
<g:if test="${style.pages}">
function ${name}_fetch(page)
{
  var table = dojo.widget.byId('${name}_list');
  var pagePer = parseInt(document.getElementById("${name}_pagePer").value);
  var requestCount = table.store.getData().length;
  var offset = (page - 1) * pagePer;
  var pageLinksPage = document.getElementById("${name}_page1")
  if (parseInt(pageLinksPage.value) != page) {
      pageLinksPage.value = page;
  }
  var bindargs = {
      url: "list",
      mimetype: "text/json",
      handler: ${name}_fetchPage,
      content: {json:true,offset:offset}
  }
  dojo.io.bind(bindargs);
}
function ${name}_onBlur(type, data, evt)
{
  var pageLinksPage = document.getElementById("${name}_page1")
  ${name}_fetch(parseInt(pageLinksPage.value));
}
function ${name}_onFirst(type, data, evt)
{
  ${name}_fetch(1);
}
function ${name}_onPrev(type, data, evt)
{
  var pageLinksPage = document.getElementById("${name}_page1")
  ${name}_fetch(parseInt(pageLinksPage.value) - 1);
}
function ${name}_onNext(type, data, evt)
{
  var pageLinksPage = document.getElementById("${name}_page1")
  ${name}_fetch(parseInt(pageLinksPage.value) + 1);
}
function ${name}_onLast(type, data, evt)
{
  var pageCount = parseInt(document.getElementById("${name}_pageCount").value);
  var pagePer = parseInt(document.getElementById("${name}_pagePer").value);
  ${name}_fetch(Math.floor((pageCount + pagePer - 1) / pagePer));
}
function ${name}_onRefresh(type, data, evt)
{
  var pageLinksPage = document.getElementById("${name}_page1")
  ${name}_fetch(parseInt(pageLinksPage.value));
}
</g:if>
<%
    def itemStr = [id:"' + id + '"]
    def actionLinks = renderActions([template:"list",
                                     actions: style.actions,
                                     item: itemStr])
%>
<g:if test="${style.pages}">
function ${name}_fetchPage(type, data, evt)
{
  if (type == 'error')
  {
    alert('Server communication error\n:' + data.message);
  }
  else if (data)
  {
    var list = data.list;
    var count = data.count;
    for (var i = 0; i < list.length; i++) {
        var id = list[i]['id']
        list[i]['field0']='${actionLinks}';
        for (var p in list[i]) {
            ${name}_renderProperty(list[i].class, p, list[i]);
        }
    }
    var table = dojo.widget.byId('${name}_list');
    var store = table.store;
    var oldData = store.getData();
    store.setData(list);
    var pageCountText = document.getElementById("${name}_numPages");
    var pagePer = parseInt(document.getElementById("${name}_pagePer").value);
    pageCountText.innerHTML = Math.floor((count + pagePer - 1) / pagePer);
    var pageCount = document.getElementById("${name}_pageCount");
    pageCount.value = count;
    /*
    var jsonDebug = document.getElementById("${name}_jsonDebug");
    jsonDebug.value = evt.responseText;
    */
  } else {
    alert('Error retrieving data from server: incorrect format');
    alert(evt.responseText);
  }
}
</g:if>
<g:else>
function ${name}_fetchData(type, data, evt, req)
{
  if (type == 'error')
  {
    alert('Server communication error\n:' + data.message);
  }
  else if (data)
  {
    var table = dojo.widget.byId('${name}_list');
    var store = table.store;
    for (var i = 0; i < data.length; i++) {
        var id = data[i]['id']
        data[i]['field0']='${actionLinks}';
    }
    store.addDataRange(data);
    var requestCount = (req.content && req.content.max) ? req.content.max : 10;
    if (data.length == requestCount) {
        var offset = (req.content && req.content.offset) ? req.content.offset : 0;
        var bindargs = {
            url: "list",
            mimetype: "text/json",
            handler: ${name}_fetchData,
            content: {json:true,offset:offset + requestCount}
        };
        dojo.io.bind(bindargs);
    }
   } else {
    alert('Error retrieving data from server: incorrect format');
    alert(evt.responseText);
   }
}
</g:else>
<g:if test="${style?.onDataSelect instanceof Map}">
function ${name}_onDataSelect()
{
  dojo.io.bind(${style.onDataSelect as DojoBindArgs});
}
</g:if>
dojo.addOnLoad(${name}_init);
