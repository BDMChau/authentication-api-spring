package authApp.User;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @GetMapping("/getuser")
    public String getUser(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        Object user = req.getAttribute("user");


        return "Get user route";
    }

}
