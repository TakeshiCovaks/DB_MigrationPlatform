import config from "../config"
import React from "react";


export function formedFetchPOST(maping, typeData, body) {
    return fetch('http://' + config.urlServer + ':' + config.portServer + config.prefixApp + '/' + maping, {
        method: "POST",
        credentials: 'include',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/' + typeData,
        },
        body: body
    })
}

export function formedFetchGET(maping) {
    return fetch('http://' + config.urlServer + ':' + config.portServer + config.prefixApp + '/' + maping, {
        method: "GET",
        credentials: 'include',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
    })
}
