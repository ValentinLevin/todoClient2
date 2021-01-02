package kz.testcenter.todoclient.config;

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ToString
@Configuration
@ConfigurationProperties(prefix = "todo.server")
public class ToDoServerProperties {
    private String url;
    private String basePath;
}
