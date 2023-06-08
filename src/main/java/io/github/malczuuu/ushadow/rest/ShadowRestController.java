package io.github.malczuuu.ushadow.rest;

import io.github.malczuuu.ushadow.core.ShadowService;
import io.github.malczuuu.ushadow.core.ThingService;
import io.github.malczuuu.ushadow.model.ShadowModel;
import io.github.malczuuu.ushadow.model.UpdateDesiredModel;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/things/{id}/shadow")
public class ShadowRestController {

  private final ThingService thingService;
  private final ShadowService shadowService;

  public ShadowRestController(ThingService thingService, ShadowService shadowService) {
    this.thingService = thingService;
    this.shadowService = shadowService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ShadowModel findShadow(@PathVariable("id") String id) {
    thingService.requireThing(id);
    return shadowService.findShadow(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ShadowModel updateShadow(
      @PathVariable("id") String id, @RequestBody @Valid UpdateDesiredModel requestBody) {
    thingService.requireThing(id);
    return shadowService.updateShadow(id, requestBody);
  }
}
