package authApp.Auth;

import Helper.HashingSHA512;
import Helper.Response;
import authApp.Auth.dto.SignUpDto;
import authApp.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {


    private final AuthRepository authRepository;

    @Autowired
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public Object signUp(SignUpDto signUpDto) throws NoSuchAlgorithmException {
        Optional<User> isExistEmail = authRepository.findByEmail(signUpDto.getEmail());
        if (isExistEmail.isPresent()) {
            Map<String, String> error = Map.of("err", "Email is existed!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, error).jsonObject(), HttpStatus.ACCEPTED);
        }

        HashingSHA512 hashingSHA512 = new HashingSHA512();
        String hashedPassword = hashingSHA512.hash(signUpDto.getPassword());
        signUpDto.setPassword(hashedPassword);

        User newUser = new User(
                signUpDto.getName(),
                signUpDto.getEmail(),
                signUpDto.getPassword()
        );
        authRepository.save(newUser);

        Map<String, String> msg = Map.of("msg", "Sign up success!");
        return new ResponseEntity<>(new Response(200, HttpStatus.OK, msg).jsonObject(), HttpStatus.OK);
    }
}
