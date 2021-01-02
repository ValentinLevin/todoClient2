package kz.testcenter.todoclient;

import kz.testcenter.todoclient.config.ToDoServerProperties;
import kz.testcenter.todoclient.domain.ToDo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ToDoRestClient {
    private final RestTemplate restTemplate;
    private final ToDoServerProperties toDoServerProperties;

    public ToDoRestClient(ToDoServerProperties toDoServerProperties) {
        this.toDoServerProperties = toDoServerProperties;
        this.restTemplate = new RestTemplate();
        this.restTemplate.setErrorHandler(new ToDoErrorHandler());
    }

    public ToDo findToDo(String id) throws Exception {
        URI serviceAddress =
                UriComponentsBuilder.fromHttpUrl(this.toDoServerProperties.getUrl())
                        .pathSegment(this.toDoServerProperties.getBasePath())
                        .pathSegment("items")
                        .pathSegment("{id}").buildAndExpand(id).toUri();

        return this.restTemplate.getForObject(serviceAddress, ToDo.class);
    }

    public ToDo addToDo(ToDo toDo) throws URISyntaxException, Exception {
        URI serviceAddress =
                UriComponentsBuilder.fromHttpUrl(toDoServerProperties.getUrl())
                    .pathSegment(toDoServerProperties.getBasePath())
                    .pathSegment("todo")
                    .pathSegment("items")
                    .build().toUri();


        RequestEntity<ToDo> request = new RequestEntity<>(toDo, HttpMethod.POST, serviceAddress);

        ResponseEntity<ToDo> response = restTemplate.exchange(request, new ParameterizedTypeReference<ToDo>() {});

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return this.restTemplate.getForObject(response.getHeaders().getLocation(), ToDo.class);
        } else {
            throw new Exception(response.getStatusCode().getReasonPhrase());
        }
    }

    public void deleteToDo(String id) {
        URI serviceAddress =
                UriComponentsBuilder.fromHttpUrl(toDoServerProperties.getUrl())
                .pathSegment(toDoServerProperties.getBasePath())
                .pathSegment("todo")
                .pathSegment("items")
                .pathSegment("{id}").buildAndExpand(id).toUri();
        this.restTemplate.delete(serviceAddress);
    }

    public Iterable<ToDo> getAll() {
        URI serviceAddress =
                UriComponentsBuilder.fromHttpUrl(toDoServerProperties.getUrl())
                        .pathSegment(toDoServerProperties.getBasePath())
                        .pathSegment("todo")
                        .pathSegment("items")
                        .build().toUri();
        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, serviceAddress);
        ResponseEntity<List<ToDo>> response = this.restTemplate.exchange(request, new ParameterizedTypeReference<List<ToDo>>() {});
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }
}
