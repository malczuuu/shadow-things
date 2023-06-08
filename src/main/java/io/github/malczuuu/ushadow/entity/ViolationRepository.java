package io.github.malczuuu.ushadow.entity;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ViolationRepository extends MongoRepository<ViolationEntity, ObjectId> {

  Page<ViolationEntity> findAllByThingUid(String thingUid, Pageable pageable);

  Page<ViolationEntity> findAllByThingUidAndActionType(
      String thingUid, String actionType, Pageable pageable);

  void deleteAllByThingUid(String thingUid);
}
