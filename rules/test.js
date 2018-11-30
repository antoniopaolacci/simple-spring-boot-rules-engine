
// Implementing rule test

//-------- input variable(s) ---------
var action;
var expecting_action = ["toDo", "done", "postpone"];

//-------- output variable(s) ---------
var output;

if (action == "toDo" ) {
	output = "1";
} else if (action == "done") {
	output = "2";
} else if (action == "postpone") {
	output = "3";
} else {
	output = "Invalid action. Expecting are: "+expecting_action.toString();
}
