package BilgenKaanRemzi.GestioneEventi.controllers;

import BilgenKaanRemzi.GestioneEventi.entieties.Event;
import BilgenKaanRemzi.GestioneEventi.entieties.User;
import BilgenKaanRemzi.GestioneEventi.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/events")
public class EventControllers {
    @Autowired
    private EventService eventService;


    @GetMapping("")
    public Page<Event> getEvent(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "id") String orderby){
        return eventService.getEvents(page, size, orderby);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Event saveEvent(@RequestBody Event body,@AuthenticationPrincipal User currentUser) {return eventService.save(body, currentUser);}

    @GetMapping("/{id}")
    public Event getById(@PathVariable int id) {
        return eventService.findById(id);
    }
    @PutMapping("/{id}")
    public Event findAndUpdate(@PathVariable int id,@RequestBody Event body,@AuthenticationPrincipal User currentUser ) {
        return  eventService.findAndUpdate(id,body,currentUser);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable int id, @AuthenticationPrincipal User currentUser){ eventService.findAndDelete(id,currentUser);}

    @PostMapping("/upload")
    public String upload(@RequestParam("picture") MultipartFile body) throws IOException {
        System.out.println(body.getSize());
        System.out.println(body.getContentType());
        return eventService.uploadPicture(body);
    }

    @PutMapping("/{id}/join")
    public Event joinEvent(@PathVariable int id, @AuthenticationPrincipal User currentUser){
        return eventService.joinEvent(id, currentUser);
    }

    @PutMapping("/{id}/remove")
    public Event removeEvent(@PathVariable int id, @AuthenticationPrincipal User currentUser) {
        return eventService.removeEvent(id, currentUser);
    }
}
