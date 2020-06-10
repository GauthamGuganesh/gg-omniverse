import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {Button, Jumbotron, Row, Col} from 'reactstrap';

import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.css';
import {FaTwitterSquare} from 'react-icons/fa';
import $ from 'jquery';

const iconStyle =  [ {color: '#008040'}, 
							 {color: '#007faa'},
							 {color: '#886288'},
							 {color: '#6c7a89'},
							 {color: '#8d6708'},
							 {color: '#d43900'},
							 {color: '#dc2a2a'},
							 {color: '#008040'},
							 {color: '#007faa'},
							 {color: '#886288'} ];

const buttonStyle = [ {backgroundColor: '#008040', border: '#008040'}, 
							 {backgroundColor: '#007faa', border: '#007faa'},
							 {backgroundColor: '#886288', border: '#886288'},
							 {backgroundColor: '#6c7a89', border: '#6c7a89'},
							 {backgroundColor: '#8d6708', border: '#8d6708'},
							 {backgroundColor: '#d43900', border: '#d43900'},
							 {backgroundColor: '#dc2a2a', border: '#dc2a2a'},
							 {backgroundColor: '#008040', border: '#008040'},
							 {backgroundColor: '#007faa', border: '#007faa'},
							 {backgroundColor: '#886288', border: '#886288'} ];

const quotesObjectArray = [ {quote: 'Be yourself; everyone else is already taken.', author: '-Oscar Wilde'},
		                            {quote: 'So many books, so little time.', author: '-Frank Zappa'},
		                            {quote: 'Be the change that you wish to see in the world.', author: '-Mahatma Gandhi'},
		                            {quote: 'In three words I can sum up everything I\'ve learned about life: it goes on.', author: '-Robert Frost'},
		                            {quote: 'If you tell the truth, you don\'t have to remember anything.', author: '-Mark Twain'},

		                            {quote: 'A friend is someone who knows all about you and still loves you.', author: '-Elbert Hubbard'},
		                            {quote: 'Without music, life would be a mistake.', author: '-Friedrich Nietzsche'},
		                            {quote: 'The fool doth think he is wise, but the wise man knows himself to be a fool.', author: '-William Shakespeare'},
		                            {quote: 'It is not a lack of love, but a lack of friendship that makes unhappy marriages.', author: '-Friedrich Nietzsche'},
		                            {quote: 'If you don\'t stand for something you will fall for anything.', author: '-Gordon A. Eadie'} ];


class RandomQuoteMachineComponent extends React.Component
{
	constructor(props){
		super(props);

		this.state={
			randomNumber: 1
		}

		this.generateRandom = this.generateRandom.bind(this);
	}

	componentDidMount()
	{
		$('document').ready( () => { 
			$('Button').click( ()=>{
										$('#text').animate({opacity: 0}, 500, ()=>
										{
											$('#text').animate({opacity: 1}, 500);
											$('#text').html(quotesObjectArray[this.state.randomNumber].quote);
											$('#text').css(iconStyle[this.state.randomNumber]);
										});

										$('#author').animate({opacity: 0}, 500, ()=>
										{
											$('#author').animate({opacity: 1}, 500);
											$('#author').html(quotesObjectArray[this.state.randomNumber].author);
											$('#author').css(iconStyle[this.state.randomNumber]);
										});

										$('#new-quote').animate({opacity: 0.8}, 500, ()=>
										{
											$('#new-quote').animate({opacity: 1}, 500);
											$('#new-quote').css(buttonStyle[this.state.randomNumber]);
										});

										$('#icon').animate({opacity: 0.8}, 500, ()=>
										{
											$('#icon').animate({opacity: 1}, 500);
											$('#icon').css(iconStyle[this.state.randomNumber]);
										});

										$('body').animate({opacity: 0.8}, 500, ()=>
										{
											$('body').animate({opacity: 1}, 500);
											$('body').css(buttonStyle[this.state.randomNumber]);
										});
									}
		 ) });
	}

	generateRandom = () =>
	{
		this.setState( () => ( {randomNumber: Math.floor(Math.random() * 10)} ) );
	}

	render()
	{

		return (<div className='container-fluid'>
					<Row>
						<p className='col-md-4'></p>
					     <Jumbotron className='col-md-4'>
							<div id = 'quote-box'>
								<p id="text">{quotesObjectArray[0].quote}</p>

								<p id="author">{quotesObjectArray[0].author} </p>

								<Row>
									<a href = "https://twitter.com/intent/tweet" className = "col-md-8" id="tweet-quote">

										<FaTwitterSquare id="icon" style = {iconStyle[this.state.randomNumber]} />

									</a>
									
									<Button id="new-quote" className = "button-class col-md-4" color = "primary" onClick = {this.generateRandom}>New Quote</Button>
									
								</Row>
							</div>
						  </Jumbotron>
				 	</Row>
			    </div>)
	}
}



export default RandomQuoteMachineComponent;
