import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import TestComponent from './fccTest';
import RandomQuoteMachineComponent from './RandomQuoteMachine/quote-machine';

import 'bootstrap/dist/css/bootstrap.min.css';
import './RandomQuoteMachine/styles.css';

import {Row} from 'reactstrap';

class MainComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<body>
				  <Row id = 'spacer'>
				  </Row>
				  	<TestComponent/>
					<div id="container">
						<RandomQuoteMachineComponent/>
					</div>
				</body>)
	}
}

ReactDOM.render(<MainComponent/>, document.getElementsByTagName('body')[0]);