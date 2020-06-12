import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import {Provider, connect} from 'react-redux';
import {Input, Row, Col} from 'reactstrap';
import {store} from './redux-reducer';
import marked from 'marked';

import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.scss';

class PreviewerComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	componentDidMount()
	{
		document.getElementById('preview').innerHTML = marked(this.props.propInputText);
	}

	render()
	{		
		if(document.getElementById('preview') != null)
		{
			document.getElementById('preview').innerHTML = this.props.propInputText;
		}

		return (<div id='previewer-container' className="container-fluid">
					<Row id = 'previewer-row'>
						<Col className = 'col-md-2'></Col> 
							<div className = 'col-md-8' id = 'preview'> {/*ONLY STORE SUBSCRIPTION THROUGH PROPS*/}
							</div>
						<Col className = 'col-md-2'></Col>
					</Row>
				</div>);
	}
}

class PreviewWrapperComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<Provider store={store}>
		 			<PreviewConnectorComponent/>
		 		</Provider>)
	}
}

const mapStateToProps = (state) =>
{
	return {propInputText : state.input};
}

const PreviewConnectorComponent = connect(mapStateToProps, null)(PreviewerComponent);

export default PreviewWrapperComponent;

