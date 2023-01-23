package dev.moreno.recipe_project.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Handling number format exception");
        log.error(exception.getMessage());

        var modelAndView = new ModelAndView("/400error.html");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
