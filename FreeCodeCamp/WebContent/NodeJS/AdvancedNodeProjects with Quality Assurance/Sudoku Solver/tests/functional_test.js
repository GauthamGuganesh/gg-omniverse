/*
*
*
*       FILL IN EACH FUNCTIONAL TEST BELOW COMPLETELY
*       -----[Keep the tests in the same order!]-----
*       (if additional are added, keep them at the very end!)
*/

const chai = require("chai");
const assert = chai.assert;

const jsdom = require("jsdom");
const { JSDOM } = jsdom;
let Solver;

suite('Functional Tests', () => {
  suiteSetup(() => {
    // DOM already mocked -- load sudoku solver then run tests
    Solver = require('../public/sudoku-solver.js');
  });
  
  suite('Text area and sudoku grid update automatically', () => {
    // Entering a valid number in the text area populates 
    // the correct cell in the sudoku grid with that number
    test('Valid number in text area populates correct cell in grid', done => {
      let domObj = document.getElementById('text-input');   
      domObj.value = '..9..5.1.85.4....2432......1...69.83.9.....6.62.71...9......1945....4.37.4.3..6..';
      let inputs   = domObj.value.split("");
      
      let event = document.createEvent('Event');
      event.initEvent('input',true, true);
      event.eventName = 'input';
      domObj.dispatchEvent(event);
           
      let gridPlaces = Solver.populateGridPlaces();
      let isValid    = gridPlaces.every((place,index) => {
        let obj = document.getElementById(place);
        return (obj.value === inputs[index]);
      });
      
      assert.equal(isValid, true);
      done();
    });

    // Entering a valid number in the grid automatically updates
    // the puzzle string in the text area
    test('Valid number in grid updates the puzzle string in the text area', done => {
      let gridValues = '..9..5.1.85.4....2432......1...69.83.9.....6.62.71...9......1945....4.37.4.3..6..';
      let values     = gridValues.split("");
      let gridObj    = {};
      
      let gridPlaces = Solver.populateGridPlaces();
      gridPlaces.forEach((place,index) => {
        let placeObj   = document.getElementById(place);
        placeObj.value = values[index]; 
        gridObj[place] = values[index];
      })
      
      let textValue = Solver.renderGridToText(gridObj);
      assert.equal(textValue, gridValues);
      done();
    });
  });
  
  suite('Clear and solve buttons', () => {
    // Pressing the "Clear" button clears the sudoku 
    // grid and the text area
    test('Function clearInput()', done => {
      let domObj = document.getElementById('text-input');   
      domObj.value = '..9..5.1.85.4....2432......1...69.83.9.....6.62.71...9......1945....4.37.4.3..6..';
      let values   = domObj.value.split("");
      
      let gridPlaces = Solver.populateGridPlaces();
      gridPlaces.forEach((place,index) => {
        let placeObj   = document.getElementById(place);
        placeObj.value = values[index]; 
      })
      
      Solver.clearTextAndGrid();
      
      let isTextAreaCleared = ( domObj.value == '' ) ? true : false;
      let isGridCleared = gridPlaces.every(place => {
        let placeObj = document.getElementById(place);
        return (placeObj.value == '');
      })
      
      assert.equal(isTextAreaCleared, true);
      assert.equal(isGridCleared, true);
      done();
    });
    
    // Pressing the "Solve" button solves the puzzle and
    // fills in the grid with the solution
    test('Function showSolution(solve(input))', done => {
      let input  = '..9..5.1.85.4....2432......1...69.83.9.....6.62.71...9......1945....4.37.4.3..6..';
      let ans    = Solver.solveSudoku(input);
      let answer = ans.split("");
      let textArea = document.getElementById('text-input');
      let isTextFilled = (textArea.value == ans)? true : false;
      
      let gridPlaces = Solver.populateGridPlaces();
      let isGridFilled = gridPlaces.every((place,index) => {
        let obj = document.getElementById(place);
        return (obj.value === answer[index]);
      });
      
      assert.equal(isTextFilled, true);
      assert.equal(isGridFilled, true);
      done();
    });
  });
});

