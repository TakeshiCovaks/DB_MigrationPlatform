import React, {Component} from 'react';

class AuthorModalWindow extends Component {

    render() {
        return (
            <div>
                <div id="modal-author" className="modal" ref={"_modal-author"}>
                    <div className="modal-content">
                        <span className="closeAuthor" id={'closeAuthor'} ref={"_closeAuthor"}
                              onClick={() => {
                                  this.refs["_modal-author"].style.display = "none";
                              }}>&times;</span>
                        <section className="flex-container">
                            <div className="author-img">
                                <img className="image_author"/>
                            </div>
                            <div className="body_author">
                                <h2 className="author_title">Alexey Sushko</h2>
                                <p className="author-contact">
                                    Tel.: +38 (095) 17-05-211
                                </p>
                                <p className="author-contact">
                                    E-mail: alex.e.sushko@gmail.com
                                </p>
                                <p className="author-contact">
                                    Skype: alexnms62
                                </p>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        )
    }
}

export default AuthorModalWindow;