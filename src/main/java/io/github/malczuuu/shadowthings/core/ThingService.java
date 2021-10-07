package io.github.malczuuu.shadowthings.core;

import io.github.malczuuu.shadowthings.entity.ShadowRepository;
import io.github.malczuuu.shadowthings.entity.ThingEntity;
import io.github.malczuuu.shadowthings.entity.ThingRepository;
import io.github.malczuuu.shadowthings.model.CreateThingModel;
import io.github.malczuuu.shadowthings.model.PasswordModel;
import io.github.malczuuu.shadowthings.model.ThingModel;
import io.github.malczuuu.shadowthings.model.ThingPageModel;
import io.github.malczuuu.shadowthings.model.UpdateThingModel;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ThingService {

  private final ShadowRepository shadowRepository;
  private final ThingRepository thingRepository;

  private final ThingMapper mapper = new ThingMapper();

  public ThingService(ShadowRepository shadowRepository, ThingRepository thingRepository) {
    this.shadowRepository = shadowRepository;
    this.thingRepository = thingRepository;
  }

  public ThingPageModel findThings(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ThingEntity> entities = thingRepository.findAll(pageable);
    return mapper.toModel(entities);
  }

  public ThingModel findThing(String id) {
    ThingEntity entity = fetchThing(id);
    return mapper.toModel(entity);
  }

  private ThingEntity fetchThing(String uid) {
    return thingRepository.findByUid(uid).orElseThrow(() -> new ThingNotFoundException(uid));
  }

  public ThingModel createThing(CreateThingModel thing) {
    try {
      ThingEntity entity = new ThingEntity(thing.getId(), thing.getName(), thing.isEnabled());
      entity = thingRepository.save(entity);
      return mapper.toModel(entity);
    } catch (DuplicateKeyException e) {
      throw new ThingDuplicateException(thing.getId());
    } catch (OptimisticLockingFailureException e) {
      throw new ConcurrentUpdateException();
    }
  }

  public ThingModel updateThing(String id, UpdateThingModel thing) {
    ThingEntity entity = fetchThing(id);
    entity.setName(thing.getName());
    entity.setEnabled(thing.isEnabled());
    entity.setVersion(thing.getVersion());
    try {
      entity = thingRepository.save(entity);
    } catch (OptimisticLockingFailureException e) {
      throw new ConcurrentUpdateException();
    }
    return mapper.toModel(entity);
  }

  public void updatePassword(String id, PasswordModel password) {
    ThingEntity entity = fetchThing(id);
    entity.setPassword(password.getPassword());
    entity.setVersion(password.getVersion());
    entity = thingRepository.save(entity);
    try {
      thingRepository.save(entity);
    } catch (OptimisticLockingFailureException e) {
      throw new ConcurrentUpdateException();
    }
  }

  public void deleteThing(String id) {
    shadowRepository.deleteByThingUid(id);
    thingRepository.deleteByUid(id);
  }
}
