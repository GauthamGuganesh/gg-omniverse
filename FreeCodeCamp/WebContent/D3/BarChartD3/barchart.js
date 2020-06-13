		//AJAX CALL.
let dataset = undefined;

fetch('https://raw.githubusercontent.com/freeCodeCamp/ProjectReferenceData/master/GDP-data.json')
	.then( response => response.json())
	.then( data =>  {

		dataset = data.data;
		extractAndProcess();

	});


let extractAndProcess = () =>
{

	let yearDataSet = dataset.map( data => {

		let year = data[0].split('-')[0]; // Date in yy-mm-dd format. Splitting year.
		return [year, data[1]];
	});

	// 'ul' should already have been there(select 'body' and append 'ul'. li alone can be dynamically generated.

 	let w = 1000;
 	let h = 600;
 	let padding = 60;

 	let svg = d3.select('body')
 				.append('svg')
 				.attr('id', 'title')
 				.attr('width', w)
 				.attr('height', h)		
 				.style('background-color','#DDDDDD');


//Y-Axis
 	let yScale = d3.scaleLinear();
 	yScale.domain([0, 18064.7])
 		  .range([0, h - 200]);  // Since linear bar chart, we need correspondingly scalable values.

 	let yAxisInvertScale = d3.scaleLinear();
 	yAxisInvertScale.domain([0, 18064.7])
 			 .range([h - 200, 0]); //Y-axis in an SVG is default inverted. So we invert the range for the axis alone, so that axis isn't in reverse.

 	let yAxis = d3.axisLeft(yAxisInvertScale);


// To scale the width(x-axis) of data within the SVG element.
 	let xScale = d3.scaleLinear();
 	xScale.domain([0, 2740]) // 2740 is total width. 0-274 elements, each multiplied by 10 (i*10 below) for width placement. 
 	      .range([padding, w - padding]);

// X-AXIS with TIME Objects.

// Initially, convert all string into date objects.

 	let parseTime = d3.timeParse('%Y-%m-%d'); // Specifying format for given date string(fetched by AJAX). timeParse returns a function.
 	let dates = dataset.map( data => parseTime(data[0]) ); //Converting given date-string(in that specified format) into date object.

 	let domain = d3.extent(dates); // d3.extent returns an array of length 2 with minimum and maximum values in a array.
 	let xAxisScale = d3.scaleTime(); //Since we have date objects as domain, we need scaleTime and not scale Linear.
 	xAxisScale.domain(domain) // setting date objects as domain.
 			  .range([padding, w - padding])

 	let xAxis = d3.axisBottom(xAxisScale);
 	xAxis.tickValues(d3.timeYear.range(dates[10], domain[1], 5)); // Specifying ticks to display tick labels every 5 years. (d3.timeYear is a time interval object) 
 																  // domain[1] is max date. dates[10] is given so that tick starts from 1950 and not 1947.


 	//Creating custom tool-tip
 	
 	let tooltip = d3.select('#custom-tooltip')			
 					.append('div')
 					.attr('id','tooltip')
 					.style("opacity", 0)
   				    .attr("class", "tooltip")
    				.style("background-color", "#DDDDDD")
  				    .style("border", "solid")
    				.style("border-width", "thin")
    				.style("border-radius", "5px")
    				.style("padding", "5px");	

 	svg.selectAll('rect')
 	   .data(dataset)
 	   .enter()
 	   .append('rect')
 	   .attr('x', (d, i) => xScale(i*10) )
 	   .attr('y', d => h - yScale(d[1]) - padding)
 	   .attr('height', d => yScale(d[1]) )
 	   .attr('class', 'bar')
 	   .attr('data-date', d => d[0])
 	   .attr('data-gdp', d => d[1])
 	   .on('mouseover', (d, i) => {  // Adding mouse event (takes a callback) here itself so that you have access to each data and its index as well as X,Y positions

 	   		tooltip.style('width', '50px')
 	   			   .style('height', '50px')
 	   			   .style('transform', 'translate(' + (xScale(i*10) + 5) + "px" + ",100px)") //Transform - translate(x,y) -> moves the element to specified coordinate (in px) 
 	   			   .attr('data-date', d[0])
 	   			   .text("$" + d[1] + "Billion")
 	   			   .style('opacity', 1);

 	   })
 	   .on('mouseleave', () => tooltip.style('opacity', 0) );
 	 

 	svg.append('g')
 	   .attr('transform', 'translate('+ 0 + ',' + (h - padding) + ')') 
 	   .attr('id','x-axis')
 	   .call(xAxis);

 	svg.append('g')
 	   .attr('transform', 'translate(' + (padding) + ',' +  (h - yScale(dataset[274][1]) - padding) + ')' )
 	   .attr('id','y-axis')
 	   .call(yAxis);

}














