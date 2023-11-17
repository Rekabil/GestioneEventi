package BilgenKaanRemzi.GestioneEventi.services;

import BilgenKaanRemzi.GestioneEventi.entieties.User;
import BilgenKaanRemzi.GestioneEventi.enums.Role;
import BilgenKaanRemzi.GestioneEventi.exceptions.BadRequestException;
import BilgenKaanRemzi.GestioneEventi.exceptions.UnautorizedException;
import BilgenKaanRemzi.GestioneEventi.payloads.NewUserDTO;
import BilgenKaanRemzi.GestioneEventi.payloads.UserLoginDTO;
import BilgenKaanRemzi.GestioneEventi.repository.UserRepository;
import BilgenKaanRemzi.GestioneEventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body){
        User user = userService.findByEmail(body.email());

        if (bcrypt.matches(body.password(),user.getPassword())){
            return jwtTools.createToken(user);
        } else {
            throw new UnautorizedException("Credentials not Valid") ;
        }
    }

    public User save(NewUserDTO body)  {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();
        newUser.setPicture("http://ui-avatars.com/api/?name="+body.name() + "+" + body.surname());
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }
}
