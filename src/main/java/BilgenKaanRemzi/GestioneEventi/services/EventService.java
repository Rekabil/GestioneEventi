package BilgenKaanRemzi.GestioneEventi.services;

import BilgenKaanRemzi.GestioneEventi.entieties.Event;
import BilgenKaanRemzi.GestioneEventi.entieties.User;
import BilgenKaanRemzi.GestioneEventi.enums.Role;
import BilgenKaanRemzi.GestioneEventi.exceptions.NotFoundException;
import BilgenKaanRemzi.GestioneEventi.exceptions.UnautorizedException;
import BilgenKaanRemzi.GestioneEventi.repository.EventRepository;
import BilgenKaanRemzi.GestioneEventi.security.RoleCheckerFilter;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EventService {

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserService userService;

    public Event save(Event body, User currentUser) {
        body.setCreatedBy(currentUser);
        if (currentUser.getRole().equals(Role.ORGANIZER)){
            System.out.println("Authorized");
            return eventRepository.save(body);
        } else {
          System.out.println("Not Authorized");
          return body;
        }

    }

    public Page<Event> getEvents(int page,int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return eventRepository.findAll(pageable);
    }

    public Event findById(int id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public void findAndDelete(int id, User currentUser) throws NotFoundException{
        Event found = this.findById(id);
        if (currentUser.equals(found.getCreatedBy())){
            System.out.println("Authorized");
            eventRepository.delete(found);
        } else {
            System.out.println("Not Authorized");
        }
    }

    public Event findAndUpdate(int id, Event body ,User currentUser) {
        Event found = this.findById(id);
        if (currentUser.equals(found.getCreatedBy())) {
            found.setId(id);
            found.setTitle(body.getTitle());
            found.setDescription(body.getDescription());
            found.setDate(body.getDate());
            found.setPicture(body.getPicture());
            found.setLuogo(body.getLuogo());
            return eventRepository.save(found);
        } else return found;

    }

    public String uploadPicture(MultipartFile file)throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

    public Event joinEvent(int id, User currentUser) {
        Event found = this.findById(id);
        if (found.getUserList().stream().anyMatch(currentUser::equals)){
            System.out.println("Already Joined");
            return found;
        } else {
            if (found.getUserList().size() < found.getNumPartecipants()) {
                found.getUserList().add(currentUser);
                return eventRepository.save(found);
            } else  return found;
        }
    }

    public Event removeEvent(int id, User currentUser){
        Event found = this.findById(id);
        found.getUserList().remove(currentUser);
        return eventRepository.save(found);

    }
}
