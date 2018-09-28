var firstTenResults = [];
var storedResults = {};

function checkPastResults(query)
{
	var results = []
	for (var key in storedResults)
	{
		if (key == query)
		{
			for (var i = 0; i < storedResults[key].length; i++)
			{
				results.push(storedResults[key][i])
			}
		}
	}
	return results
}

function handleLookup(query, doneCallback) {
	console.log("autocomplete initiated")
		
	var past = checkPastResults(query)
	if (past.length != 0)
	{
		console.log("Using cached results")
		console.log(JSON.stringify(past))
		doneCallback( { suggestions: past } );
		return
	}
	
	
	console.log("sending AJAX request to backend Java Servlet")

	jQuery.ajax({
		"method": "GET",
		"url": "movie-suggestion?query=" + escape(query),
		"success": function(data) {
			handleLookupAjaxSuccess(data, query, doneCallback) 
		},
		"error": function(errorData) {
			console.log("lookup ajax error")
			console.log(errorData)
		}
	})
}

function handleLookupAjaxSuccess(data, query, doneCallback) {
	console.log("lookup ajax successful")
	
	var jsonData = JSON.parse(data);
	
	firstTenResults = jsonData.slice(0, 10);
	storedResults[query] = firstTenResults
	console.log(JSON.stringify(firstTenResults))

	doneCallback( { suggestions: firstTenResults } );
}

function handleSelectSuggestion(suggestion) {
	console.log("you select " + suggestion["value"]);
	var url = suggestion["data"]["category"] + "-name" + "?id=" + suggestion["data"]["ID"];
	console.log(url);
	
	if (suggestion["data"]["category"] == "MOVIES")
		window.location.replace("/project2/singlemovie.jsp?title=" + suggestion["value"])
	else if (suggestion["data"]["category"] == "STARS")
		window.location.replace("/project2/singlestar.jsp?starName=" + suggestion["value"])
}

jQuery.fn.extend({
    propAttr: $.fn.prop || $.fn.attr
});
$('#autocomplete').autocomplete({
    minChars: 3,

    lookup: function (query, doneCallback) {
    		handleLookup(query, doneCallback)
    },
        
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    groupBy: "category",
    deferRequestBy: 300
});


/*
 * do normal full text search if no suggestion is selected 
 */
function handleNormalSearch(query) {
	console.log("doing normal search with query: " + query);

	window.location.replace("/project2/normalSearch.jsp?title=" + query);
}

// bind pressing enter key to a handler function
$('#autocomplete').keypress(function(event) {
	// keyCode 13 is the enter key
	if (event.keyCode == 13) {
		// pass the value of the input box to the handler function
		handleNormalSearch($('#autocomplete').val())
	}
})

function doNormalSearch(){
	handleNormalSearch(document.getElementById("autocomplete").value)
}
