package com.example.kursovayasocks.controller;



import com.example.kursovayasocks.dto.SocksDto;
import com.example.kursovayasocks.dto.request.SocksParamsRequest;
import com.example.kursovayasocks.dto.response.ErrorResponse;
import com.example.kursovayasocks.exceptions.InvalidRequestParamsException;
import com.example.kursovayasocks.exceptions.NotEnoughItemsException;
import com.example.kursovayasocks.exceptions.SocksNotFoundException;
import com.example.kursovayasocks.service.SocksService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;




import static com.example.kursovayasocks.Api.BASE_ROUTE;
import static com.example.kursovayasocks.Api.SERVICE_ROUTE;
import static com.example.kursovayasocks.Api.Service.INCOME_ROUTE;
import static com.example.kursovayasocks.Api.Service.OUTCOME_ROUTE;

@RestController
@RequestMapping(BASE_ROUTE + SERVICE_ROUTE)
public class SocksController {

    private final SocksService socksService;

    @Autowired
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping(INCOME_ROUTE)
    public ResponseEntity<SocksDto> incomeSocks(@Valid @RequestBody SocksDto body) {
        return new ResponseEntity<>(socksService.incomeSocks(body), HttpStatus.OK);
    }

    @PostMapping(OUTCOME_ROUTE)
    public ResponseEntity<SocksDto> outcomeSocks(@Valid @RequestBody SocksDto body)
            throws NotEnoughItemsException, SocksNotFoundException {
        return new ResponseEntity<>(socksService.outcomeSocks(body), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getSocksCount(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String cottonPart)
            throws InvalidRequestParamsException {
        Byte cottonPartValue = null;
        try{
            if(cottonPart != null) {
                cottonPartValue = new Byte(cottonPart);
            }
        } catch (NumberFormatException e){
            throw new InvalidRequestParamsException("Wrong cottonPart value format", e);
        }
        SocksParamsRequest params = new SocksParamsRequest(
                color,
                operation,
                cottonPartValue);
        return new ResponseEntity<>(Long.toString(socksService.getSocksCountByParams(
                params)), HttpStatus.OK);
    }


    //handlers:
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Invalid values",
                        e.getMessage() + ": " + e.getConstraintViolations()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Invalid values",
                        e.getMessage() + ": " + e.getParameter()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughItemsException.class)
    public ResponseEntity<ErrorResponse> handleException(NotEnoughItemsException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Wrong socks quantity value",
                        e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SocksNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(SocksNotFoundException e){
        String message = e.getMessage();
        if (e.getCause() != null){
            message = message + ": " + e.getCause().getMessage();
        }
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Wrong body values for socks request",
                        message),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestParamsException.class)
    public ResponseEntity<ErrorResponse> handleException(InvalidRequestParamsException e){
        String message = e.getMessage();
        if (e.getCause() != null){
            message = message + ": " + e.getCause().getMessage();
        }
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Wrong param values in request",
                        message),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        400,
                        "Unable to read your request",
                        e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(TransactionException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "internal server database error",
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<ErrorResponse> handleInternalException(BeanCreationException e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "Internal server fatal init error",
                        e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e){
        return new ResponseEntity<>(
                new ErrorResponse(
                        500,
                        "Server returned an unexpected error: " + e.getClass().getName(),
                        e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}



