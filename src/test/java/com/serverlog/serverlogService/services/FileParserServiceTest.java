package com.serverlog.serverlogService.services;

import com.serverlog.serverlogService.entity.Event;
import com.serverlog.serverlogService.service.FileParserService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParserServiceTest {

    private FileParserService mockParser = new FileParserService();

    @Test
    public void parseLogsTest() {
        try {
            mockParser.parseLogs("src/test/resources/testLogFile.txt");
            assert(mockParser.getEvents().size() == 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeDataToFileTest() {
        try {
            List<Event> data = new ArrayList<>();
            data.add(new Event());
            mockParser.writeDataToFile(data, "src/test/resources/outputFile.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
