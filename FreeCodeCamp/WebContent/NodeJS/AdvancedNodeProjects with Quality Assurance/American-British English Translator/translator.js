import { americanOnly } from './american-only.js';
import { britishOnly } from './british-only.js';
import { americanToBritishSpelling } from './american-to-british-spelling.js';
import { americanToBritishTitles } from './american-to-british-titles.js';

const textArea     = document.getElementById('text-input');
const translateBtn = document.getElementById('translate-btn');
const clearBtn     = document.getElementById('clear-btn');

const britishToAmericanSpelling = Object.keys(americanToBritishSpelling).reduce((acc, key) => {
  
  let val  = americanToBritishSpelling[key];
  acc[val] = key; 
  return acc;
}, {});

const britishToAmericanTitles = Object.keys(americanToBritishTitles).reduce((acc, key) => {
  
  let val  = americanToBritishTitles[key];
  acc[val] = key; 
  return acc;
}, {});

translateBtn.addEventListener('click', () => {
   let mode = document.getElementById('locale-select').value;
   let result = translateInput(textArea.value, mode);
  
   if(!result.value)
   {
     document.getElementById('error-msg').innerHTML = 'Error: No text to translate.';
     return;
   }
   else if(result.translatedWords.length == 0)
   { 
     document.getElementById('translated-sentence').innerHTML = 'Everything looks good to me!';
     return;
   }
   let output = result.value;
   result.translatedWords.forEach( word => {
     output = output.replace(word, '<span class="highlight">' + word + '</span>');
   })
   document.getElementById('translated-sentence').innerHTML = output;
});  

clearBtn.addEventListener('click', () => clearData());

let translateInput = (value, mode) => {
  
  if(!value) 
  { 
    return {value: value};
  }
  switch(mode)
  {
    case 'american-to-british':
    {
       let americanWords = Object.keys(americanOnly);
       let americanSpell = Object.keys(americanToBritishSpelling);
       let americanTitles= Object.keys(americanToBritishTitles);
       let britishEqArr = [];
       let translatedTokens = [];
      
      //Whole-Words
       americanWords.forEach( word => {
         let britishEq = americanOnly[word];
         let regex     = new RegExp(word, 'i');
        
         if(value.match(regex))
           britishEqArr.push({token: word, replacement: britishEq});
       });
      
       if(britishEqArr.length > 1)
       {
         let word = britishEqArr[1].token;
         let repl = britishEqArr[1].replacement;
         
         let regex = new RegExp(word, 'i');
         value = value.replace(regex, repl);
         translatedTokens.push(repl);
       }
       else if(britishEqArr.length == 1)
       {
         let word = britishEqArr[0].token;
         let repl = britishEqArr[0].replacement;
         
         let regex = new RegExp(word, 'i');
         value = value.replace(regex, repl);
         translatedTokens.push(repl);
       }
      
      //Spelling
       americanSpell.forEach( spell => {
         let britishEq = americanToBritishSpelling[spell];
         let regex     = new RegExp(spell, 'i');
        
         if(value.match(regex))
           translatedTokens.push(britishEq);
         
         value = value.replace(regex, britishEq);
         
        })
      
      //Titles
       americanTitles.forEach( title => {
        let britishEq = americanToBritishTitles[title];
        let regex     = new RegExp(title, 'i');
        let isUpperCase = false;
        if(value.match(regex))
        {
          let matchedWord = value.match(regex)[0];
          if(matchedWord[0] === matchedWord[0].toUpperCase()) isUpperCase = true;
        }
        
        if(isUpperCase)
        {
          britishEq = britishEq.replace(britishEq[0], britishEq[0].toUpperCase());
          translatedTokens.push(britishEq);
          value = value.replace(regex, britishEq);
        }
                
      })
      
      //Time
      let matchedTime = value.match(/\d+:\d+/g);
      if(matchedTime) 
      {
        value = value.replace(":", ".");
        translatedTokens.push(matchedTime[0]);
      }
  
      return {value: value, translatedWords: translatedTokens};
    }
    case 'british-to-american':
    {
      let britishWords = Object.keys(britishOnly);
      let britishSpell = Object.keys(britishToAmericanSpelling);
      let britishTitles= Object.keys(britishToAmericanTitles);
      
      let translated   = [];
      let translatedTokens = [];
      //Whole-words
      britishWords.forEach( word => {
         let americEq = britishOnly[word];
         let regex    = new RegExp(word, 'i');
        
         if(value.match(regex))
           translated.push({word: word, repl: americEq});               
       });
      
      translated.forEach(obj => {
        if(obj.word !== 'ta') 
        {
          value = value.replace(new RegExp(obj.word, 'i'), obj.repl);
          translatedTokens.push(obj.repl);
        }
      })
      
      //Spelling
      britishSpell.forEach( spell => {
         let americEq = britishToAmericanSpelling[spell];
         let regex     = new RegExp(spell, 'i');
        
         if(value.match(regex))
         {
           translated.push({word: spell, repl: americEq});
           translatedTokens.push(americEq);
         }
         
         value = value.replace(regex, americEq);
        })
      
      let matchingTitles = 0;
      //Titles
      britishTitles.forEach( title => {
        let americEq = britishToAmericanTitles[title];
        let regex     = new RegExp(title, 'i');
        let isUpperCase = false;
        if(value.match(regex))
        {
          let matchedWord = value.match(regex)[0];
          
          if(matchedWord[0] === matchedWord[0].toUpperCase()) 
          {
            americEq = americEq.replace(americEq[0], americEq[0].toUpperCase());
            translated.push({word: matchedWord, repl: americEq});
            matchingTitles++;
          }
        }                
      });
      
      if(matchingTitles == 2)
      {
        value = value.replace(translated[1].word, translated[1].repl);
        translatedTokens.push(translated[1].repl);
      }
      else if(matchingTitles == 1)
      {
        value = value.replace(translated[0].word, translated[0].repl);
        translatedTokens.push(translated[0].repl);
      }
      
      //Time
      let time = value.match(/\d+\.\d+/g);
      if(time) 
      {
        value = value.replace(".", ":");
        translatedTokens.push(time[0]);
      }
      
      return {value: value, translatedWords: translatedTokens};
    }
    default: break;
  }
}

let clearData = () => {
  textArea.value = "";
  document.getElementById('translated-sentence').innerHTML = "";
  document.getElementById('error-msg').innerHTML = "";
}

/* 
  Export your functions for testing in Node.
  Note: The `try` block is to prevent errors on
  the client side
*/
try {
  module.exports = {   
    translateInput: translateInput,
    clearData: clearData
  }
} catch (e) {}
