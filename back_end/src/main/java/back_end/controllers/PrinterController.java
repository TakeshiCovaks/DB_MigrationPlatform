package back_end.controllers;

import back_end.services.Printers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController()
public class PrinterController {

    @Bean
    public Printers printers() {
        return new Printers();
    }

    @Autowired
    private Printers printers;

    /**
     * Method execute create DDL for node by node path.
     *
     * @return created DDL as String.
     */
    @PostMapping(value = "createDDL")
    private String createDDL(@RequestBody String pathNode, HttpServletRequest request) {
        return printers.createDdl(pathNode, request);
    }
}
