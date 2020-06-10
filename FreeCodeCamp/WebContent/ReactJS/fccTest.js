import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import ReactFCCtest from 'react-fcctest';

class TestComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<ReactFCCtest/>);
	}
} 

export default TestComponent;