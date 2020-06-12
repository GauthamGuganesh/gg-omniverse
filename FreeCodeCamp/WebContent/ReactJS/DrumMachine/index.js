import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import DrumMachineComponent from './DrumMachine/DrumMachineComponent';
import TestComponent from './fccTest';
import './DrumMachine/styles.scss';

class MainComponent extends React.Component
{
	constructor(props)
	{
		super(props)
	}

	render()
	{
		return (<div id="main">
					<TestComponent/>
			       <DrumMachineComponent/>
			    </div>)
	}
}

ReactDOM.render(<MainComponent/>, document.getElementsByTagName('body')[0]);