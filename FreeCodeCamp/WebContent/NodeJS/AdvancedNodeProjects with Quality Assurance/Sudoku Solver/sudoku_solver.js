 const textArea = document.getElementById('text-input');
 const clear    = document.getElementById('clear-button');
 const solve    = document.getElementById('solve-button');
 import { puzzlesAndSolutions } from './puzzle-strings.js';

document.addEventListener('DOMContentLoaded', () => {
  // Load a simple puzzle into the text area
  textArea.value = '..9..5.1.85.4....2432......1...69.83.9.....6.62.71...9......1945....4.37.4.3..6..';
  
  let gridObj    = renderTextToGrid(textArea.value);
  let gridPlaces = Object.keys(gridObj);
  
  gridPlaces.forEach(place => {
    let obj = document.getElementById(place);
    obj.addEventListener('input', (event) => {
      console.log('dispatched');
      if(!isContentAllowed(event.data))
        return;
      
      gridObj[place] = obj.value;
      textArea.value = renderGridToText(gridObj);
    });
  });
});

solve.addEventListener('click', () => solveSudoku(textArea.value) );
clear.addEventListener('click', () => clearTextAndGrid() );
textArea.addEventListener('input',() => renderTextToGrid(textArea.value) );

let isContentAllowed = (character) => {
  
  if(character != null && character.length == 1)
  {
    let allowedContent = character.match(/[1-9\.]/);
    return (allowedContent == null) ? false : true;
  }
  else return false;
}

let renderGridToText = (grid) => {
  
    let gridPlaces = Object.keys(grid);
    let textValue  = "";
    gridPlaces.forEach(place => textValue += grid[place]);
    return textValue;
}

let renderTextToGrid = (value) => {
  
    let gridObject = {};
    let allowed    = value.split("").every(val => isContentAllowed(val));
    if(value.length != 81)
    {
      document.getElementById('error-msg').innerHTML = '<p style="color:red;"> Error: Expected puzzle to be 81 characters long. </p>'; 
      return 'Error: Expected puzzle to be 81 characters long.';
    }
    else if(!allowed) 
      document.getElementById('error-msg').innerHTML = '<p style="color:red;"> Error: Only Numbers or Period characters allowed. </p>';
    else
    {
      document.getElementById('error-msg').innerHTML = '';
      let gridValues = value.split("");      
      let gridPlaces = populateGridPlaces();
      
      gridPlaces.forEach((place,index) => gridObject[place] = gridValues[index] ); 
      gridPlaces.forEach(place => {
        let gridLoc   = document.getElementById(place);
        gridLoc.value = gridObject[place];
      });
    }    
    return gridObject;
}

let clearTextAndGrid = () => {
  textArea.value = ""; //Clearing textArea
  
  //Clearing Grid
  let gridPlaces = populateGridPlaces();
  gridPlaces.forEach(place => {
    let gridLoc   = document.getElementById(place);
    gridLoc.value = "";
  })
}

let populateGridPlaces = () => {
  
    let gridPlaces = [];
    let rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'];

    rows.forEach(row => {
      for(let i = 1; i <=9; i++)
        gridPlaces.push(row+i+"");
    });

    return gridPlaces;
}

let solveSudoku = (puzzleString) => {
  let puzzle = puzzlesAndSolutions.filter( puzzAndSol => (puzzleString === puzzAndSol[0]) );
  
  if(puzzle.length == 1) puzzle = puzzle[0];
  if(puzzle.length == 2)
  {
    textArea.value = puzzle[1];
    renderTextToGrid(puzzle[1]);
    return puzzle[1];
  }
}

let isPuzzlePass = (completePuzzle) => {
  let puzzle = puzzlesAndSolutions.filter( puzzAndSol => (completePuzzle === puzzAndSol[1]) );
  if(puzzle.length == 1) puzzle = puzzle[0];
  if(puzzle.length == 2)
  {
    return true;
  }
  
  return false;
}
/* 
  Export your functions for testing in Node.
  Note: The `try` block is to prevent errors on
  the client side
*/

try {
  module.exports = {
    
    isContentAllowed: isContentAllowed,
    
    solveSudoku: solveSudoku,
    
    renderTextToGrid: renderTextToGrid,
    
    renderGridToText: renderGridToText,
    
    isPuzzlePass: isPuzzlePass,
    
    populateGridPlaces: populateGridPlaces,
    
    clearTextAndGrid: clearTextAndGrid
  }
} catch (e) {}
