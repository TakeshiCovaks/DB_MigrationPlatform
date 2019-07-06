import React, {Component} from 'react';
import '../css/start_page.css';
import ReactDOM from "react-dom";
import TreePage from "./TreePage";
import * as Utils from "./Utils.js";
import * as LocalStorage from "./LocalStorage.js";
import AuthorModalWindow from "./modal/AuthorModalWindow.js";
import HelpModalWindow from "./modal/HelpModalWindow";

class Authorization extends Component {

    constructor(props) {
        super(props);
        this.state = {
            url: '',
            port: '',
            user: '',
            pass: '',
            nameDb: '',
            typeDb: '',
            typeDatabases: [],
            treeIsExist: 'false'
        };
        LocalStorage.checkLocaleStorage();
    }

    componentDidMount() {
        if (!localStorage.getItem("url")) {
            this.getTypeDB();
        }
    }

    /**
     * Selecting type DB and write to typeDB.
     * @param event
     */
    onInputChangeTypeDB = (event) => {
        this.setState({typeDb: event.target.value});
    }

    /**
     * Change all input field except typeDB.
     * @param event
     */
    onInputChange = (event) => {
        const name = event.target.name;
        this.setState({[name]: event.target.value});
    }

    onSubmit = (event) => {
        if (this.validateFields() === true) {
            event.preventDefault();
            Utils.formedFetchPOST('validateStartForm', 'json', JSON.stringify(this.state))
                .then(response => response.json())
                .then(data => this.resultConnection(data));
        } else {
            alert("Login is not possible. Some fields are not filled. Check the correct fields.");
        }

    }

    validateFields = () => {
        if(this.refs._typeDbID.value === ""|
            this.refs._url.value === ""|
            this.refs._port.value === ""|
            this.refs._database.value === ""|
            this.refs._user.value === ""|
            this.refs._pass.value === ""){
            return false;
        }
        return true;
    }

    resultConnection = (data) => {
        if (Object.keys(data).length === 0 || data.status === 500) {
            alert("Failed to load page. Please check data and try again.")
        } else {
            ReactDOM.render(<TreePage url={this.state.url}/>, document.getElementById('root'));
        }
    }

    /**
     * Function for getting type databases which use app.
     */
    getTypeDB = () => {

        Utils.formedFetchGET('typeDB').then(response => response.json())
            .then(databasesType => this.setState({typeDatabases: databasesType}))
            .then(this.fullTypeDB);
    }

    /**
     * Complete list with typeDB that are in the app.
     */
    fullTypeDB = () => {

        if (this.state.typeDatabases.length > 0) {
            for (var currentType in this.state.typeDatabases) {
                var variant = document.createElement("option")
                variant.textContent = this.state.typeDatabases[currentType];
                variant.value = this.state.typeDatabases[currentType];
                this.refs._typeDbID.appendChild(variant);
            }
        }
    }

    onClickHelp = () => {
        var modal = this.refs._modalHelp.refs["_modal-help"];
        modal.style.display = "block";

        window.onclick = function (event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    }

    onClickAuthor = () => {
        var modal = this.refs._modalAuthor.refs["_modal-author"];
        modal.style.display = "block";

        window.onclick = function (event) {
            if (event.target === modal) {
                modal.style.display = "none";
            }
        }
    }

    createModalWindow() {
        return (
            <div id={'inform-buttons'}>
                <button id="button-help" type={'button'} onClick={() => this.onClickHelp()}>Help</button>
                <HelpModalWindow ref={"_modalHelp"}/>

                <button id="button-author" type={'button'} onClick={() => this.onClickAuthor()}>Author</button>
                <AuthorModalWindow ref={"_modalAuthor"}/>
            </div>
        )
    }

    render() {

        return (
            <div className="StartPage">

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">Type database:</label>
                    <select name="typeDb" className={"input-start-page"} id={'typeDbID'}
                            onChange={this.onInputChangeTypeDB} ref={'_typeDbID'}>
                        <option></option>
                    </select>
                </div>

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">URL: </label>
                    <input className={"input-start-page"} name="url" id={'id-url'} type="text" placeholder="URL"
                           value={this.props.url} ref={'_url'}
                           onChange={this.onInputChange}/></div>

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">Port: </label>
                    <input className={"input-start-page"} name="port" id={'id-port'} type="number"
                           placeholder="Port" value={this.props.port} ref={'_port'}
                           onChange={this.onInputChange}/></div>

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">Name database:</label>
                    <input className={"input-start-page"} name="nameDb" id={'id-name-database'} type="text"
                           placeholder="Name database" ref={'_database'}
                           value={this.state.nameDb} onChange={this.onInputChange}/></div>

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">Username:</label>
                    <input className={"input-start-page"} name="user" id={'id-name-user'} type="text"
                           placeholder="User" value={this.state.user} ref={'_user'}
                           onChange={this.onInputChange}/></div>

                <div className={"fieldStartPage"}>
                    <label className={'startPage'} htmlFor="ln">Password:</label>
                    <input className={"input-start-page"} name="pass" id={'id-pass'} type="password"
                           placeholder="Password" value={this.state.pass} ref={'_pass'}
                           onChange={this.onInputChange}/></div>

                <input id={'button-submit'} type="submit" value="Connect" onClick={(event) => this.onSubmit(event)}/>

                {this.createModalWindow()}
            </div>
        );
    }
}

export default Authorization;
