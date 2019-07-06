import React, {Component} from 'react';
import * as Utils from "./Utils.js";
import ReactDOM from "react-dom";
import Authorization from "./Authorization";
import FileSaver from "file-saver";

class DdlBlock extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentNodeName: this.props.currentNodeName
        }

        this.textAreaDdl = React.createRef();
    }

    componentDidMount() {
        if (localStorage.getItem("currentDDL")) {
            this.textAreaDdl.current.value = localStorage.getItem("currentDDL");
        }
    }

    onClickOut = () => {

        Utils.formedFetchPOST('deleteSession', 'text', '')
            .then(response => response.text());

        localStorage.clear();
        ReactDOM.render(<Authorization/>, document.getElementById('root'));
    }

    /**
     * Save DDL from input area to file. Name file equally node.value
     */
    onClickSaveDdl = () => {
        var text = this.textAreaDdl.current.value;
        var filename = this.props.currentNodeName + '_DDL.sql'
        var blob = new Blob([text], {type: "text/plain;charset=utf-8"});
        FileSaver.saveAs(blob, filename);
    }

    saveDDL = () => {
        localStorage.setItem("currentDDL", this.textAreaDdl.current.value);
    }

    render() {
        return (
            <div id={'ddl-main-div'}>
                <button id={'button-out'} onClick={(event) => this.onClickOut()}>Close session</button>

                <div id="paper">
                    <div id="margin">DDL for element DB
                        <textarea placeholder="Element does not have DDL" id="text" name="text" ref={this.textAreaDdl}
                                  onChange={this.saveDDL}></textarea>
                        <br></br>
                    </div>
                    <button id={'button-save'} type="submit" onClick={this.onClickSaveDdl}>Save current DDL</button>
                </div>
            </div>
        );
    }
}

export default DdlBlock;
