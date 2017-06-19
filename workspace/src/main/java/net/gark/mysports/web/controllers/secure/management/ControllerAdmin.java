package net.gark.mysports.web.controllers.secure.management;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by mark on 11/02/2017.
 */
@Controller
public class ControllerAdmin extends ControllerAbstract {

    @RequestMapping("/admin")
    public String admin(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "Welcome " +
                this.getLoggedInUser().getFirstName() +
                " to the Administrative page!");
        return "private/management";
    }
}
