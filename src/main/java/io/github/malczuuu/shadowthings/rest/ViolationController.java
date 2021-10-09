package io.github.malczuuu.shadowthings.rest;

import io.github.malczuuu.shadowthings.core.ParamMapper;
import io.github.malczuuu.shadowthings.core.ThingService;
import io.github.malczuuu.shadowthings.core.ViolationService;
import io.github.malczuuu.shadowthings.model.ViolationPage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/things/{id}/shadow/violations")
public class ViolationController {

  private final ThingService thingService;
  private final ViolationService violationService;

  private final ParamMapper paramMapper = new ParamMapper();

  public ViolationController(ThingService thingService, ViolationService violationService) {
    this.thingService = thingService;
    this.violationService = violationService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ViolationPage getViolations(
      @PathVariable("id") String id,
      @RequestParam(name = "page", required = false) String page,
      @RequestParam(name = "size", required = false) String size) {
    thingService.requireThing(id);
    int pageAsInt = paramMapper.parseInteger(page, 0, 0);
    int sizeAsInt = paramMapper.parseInteger(size, 20, 1, 1000);
    return violationService.getViolations(id, pageAsInt, sizeAsInt);
  }

  @GetMapping(
      params = {"action_type"},
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ViolationPage getViolationsByActionType(
      @PathVariable("id") String id,
      @RequestParam(name = "action_type", required = false) String actionType,
      @RequestParam(name = "page", required = false) String page,
      @RequestParam(name = "size", required = false) String size) {
    thingService.requireThing(id);
    int pageAsInt = paramMapper.parseInteger(page, 0, 0);
    int sizeAsInt = paramMapper.parseInteger(size, 20, 1, 1000);
    return violationService.getViolations(id, actionType, pageAsInt, sizeAsInt);
  }
}
