import React, {Component} from 'react';
import ReactDOM from 'react-dom';

import {Row, Col, Container} from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './styles.scss';

const drumKeys = [ {'Q' : 'Heater-1'},
                   {'W' : 'Heater-2'},
                   {'E' : 'Heater-3'},
                   {'A' : 'Heater-4'},
                   {'S' : 'clap'},
                   {'D' : 'open-HH'},
                   {'Z' : 'kick-n-hat'},
                   {'X' : 'kick'},
                   {'C' : 'closed-HH'} ];

let drumpadObjects = [];

class DrumMachineComponent extends React.Component
{
	constructor(props)
	{
		super(props)
		this.keyPressHandler = this.keyPressHandler.bind(this);
	}

	keyPressHandler = (event) => 
	{
		let key = event.key.toUpperCase();
		let keys = drumKeys.map( drumKey => Object.keys(drumKey)[0] );

		if(drumpadObjects.length > 0 && keys.indexOf(key) != -1)
		{
			let drumpad = drumpadObjects.filter( drumpadObject => drumpadObject.key === key);

			drumpad[0].audioclip.addEventListener('playing', () => drumpad[0].drumpad.classList.add('bg-col'));
			drumpad[0].audioclip.addEventListener('ended', () => drumpad[0].drumpad.classList.remove('bg-col'));

			drumpad[0].audioclip.play();
			document.getElementById('display').innerHTML = drumpad[0].desc;
		}
	}

	componentDidMount()
	{
		// Build a drumpad Object = { key: key, drumpad: drumpadElement, audioclip: clip-element, desc: desc}

		let keys = drumKeys.map( drumKey => Object.keys(drumKey)[0] );
		let drumpads = Object.values(document.getElementsByClassName('drum-pad'));
		let audioObjects = Object.values(document.getElementsByClassName('clip'));
		let descValues = drumKeys.map( drumKey => Object.values(drumKey)[0] );


		//Building drum-pad object.

		drumpadObjects = keys.map( (key, index) => ({ key: key, drumpad: drumpads[index], audioclip: audioObjects[index], desc: descValues[index]}) );
		drumpadObjects.forEach( drumpadObject => ( drumpadObject.drumpad.addEventListener('click', () => {

			drumpadObject.audioclip.play();
			document.getElementById('display').innerHTML = drumpadObject.desc; 

			}) 
		));

		//Key-Press event listener adding.
		document.addEventListener('keypress', this.keyPressHandler);

	}

	render()
	{
		return (<div id = 'drum-machine-container'>
					<Row id='spacer'></Row>

					<Row id='drum-machine-row'>
						<Col className='col-md-2.5'></Col>

						<div id='drum-machine' className='col-md-6'>
							<div id = 'drum-pad-main'>
								<Container id='drum-pad-container' className='container-fluid'>
									<Row className='drum-pad-row'>
										<Col id='Heater-1' className = 'col-md-3.3 drum-pad'>Q
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Heater-1.mp3' id='Q' className='clip'>
											</audio>
										</Col>

										<Col id='Heater-2' className = 'col-md-3.3 drum-pad'>W
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Heater-2.mp3' id='W' className='clip'>
											</audio>
										</Col>

										<Col id='Heater-3' className = 'col-md-3.3 drum-pad'>E
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Heater-3.mp3' id='E' className='clip'>
											</audio>
										</Col>
									</Row>

									<Row className='drum-pad-row'>
										<Col id='Heater-4' className = 'col-md-3.3 drum-pad'>A
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Heater-4_1.mp3' id='A' className='clip'>
											</audio>
										</Col>

										<Col id='clap' className = 'col-md-3.3 drum-pad'>S
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Heater-6.mp3' id='S' className='clip'>
											</audio>
										</Col>
										
										<Col id='open-HH' className = 'col-md-3.3 drum-pad'>D
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Dsc_Oh.mp3' id='D' className='clip'>
											</audio>
										</Col>
									</Row>

									<Row className='drum-pad-row'>
										<Col id='kick-n-hat' className = 'col-md-3.3 drum-pad'>Z
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Kick_n_Hat.mp3' id='Z' className='clip'>
											</audio>
										</Col>

										<Col id='kick' className = 'col-md-3.3 drum-pad'>X
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/punchy_kick_1.mp3' id='X' className='clip'>
											</audio>
										</Col>
										
										<Col id='closed-HH' className = 'col-md-3.3 drum-pad'>C
											<audio src='https://s3.amazonaws.com/freecodecamp/drums/Dry_Ohh.mp3' id='C' className='clip'>
											</audio>
										</Col>
									</Row>
								</Container>
							</div>

							<div id = 'display-container'>
								<p id='display' className='container-fluid'></p>
							</div>

						</div>

						<Col className='col-md-2.5'></Col>
					</Row>

				</div>)
	}
}

export default DrumMachineComponent;