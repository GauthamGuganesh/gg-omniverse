
let buildchloropleth = () =>
{
	let width = 1200;
	let height = 600;

	let svg = d3.select('#mydiv')
	            .append('svg')
	            .attr('width', width)
	            .attr('height', height);

  let tooltip = d3.select('#tooltip')      
                  .style("opacity", 0)
                  .style("background-color", "#DDDDDD")
                  .style("border", "solid")
                  .style("border-width", "thin")
                  .style("border-radius", "5px")
                  .style("padding", "5px");  

  let legend = d3.select('#legend')
                 .append('svg')
                 .attr('width', 500)
                 .attr('height', 500);              

	let projection = d3.geoAlbersUsa() //The type of map you need to draw. Here mercator --> is world map
                     .scale(1300)
                     .translate([487.5, 305]);

	console.log(projection);

	let path = d3.geoPath();


  let data = d3.map(); // Data to be mapped. data.set(d.code, d.pop) => in API call takes values from CSV/json and forms this data object. 
  console.log(data); 

	let ready = (err, topo, edudata) => 
	{
    //edudata = an array of json objects that is fetched by 2nd API call. 
    //d.fips,d.bachelorsOrHigher - data in json. fips - the common 'id' field //between geojson and json.
    edudata.forEach( d =>  data.set(d.fips, d.bachelorsOrHigher)); 

    let counties = edudata.reduce( (acc, d) => {

         acc[d.fips] = d.area_name;
         return acc;

       }, {});

    let colorScale = d3.scaleThreshold()
              .domain([2.1, 20.3, 40.6, 75.1, 98]) //Splitting the range into approxly 5 equal halves and assigning each range the same color of varying darkness.
              .range(["#fee5d9", "#fcae91", "#fb6a4a", "#de2d26", "#a50f15"]);
                                                                                                                                      
    //topo = result from 1st API call                                                                                                                                     
		svg.append('g') // <path d=''> 'Path' with 'd' attribute - The central feature of D3 chloropleth.
       .selectAll('path')
		   .data(topojson.feature(topo, topo.objects.counties).features) //converting counties in topojson file into geojson and then taking it's 'features' to map data.
		   .enter()
        //Drawing each county with 'd'
		   .append('path', d => d3.geoPath.projection(projection)) // Each geography has a path represented by the PATH element here. Value for PATH element in attribute 'd' which is filled by d3.geoPath.projection.
       .attr('d', path) //Each path object is a country(like 'rect').
       .attr('class', 'county')
       .style("stroke", "#DDD")
       .attr('data-fips', d => d.id)
       .attr('data-education', d => data.get(d.id))
		   .attr('fill', d => colorScale(data.get(d.id)) ) //d.id = id from geojson object. 'id' the common field between geojson and the csv/json. 
                                                            //Using id from geojson we are retrieving education data based on which color is filled.
       .on('mouseover', d => {

        let x = d3.mouse(d3.event.currentTarget)[0];
        let y = d3.mouse(d3.event.currentTarget)[1];

        tooltip.style('width', '65px')
               .style('height', '30px')
               .style('transform', 'translate(' + x + "px" + "," + y + "px)") //Transform - translate(x,y) -> moves the element to specified coordinate (in px) 
               .attr('data-education', data.get(d.id))
               .text("County : " + counties[d.id])
               .text("Education  : " + data.get(d.id) + "%")
               .style('opacity', 1)
               .style('font-size', '12px');

         })

       .on('mouseleave', () => tooltip.style('opacity', '0'));

    legend.selectAll('rect')
        .data([2.1, 20.3, 40.6, 75.1, 98])
        .enter()
        .append('rect')
        .attr('x', (d, i) => i*20 )
        .attr('y', 100 )
        .attr('width', 10)
        .attr('height', 10)
        .style('stroke', '#000')
        .style('fill', d => colorScale(d));

  }

	d3.queue()
	  .defer(d3.json, "https://cdn.freecodecamp.org/testable-projects-fcc/data/choropleth_map/counties.json") //1st API call retrieves topojson.
	  .defer(d3.json, "https://cdn.freecodecamp.org/testable-projects-fcc/data/choropleth_map/for_user_education.json") //2nd API call retrieves edu data.                                                                       	  																																						 
	  .await(ready); // When each asynchronous fetch is finished, the function inside await is executed. Can also be done through Promises.all([promises])
}

buildchloropleth();
















