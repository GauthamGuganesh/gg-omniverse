import {createStore} from 'redux';

const operators = {'+': 'ADD', '-': 'SUB','x': 'MULT','/': 'DIV'} ;
const calcOps = Object.keys(operators); 

//state object initialized as  { input: [] }
const reducer = (state = { input: [0] }, action) => {

	let newState = [...state.input];

	switch(action.type)
	{
		case 'INPUT':
			  if(state.input.length == 1 && state.input[0] == 0)
			  {
			  	return (action.data == '0') ? state : ({ input: [action.data] });
			  }

			  let lastElement = newState[newState.length-1];

			  if(lastElement == '-')
			  {
			  	let lastButOne = newState[newState.length-2];

			  	if(calcOps.indexOf(lastButOne) != -1 )
			  	{
			  		newState.pop();
			  		newState.push('-' + action.data);
			  		return { input: newState };
			  	}
			  }

			  newState.push(action.data);
			  return { input: newState };

		case 'EQUALS':
				let answer = performCalculation(state.input);
				return { input: [answer] };

		case 'CLEAR':
					return { input: [0] }

		case 'DECIMAL':

			  let lastOperatorIndex = 0;

			  newState.forEach( (item,index,array) => lastOperatorIndex = (calcOps.indexOf(item) != -1) ? 
			  															   						index : lastOperatorIndex );

 																							//searches for index from given position : indexOf(key, pos);
			  if( !(newState[newState.length - 1] == '.') && newState.indexOf('.', lastOperatorIndex) == -1) //If decimal point comes continously or twice in a same number, won't use it.
			  {
			  	newState.push(action.data);                     
			  }
			  return { input: newState };
		
		case 'OPERATOR':

				let lastInput = newState[newState.length-1];

				if(calcOps.indexOf(action.data) != -1 && calcOps.indexOf(lastInput) != -1 && action.data != '-')
				{
					newState.pop();  //Popping will change index of array. So to handle consecutive operator usecase, checking last-before index separately below.

				}

				let lastBefore = newState[newState.length-1];

				if(calcOps.indexOf(lastBefore) != -1 && action.data != '-')
				{
					newState.pop();
				}

				newState.push(action.data);
				console.log(newState);

				return {input: newState};		

		default:
		       return state;
	}
}

let performCalculation = (input) => {

	let operation = '';
	let operatorIndex = 0;

	let calcArray = []; //Array that has the numbers and the operation to be performed.
	let number = '';

	input.forEach( (value, index, inputArray) => { //Slicing digits before and after operators and forming full numbers.

		if(calcOps.indexOf(value) != -1)
		{
			operation = operators[value];
			calcArray.push(parseFloat(number), operation);
			number='';
		}
		else
		{
			number += (value+'');
			if(index == (inputArray.length -1) )
			{
				calcArray.push(parseFloat(number));
			}
		}
	});

	return calcArray.reduce( (acc, value, index, array) => {

		let num = array[index+1];

		acc = (value == 'ADD') ? acc + num : 
			  (value == 'SUB') ? acc - num : 
			  (value == 'MULT') ? acc * num : 
			  (value == 'DIV') ? acc / num : acc + 0;

		return acc;
	});

}

export let store = createStore(reducer);





