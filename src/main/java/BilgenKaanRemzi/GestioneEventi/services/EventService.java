package BilgenKaanRemzi.GestioneEventi.services;

import BilgenKaanRemzi.GestioneEventi.entieties.Event;
import BilgenKaanRemzi.GestioneEventi.exceptions.NotFoundException;
import BilgenKaanRemzi.GestioneEventi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event save(Event body) {
        return eventRepository.save(body);
    }

    public Page<Event> getEvents(int page,int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return eventRepository.findAll(pageable);
    }

    public Event findById(int id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public void findAndDelete(int id) throws NotFoundException{
        Event found = this.findById(id);
        eventRepository.delete(found);
    }

    public Event findAndUpdate(int id, Event body) {
        Event found = this.findById(id);
        found.setId(id);
        found.setTitle(body.getTitle());
        return eventRepository.save(found);
    }

}
