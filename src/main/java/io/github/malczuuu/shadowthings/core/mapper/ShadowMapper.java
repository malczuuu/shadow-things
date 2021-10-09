package io.github.malczuuu.shadowthings.core.mapper;

import io.github.malczuuu.shadowthings.entity.ShadowEntity;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import java.util.HashMap;

public class ShadowMapper {

  private final TimeMapper timeMapper = new TimeMapper();

  public ShadowModel toModel(ShadowEntity shadow) {
    return new ShadowModel(
        new HashMap<>(shadow.getDesired()),
        new HashMap<>(shadow.getReported()),
        shadow.getLastModifiedDate() != null
            ? timeMapper.toIsoString(shadow.getLastModifiedDate())
            : null,
        shadow.getVersion());
  }
}
