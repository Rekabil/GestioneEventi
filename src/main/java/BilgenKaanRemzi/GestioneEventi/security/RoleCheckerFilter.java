package BilgenKaanRemzi.GestioneEventi.security;

import BilgenKaanRemzi.GestioneEventi.entieties.User;
import BilgenKaanRemzi.GestioneEventi.enums.Role;


public class RoleCheckerFilter extends Filter {
    @Override
    public void doCheck(User user) {
        if (user.getRole().equals(Role.ORGANIZER)){
            System.out.println("Authorized");
            this.goNext(user);
        } else {
            System.out.println("Not Authorized");
        }
    }
}
