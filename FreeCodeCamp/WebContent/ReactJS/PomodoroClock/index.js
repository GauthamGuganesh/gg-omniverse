import React,{Component} from 'react';
import ReactDOM from 'react-dom';

import ClockProvider from './PomodoroClock/clock';
import TestComponent from './fccTest';

import './PomodoroClock/styles.css';
import 'bootstrap/dist/css/bootstrap.min.css';


class MainComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<div id = 'main'>
					<TestComponent/>
					<ClockProvider/>
				</div>)
	}
}

ReactDOM.render(<MainComponent/>, document.getElementsByTagName('body')[0]);
