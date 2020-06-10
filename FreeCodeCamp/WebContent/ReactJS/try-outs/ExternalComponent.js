import React from 'react';
import ReactDOM from 'react-dom';
import { Form, FormGroup, Label, Input } from 'reactstrap';
import {Provider, connect} from 'react-redux';
import {createStore, Redux} from 'redux'; 

class ExternalComponent extends React.Component
{

	constructor(props)
	{
		super(props);
		this.handleChange = this.handleChange.bind(this);
	}
	

	handleChange(event)
	{
		let eventData = event.target.value;
		this.props.newChangeAction(eventData);
	}

	render()
	{
		return (<div>
			       <Form className="text-center">
			           <FormGroup>
			               <Label> Controlled Input </Label>
			               <Input type = 'text' value = {this.props.propText} onChange={this.handleChange} />
			            </FormGroup>
			        </Form>
		        	<p> Watch Closely ! {this.props.propText} </p>
			    </div>);
			
	}
}

class MainComponent extends React.Component
{
	constructor(props)
	{
		super(props)
	}

	render()
	{
		return (<Provider store={store}>
			       <Container/>
			    </Provider> )
	}
}

let changeAction = (valueText) =>
{
	return {type: 'CHANGE', text: valueText};
}

let mapStateToProps = (state) =>
{
	return {propText: state.input};
}

let mapDispatchToProps = (dispatch) =>
{
	return { newChangeAction : (controlledData) => { dispatch(changeAction(controlledData)) } }
}

let reducer = (state = {}, action) =>
{
	if(action.type == 'CHANGE')
	{
		return {input: action.text};
	}
	else
	{
		return state;
	}
}

export let store = createStore(reducer);
const Container = connect(mapStateToProps, mapDispatchToProps)(ExternalComponent);

export default MainComponent;
