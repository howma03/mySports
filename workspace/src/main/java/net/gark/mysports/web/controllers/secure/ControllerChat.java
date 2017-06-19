package net.gark.mysports.web.controllers.secure;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class ControllerChat extends ControllerAbstract {

    @RequestMapping("/chat")
    public String admin(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "Welcome " +
                this.getLoggedInUser().getFirstName() +
                " to the chat page!");
        return "private/chat";
    }
}
