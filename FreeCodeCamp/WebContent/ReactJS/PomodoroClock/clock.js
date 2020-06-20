import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import {createStore} from 'redux';
import {Provider, connect} from 'react-redux';

import './styles.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import {Row, Col, Container} from 'reactstrap';

class ClockComponent extends React.Component
{
	constructor(props){
		super(props);
		this.timerFunction = this.timerFunction.bind(this);
		this.intervalFunction = undefined;
	}

	timerFunction = () => {
		
		let min = this.props.propMin;
		let sec = this.props.propSec;

		if(min == 0 && sec == 0)
		{
			document.getElementById('beep').play();

			let action = () => ( {type: 'CHANGE_MODE'} ) ;
			this.props.toggleControls(action);  //If timer hits 0:00, change from session to break or break to session.
		}

		let action = () => ( {type: 'TIMER'} ) ; 
		this.props.toggleControls(action);

	}

	componentDidMount()
	{
		//action object should have type, objType(session or break)

		document.getElementById('break-increment').addEventListener('click', () => {

			if(!this.props.propStatus) //Can increment/decrement only when the timer is inactive.
			{
				let action = () => ( {type: 'INCREMENT', objType: 'break'} ) ;
				this.props.toggleControls(action);
			}

		});


		document.getElementById('break-decrement').addEventListener('click', () => {

			if(!this.props.propStatus)
			{
				let action = () => ( {type: 'DECREMENT', objType: 'break'} ) ;
				this.props.toggleControls(action);
			}

		});


		document.getElementById('session-increment').addEventListener('click', () => {

			if(!this.props.propStatus)
			{
				let action = () => ( {type: 'INCREMENT', objType: 'session'} ) ;
				this.props.toggleControls(action);
			}

		});


		document.getElementById('session-decrement').addEventListener('click', () => {

			if(!this.props.propStatus)
			{
				let action = () => ( {type: 'DECREMENT', objType: 'session'} ) ;
				this.props.toggleControls(action);
			}

		});

		document.getElementById('start_stop').addEventListener('click', () => {

			let action = () => ( {type: 'START_STOP'} ) ;
			this.props.toggleControls(action);

			if(this.props.propStatus) // Start timer if active status is set to true.
			{
				this.intervalFunction = setInterval(this.timerFunction, 1000);
			}
			else
			{
				if(this.intervalFunction != undefined) { clearInterval(this.intervalFunction) } ;
			}

		});

		document.getElementById('reset').addEventListener('click', () => {

			let audio = document.getElementById('beep');
			audio.pause();
			audio.currentTime = 0; // This property sets the audio playback to 0 seconds. (rewounds to beginning)

			if(this.props.propStatus) //If timer is active when reset is pressed, stop it.
			{
				if(this.intervalFunction != undefined) { clearInterval(this.intervalFunction) } ;
			}

			let action = () => ( {type: 'RESET'} ) ;
			this.props.toggleControls(action);

		});
	}

	render()
	{
		return (<div id = 'clock-container'>
					<h1> Pomodoro Clock </h1>

						<div id = 'control-holder1'>

							<div id = 'break-holder'> 

								<h3 id = 'break-label'> Break Length </h3> 

								<Container id='row-container1'>
									<Row id = 'break-toggle-row'>
										<Col id='break-decrement' className = 'col-md-1 offset-md-1 pointer'> - </Col>
										<Col id='break-length' className = 'col-md-1' value={this.props.propBreak}> {this.props.propBreak} </Col>
										<Col id='break-increment' className = 'col-md-1 pointer'> + </Col>
									</Row>
								</Container>

							</div>

							<div id = 'session-holder'>

								<h3 id = 'session-label'> Session Length </h3>

								<Container id='row-container2'>
									<Row id = 'session-toggle-row'>
										<Col id='session-decrement' className = 'col-md-1 offset-md-1 pointer'> - </Col>
										<Col id='session-length' className = 'col-md-1' value={this.props.propSession}> {this.props.propSession} </Col>
										<Col id='session-increment' className = 'col-md-1 offset-md-1 pointer'> + </Col>
									</Row>
								</Container>

							</div>
						</div>


						<div id = 'timer-holder'>
							<h3 id = 'timer-label'> {this.props.propMode} </h3>

							<h1 id = 'time-left'> {this.props.propTimeLeft} </h1> 
						</div>

						<div id = 'control-holder2'>
							<span id = 'start_stop' className = 'pointer'>Start/Stop</span>
							<span id = 'reset' className = 'pointer'>Reset</span>
						</div>

						<audio src='https://raw.githubusercontent.com/freeCodeCamp/cdn/master/build/testable-projects-fcc/audio/BeepSound.wav' id='beep'>
						</audio>

				</div>);
	}
}

const initialState = { break: 5, 
					   session: 25,
					   minutes: '25',
					   seconds: '00',
					   mode: 'Session',
					   active: false
					 };



let mapStateToProps = (state) => {

	let timeLeft = ( state.minutes.length < 2 ) ? '0'+state.minutes : state.minutes;
	let timeLeftSeconds = ( state.seconds.length < 2 ) ? '0'+state.seconds : state.seconds;

	return ({ propBreak: state.break, 
			  propSession: state.session, 
			  propTimeLeft: timeLeft + ":" + timeLeftSeconds, 
			  propStatus: state.active,
			  propMode: state.mode, 
			  propMin: state.minutes,
			  propSec: state.seconds });

}

let mapDispatchToProps = (dispatch) => {

	return ({ toggleControls: action => dispatch(action()) });
}


let reducer = (state  , action) =>
{
	switch(action.type)
	{
		case 'INCREMENT':
						if (action.objType == 'break')
						{
							let newState = Object.assign({}, state);
							newState.break = (state.break < 60) ? newState.break += 1 : newState.break;
							return newState;
						}
						else
						{
							let newState = Object.assign({}, state);
							newState.session = (state.session < 60) ? newState.session += 1 : newState.session;
							newState.minutes = newState.session.toString();
							newState.seconds = '00';
							return newState;						
						}
													  		 
		case 'DECREMENT':
						if (action.objType == 'break')
						{
							let newState = Object.assign({}, state);
							newState.break = (state.break > 1) ? newState.break -= 1 : newState.break;
							return newState;						
						}
						else
						{
							let newState = Object.assign({}, state);
							newState.session = (state.session > 1) ? newState.session -= 1 : newState.session;
							newState.minutes = newState.session.toString();
							newState.seconds = '00';
							return newState;						
						}

		case 'START_STOP':
						let newState = Object.assign({}, state);
						newState.active = !(newState.active);

						return newState;

		case 'TIMER':
				{
					let newState = Object.assign({}, state);
					let min = parseInt(newState.minutes);
					let sec = parseInt(newState.seconds);

					if(sec == 0)
					{
						sec = 60;
						min -= 1;
					}

					sec -= 1;

					newState.minutes = min.toString();
					newState.seconds = sec.toString();

					return newState;
				}

		case 'CHANGE_MODE':
				{
					let newState = Object.assign({}, state);
					newState.mode = (newState.mode == 'Session') ? 'Break' : 'Session';

					if(newState.mode == 'Session')
					{
						newState.minutes = newState.session.toString();
						newState.seconds = '00';
					}
					else
					{
						newState.minutes = newState.break.toString();
						newState.seconds = '00';
					}

					return newState;
				}

		case 'RESET':
					return ({ break: 5, session: 25, minutes: '25',seconds: '00', mode: 'Session', active: false });
						
		default: 
			return state;
	}
}

let store = createStore(reducer, initialState);
let ConnectedClock = connect(mapStateToProps, mapDispatchToProps)(ClockComponent);


class ClockProvider extends React.Component
{
	render()
	{ 	
		return <Provider store = {store}>
					<ConnectedClock/>
		  	   </Provider>
	}
}

export default ClockProvider;
