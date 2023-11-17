package BilgenKaanRemzi.GestioneEventi.entieties;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "events")
public class Event {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
private String title;
private String description;
private LocalDate date;
private String luogo;
private int numPartecipants;
private String picture;
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User createdBy;

    @ManyToMany
    @JoinColumn(name = "users_id")
    private List<User> userList;
}
