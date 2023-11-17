package BilgenKaanRemzi.GestioneEventi.payloads;

import java.util.Date;
import java.util.List;

public record ErrorsResponseWithListDTO(String message, Date timestamp, List<String> errorList) {
}
