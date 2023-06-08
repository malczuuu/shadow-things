package io.github.malczuuu.ushadow.entity;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "things")
public class ThingEntity implements Persistable<ObjectId> {

  public static final String ID = "_id";
  public static final String UID = "uid";
  public static final String NAME = "name";
  public static final String ENABLED = "enabled";
  public static final String PASSWORD = "password";
  public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
  public static final String VERSION = "version";

  @MongoId(targetType = FieldType.OBJECT_ID)
  private ObjectId id;

  @Field(name = UID)
  private String uid;

  @Field(name = NAME)
  private String name;

  @Field(name = ENABLED)
  private Boolean enabled;

  @Field(name = PASSWORD)
  private String password;

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
  public ThingEntity() {}

  public ThingEntity(String uid, String name, boolean enabled) {
    this(uid, name, enabled, null);
  }

  public ThingEntity(String uid, String name, boolean enabled, String password) {
    this(null, uid, name, enabled, password, null, null);
  }

  public ThingEntity(
      ObjectId id,
      String uid,
      String name,
      boolean enabled,
      String password,
      Instant lastModifiedDate,
      Long version) {
    this.id = id;
    this.uid = uid;
    this.name = name;
    this.enabled = enabled;
    this.password = password;
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

  public String getUid() {
    return uid;
  }

  public String getName() {
    return name;
  }

  public boolean isEnabled() {
    return enabled != null && enabled;
  }

  public String getPassword() {
    return password;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public Long getVersion() {
    return version;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
