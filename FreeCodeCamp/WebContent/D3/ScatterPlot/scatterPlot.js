let dataset = [];

fetch('https://raw.githubusercontent.com/freeCodeCamp/ProjectReferenceData/master/cyclist-data.json')
	.then(response => response.json())
	.then(json => {

		dataset = json.map( data => [data.Time, data.Year]);
		processData();
	})

let processData = () =>
{
	let h = 600;
	let w = 1000;
	let padding = 60; //SVG dimensions

	// Converting minute-string to date objects for Y-axis.
	let parseTime = d3.timeParse('%M:%S');

	let dates = dataset.map( data => parseTime(data[0]));
	let domainY = d3.extent(dates);

	let xScale = d3.scaleLinear()
				   .domain([1993, 2016])
				   .range([padding, w - padding]);

	let xAxis = d3.axisBottom(xScale);


	let yScale = d3.scaleTime()
				   .domain([parseTime("36:40"), parseTime("39:50")])		
				   .range([h - padding, padding]);		

	let yAxiScaleInvert = d3.scaleTime()
							.domain([parseTime("36:40"), parseTime("39:50")])
							.range([padding, h - padding]);

	let yAxis = d3.axisLeft(yAxiScaleInvert);


	let svg = d3.select('body')
	  .append('svg')
	  .attr('id', 'title')
	  .attr('height', h)
	  .attr('width', w)
	  .style('background-color', '#DDDDDD');

	svg.selectAll('circle')
	   .data(dataset)
	   .enter()
	   .append('circle')
	   .attr('class', 'dot')
	   .attr('data-xvalue', d => d[1])
	   .attr('data-yvalue', (d,i) => dates[i])
	   .attr('cx', d => xScale(d[1]) )
	   .attr('cy', (d,i) => h - yScale(dates[i]))
	   .attr('r', 4)
	   .on('mouseover', (d, i) => {  // Adding mouse event (takes a callback) here itself so that you have access to each data and its index as well as X,Y positions

	   		let x = xScale(d[1]) + 5;
	   		let y = h - yScale(dates[i]) + padding;

 	   		tooltip.style('width', '65px')
 	   			   .style('height', '30px')
 	   			   .style('transform', 'translate(' + x + "px" + "," + y + "px)") //Transform - translate(x,y) -> moves the element to specified coordinate (in px) 
 	   			   .attr('data-year', d[1])
 	   			   .text("Year:" + d[1] + ", Time:" + d[0])
 	   			   .style('opacity', 1)
 	   			   .style('font-size', '12px')
 	   			})

	   .on('mouseleave', () => tooltip.style('opacity', '0'));

	xAxis.tickFormat(d3.format("d"));


	svg.append('g')
	   .attr('id', 'x-axis')
	   .attr('transform', 'translate(' + 0 + "," + (h - padding) + ")")
	   .call(xAxis);

	yAxis.tickFormat(d3.timeFormat('%M:%S'));
	yAxis.tickValues(d3.timeSecond.range(parseTime("36:50"), parseTime("39:50"), 15)); // Specifying ticks to display tick labels every 15 minutes. (d3.timeMinute is a time interval object) 


	svg.append('g')
	   .attr('id', 'y-axis')
	   .attr('transform', 'translate('+ padding + ",0)")
	   .call(yAxis);

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

}


