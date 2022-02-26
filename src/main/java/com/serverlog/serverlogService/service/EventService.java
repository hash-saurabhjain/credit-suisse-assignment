package com.serverlog.serverlogService.service;

import com.serverlog.serverlogService.entity.Event;
import com.serverlog.serverlogService.repository.EventRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventService {
    Logger logger = LoggerFactory.getLogger(EventService.class);
    @Autowired
    EventRepo eventRepo;

    public void saveAllEvents(Map<String, Event> events){
        try{
            eventRepo.saveAll(events.values());
            logger.info("All events saved successfully");
        }catch (Exception e){
            logger.error("Error on Save: {}",e.getMessage());
        }
    }

    // Retrieves all events whose alert is set true
    public List<Event> getAlertEvents(){
        return eventRepo.findByAlert(true);
    }

    public List<Event> getAllEvents(){
        return (ArrayList<Event>)eventRepo.findAll();
    }
}
