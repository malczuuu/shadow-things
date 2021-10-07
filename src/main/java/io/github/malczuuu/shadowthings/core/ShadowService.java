package io.github.malczuuu.shadowthings.core;

import io.github.malczuuu.shadowthings.entity.ShadowEntity;
import io.github.malczuuu.shadowthings.entity.ShadowRepository;
import io.github.malczuuu.shadowthings.model.ShadowModel;
import io.github.malczuuu.shadowthings.model.UpdateShadowModel;
import java.util.HashMap;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class ShadowService {

  private final ShadowRepository shadowRepository;

  private final ShadowMapper mapper = new ShadowMapper();

  public ShadowService(ShadowRepository shadowRepository) {
    this.shadowRepository = shadowRepository;
  }

  public ShadowModel findShadow(String thingId) {
    ShadowEntity shadow = fetchThing(thingId);
    return mapper.toModel(shadow);
  }

  public ShadowModel updateShadow(String thingId, UpdateShadowModel update) {
    ShadowEntity shadow = fetchThing(thingId);
    shadow.setDesired(new HashMap<>(update.getDesired()));
    shadow.setVersion(update.getVersion());
    try {
      shadow = shadowRepository.save(shadow);
    } catch (OptimisticLockingFailureException e) {
      throw new ConcurrentUpdateException();
    }
    return mapper.toModel(shadow);
  }

  private ShadowEntity fetchThing(String thingUid) {
    return shadowRepository
        .findByThingUid(thingUid)
        .orElseGet(() -> shadowRepository.save(new ShadowEntity(thingUid)));
  }
}
