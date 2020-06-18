import React,{Component} from 'react';
import ReactDOM from 'react-dom';


import {Row, Col, Container} from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.css';

import {Provider, connect} from 'react-redux';
import {store} from './reducer';


let buttonObjects =[];

class ButtonPanelComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	componentDidMount(){

		let operations = ['add','subtract', 'multiply', 'divide'];

		//Build button object: { id: tagId, displayValue: value }
		let objects = Object.values(document.getElementsByClassName('calc-button'));
		objects.push(document.getElementById('equals'));

	    buttonObjects = objects.map(obj => {

				obj.addEventListener('click', () => {

					let action = undefined;

					if(obj.textContent == '=')
					{
						action = () => ( { type: 'EQUALS'} );
					}
					else if(operations.indexOf(obj.getAttribute('id')) != -1) 
					{
						action = () => ( { type: 'OPERATOR', data: obj.textContent } );
					}
					else if( obj.textContent == '.')
					{
						action = () => ( { type: 'DECIMAL', data: obj.textContent } );
					}
					else if( obj.textContent == 'AC')
					{
						action = () => ( { type: 'CLEAR'} );
					}
					else
					{
						action = () => ( { type: 'INPUT', data: obj.textContent } );
					}

					this.props.propDispatch(action);

				});

		});

	}

	render(){

		return ( <div id = 'buttonpanel'>
					<Container id = 'button-container'>

						<Row id='row-1'>
							<Col id='clear' className='calc-button col-md-6'>AC</Col>
							<Col id='divide' className='calc-button col-md-3'>/</Col>
							<Col id='multiply' className='calc-button col-md-3'>x</Col>
						</Row>

						<Row id='row-2'>
							<Col id='seven' className='calc-button col-md-3'>7</Col>
							<Col id='eight' className='calc-button col-md-3'>8</Col>
							<Col id='nine' className='calc-button col-md-3'>9</Col>
							<Col id='subtract' className ='calc-button col-md-3'>-</Col>
						</Row>

						<Row id='row-3'>
							<Col id='four' className='calc-button col-md-3'>4</Col>
							<Col id='five' className='calc-button col-md-3'>5</Col>
							<Col id='six' className='calc-button' col-md-3>6</Col>
							<Col id='add' className='calc-button col-md-3'>+</Col>
						</Row>

						<Row id='row-4'>
							<Col id='one' className='calc-button col-md-3'>1</Col>
							<Col id='two' className='calc-button col-md-3'>2</Col>
							<Col id='three' className='calc-button col-md-3'>3</Col>
							<Col id='equals' className='equals-button'>=</Col>
						</Row>

						<Row id='row-5'>
							<Col id='zero' className='calc-button col-md-6'>0</Col>
							<Col id='decimal' className='calc-button col-md-3'>.</Col>
						</Row>

					</Container>
				</div>
			
				)

	}	

}

let mapDispatchToProps = (dispatch) =>
{
	return { propDispatch: action => dispatch( action() ) }
}

class ButtonProvider extends React.Component
{
	render(){

		return <Provider store = {store}>
					<ButtonConnect/>
			   </Provider>
	}
}

let ButtonConnect = connect(null, mapDispatchToProps)(ButtonPanelComponent);

export default ButtonProvider;




