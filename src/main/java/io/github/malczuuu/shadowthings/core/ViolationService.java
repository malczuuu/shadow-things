package io.github.malczuuu.shadowthings.core;

import io.github.malczuuu.shadowthings.entity.Violation;
import io.github.malczuuu.shadowthings.entity.ViolationEntity;
import io.github.malczuuu.shadowthings.entity.ViolationRepository;
import io.github.malczuuu.shadowthings.model.ViolationPage;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class ViolationService {

  private final ViolationRepository violationRepository;

  private final ViolationMapper mapper = new ViolationMapper();

  public ViolationService(ViolationRepository violationRepository) {
    this.violationRepository = violationRepository;
  }

  public ViolationPage getViolations(String thingId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Order.desc(ViolationEntity.ID)));
    Page<ViolationEntity> entities = violationRepository.findAllByThingUid(thingId, pageable);
    return mapper.toModel(entities);
  }

  public ViolationPage getViolations(String thingId, String actionType, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Order.desc(ViolationEntity.ID)));
    Page<ViolationEntity> entities =
        violationRepository.findAllByThingUidAndActionType(thingId, actionType, pageable);
    return mapper.toModel(entities);
  }

  public <T> void storeViolation(
      String thingId, String actionType, Set<ConstraintViolation<T>> violations) {
    ViolationEntity entity =
        new ViolationEntity(
            thingId,
            actionType,
            violations.stream()
                .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList()));
    violationRepository.save(entity);
  }
}
