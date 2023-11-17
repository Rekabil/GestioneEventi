package BilgenKaanRemzi.GestioneEventi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewUserDTO(
        @NotEmpty(message = "Name is Obligatory.")
        @Size(min = 3,max = 30,message = "The Name Has to be from 3 to 30 caracters long.")
        String name,
        @NotEmpty(message = "Surname Is Obligatory")
        String surname,
        @NotEmpty(message = "Email is Obligatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email is not Valid")
        String email,
        @NotEmpty(message = "Password is Obligatory")
        String password) {
}
