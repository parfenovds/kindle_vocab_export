package com.parfenov.kindleVocabExport.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllExceptions(Exception ex, Model model) {
    log.error("An exception occurred: {}", ex.getMessage());
    model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
    return new ModelAndView("error");
  }

  @ExceptionHandler(RuntimeException.class)
  public ModelAndView handleRuntimeException(RuntimeException ex, Model model) {
    log.error("A runtime exception occurred: {}", ex.getMessage());
    model.addAttribute("errorMessage", "A runtime error occurred: " + ex.getMessage());
    return new ModelAndView("error-runtime");
  }
}
