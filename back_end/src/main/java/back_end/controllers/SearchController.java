package back_end.controllers;


import back_end.services.Search;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SearchController {

    @Bean
    public Search search() {
        return new Search();
    }

    @Autowired
    private Search search;

    /**
     * Method execute search node for key/value.
     *
     * @return array for found nodes.
     */
    @PostMapping(value = "/searchNodes")
    private JSONArray searchNodes(@RequestBody JSONObject keyValueForSearch, HttpServletRequest request, HttpServletResponse response) {
        return search.executeSearch(keyValueForSearch, request);
    }
}
