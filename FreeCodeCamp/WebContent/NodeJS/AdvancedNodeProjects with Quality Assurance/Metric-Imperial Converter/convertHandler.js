/*
*
*
*       Complete the handler logic below
*       
*       
*/

function ConvertHandler() {
  
  this.getNum = function(input) {
    var result;
    let regex = /[a-z]+/ig;
    
    let unit = input.match(regex);
    unit = (unit) ? unit.join("") : 'invalid number'; 
    let unitStartIndex = input.indexOf(unit[0]);
    
    result = input.substring(0, unitStartIndex);
    if(result == '') return parseFloat(1).toFixed(1);
    if(result.indexOf('/') !== result.lastIndexOf('/'))
      return 'invalid number';
    
    result = (result) ? parseFloat(eval(result)).toFixed(1) : "invalid number";
    return result;
  };
  
  this.getUnit = function(input) {
    let regex = /[a-z]+/ig;
    let units = ['gal', 'l', 'lbs', 'kg', 'mi', 'km'];
    
    var result = input.match(regex);  
    result = (result) ? result.join("") : "invalid unit";
    result = units.indexOf(result.toLowerCase()) == -1 ? "invalid unit" : result;
    return result;
  };
  
  this.getReturnUnit = function(initUnit) {
    var result;
    
    switch(initUnit.toLowerCase())
    {
      case 'gal': result = 'l'; break;
      case 'l': result = 'gal'; break;
      case 'lbs': result = 'kg'; break;
      case 'kg': result = 'lbs'; break;
      case 'mi': result = 'km'; break;
      case 'km': result = 'mi'; break;
      default: result = 'invalid unit';
    }
    
    return result;
  };

  this.spellOutUnit = function(unit) {
    var result;
    switch(unit.toLowerCase())
    {
      case 'gal': result = 'gallons'; break;
      case 'l': result = 'liters'; break;
      case 'lbs': result = 'pounds'; break;
      case 'kg': result = 'kilograms'; break;
      case 'mi': result = 'miles'; break;
      case 'km': result = 'kilometers'; break; 
    }
    return result;
  };
  
  this.convert = function(initNum, initUnit) {
    const galToL = 3.78541;
    const lbsToKg = 0.453592;
    const miToKm = 1.60934;
    var result;
    
    switch(initUnit.toLowerCase())
    {
      case 'gal': result = initNum * galToL; break;
      case 'l': result = (initNum / galToL); break;
      case 'lbs': result = initNum * lbsToKg; break;
      case 'kg': result = (initNum / lbsToKg); break;
      case 'mi': result = initNum * miToKm; break;
      case 'km': result = (initNum / miToKm); break;
      default: result = 'invalid values';
    }
    
    result = parseFloat(result).toFixed(5);

    return (!isNaN(result)) ? (parseFloat(result).toFixed(5)) : 'invalid number';
  };
  
  this.getString = function(initNum, initUnit, returnNum, returnUnit) {
    var result = `${initNum} ${initUnit} converts to ${returnNum} ${returnUnit}`;
  
    if(result.indexOf('invalid') != -1)
      return 'invalid values';
    return result;
  };
  
}

module.exports = ConvertHandler;
