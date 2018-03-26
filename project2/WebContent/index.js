/*
 * This function is called by the library when it needs to lookup a query.
 * 
 * The parameter query is the query string.
 * The doneCallback is a callback function provided by the library, after you get the
 *   suggestion list from AJAX, you need to call this function to let the library know.
 */
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
	
	// sending the HTTP GET request to the Java Servlet endpoint hero-suggestion
	// with the query data
	console.log("sending AJAX request to backend Java Servlet")

	jQuery.ajax({
		"method": "GET",
		// generate the request url from the query.
		// escape the query string to avoid errors caused by special characters 
		"url": "movie-suggestion?query=" + escape(query),
		"success": function(data) {
			// pass the data, query, and doneCallback function into the success handler
			handleLookupAjaxSuccess(data, query, doneCallback) 
		},
		"error": function(errorData) {
			console.log("lookup ajax error")
			console.log(errorData)
		}
	})
}


/*
 * This function is used to handle the ajax success callback function.
 * It is called by our own code upon the success of the AJAX request
 * 
 * data is the JSON data string you get from your Java Servlet
 * 
 */
function handleLookupAjaxSuccess(data, query, doneCallback) {
	console.log("lookup ajax successful")
	
	// parse the string into JSON
	var jsonData = JSON.parse(data);
	
	firstTenResults = jsonData.slice(0, 10);
	storedResults[query] = firstTenResults
	console.log(JSON.stringify(firstTenResults))

	// call the callback function provided by the autocomplete library
	// add "{suggestions: jsonData}" to satisfy the library response format according to
	//   the "Response Format" section in documentation
	doneCallback( { suggestions: firstTenResults } );
}


/*
 * This function is the select suggestion hanlder function. 
 * When a suggestion is selected, this function is called by the library.
 * 
 * You can redirect to the page you want using the suggestion data.
 */
function handleSelectSuggestion(suggestion) {
	console.log("you select " + suggestion["value"]);
	var url = suggestion["data"]["category"] + "-name" + "?id=" + suggestion["data"]["ID"];
	console.log(url);
	
	if (suggestion["data"]["category"] == "MOVIES")
		window.location.replace("/project2/singlemovie.jsp?title=" + suggestion["value"])
	else if (suggestion["data"]["category"] == "STARS")
		window.location.replace("/project2/singlestar.jsp?starName=" + suggestion["value"])
}


/*
 * This statement binds the autocomplete library with the input box element and 
 *   sets necessary parameters of the library.
 * 
 * The library documentation can be find here: 
 *   https://github.com/devbridge/jQuery-Autocomplete
 *   https://www.devbridge.com/sourcery/components/jquery-autocomplete/
 * 
 */
// $('#autocomplete') is to find element by the ID "autocomplete"
$('#autocomplete').autocomplete({
    minChars: 3,

	// documentation of the lookup function can be found under the "Custom lookup function" section
    lookup: function (query, doneCallback) {
    		handleLookup(query, doneCallback)
    },
        
    onSelect: function(suggestion) {
    		handleSelectSuggestion(suggestion)
    },
    // set the groupby name in the response json data field
    groupBy: "category",
    // set delay time
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
