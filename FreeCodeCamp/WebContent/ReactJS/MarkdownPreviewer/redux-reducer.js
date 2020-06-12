import {createStore} from 'redux';

const TEXTACTION = 'TEXT-ACTION';

let h1 = "# Heading(H1)\n";
let h2 = "## Sub-Heading(H2)\n";
let link = "[link](https://www.freecodecamp.com)\n";
let inline = "`<div></div>`\n";


let line1 = "```\n";
let lastLine = "\n```\n";
let codeBlock = line1 + `function anotherExample(firstLine, lastLine) {
  if (firstLine == "\`\`\`" && lastLine == "\`\`\`") {
    return multiLineCode;
  }
}` + lastLine;


let listItem = '1. List Item\n';
let blockQuote = '>Block Quotes!\n';
let img = '![React Logo w/ Text](https://goo.gl/Umyytc)\n'; // ! sign before [] makes it an img link. [] makes it a hyperlink.
let boldedText = '**boldedText**\n';


let onloadInput = h1 + h2 + link + inline + codeBlock + listItem + blockQuote + img + boldedText;

const reducer = (state = {input: onloadInput}, action) => 
{
	switch(action.type)
	{
		case TEXTACTION:
			return {input: action.text};
		default: 
	    	return state;
	}
}

export const store = createStore(reducer);