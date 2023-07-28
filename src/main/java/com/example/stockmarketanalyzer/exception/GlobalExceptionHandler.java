/*
 * Copyright © Progmasters (QTC Kft.), 2018.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed,
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft.
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

package com.example.stockmarketanalyzer.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error("A validation error occurred: ", ex);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
        ValidationError validationError = new ValidationError();

        for (FieldError fieldError: fieldErrors) {
            validationError.addFieldError(fieldError.getField(), messageSource.getMessage(fieldError, Locale.getDefault()));
        }

        return validationError;
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ApiError> handleJsonParseException(JsonParseException ex) {
        logger.error("Request JSON could no be parsed: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("JSON_PARSE_ERROR","The request could not be parsed as a valid JSON.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiError> handleIOException(IOException ex) {
        logger.error("Request JSON could no be parsed: ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError body = new ApiError("IOException","An IOException has occurred.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Illegal argument error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("ILLEGAL_ARGUMENT_ERROR", "An illegal argument has been passed to the method.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.error("Entity not found error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("ENTITY_NOT_FOUND_ERROR", "The entity was not found", ex.getLocalizedMessage());

        return new ResponseEntity<>(body,status);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ApiError> handleClassCastException(ClassCastException ex) {
        logger.error("Class cast error: ", ex);
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiError body = new ApiError("ACCESS_IS_UNAUTHORIZED", "You have to register/login to use this feature.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body,status);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ApiError> handleInvalidFormatException(InvalidFormatException ex) {
        logger.error("Invalid format error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("INVALID_FORMAT_ERROR", "Validation failed.Please check the given parameters.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body,status);
    }

    @ExceptionHandler(IllegalCallerException.class)
    public ResponseEntity<ApiError> handleIllegalCallerException(IllegalCallerException ex) {
        logger.error("Illegal caller error: ", ex);
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ApiError body = new ApiError("ILLEGAL_CALLER_ERROR", "Illegal caller, access denied.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body,status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("HTTP message not readable  error: ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError("VALIDATION_ERROR", "Validation failed.Please check the given parameters.", ex.getLocalizedMessage());

        return new ResponseEntity<>(body,status);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> defaultErrorHandler(Throwable t) {
        logger.error("An unexpected error occurred: ", t);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError body = new ApiError("UNCLASSIFIED_ERROR", "Oh, snap! Something really unexpected occurred.", t.getLocalizedMessage());

        return new ResponseEntity<>(body, status);
    }

}

