package io.github.malczuuu.shadowthings.entity;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class ShadowEntity implements Persistable<ObjectId> {

  public static final String THING_UID = "thingUid";
  public static final String DESIRED = "desired";
  public static final String REPORTED = "reported";
  private static final String LAST_MODIFIED_DATE = "lastModifiedDate";
  private static final String VERSION = "version";

  @MongoId(targetType = FieldType.OBJECT_ID)
  private ObjectId id;

  @Field(name = THING_UID)
  private String thingUid;

  @Field(name = DESIRED)
  private Map<String, Object> desired;

  @Field(name = REPORTED)
  private Map<String, Object> reported;

  @LastModifiedDate
  @Field(name = LAST_MODIFIED_DATE)
  private Instant lastModifiedDate;

  @Version
  @Field(name = VERSION)
  private Long version;

  /**
   * No-args constructor serves only the purpose of Spring Data MongoDB support.
   *
   * @deprecated
   */
  @Deprecated
  public ShadowEntity() {}

  public ShadowEntity(String thingUid) {
    this(thingUid, new HashMap<>(), new HashMap<>());
  }

  public ShadowEntity(String thingUid, Map<String, Object> desired, Map<String, Object> reported) {
    this(null, thingUid, desired, reported, null, null);
  }

  public ShadowEntity(
      ObjectId id,
      String thingUid,
      Map<String, Object> desired,
      Map<String, Object> reported,
      Instant lastModifiedDate,
      Long version) {
    this.id = id;
    this.thingUid = thingUid;
    this.desired = desired != null ? new HashMap<>(desired) : null;
    this.reported = reported != null ? new HashMap<>(reported) : null;
    this.lastModifiedDate = lastModifiedDate;
    this.version = version;
  }

  @Override
  public boolean isNew() {
    return getId() == null;
  }

  @Override
  public ObjectId getId() {
    return id;
  }

  public String getThingUid() {
    return thingUid;
  }

  public Map<String, Object> getDesired() {
    return desired != null ? Collections.unmodifiableMap(desired) : null;
  }

  public Map<String, Object> getReported() {
    return reported != null ? Collections.unmodifiableMap(reported) : null;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public Long getVersion() {
    return version;
  }

  public void setDesired(Map<String, Object> desired) {
    this.desired = desired;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
