package io.github.malczuuu.shadowthings.core;

import io.github.malczuuu.problem4j.core.Problem;
import io.github.malczuuu.problem4j.core.ProblemException;
import org.springframework.http.HttpStatus;

public class ConcurrentUpdateException extends ProblemException {

  public ConcurrentUpdateException() {
    super(
        Problem.builder()
            .title(HttpStatus.CONFLICT.getReasonPhrase())
            .status(HttpStatus.CONFLICT.value())
            .detail("Version field mismatched")
            .build());
  }
}
