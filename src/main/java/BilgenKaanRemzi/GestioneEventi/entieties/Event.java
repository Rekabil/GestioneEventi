package BilgenKaanRemzi.GestioneEventi.entieties;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private Date createdAt;

    private User createdBy;

    @ManyToMany
    @JoinColumn(name = "users_id")
    private List<User> userList;
}
