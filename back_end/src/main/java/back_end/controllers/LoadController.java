package back_end.controllers;

import back_end.services.Loaders;
import back_end.services.ManagerApp;
import database.loaders.TypeLoad;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController()
public class LoadController {

    @Bean
    public Loaders loaders() {
        return new Loaders();
    }

    @Autowired
    private Loaders loaders;

    @PostMapping(value = "/lazyLoad")
    private JSONObject lazyLoad(@RequestBody ArrayList<String> keyPathNode, HttpServletRequest request, HttpServletResponse response) {
        return loaders.load(keyPathNode, TypeLoad.LAZY, ManagerApp.getContextFromSession(request));
    }

    @PostMapping(value = "/detailsLoad")
    private JSONObject detailsLoad(@RequestBody ArrayList<String> keyPathNode, HttpServletRequest request, HttpServletResponse response) {
        return loaders.load(keyPathNode, TypeLoad.DETAILS, ManagerApp.getContextFromSession(request));
    }

    @PostMapping(value = "/fullLoad")
    private JSONObject fullLoadList(@RequestBody ArrayList<String> keyPathNode, HttpServletRequest request, HttpServletResponse response) {

        if (keyPathNode.isEmpty()) {
            return new JSONObject();
        }
        return loaders.load(keyPathNode, TypeLoad.FULL, ManagerApp.getContextFromSession(request));
    }


}
