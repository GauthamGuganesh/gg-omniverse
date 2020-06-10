import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import MainComponent from './ExternalComponent';
import {store} from './ExternalComponent';
import {connect, Provider} from 'react-redux';
import {createStore, Redux} from 'redux'; 
import TestComponent from './fccTest';

import 'bootstrap/dist/css/bootstrap.min.css';
import {Button} from 'reactstrap';
import {Jumbotron} from 'reactstrap';
import './customStyles.css';

class MyFirstComponent extends React.Component
{
	constructor(props){
		super(props)
	}

	render()
	{
		return (<div className='container-fluid'>
				  <TestComponent/>
				  <Jumbotron id = "Jumbo" className="text-center col-md-12">
						<h1> First react program </h1>
						<MainComponent/>
						<Button color = 'primary'> Learn More </Button>
						<p> Susbscriber 2 Rendering here ! {this.props.propText} </p>
				  </Jumbotron>
			    </div>)
	}
}

class RenderingComp extends React.Component
{
	constructor(props){
		super(props)
	}

	render()
	{
		return (<Provider store={store}>
					<ConnectedComp/>
				</Provider>)
	}
}

let mapStateToProps = (state) =>
{
	return {propText: state.input};
}

const ConnectedComp = connect(mapStateToProps, null)(MyFirstComponent);

ReactDOM.render(<RenderingComp/>, document.getElementById('root'));