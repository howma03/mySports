package net.gark.mysports.web.controllers.unsecured;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mark on 11/02/2017.
 */
@Controller
public class ControllerPublic {

    @RequestMapping("/landing")
    public String landing() {
        return "public/landing";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "public/accessDenied";
    }

    @RequestMapping("/error2")
    public String errorHandler() {
        return "public/error";
    }
}
