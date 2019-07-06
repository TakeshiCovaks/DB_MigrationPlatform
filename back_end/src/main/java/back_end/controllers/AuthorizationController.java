package back_end.controllers;

import back_end.services.Authorization;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController()
public class AuthorizationController {

    @Bean
    public Authorization authorization() {
        return new Authorization();
    }

    @Autowired
    private Authorization authorization;

    /**
     * Method for getting type databases which use in app.
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/typeDB")
    private JSONArray getTypeDB(HttpServletRequest request, HttpServletResponse response) {
        return authorization.getTypeDb();
    }

    /**
     * Method for first connection DB for client.
     *
     * @param form form with data from connection.
     * @return JSONObject with ID tree and tree as a JSON structure.
     */
    @PostMapping(value = "/validateStartForm")
    private JSONObject validateStartForm(@RequestBody JSONObject form, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
        return authorization().validateEnter(form, httpSession);
    }
}
