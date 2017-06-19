package net.gark.mysports.web.services;

import net.gark.mysports.web.controllers.ControllerAbstract;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestControllerHello extends ControllerAbstract {

    public static class Greeting {
        private String message = "";

        public Greeting() {

        }

        public Greeting(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting hello() {
        return new Greeting("Greetings from Mysports!");
    }
}
