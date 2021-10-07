package io.github.malczuuu.shadowthings.core;

import io.github.malczuuu.shadowthings.entity.ThingEntity;
import io.github.malczuuu.shadowthings.model.ThingModel;
import io.github.malczuuu.shadowthings.model.ThingPageModel;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class ThingMapper {

  private final TimeMapper timeMapper = new TimeMapper();

  public ThingPageModel toModel(Page<ThingEntity> things) {
    return new ThingPageModel(
        things.getContent().stream().map(this::toModel).collect(Collectors.toList()),
        things.getNumber(),
        things.getSize(),
        things.getTotalElements(),
        things.getTotalPages());
  }

  public ThingModel toModel(ThingEntity thing) {
    return new ThingModel(
        thing.getUid(),
        thing.getName(),
        thing.isEnabled(),
        thing.getLastModifiedDate() != null
            ? timeMapper.toIsoString(thing.getLastModifiedDate())
            : null,
        thing.getVersion());
  }
}
