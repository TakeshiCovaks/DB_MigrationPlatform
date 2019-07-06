import React, {Component} from 'react';

class HelpModalWindow extends Component {

    render() {
        return (
            <div>
                <div id="modal-help" className="modal" ref={"_modal-help"}>
                    <div className="modal-content-help">
                        <span className="closeHelp" id={'close-help'} ref={"_close-help"}
                              onClick={() => {
                                  this.refs["_modal-help"].style.display = "none";
                              }}>&times;</span>
                        <section className="flex-container">
                            <div className="help-img">
                                <img className="image_help"/>
                            </div>
                            <div className="body_help">
                                <div className="three"><h1>Database migration platform</h1></div>
                                <p className="help-text">
                                    This application is designed to read database metadata and display it in a tree
                                    structure.<br/>
                                    In the application, there are three types of tree node loading: lazy, details, full.
                                    <br/>
                                    <br/>
                                    Lazy - downloads a list of children selected item.<br/>
                                    Details - loads the attributes of the element.<br/>
                                    Full - recursively loads all children with attributes.<br/>
                                </p>
                                <p className="help-text">
                                    By clicking on the title of the element, the attributes of the element are displayed
                                    in the form<br/>
                                    of a table in the central part of the screen and the DDL is generated.
                                    DDL has the ability to save to a file.<br/>
                                    There is also the possibility of searching for elements by the key and value of
                                    attributes and
                                    <br/>highlighting of the elements found in the tree.
                                    The page state is saved at the time of the session.
                                    <br/>
                                </p>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        )
    }
}

export default HelpModalWindow;