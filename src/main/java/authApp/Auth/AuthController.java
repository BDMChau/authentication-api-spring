package authApp.Auth;

import Enums.isValidEnum;
import Helper.Response;
import authApp.Auth.dto.SignUpDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    @ResponseBody
    public Object signUp(@RequestBody SignUpDto signUpDto) {
        if (signUpDto.isValid() == isValidEnum.missing_credentials) {
            Map<String, String> empty = Map.of("err", "Missing credentials!");
            return new ResponseEntity<>(new Response(400, HttpStatus.BAD_REQUEST, empty).jsonObject(), HttpStatus.BAD_REQUEST);

        } else if (signUpDto.isValid() == isValidEnum.password_strong_fail) {
            Map<String, String> empty = Map.of("err", "Eight characters, at least one letter and 1 number for password required!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, empty).jsonObject(), HttpStatus.ACCEPTED);

        } else if (signUpDto.isValid() == isValidEnum.email_invalid) {
            Map<String, String> empty = Map.of("err", "Invalid email!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, empty).jsonObject(), HttpStatus.ACCEPTED);
        }





        Map<?, ?> empty = Map.of();
        return new ResponseEntity<>(new Response(200, HttpStatus.OK, empty).jsonObject(),
                HttpStatus.OK);


    }


}
