package back_end.controllers;

import back_end.services.ManagerApp;
import back_end.utils.Converter;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import structures.TreeNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController()
public class TreeNodeController {

    /**
     * Method for getting tree as JSON.
     *
     * @return tree as a JSON structure.
     */

    @GetMapping(value = "/treeJson")
    private JSONObject getTreeJson(HttpServletRequest request, HttpServletResponse response) {

        TreeNode tree = ManagerApp.getTreeFromSession(request);
        return Converter.convertTreeToJson(tree);
    }

    @PostMapping(value = "/deleteSession")
    private String deleteSession(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "200";
    }
}
