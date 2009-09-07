<%/* Uncomment if you're using Dojo's TimeSpinner
dojo.require("dojo.widget.Spinner"); // experimental widget
*/%>
dojo.require("dojo.event.*");
dojo.require("dojo.widget.DatePicker");
function init${name}()
{
    var datePicker = dojo.widget.byId('${name}_date');
    dojo.event.connect(datePicker, 'onValueChanged', '${name}DateChanged');
    var timePicker = dojo.widget.byId('${name}_time');
    dojo.event.connect(timePicker, 'onValueChanged', '${name}TimeChanged');
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
function ${name}TimeChanged(time)
{
    var date = dojo.widget.TimePicker.util.fromRfcDateTime(time, true, true);
    var hourField = document.getElementById('${name}_hour');
    var minuteField = document.getElementById('${name}_minute');
    hourField.value = date.getHours();
    minuteField.value = date.getMinutes();
}
dojo.addOnLoad(init${name});