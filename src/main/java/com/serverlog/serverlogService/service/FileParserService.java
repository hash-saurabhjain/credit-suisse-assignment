package com.serverlog.serverlogService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverlog.serverlogService.entity.Event;
import com.serverlog.serverlogService.model.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class FileParserService {
    private Map<String, Event> eventsMap;
    Logger logger = LoggerFactory.getLogger(FileParserService.class);

    public FileParserService(){
        eventsMap = new HashMap<>();
    }

    public Map<String, Event> getEvents(){
        return eventsMap;
    }

    public void parseLogs(String filePath) throws IOException {
        logger.info("Parsing of logs started");
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream, "UTF-8");
            // To handle large files reading one entry at a time
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Log log = new ObjectMapper().readValue(line, Log.class);
                Event event = null;
                boolean alert = false;
                if (!eventsMap.containsKey(log.getId())) {
                    event = new Event(log.getId(), log.getTimestamp(), alert, log.getType(), log.getHost());
                    eventsMap.put(log.getId(), event);
                    continue;
                }

                Event existingEvent = eventsMap.remove(log.getId());
                long startTime = existingEvent.getDuration();
                long duration = Math.abs(log.getTimestamp() - startTime);
                if (duration > 4) {
                    alert = true;
                }
                event = new Event(log.getId(), duration, alert, log.getType(), log.getHost());
                eventsMap.put(log.getId(),event);
            }
            logger.info("Parsing of logs ended");
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            logger.error("Log file not found: {}",e.getMessage());
        } catch (IOException e) {
            logger.error("Error: {}",e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    // Writes data from db to output file
    public void writeDataToFile(List<Event> data, String file){
        ObjectMapper mapper = new ObjectMapper();
        try {
            FileWriter fileWriter = new FileWriter(file);
            for(Event entry : data){
                    String json = mapper.writeValueAsString(entry);
                    fileWriter.write(json);
                    fileWriter.write("\n");
            }
            fileWriter.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
