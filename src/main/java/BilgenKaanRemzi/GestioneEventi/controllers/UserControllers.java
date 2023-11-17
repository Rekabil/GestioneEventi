package BilgenKaanRemzi.GestioneEventi.controllers;

import BilgenKaanRemzi.GestioneEventi.entieties.User;
import BilgenKaanRemzi.GestioneEventi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserControllers {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public Page<User> getDevice(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "id") String orderby){
        return userService.GetUsers(page, size, orderby);
    }

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    }
    @PutMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser, @RequestBody User body) {
        return userService.findAndUpdate(currentUser.getId(),body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getProfile(@AuthenticationPrincipal User currentUser){
        userService.findAndDelete(currentUser.getId());
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return userService.findById(id);
    }
    @PutMapping("/{id}")
    public User findAndUpdate(@PathVariable int id,@RequestBody User body) {
        return  userService.findAndUpdate(id,body);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable int id){ userService.findAndDelete(id);}

}
