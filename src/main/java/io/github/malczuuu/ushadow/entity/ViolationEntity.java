package io.github.malczuuu.ushadow.entity;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "violations")
public class ViolationEntity {

  public static final String ID = "_id";
  public static final String THING_UID = "thingUid";
  public static final String ACTION_TYPE = "actionType";
  public static final String VIOLATIONS = "violations";

  @MongoId(targetType = FieldType.OBJECT_ID)
  private ObjectId id;

  @Field(name = THING_UID)
  private String thingUid;

  @Field(name = ACTION_TYPE)
  private String actionType;

  @Field(name = VIOLATIONS)
  private List<Violation> violations;

  public ViolationEntity() {}

  public ViolationEntity(String thingUid, String actionType, List<Violation> violations) {
    this(null, thingUid, actionType, violations);
  }

  public ViolationEntity(
      ObjectId id, String thingUid, String actionType, List<Violation> violations) {
    this.id = id;
    this.thingUid = thingUid;
    this.actionType = actionType;
    this.violations = violations;
  }

  public ObjectId getId() {
    return id;
  }

  public String getActionType() {
    return actionType;
  }

  public String getThingUid() {
    return thingUid;
  }

  public List<Violation> getViolations() {
    return violations;
  }
}
