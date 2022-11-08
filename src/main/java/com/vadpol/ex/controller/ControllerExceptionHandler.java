package com.vadpol.ex.controller;

import com.vadpol.ex.exceptions.WalletNotFoundException;
import com.vadpol.ex.exceptions.UserAlreadyExistsException;
import com.vadpol.ex.exceptions.NotEnoughtMoneyException;
import com.vadpol.ex.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleInvalidTopUpTypeException(Exception ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {
            UserAlreadyExistsException.class})
    public ResponseEntity<?> handleNonUniqueUserException(Exception ex) {
        return new ResponseEntity("A user with the same name already exists in the system", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {
            UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(Exception ex) {
        return new ResponseEntity("User is not in the system", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {
            WalletNotFoundException.class})
    public ResponseEntity<?> handleAmountNotFoundException(Exception ex) {
        return new ResponseEntity("You entered incorrect data ....", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = {
            NotEnoughtMoneyException.class})
    public ResponseEntity<?> handleMoneyException(Exception ex) {
        return new ResponseEntity("No money no honey ...)", HttpStatus.BAD_REQUEST);

    }
}