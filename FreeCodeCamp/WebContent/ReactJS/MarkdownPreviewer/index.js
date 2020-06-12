import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import EditorWrapperComponent from './MarkdownPreviewer/editor';
import PreviewWrapperComponent from './MarkdownPreviewer/previewer';
import TestComponent from './fccTest';

import './MarkdownPreviewer/styles.scss';

class MainComponent extends React.Component
{
	constructor(props){
		super(props);
	}

	render()
	{
		return (<div id="main" className="container-md">
					<TestComponent/>
					<EditorWrapperComponent/>
						<br/>
					<PreviewWrapperComponent/>
			    </div>);
	}
}

ReactDOM.render(<MainComponent/>, document.getElementsByTagName('body')[0]);