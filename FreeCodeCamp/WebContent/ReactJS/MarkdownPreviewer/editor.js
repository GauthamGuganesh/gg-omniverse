import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.scss';

import {Provider, connect} from 'react-redux';
import {Row, Col} from 'reactstrap';
import {store} from './redux-reducer';
import marked from 'marked';

const TEXTACTION = 'TEXT-ACTION';

class EditorComponent extends React.Component
{
	constructor(props){
		super(props);

		this.changeHandler = this.changeHandler.bind(this);
	}

	changeHandler = (event) => 
	{
		let textAreaValue = event.target.value;
		this.props.changeHandlerProp(marked(textAreaValue)); //ONLY DISPATCH
	}

	render()
	{
		return (<div id='editor-container' className="container-md">
					<Row id = 'editor-row'>
						<Col className = 'col-md-3'></Col> 
							<textarea className = 'col-md-6' id="editor" type="textarea" onChange = {this.changeHandler}>
								{this.props.propInputText}
							</textarea>
						<Col className = 'col-md-3'></Col>
					</Row>
				</div>)
	}
}

class EditorWrapperComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<Provider store={store}>
		           <ConnectedEditorComponent/>
		        </Provider>);
	}
}

const textAction = (value) => 
{
	return ( {type: TEXTACTION, text: value} );
}

const mapStateToProps = (state) =>
{
	return {propInputText : state.input};
}

const mapDispatchToProps = (dispatch) =>
{
	return {changeHandlerProp : (textValue) => dispatch(textAction(textValue)) }
}
	
const ConnectedEditorComponent = connect(mapStateToProps, mapDispatchToProps)(EditorComponent);

export default EditorWrapperComponent;

