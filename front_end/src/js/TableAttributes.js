import React, {Component} from 'react';

class TableAttributes extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentNodeName: this.props.currentNodeName,
            tree: this.props.tree,
        }
    }

    componentDidMount() {
        this.readNodeAttributes(this.state.currentNodeName.split('.'), this.state.tree);
    }

    /**
     * Creating body table with attribute.
     *
     // * @param attrNew attributes for table.
     * @returns {Array}
     */
    createTable() {

        let table = [];
        let title = [];
        title.push(<td id={'title-table'} key={'titleName'}>Name attribute</td>);
        title.push(<td id={'title-table'} key={'titleValue'}>Value attribute</td>);
        table.push(<tr key={'titleLineValue'}>{title}</tr>);

        var attrNew = {};
        attrNew = this.readNodeAttributes(this.props.currentNodeName.split('.'), this.props.tree);

        var keysAttr = [];
        keysAttr = Object.keys(attrNew);
        for (let i = 0; i < keysAttr.length; i++) {
            let children = [];

            children.push(<td key={i + 'columnName'}>{keysAttr[i]}</td>);
            children.push(<td key={i + 'columnValue'}>{attrNew[keysAttr[i]]}</td>);
            table.push(<tr key={'line' + i}>{children}</tr>);
        }
        return table;
    }

    /**
     * Function for read attributes from node.
     *
     * @param nodePath path for search node.
     * @param listNodes list with nodes.
     * @returns {*} attributes.
     */
    readNodeAttributes(nodePath, listNodes) {

        if ((listNodes.length === 0) || (nodePath.length === 0)) {
            return {}
        }

        var nameForEquals = nodePath[0];
        for (var node in listNodes) {

            if (listNodes[node].label === nameForEquals && nodePath.length === 1) {
                return listNodes[node].attributes;
            }
            if (listNodes[node].label === nameForEquals) {
                return this.readNodeAttributes(nodePath.slice(1), listNodes[node].children);
            }
        }
        return {};
    }

    render() {
        return (
            <table id={'attr-table'} border="2px">
                <caption id={'title-table-attr'}>Attributes for element DB</caption>
                <tbody>
                {this.createTable()}
                </tbody>
            </table>
        )
    }
}

export default TableAttributes;
