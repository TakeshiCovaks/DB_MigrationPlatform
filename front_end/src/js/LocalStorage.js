import ReactDOM from "react-dom";
import TreePage from "./TreePage";
import React from "react";


export function checkLocaleStorage() {
    if (localStorage.getItem("url")) {
        ReactDOM.render(<TreePage/>, document.getElementById('root'));
    }
}

export function saveCheckedExpanded(nextState) {
    localStorage.setItem("checked", JSON.stringify(nextState.checked));
    localStorage.setItem("expanded", JSON.stringify(nextState.expanded));
}

export function saveValueInLocalStorege(key, value) {
    localStorage.setItem(key, value);
}

export function loadTextFromStorage(keyForLoad, defaultValue) {
    if (localStorage.getItem(keyForLoad)) {
        return localStorage.getItem(keyForLoad);
    }
    return defaultValue;
}

export function loadArrayFromStorage(keyForLoad, defaultValue) {
    if (localStorage.getItem(keyForLoad)) {
        return JSON.parse(localStorage.getItem(keyForLoad));
    }
    return JSON.parse(defaultValue);
}

export function loadTreeFromStorage(keyForLoad, defaultValue) {
    if (localStorage.getItem(keyForLoad)) {
        return [JSON.parse(localStorage.getItem(keyForLoad))];
    }
    return defaultValue;

}
