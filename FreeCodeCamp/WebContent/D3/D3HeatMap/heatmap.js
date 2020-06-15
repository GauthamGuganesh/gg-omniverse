let extractAndProcess = () =>
{

// set the dimensions and margins of the graph
 var margin = {top: 30, right: 30, bottom: 30, left: 30},
  width = 1200 - margin.left - margin.right,
  height = 450 - margin.top - margin.bottom;

// append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
            .append("svg")
            .attr("id", "title")
  			.attr("width", width + margin.left + margin.right)
  			.attr("height", height + margin.top + margin.bottom)
  			.attr("background-color", "#DDDDDD")
			.append("g")
 			.attr("transform", "translate(" + margin.left + "," + margin.top + ")")

// Labels of row and columns
var myRows = [];

for(let i = 1752; i <= 2016; i++)
{
	myRows.push(i); //years from 1753-2016.
}

var myColumns = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]

// Build X scales and axis:
var x = d3.scaleBand()
          .domain(myRows)
          .range([0, width])


var xAxis = d3.axisBottom(x);
xAxis.tickValues(x.domain().filter( year => year % 10 == 0)); // Years divisible by 10 alone tick values.


svg.append("g")
   .attr("id", "x-axis")
   .attr("transform", "translate(0," + height + ")")
   .call(xAxis);



// Build Y scales and axis:
var y = d3.scaleBand()
  		  .domain(myColumns)
  		  .range([0, height]);

svg.append("g")
   .attr("id", "y-axis")
   .call(d3.axisLeft(y));

// Build color scale
var myColor = d3.scaleLinear()
  .range(["white", "#69B3A2"])
  .domain([0,14]) //Color variance for different variance values. (start variance : 1.7, highest: 13.9)


let tooltip = d3.select('#custom-tooltip')			
 					.append('div')
 					.attr('id','tooltip')
 					.style("opacity", 0)
    				.style("background-color", "#DDDDDD")
  				    .style("border", "solid")
    				.style("border-width", "thin")
    				.style("border-radius", "5px")
    				.style("padding", "5px");	


//Read the data
d3.json("https://raw.githubusercontent.com/freeCodeCamp/ProjectReferenceData/master/global-temperature.json", (data) => {

let baseTemperature = parseFloat(data.baseTemperature);

  svg.selectAll("rect")
      .data(data.monthlyVariance)
      .enter()
      .append("rect")
      .attr("class", "cell")
      .attr("x", d => x(d.year) )
      .attr("y", d => y(myColumns[d.month-1]) ) // Since month array starts from 0, the given data month starts from 01 as January.
      .attr("width", x.bandwidth() )
      .attr("height", y.bandwidth() )
      .attr("data-month", d => d.month-1)
      .attr("data-year",d => d.year)
      .attr("data-temp", d => baseTemperature - parseFloat(d.variance))
      .style("fill", d => {

      		let temperature = baseTemperature - parseFloat(d.variance);
      		return myColor(temperature);

      })
      .on('mouseover', (d) => {

      	xloc = x(d.year) + 5;
      	yloc = y(myColumns[d.month-1]);

      	tooltip.style('width', '65px')
 	   			   .style('height', '30px')
 	   			   .style('transform', 'translate(' + xloc + "px" + "," + yloc + "px)") //Transform - translate(x,y) -> moves the element to specified coordinate (in px) 
 	   			   .attr('data-year', d.year)
 	   			   .text("Year:" + d.year + ", Temp:" + (baseTemperature - parseFloat(d.variance)))
 	   			   .style('opacity', 1)
 	   			   .style('font-size', '12px')
 	   		

      })
      .on('mouseleave', () => tooltip.style('opacity', 0));

})

d3.select("#legend").selectAll("rect")
  .data([5,2,8,13])
  .enter()
  .append("rect")
  .attr("height", "20px")
  .attr("width", "20px")
  .style("fill", d => myColor(d));


}



extractAndProcess();


