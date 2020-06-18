import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import TestComponent from './fccTest';
import CalculatorProvider from './JScalculator/calculator'
import {Row} from 'reactstrap';

import 'bootstrap/dist/css/bootstrap.min.css';
import './JScalculator/styles.css';

class MainComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<div id = 'main'> 
					<TestComponent/>
					<Row id = 'spacer' /> 
					<Row id='calculator-row'>
						<CalculatorProvider/>
					</Row>
				</div>);
	}
}

ReactDOM.render(<MainComponent/>, document.getElementsByTagName('body')[0]);