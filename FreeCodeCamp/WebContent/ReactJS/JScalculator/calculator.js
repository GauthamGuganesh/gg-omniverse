import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import ButtonProvider from './buttonpanel'


import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.css';

import {Provider, connect} from 'react-redux';
import {store} from './reducer';

class CalculatorComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render(){

		return (<div id = 'calculator'>

					<div id='display'>
						<p id='input'> {this.props.propInput} </p>
					</div>

					<div id='buttons'>
						<ButtonProvider />
					</div>

				</div>)

	}	

}

let mapStateToProps = (state) =>
{
	return ( {propInput: state.input } );
}

class CalculatorProvider extends React.Component
{
	render()
	{
		return <Provider store = {store}>
					<CalculatorConnect/>
				</Provider>
	}
}

let CalculatorConnect = connect(mapStateToProps, null)(CalculatorComponent);

export default CalculatorProvider;






