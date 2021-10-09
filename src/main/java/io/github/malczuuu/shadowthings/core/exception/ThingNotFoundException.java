package io.github.malczuuu.shadowthings.core.exception;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class ThingNotFoundException extends ProblemException {

  public ThingNotFoundException(String id) {
    super(
        Problem.builder()
            .title(HttpStatus.NOT_FOUND.getReasonPhrase())
            .status(HttpStatus.NOT_FOUND.value())
            .detail("Thing \"" + id + "\" not found")
            .build());
  }
}
