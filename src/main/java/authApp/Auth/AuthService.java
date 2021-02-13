package authApp.Auth;

import Helper.HashingSHA512;
import Helper.Response;
import authApp.Auth.dto.SignDto;
import authApp.User.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
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


    public ResponseEntity signUp(SignDto signDto) throws NoSuchAlgorithmException {
        Optional<User> isExistEmail = authRepository.findByEmail(signDto.getEmail());
        if (isExistEmail.isPresent()) {
            Map<String, String> error = Map.of("err", "Email is existed!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, error).jsonObject(), HttpStatus.ACCEPTED);
        }

        HashingSHA512 hashingSHA512 = new HashingSHA512();
        String hashedPassword = hashingSHA512.hash(signDto.getPassword());
        signDto.setPassword(hashedPassword);

        User newUser = new User(
                signDto.getName(),
                signDto.getEmail(),
                signDto.getPassword()
        );
        authRepository.save(newUser);

        Map<String, String> msg = Map.of("msg", "Sign up success!");
        return new ResponseEntity<>(new Response(200, HttpStatus.OK, msg).jsonObject(), HttpStatus.OK);
    }


    public ResponseEntity signIn(SignDto signDto) {
        Optional<User> optionalUser = authRepository.findByEmail(signDto.getEmail());
        if (!optionalUser.isPresent()) {
            Map<String, String> error = Map.of("err", "Email is not existed!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, error).jsonObject(), HttpStatus.ACCEPTED);
        }
        User user = optionalUser.get();

        HashingSHA512 hashingSHA512 = new HashingSHA512();
        Boolean comparePass = hashingSHA512.compare(signDto.getPassword(), user.getPassword());
        if (!comparePass) {
            Map<String, String> error = Map.of("err", "Password does not match!");
            return new ResponseEntity<>(new Response(202, HttpStatus.ACCEPTED, error).jsonObject(), HttpStatus.ACCEPTED);
        }


        String name = user.getName();
        String email = user.getEmail();

        String token = Jwts.builder()
                .claim("user", user)
                .signWith(SignatureAlgorithm.HS256, System.getenv("JWT_KEY").getBytes(StandardCharsets.UTF_8))
                .compressWith(CompressionCodecs.DEFLATE)
                .compact();


        Map<String, Object> msg = Map.of(
                "msg", "Sign in success",
                "token", token,
                "user", Map.of("name", name,"email", email)
                );
        return new ResponseEntity<>(new Response(200, HttpStatus.OK, msg).jsonObject(), HttpStatus.OK);
    }
}
