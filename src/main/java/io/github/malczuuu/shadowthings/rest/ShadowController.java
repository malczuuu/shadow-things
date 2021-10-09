package io.github.malczuuu.shadowthings.rest;

import io.github.malczuuu.shadowthings.core.ShadowService;
import io.github.malczuuu.shadowthings.core.ThingService;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import io.github.malczuuu.shadowthings.model.UpdateDesiredModel;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/things/{id}/shadow")
public class ShadowController {

  private final ThingService thingService;
  private final ShadowService shadowService;

  public ShadowController(ThingService thingService, ShadowService shadowService) {
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
