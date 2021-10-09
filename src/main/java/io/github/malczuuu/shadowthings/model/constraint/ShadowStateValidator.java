package io.github.malczuuu.shadowthings.model.constraint;

import java.util.Map;
import java.util.Map.Entry;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShadowStateValidator
    implements ConstraintValidator<ValidShadowState, Map<String, Object>> {

  private int fieldNameMaxLength = 256;
  private int fieldValueMaxLength = 1024;

  @Override
  public void initialize(ValidShadowState constraintAnnotation) {
    fieldNameMaxLength = constraintAnnotation.fieldNameMaxLength();
    fieldValueMaxLength = constraintAnnotation.fieldValueMaxLength();
  }

  @Override
  public boolean isValid(Map<String, Object> value, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    boolean valid = true;
    for (Entry<String, Object> entry : value.entrySet()) {
      String fieldName = entry.getKey();
      Object fieldValue = entry.getValue();

      if (isFieldNameNotTooLong(fieldName)) {
        context
            .buildConstraintViolationWithTemplate("must have field name no longer than 256")
            .addPropertyNode(fieldName)
            .addConstraintViolation();
        valid = false;
      }

      if (isStringValueNotTooLong(fieldValue)) {
        context
            .buildConstraintViolationWithTemplate("must not be longer than 1024")
            .addPropertyNode(fieldName)
            .addConstraintViolation();
        valid = false;
      }

      if (isValidType(fieldValue)) {
        context
            .buildConstraintViolationWithTemplate("must be a number, string or boolean value")
            .addPropertyNode(fieldName)
            .addConstraintViolation();
        valid = false;
      }
    }
    return valid;
  }

  private boolean isFieldNameNotTooLong(String fieldName) {
    return fieldName.length() > fieldNameMaxLength;
  }

  private boolean isStringValueNotTooLong(Object fieldValue) {
    return fieldValue instanceof String && ((String) fieldValue).length() > fieldValueMaxLength;
  }

  private boolean isValidType(Object fieldValue) {
    return !(fieldValue instanceof String)
        && !(fieldValue instanceof Boolean)
        && !(fieldValue instanceof Number);
  }
}
