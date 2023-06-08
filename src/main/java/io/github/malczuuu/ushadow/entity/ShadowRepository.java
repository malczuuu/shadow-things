package io.github.malczuuu.ushadow.entity;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShadowRepository extends MongoRepository<ShadowEntity, ObjectId> {

  Optional<ShadowEntity> findByThingUid(String thingUid);

  void deleteByThingUid(String thingUid);
}
