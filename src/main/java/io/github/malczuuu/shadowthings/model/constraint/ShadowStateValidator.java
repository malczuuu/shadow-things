package io.github.malczuuu.shadowthings.model.constraint;

import java.util.Map;
import java.util.Map.Entry;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShadowStateValidator
    implements ConstraintValidator<ValidShadowState, Map<String, Object>> {

  private int fieldNameMaxLength = 256;
  private String fieldNameRegexp = "^[a-zA-Z]\\w*$";
  private int fieldValueMaxLength = 1024;

  @Override
  public void initialize(ValidShadowState constraintAnnotation) {
    fieldNameMaxLength = constraintAnnotation.fieldNameMaxLength();
    fieldNameRegexp = constraintAnnotation.fieldNameRegexp();
    fieldValueMaxLength = constraintAnnotation.fieldValueMaxLength();
  }

  @Override
  public boolean isValid(Map<String, Object> value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    context.disableDefaultConstraintViolation();
    boolean valid = true;

    for (Entry<String, Object> entry : value.entrySet()) {
      String fieldName = entry.getKey();
      Object fieldValue = entry.getValue();

      if (isFieldNameNotTooLong(fieldName)) {
        addFieldNameTooLongViolation(context, fieldName);
        valid = false;
      }

      if (isFieldNameInvalid(fieldName)) {
        addFieldNameInvalidViolation(context, fieldName);
        valid = false;
      }

      if (isStringValueNotTooLong(fieldValue)) {
        addStringValueTooLongViolation(context, fieldName);
        valid = false;
      }

      if (isValidType(fieldValue)) {
        addTypeInvalidViolation(context, fieldName);
        valid = false;
      }
    }
    return valid;
  }

  private boolean isFieldNameNotTooLong(String fieldName) {
    return fieldName.length() > fieldNameMaxLength;
  }

  private void addFieldNameTooLongViolation(ConstraintValidatorContext context, String fieldName) {
    fieldName = "'" + fieldName.substring(0, 26) + "...'";
    addViolation(context, fieldName, "must have field name no longer than " + fieldNameMaxLength);
  }

  private void addViolation(ConstraintValidatorContext context, String fieldName, String s) {
    context
        .buildConstraintViolationWithTemplate(s)
        .addPropertyNode(fieldName)
        .addConstraintViolation();
  }

  private boolean isFieldNameInvalid(String fieldName) {
    return !fieldName.matches(fieldNameRegexp);
  }

  private void addFieldNameInvalidViolation(ConstraintValidatorContext context, String fieldName) {
    if (fieldName.contains(".")) {
      fieldName = "'" + fieldName + "'";
    }
    addViolation(context, fieldName, "must have field name matching " + fieldNameRegexp);
  }

  private boolean isStringValueNotTooLong(Object fieldValue) {
    return fieldValue instanceof String && ((String) fieldValue).length() > fieldValueMaxLength;
  }

  private void addStringValueTooLongViolation(
      ConstraintValidatorContext context, String fieldName) {
    addViolation(context, fieldName, "must not be longer than " + fieldValueMaxLength);
  }

  private boolean isValidType(Object fieldValue) {
    return !(fieldValue instanceof String)
        && !(fieldValue instanceof Boolean)
        && !(fieldValue instanceof Number);
  }

  private void addTypeInvalidViolation(ConstraintValidatorContext context, String fieldName) {
    addViolation(context, fieldName, "must be a number, string or boolean value");
  }
}
