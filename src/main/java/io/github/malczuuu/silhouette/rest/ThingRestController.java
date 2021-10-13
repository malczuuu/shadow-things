package io.github.malczuuu.silhouette.rest;

import io.github.malczuuu.silhouette.core.ThingService;
import io.github.malczuuu.silhouette.core.mapper.ParamMapper;
import io.github.malczuuu.silhouette.model.CreateThingModel;
import io.github.malczuuu.silhouette.model.PasswordModel;
import io.github.malczuuu.silhouette.model.ThingModel;
import io.github.malczuuu.silhouette.model.ThingPageModel;
import io.github.malczuuu.silhouette.model.UpdateThingModel;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "things")
@RestController
@RequestMapping(path = "/api/things")
public class ThingRestController {

  private final ThingService thingService;

  private final ParamMapper paramMapper = new ParamMapper();

  public ThingRestController(ThingService thingService) {
    this.thingService = thingService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ThingPageModel findThings(
      @RequestParam(name = "page", required = false) String page,
      @RequestParam(name = "size", required = false) String size) {
    int pageAsInt = paramMapper.parseInteger(page, 0, 0);
    int sizeAsInt = paramMapper.parseInteger(size, 20, 1, 1000);
    return thingService.findThings(pageAsInt, sizeAsInt);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ThingModel findThing(@PathVariable("id") String id) {
    return thingService.findThing(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ThingModel createThing(@RequestBody @Valid CreateThingModel requestBody) {
    return thingService.createThing(requestBody);
  }

  @PutMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ThingModel updateThing(
      @PathVariable("id") String id, @RequestBody @Valid UpdateThingModel requestBody) {
    return thingService.updateThing(id, requestBody);
  }

  @PutMapping(
      path = "/{id}/password",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePassword(
      @PathVariable("id") String id, @RequestBody @Valid PasswordModel requestBody) {
    thingService.updatePassword(id, requestBody);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteThing(@PathVariable("id") String id) {
    thingService.deleteThing(id);
  }
}
