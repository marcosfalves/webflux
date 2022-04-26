package br.com.mfalves.webflux.exceptionhandler

import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import java.util.stream.Collectors

@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(value = [WebExchangeBindException::class])
    fun handleException(e:WebExchangeBindException) : ResponseEntity<MutableList<String?>> {

        val errors2 = e.bindingResult
            .allErrors
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList())
            .map { it }.toMutableList()

        return ResponseEntity.badRequest().body(errors2)
    }
}