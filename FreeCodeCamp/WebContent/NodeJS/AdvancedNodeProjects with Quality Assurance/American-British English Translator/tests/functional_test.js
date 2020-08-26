/*
 *
 *
 *       FILL IN EACH FUNCTIONAL TEST BELOW COMPLETELY
 *       -----[Keep the tests in the same order!]----
 *       (if additional are added, keep them at the very end!)
 */

const chai = require('chai');
const assert = chai.assert;

let Translator;

suite('Functional Tests', () => {
  suiteSetup(() => {
    // DOM already mocked -- load translator then run tests
    Translator = require('../public/translator.js');
  });

  suite('Function ____()', () => {
    /* 
      The translated sentence is appended to the `translated-sentence` `div`
      and the translated words or terms are wrapped in 
      `<span class="highlight">...</span>` tags when the "Translate" button is pressed.
    */
    test("Translation appended to the `translated-sentence` `div`", done => {
      document.getElementById('locale-select').value = 'american-to-british';
      document.getElementById('text-input').value = 'We had a party at my friend\'s condo.';
      let translateBtn = document.getElementById('translate-btn');
      
      let event = document.createEvent('Event');
      event.initEvent('click', true, true);
      event.eventName = 'click';
      translateBtn.dispatchEvent(event);
      
      let resultDiv = document.getElementById('translated-sentence');
      assert.equal(resultDiv.innerHTML, 'We had a party at my friend\'s <span class="highlight">flat</span>.');
      done();
    });

    /* 
      If there are no words or terms that need to be translated,
      the message 'Everything looks good to me!' is appended to the
      `translated-sentence` `div` when the "Translate" button is pressed.
    */
    test("'Everything looks good to me!' message appended to the `translated-sentence` `div`", done => {
      document.getElementById('locale-select').value = 'american-to-british';
      document.getElementById('text-input').value = 'epid iruka thambi';
      let translateBtn = document.getElementById('translate-btn');
      
      let event = document.createEvent('Event');
      event.initEvent('click', true, true);
      event.eventName = 'click';
      translateBtn.dispatchEvent(event);
      
      let resultDiv = document.getElementById('translated-sentence');
      assert.equal(resultDiv.innerHTML, 'Everything looks good to me!');
      done();
    });

    /* 
      If the text area is empty when the "Translation" button is
      pressed, append the message 'Error: No text to translate.' to 
      the `error-msg` `div`.
    */
    test("'Error: No text to translate.' message appended to the `translated-sentence` `div`", done => {

      document.getElementById('locale-select').value = 'american-to-british';
      document.getElementById('text-input').value = '';
      let translateBtn = document.getElementById('translate-btn');
      
      let event = document.createEvent('Event');
      event.initEvent('click', true, true);
      event.eventName = 'click';
      translateBtn.dispatchEvent(event);
      
      let resultDiv = document.getElementById('error-msg');
      assert.equal(resultDiv.innerHTML, 'Error: No text to translate.');
      done();
    });

  });

  suite('Function ____()', () => {
    /* 
      The text area and both the `translated-sentence` and `error-msg`
      `divs` are cleared when the "Clear" button is pressed.
    */
    test("Text area, `translated-sentence`, and `error-msg` are cleared", done => {
      let textArea = document.getElementById('text-input');
      let translateDiv = document.getElementById('translated-sentence');
      let errorDiv = document.getElementById('error-msg');
      
      textArea.value = "some value";
      translateDiv.innerHTML = "test values";
      errorDiv.innerHTML = "test errors";
      
      let event = document.createEvent('Event');
      event.initEvent('click', true, true);
      event.eventName = 'click';
      document.getElementById('clear-btn').dispatchEvent(event);
      
      assert.equal(textArea.value, "");
      assert.equal(translateDiv.innerHTML, "");
      assert.equal(errorDiv.innerHTML, "");
      done();
    });

  });

});
