import React, {Component} from 'react';
import CheckboxTree from 'react-checkbox-tree';
import '../css/style_tree.css';
import * as Utils from "./Utils.js";

import TableAttributes from "./TableAttributes.js"
import DdlBlock from "./DdlBlock";
import SearchBlock from "./SearchBlock";

import * as LocalStorage from "./LocalStorage.js";


class TreePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tree: [],
            checked: [],
            expanded: [],
            currentNodeName: "",
            resultSearch: [],
            url: this.props.url,
            attrState: []
        }
        this.attributes = [];
    }

    componentDidMount() {
        if (localStorage.getItem("tree")) {
            this.setState({"tree": LocalStorage.loadTreeFromStorage("tree", "")});
            this.setState({"url": LocalStorage.loadTextFromStorage("url", "")});
            this.setState({"checked": LocalStorage.loadArrayFromStorage("checked", "")});
            this.setState({"expanded": LocalStorage.loadArrayFromStorage("expanded", "")});
        } else {
            this.gettingTreeJson();
        }
        this.setState({"currentNodeName": LocalStorage.loadTextFromStorage("currentNodeName", "")});
    }

    componentWillUpdate(nextProps, nextState, nextContext) {
        LocalStorage.saveCheckedExpanded(nextState);
    }

    /**
     * Method for get tree from server as JSON structure.
     */
    gettingTreeJson = () => {
        Utils.formedFetchGET('treeJson').then(response => response.json())
            .then(data => {
                this.setState({tree: [data]});
                localStorage.setItem("tree", JSON.stringify(data));
                localStorage.setItem("url", this.state.url);
            });
    }

    /**
     * By clicking on the node header DDL is created.
     *
     * @param node for DDL.
     */
    onClickNode = (node) => {
        this.setState({currentNodeName: node.value});

        localStorage.setItem("currentNodeName", node.value);

        Utils.formedFetchPOST('createDDL', 'text', node.value)
            .then(response => response.text()).then(ddl => {
            this.refs.ddlElement.textAreaDdl.current.value = ddl;
            localStorage.setItem("currentDDL", ddl);
        });
    }

    onClickLazy() {
        this.loadNodes('lazyLoad')
    }

    onClickDetails() {
        this.loadNodes('detailsLoad')
    }

    onClickFull() {
        this.loadNodes('fullLoad')
    }

    /**
     * Function for load node depending on typeLoad.
     *
     * @param typeLoad type load for node (lazy, details, full).
     */
    loadNodes(typeLoad) {
        var arrForLoad = [];

        for (var currentCheckedNode in this.state.checked) {
            let nameNode = this.state.checked[currentCheckedNode];

            //For load when node closed and checked
            var arrNamesPath = [];
            arrNamesPath = nameNode.split('.')
            if (arrNamesPath.length === 5 && this.loadParent(arrNamesPath[3])) {
                var firstParent = this.ref.state.model.flatNodes[nameNode].parent.value;
                var secondParent = this.ref.state.model.flatNodes[firstParent].parent.value;
                if (arrForLoad.indexOf(secondParent) === -1 &&
                    nameNode !== undefined &&
                    this.ref.state.model.clone().getNode(secondParent).checkState === 1) {
                    arrForLoad[arrForLoad.length] = secondParent;
                }
            }
            if (this.state.expanded.indexOf(nameNode) === -1) {
                arrForLoad[arrForLoad.length] = nameNode;
            }
        }

        //add opened and checked nodes
        for (var currentExpandedNode in this.state.expanded) {
            let nameNode = this.state.checked[currentExpandedNode];
            if (nameNode !== undefined && this.ref.state.model.clone().getNode(nameNode).checkState === 1 &&
                arrForLoad.indexOf(nameNode) === -1) {
                arrForLoad[arrForLoad.length] = nameNode;
            }
        }
        let nameRoot = this.state.tree[0].label;
        if(arrForLoad.indexOf(nameRoot) === -1 &&
            this.ref.state.model.clone().getNode(nameRoot).checkState === 1){
            arrForLoad[arrForLoad.length] = nameRoot;
        }

        this.setState({checked: []});

        if (arrForLoad.length > 0) {
            Utils.formedFetchPOST(typeLoad, 'json', JSON.stringify(arrForLoad))
                .then(response => response.json())
                .then(data => {
                    this.setState({tree: [data]});
                    localStorage.setItem("tree", JSON.stringify(data));
                });
        } else {
            alert("No node selected. Please select node")
        }
    }

    loadParent(nodeLabel) {
        if (nodeLabel === "Columns" |
            nodeLabel === "Indexes" |
            nodeLabel === "Foreign keys" |
            nodeLabel === "Triggers") {
            return true;
        }
        return false;
    }

    createSearchBlock = () => {
        let prefix = "";
        if (this.ref !== undefined) {
            prefix = this.ref.state.id
        }
        return (
            <SearchBlock prefix={prefix}/>
        )
    }

    render() {

        return (
            <div id={'top-line'}>
                <div id={'tree-page'}>
                    <div className="tree-box">
                        <div id={'title-tree'}>Database structures</div>
                        <div id={'load-button-block'}>
                            Download types
                            <br></br>
                            <button id={'lazy-button'} onClick={(event) => this.onClickLazy()}>Lazy</button>
                            <button id={'details-button'} onClick={(event) => this.onClickDetails()}>Details</button>
                            <button id={'full-button'} onClick={(event) => this.onClickFull()}>Full</button>
                        </div>
                        <CheckboxTree
                            ref={(ref) => {
                                if (!this.ref) {
                                    this.ref = ref
                                }
                            }}
                            nodes={this.state.tree}
                            onClick={(node) => this.onClickNode(node)}
                            checked={this.state.checked}
                            expanded={this.state.expanded}
                            showCheckbox={false}
                            onCheck={checked => this.setState({checked})}
                            onExpand={expanded => this.setState({expanded})}
                            showNodeIcon={false}
                            noCascade={false}
                        />
                    </div>

                    <TableAttributes currentNodeName={this.state.currentNodeName} tree={this.state.tree}/>

                    <div id="wrapper">
                        <DdlBlock currentNodeName={this.state.currentNodeName} ref={"ddlElement"}/>
                        {this.createSearchBlock()}
                    </div>

                </div>
            </div>
        );
    }
}

export default TreePage;
