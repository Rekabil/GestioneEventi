package BilgenKaanRemzi.GestioneEventi.security;

import BilgenKaanRemzi.GestioneEventi.entieties.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Filter {
    private Filter next;
    public abstract void doCheck(User user  );

    public  void goNext(User user ){
        if (this.getNext() != null){
            this.next.doCheck(user);
        } else {
            System.out.println("Siamo alla fine della catena");
        }
    } }