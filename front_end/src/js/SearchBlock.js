import React, {Component} from 'react';
import * as Utils from "./Utils";
import * as LocalStorage from "./LocalStorage.js";


class SearchBlock extends Component {
    constructor(props) {
        super(props);
        this.state = {
            resultSearch: [],
            prefix: this.props.prefix
        }
    }

    componentDidMount() {
        this.loadKeyValueFromLocalStorage("currentKey", "_search-key-attr");
        this.loadKeyValueFromLocalStorage("currentValue", "_search-value-attr");
        this.loadArrayFromLocalStorage("resultSearch", "resultSearch")
    }

    loadKeyValueFromLocalStorage = (key, ref) => {
        if (localStorage.getItem(key)) {
            this.refs[ref].value = localStorage.getItem(key);
        }
    }

    async loadArrayFromLocalStorage(keyForLoad, nameState) {
        if (localStorage.getItem(keyForLoad)) {
            const currentData = localStorage.getItem(keyForLoad);
            await currentData;
            await this.setState({[nameState]: JSON.parse(currentData)});
            this.addNodesToSelectList();
        }
    }

    /**
     * Search node by key/value.
     */
    onClickSearchNode = () => {
        var key = this.refs["_search-key-attr"].value;
        var value = this.refs["_search-value-attr"].value;
        var keyVal = {key, value};

        Utils.formedFetchPOST('searchNodes', 'json', JSON.stringify(keyVal))
            .then(response => response.json())
            .then(foundNodes => {
                this.setState({resultSearch: foundNodes});
                localStorage.setItem("resultSearch", JSON.stringify(foundNodes));
            })
            .then(this.addNodesToSelectList);
    }

    /**
     * Show next level path for found node.
     */
    showAllFoundElements = () => {
        this.resetSccForTitleElements();
        for (var currentElement in this.state.resultSearch) {
            this.showSelectedFoundNodes(this.state.resultSearch[currentElement])
        }
    }

    /**
     * Function open structure tree and mark node by path.
     *
     * @param pathNode for showing.
     */
    async showSelectedFoundNodes(pathNode) {

        var searchID = pathNode;
        var arrPath = [];
        arrPath = searchID.split(".")

        var allPath = this.props.prefix + "-";

        for (var currentElement in arrPath) {
            allPath = allPath + arrPath[currentElement];
            if (document.getElementById(allPath) != null) {
                var element = document.getElementById(allPath).labels[0].previousElementSibling;
                if (element.tagName === 'BUTTON') {
                    if (element.childNodes[0].attributes.class.ownerElement.className === "rct-icon rct-icon-expand-close") {
                        const afterClick = document.getElementById(allPath).labels[0].previousElementSibling.click();
                        await afterClick;
                    }
                }
            }
            //For correct value allPath in block for change background color
            if (currentElement < arrPath.length - 1) {
                allPath += "."
            }
        }
        document.getElementById(allPath).parentElement.parentElement.lastChild.lastChild.style = "background: rgb(255, 0, 0);"
    }

    resetSccForTitleElements = () => {
        var allCSS = document.getElementsByClassName("rct-title")
        for (var i = 0; i < allCSS.length; i++) {
            allCSS[i].style = "background: rgba(5, 49, 64, 0.75);"
        }
    }

    /**
     * Click to selected value in found nodes list.
     * @param event
     */
    onClickSelectedFoundNode = (event) => {
        this.resetSccForTitleElements();
        this.showSelectedFoundNodes(event.target.value)
    }

    /**
     * Function add found nodes from resultSearch to found-list.
     */
    addNodesToSelectList = () => {

        this.refs["_found-list"].children[1].length = 0;
        if (this.state.resultSearch.length > 0) {
            for (var currentType in this.state.resultSearch) {
                var variant = document.createElement("option");
                variant.textContent = this.state.resultSearch[currentType];
                variant.value = this.state.resultSearch[currentType];
                this.refs["_found-list"].children[1].appendChild(variant);
            }
        }
    }

    saveKey = () => {
        LocalStorage.saveValueInLocalStorege("currentKey", this.refs["_search-key-attr"].value);
    }

    saveValue = () => {
        LocalStorage.saveValueInLocalStorege("currentValue", this.refs["_search-value-attr"].value)
    }

    render() {
        return (
            <div id={'search-block'}>
                Search for a element by key and value
                <div id={'search-form'} action="">
                    <input id={"search-key-attr"} type="text" placeholder="Key attributes"
                           ref={"_search-key-attr"} onChange={() => this.saveKey()}/>
                    <input id={"search-value-attr"} type="text" placeholder="Value attributes"
                           ref={"_search-value-attr"} onChange={() => this.saveValue()}/>
                    <button id={'button-search'} type='submit' onClick={this.onClickSearchNode}>Search node</button>

                    <div id={'found-list-div'} ref={"_found-list"}>
                        <label className={'result-search-label'} htmlFor="ln">Search result:</label>
                        <select name="found-list" className={"result-search-list"} id={'found-list'}
                                onChange={(event) => this.onClickSelectedFoundNode(event)}>
                        </select>
                        <button id={'button-next-step'} onClick={() => this.showAllFoundElements()}>Show all elements
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

export default SearchBlock;
