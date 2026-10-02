package dev.swaleha.bookmysalonappointment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping(value = {
            "/",
            "/login",
            "/register",
            "/register/barber",
            "/register/customer",
            "/customer-dashboard",
            "/barber-dashboard"
    })
    public String forwardToSpa() {
        return "forward:/index.html";
    }
}
