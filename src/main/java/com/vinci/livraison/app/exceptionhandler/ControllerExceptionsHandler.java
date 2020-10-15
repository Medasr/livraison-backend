package com.vinci.livraison.app.exceptionhandler;

import com.vinci.livraison.app.module.article.entity.ProdCat;
import com.vinci.livraison.app.module.article.exception.*;
import com.vinci.livraison.app.module.commande.exception.CommandeCantHaveNoLigneCommande;
import com.vinci.livraison.app.module.commande.exception.UnableToUpdateCommandeEtat;
import com.vinci.livraison.app.module.shared.exception.EntityNotFoundException;
import com.vinci.livraison.app.module.shared.exception.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                ex.getErrors()
        );

        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    @ExceptionHandler({EntityNotFoundException.class, ProdCatNotExistsException.class, ProdMenuNotExistsException.class})
    public ResponseEntity<Object> handleEntityNotFoundExceptionException(RuntimeException ex, WebRequest request) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.badRequest()
                .body(apiErrorResponse);
    }

    @ExceptionHandler({
            ArticleCantHaveNoCategorie.class, MenuCantHaveNoProduit.class ,
            ProdMenuAlreadyExistsException.class , ProdCatAlreadyExistsException.class,
            CommandeCantHaveNoLigneCommande.class , UnableToUpdateCommandeEtat.class
    })
    public ResponseEntity<Object> handleAuthenticationException(RuntimeException ex, WebRequest request) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return ResponseEntity.badRequest()
                .body(apiErrorResponse);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        String message = "Validation échoue";

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    String msg = error.getDefaultMessage();
                    errors.put(field, msg);
                });

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(status.getReasonPhrase(), message, ((errors.isEmpty()) ? null : errors));

        return handleExceptionInternal(ex, apiErrorResponse, headers, status, request);
    }


}
