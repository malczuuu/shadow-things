package io.github.malczuuu.silhouette.core.mapper;

import io.github.malczuuu.silhouette.entity.ViolationEntity;
import io.github.malczuuu.silhouette.model.ViolationModel;
import io.github.malczuuu.silhouette.model.ViolationPage;
import io.github.malczuuu.silhouette.model.ViolationRecord;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class ViolationMapper {

  private final TimeMapper timeMapper = new TimeMapper();

  public ViolationModel toModel(ViolationEntity entity) {
    return new ViolationModel(
        entity.getActionType(),
        entity.getViolations().stream()
            .map(v -> new ViolationRecord(v.getPath(), v.getMessage()))
            .collect(Collectors.toList()),
        entity.getId() != null ? timeMapper.toIsoString(entity.getId()) : null);
  }

  public ViolationPage toModel(Page<ViolationEntity> entities) {
    return new ViolationPage(
        entities.getContent().stream().map(this::toModel).collect(Collectors.toList()),
        entities.getNumber(),
        entities.getSize(),
        entities.getTotalElements(),
        entities.getTotalPages());
  }
}
