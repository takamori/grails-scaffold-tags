<%/* Uncomment if you're using Dojo's TimeSpinner
dojo.require("dojo.widget.Spinner"); // experimental widget
*/%>
dojo.require("dojo.event.*");
function init${name}()
{
    var datePicker = dojo.widget.byId('${name}_date');
    dojo.event.connect(datePicker, 'onValueChanged', '${name}DateChanged');
}
function ${name}DateChanged(date)
{
    var dayField = document.getElementById('${name}_day');
    var monthField = document.getElementById('${name}_month');
    var yearField = document.getElementById('${name}_year');
    dayField.value = date.getDate();
    monthField.value = date.getMonth() + 1;
    yearField.value = date.getFullYear();
}
dojo.addOnLoad(init${name});