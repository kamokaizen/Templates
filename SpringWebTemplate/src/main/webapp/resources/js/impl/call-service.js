
function makeServiceCall(type, url, data, dataType, callback){
    var token = $('#csrfToken').val();
    var header = $('#csrfHeader').val();
	
	$.ajax({
		type : type,
		url : url,
		data : data,
		dataType : dataType,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
		success : function(response) {
			callback(null,response);
		},
		error: function (xhr, status, error) {
			var responseTitle= $(xhr.responseText).filter('title').get(0);
		    var errorMessage = $(responseTitle).text() + "\n" + formatErrorMessage(xhr, error);
		    callback(errorMessage, response);
	    }
	});
}

function makeMultipartFormDataCall(type, url, formData, callback){
    var token = $('#csrfToken').val();
    var header = $('#csrfHeader').val();
	
	$.ajax({
	  url : url,
	  data : formData,
	  type : type,
	  cache: false,
	  contentType: false,
	  processData: false,
      beforeSend: function(xhr) {
          xhr.setRequestHeader(header, token);
      },
	  success: function(response) {
		  callback(null,response);
	  },
	  error: function (xhr, status, error) {
			var responseTitle= $(xhr.responseText).filter('title').get(0);
		    var errorMessage = $(responseTitle).text() + "\n" + formatErrorMessage(xhr, error);
		    callback(errorMessage, response);
	  }
	});
}

function formatErrorMessage(xhr, exception) {

    if (xhr.status === 0) {
        return ('Not connected.\nPlease verify your network connection.');
    } else if (xhr.status == 404) {
        return ('The requested page not found. [404]');
    } else if (xhr.status == 500) {
        return ('Internal Server Error [500].');
    } else if (exception === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else if (exception === 'timeout') {
        return ('Time out error.');
    } else if (exception === 'abort') {
        return ('Ajax request aborted.');
    } else {
        return ('Uncaught Error.\n' + xhr.responseText);
    }
}