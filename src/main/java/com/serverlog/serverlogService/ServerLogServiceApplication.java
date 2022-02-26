package com.serverlog.serverlogService;

import com.serverlog.serverlogService.service.EventService;
import com.serverlog.serverlogService.service.FileParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ServerLogServiceApplication implements CommandLineRunner {
	@Autowired
	FileParserService fileParserService;

	@Autowired
	EventService eventService;

	public static void main(String[] args) {
		SpringApplication.run(ServerLogServiceApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		fileParserService.parseLogs(args[0]);
		eventService.saveAllEvents(fileParserService.getEvents());
		fileParserService.writeDataToFile(eventService.getAlertEvents(), "AlertEvents.txt");
		fileParserService.writeDataToFile(eventService.getAllEvents(), "AllEvents.txt");
	}
}
