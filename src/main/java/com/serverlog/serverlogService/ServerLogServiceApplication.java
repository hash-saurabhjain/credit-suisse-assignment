package com.serverlog.serverlogService;

import com.serverlog.serverlogService.service.EventService;
import com.serverlog.serverlogService.service.FileParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ServerLogServiceApplication implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(ServerLogServiceApplication.class);
	@Autowired
	FileParserService fileParserService;

	@Autowired
	EventService eventService;

	public static void main(String[] args) {
		SpringApplication.run(ServerLogServiceApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length == 0) {
			logger.error("Log file not provided");
			return;
		}
		fileParserService.parseLogs(args[0]);
		eventService.saveAllEvents(fileParserService.getEvents());
		fileParserService.writeDataToFile(eventService.getAlertEvents(), "AlertEvents.txt");
		fileParserService.writeDataToFile(eventService.getAllEvents(), "AllEvents.txt");
	}
}
