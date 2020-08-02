function ExportToTable() {
    var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xlsx|.xls)$/;
    /*Checks whether the file is a valid excel file*/
    if (regex.test($("#excelfile").val().toLowerCase())) {
        var xlsxflag = false; /*Flag for checking whether excel is .xls format or .xlsx format*/
        if ($("#excelfile").val().toLowerCase().indexOf(".xlsx") > 0) {
            xlsxflag = true;
        }
        /*Checks whether the browser supports HTML5*/
        if (typeof (FileReader) != "undefined") {
            var reader = new FileReader();
            reader.onload = function (e) {
                var data = e.target.result;
                /*Converts the excel data in to object*/
                if (xlsxflag) {
                    var workbook = XLSX.read(data, { type: 'binary' });
                }
                else {
                    var workbook = XLS.read(data, { type: 'binary' });
                }
                /*Gets all the sheetnames of excel in to a variable*/
                var sheet_name_list = workbook.SheetNames;

                var cnt = 0; /*This is used for restricting the script to consider only first sheet of excel*/
                sheet_name_list.forEach(function (y) { /*Iterate through all sheets*/
                    /*Convert the cell value to Json*/
                    if (xlsxflag) {
                        var exceljson = XLSX.utils.sheet_to_json(workbook.Sheets[y]);
                    }
                    else {
                        var exceljson = XLS.utils.sheet_to_row_object_array(workbook.Sheets[y]);
                    }
                    if (exceljson.length > 0 && cnt == 0) {
                        BindTable(exceljson, '#exceltable');
                        cnt++;
                    }
                });
                $('#exceltable').show();
                updateTable();
            }
            if (xlsxflag) {/*If excel file is .xlsx extension than creates a Array Buffer from excel*/
                reader.readAsArrayBuffer($("#excelfile")[0].files[0]);
            }
            else {
                reader.readAsBinaryString($("#excelfile")[0].files[0]);
            }
        }
        else {
            alert("Sorry! Your browser does not support HTML5!");
        }
    }
    else {
        alert("Please upload a valid Excel file!");
    }
}
function BindTable(jsondata, tableid) {/*Function used to convert the JSON array to Html Table*/
    var columns = BindTableHeader(jsondata, tableid); /*Gets all the column headings of Excel*/
    for (var i = 0; i < jsondata.length; i++) {
        var row$ = $('<tr/>');
        for (var colIndex = 0; colIndex < columns.length; colIndex++) {
            var cellValue = jsondata[i][columns[colIndex]];
            if (colIndex == 0){
                if (cellValue == 'r')
                    cellValue = '<input type="checkbox" checked/>';
                if (cellValue == null)
                    cellValue = '<input type="checkbox" />';
            }
            if (cellValue == null)
                cellValue = "";
            row$.append($('<td/>').html(cellValue));
        }
        $(tableid).append(row$);
    }
}
function BindTableHeader(jsondata, tableid) {/*Function used to get all column names from JSON and bind the html table header*/
    var columnSet = [];
    var headerTr$ = $('<tr/>');
    for (var i = 0; i < jsondata.length; i++) {
        var rowHash = jsondata[i];
        for (var key in rowHash) {
            if (rowHash.hasOwnProperty(key)) {
                if ($.inArray(key, columnSet) == -1) {/*Adding each unique column names to a variable array*/
                    if(key == null) key = '';
                    columnSet.push(key);
                    headerTr$.append($('<th/>').html(key));
                }
            }
        }
    }
    $(tableid).append(headerTr$);
    return columnSet;
}

function clearTableBody() {
    $('#excelfile').val('');
    $("#exceltable").remove();
}

function updateTable(){
    $('#exceltable th').filter(function() {
       if($(this).text() == 'undefined') $(this).text('');
    });
}

function openDialog(){
	$('#dialogWaiting').attr("open", "");
}


function displayObj(){
    var obj = $('#obj').text();
    obj = JSON.parse(obj);
    $.each(obj, function(k1, v1){
        //var list = v1.toArray();
        $("#result").append(k1+'<br>');
        $.each(v1, function(k2, v2){
            var img = './Report/Screenshot/'+k1+'/'+v2;
            var htmlImg = $('<img>',{id:v2,src:img,alt:v2});
            $("#result").append(
            		$('<a>',{href:img,target: "_blank", alt:v2, title:v2, 
            	html: $('<img>',{id:v2,src:img,alt:v2, style:"width:20%;height:auto;"})}));
//            $("#result").append($('<figure>',{
//                html: 
//                    $('<a>',{href:img,target: "_blank", alt:v2, title:v2, 
//                        html: htmlImg}) 
//                }
//            ));
//            $("#result").append($('<figcaption>',{ html: v2}));
            //$("#result").append('<br>');
        });
        $("#result").append('<br><br>');
    });
}

function download(){
	$.ajax({
        url: "http://rest-service.guides.spring.io/greeting"
    }).then(function(data) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.content);
    });
}


//const configuration = {
//    locale: "en_US",
//    originKey: "123",
//    loadingContext: "https://checkoutshopper-test.adyen.com/checkoutshopper/"
//};
// 
//const checkout = new AdyenCheckout(configuration);
//const card = checkout.create("card", {
//    onChange: handleOnChange
//}).mount("#card");
//
//
//function handleOnChange(state, component) {
//    state.isValid // true or false.
//    state.data
//    /* {type: "scheme",
//        encryptedCardNumber: "adyenjs_0_1_18$MT6ppy0FAMVMLH...",
//        encryptedExpiryMonth: "adyenjs_0_1_18$MT6ppy0FAMVMLH...",
//        encryptedExpiryYear: "adyenjs_0_1_18$MT6ppy0FAMVMLH...",
//        encryptedSecurityCode: "adyenjs_0_1_18$MT6ppy0FAMVMLH..."}
//    */
//}

