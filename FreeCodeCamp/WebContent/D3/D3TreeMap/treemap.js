let buildTreeMap = () =>
{
	let width = 1000;
	let height = 800;

	let svg = d3.select('#mydiv')
	  .append('svg')
	  .attr('width', width)
	  .attr('height', height)
	  .style('background-color', "#DDDDDD");

	let legend = d3.select('#legend')
	               .append('svg')
	               .attr('width', 500)
	               .attr('height', 500);

	let tooltip = d3.select('#tooltip')			
 					.style("opacity", 0)
    				.style("background-color", "#DDDDDD")
  				    .style("border", "solid")
    				.style("border-width", "thin")
    				.style("border-radius", "5px")
    				.style("padding", "5px");


	d3.json('https://cdn.freecodecamp.org/testable-projects-fcc/data/tree_map/video-game-sales-data.json', (data) => {

		let colors = ['red','orange','yellow','green','blue','purple', 'cyan', '#fff7ec', '#80b1d3', '#fddbc7'];


		let categoryColor = data.children.reduce( (acc, child) => {

			let random = Math.floor(Math.random() * 10);
			acc[child.name] = colors[random];
			return acc;

		}, {}); //Build a single object where each category corresponds to a particular color.

		let categories = Object.keys(categoryColor);
		let legends = categories.map( categ => ({ category: categ }) );

		//Most important. Creating the treemap.
		let root = d3.hierarchy(data).sum( d => d.value);
		let treemap = d3.treemap()
		  				.size([width, height])
		  				.padding(2);

		treemap(root);

	svg.selectAll('rect')
	   .data(root.leaves())
	   .enter()
	   .append('rect')
	   .attr('class', 'tile')
	   .attr('x', d => d.x0)
	   .attr('y', d => d.y0)
	   .attr('width', d => (d.x1 - d.x0))
	   .attr('height', d => (d.y1 - d.y0))
	   .attr('data-name', d => d.data.name)
	   .attr('data-category', d => d.data.category)
	   .attr('data-value', d => d.data.value)
	   .style('stroke', 'black')
	   .style('fill', d => categoryColor[d.data.category] )
	   .on('mouseover', d => {

	   		let x = d.x0 + 10;
	   		let y = d.y0 + 10;

	   		tooltip.style('width', '65px')
 	   			   .style('height', '30px')
 	   			   .style('transform', 'translate(' + x + "px" + "," + y + "px)") //Transform - translate(x,y) -> moves the element to specified coordinate (in px) 
 	   			   .attr('data-value', d.data.value)
 	   			   .text("Name : " + d.data.name)
 	   			   .style('opacity', 1)
 	   			   .style('font-size', '12px') })

	   .on('mouseleave', () => tooltip.style('opacity', '0'));

	svg.selectAll('text')
	   .data(root.leaves())
	   .enter()
	   .append('text')
	   .attr('x', d => d.x0)
	   .attr('y', d => d.y0 + 10)
	   .text(d => d.data.name)
	   .attr('font-size', '12px');

	legend.selectAll('rect')
	      .data(legends)
	      .enter()
	      .append('rect')
	      .attr('x', d => 100 )
	      .attr('y', (d, i) => i * 20 )
	      .attr('width', 10)
	      .attr('height', 10)
	      .attr('class', 'legend-item')
	      .style('fill', d => categoryColor[d.category]);

	legend.selectAll('text')
	      .data(legends)
	      .enter()
	      .append('text')
	      .attr('x', d => 100 + 50)
	      .attr('y', (d, i) => i * 20.75)
	      .text(d => d.category)
	      .style('font-size', '10px');
          
	});
}

buildTreeMap();