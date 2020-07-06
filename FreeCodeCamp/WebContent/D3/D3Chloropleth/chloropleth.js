
let buildchloropleth = () =>
{
	let width = 1200;
	let height = 600;

	let svg = d3.select('#mydiv')
	            .append('svg')
	            .attr('width', width)
	            .attr('height', height)
	            .style('background-color', '#DDDDDD');

	let projection = d3.geoMercator() //The type of map you need to draw. Here mercator --> is world map
					   .scale(150) // Size of the total map.
					   .center([0, 50])  // (x,y) Position the map. 
					   .translate([width/2, height/2]);

	let path = d3.geoPath().projection(projection);


    let data = d3.map(); // Data to be mapped. data.set(d.code, d.pop) => in API call takes values from CSV and forms this data object. 
    // console.log(data); 
    let colorScale = d3.scaleThreshold()
  						.domain([100000, 1000000, 10000000, 30000000, 100000000, 500000000])
  						.range(d3.schemeBlues[7]);


  	let ready = (err, topo) => 
  	{
  		console.log('called');
  		svg.append('g')
  		   .selectAll('path')
  		   .data(topo.features)
  		   .enter()
  		   .append('path') // Each geography has a path represented by the PATH element here. Value for PATH element in attribute 'd' which is filled by d3.geoPath.projection.
  		   //Drawing each country with 'd'
  		   .attr('d', path) //Each path object is a country(like 'rect').
  		   .attr('fill', d => {

  		   		console.log(d); // d = Data (ie) features from geojson file.
  		   		d.total = data.get(d.id) || 0; //Total = the total population that is retrieved from json/csv and added to 'data' object.
  		   									   //d.id = id from geojson object. 'id' the common field between geojson and the csv. 
  		   									   //Using id from geojson we are retrieving population based on which color is filled.
  		   		return colorScale(d.total);

  		   })
  		   
  	d3.queue()
  	  .defer(d3.json, "https://raw.githubusercontent.com/holtzy/D3-graph-gallery/master/DATA/world.geojson") //1st API call retrieves geojson.
  	  .defer(d3.csv, "https://raw.githubusercontent.com/holtzy/D3-graph-gallery/master/DATA/world_population.csv", function(d) { data.set(d.code, d.pop) }) //2nd API call. Setting 
  	  																																						 //data that is to be mapped
  	  																																						 //d.code,d.pop - data in CSV.

  	  .await(ready); // When each asynchronous fetch is finished, the function inside await is executed. Can also be done through Promises.all([promises])
}

buildchloropleth();