package kz.testcenter.todoclient;

import kz.testcenter.todoclient.domain.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class TodoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner process(ToDoRestClient toDoClient) {
        return (String[] args) -> {
            toDoClient.addToDo(new ToDo("first todo"));
            toDoClient.addToDo(new ToDo("second todo"));

            Iterable<ToDo> toDos = toDoClient.getAll();

            if (toDos != null) {
                toDos.forEach((ToDo item) -> {
                    log.info(item.toString());
                    toDoClient.deleteToDo(item.getId());
                });
            }

            toDos = toDoClient.getAll();

            if (toDos != null) {
                toDos.forEach((ToDo item) -> {
                    log.info(item.toString());
                });
            }

        };
    }

}
