package io.github.malczuuu.silhouette.configuration;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket docket() {
    ApiInfo apiInfo =
        new ApiInfo(
            "Silhouette API",
            "",
            "1.0.0-SNAPSHOT",
            "",
            new Contact("", "", ""),
            "",
            "",
            Collections.emptyList());
    return new Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo)
        .select()
        .paths(path -> path.startsWith("/api"))
        .build()
        .tags(new Tag("shadow", "Shadow API"), new Tag("things", "Things API"));
  }
}
