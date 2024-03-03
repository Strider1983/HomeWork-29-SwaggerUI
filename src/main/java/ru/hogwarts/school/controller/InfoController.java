package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController

public class InfoController {
    @Value("${server.port}")
    private Integer port;
    @GetMapping("port")
    public int getPort() {
        return port;

    }
    private final Logger logger = LoggerFactory.getLogger(InfoController.class);
    @GetMapping("sum")
    public int calculateSum() {
        //Последовательный метод
        long startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1)
                .limit(10_000_000)
                .reduce(0, (a, b) -> a + b );
        long finishTime = System.currentTimeMillis();
        logger.info("Последовательный метод занял " + (finishTime - startTime));

        //Параллельный метод
        startTime = System.currentTimeMillis();
        sum = Stream.iterate(1, a -> a +1)
                .limit(10_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        finishTime = System.currentTimeMillis();
        logger.info("Параллельный метод занял " + (finishTime - startTime));

        return sum;
    }


}
