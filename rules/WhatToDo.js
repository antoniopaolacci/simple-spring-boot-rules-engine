
// Implementing rule WhatToDo

//-------- input variable(s) ---------
var family_visiting;
var weather;
var money;

var known_weathers = [ "sunny", "rainy", "windy" ];
    var rich_money = [ "rich", "wealthy" ];

// function(s)
var contains = function(needle) {
	
	// Per spec, the way to identify NaN is that it is not equal to itself
	var findNaN = needle !== needle;
	var indexOf;

	if(!findNaN && typeof Array.prototype.indexOf === 'function') {
		indexOf = Array.prototype.indexOf;
	} else {
		indexOf = function(needle) {
			var i = -1, index = -1;

			for(i = 0; i < this.length; i++) {
				var item = this[i];

				if((findNaN && item !== item) || item === needle) {
					index = i;
					break;
				}
			}

			return index;
		};
	}

	return indexOf.call(this, needle) > -1;
	
};


// -------- output variable(s) ---------
var output;


// --- starts here

if (family_visiting == "yes")  {
	
	output = "Cinema";
	
} else {
	
	if (known_weathers.indexOf(weather) == -1) {
		
		output = "No configuration.";
		
	} else if (weather == "sunny" ) {
		
		output = "Play Tennis";
		
	} else if (weather == "rainy") {
		
		output = "Stay In";
		
	} else if (weather == "windy") {
		
		output = "Kite"; 
		
	} else {
		
		output = "Invalid params."; 
		
	}
	
}
