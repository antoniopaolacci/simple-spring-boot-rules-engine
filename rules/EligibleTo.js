
// Implementing rule test

//-------- input variable(s) ---------
var age;
var eligible_age = ["18", "30", "60"];

//-------- output variable(s) ---------
var output;

if (eligible_age.indexOf(age) == -1) {
	output = "No eligible option.";
} else if (age == "18" ) {
	output = "Young Option";
} else if (age == "30") {
	output = "Genius Option";
} else if (age == "60") {
	output = "Over 60 Option";
} else {
	output = "Invalid params.";
}



