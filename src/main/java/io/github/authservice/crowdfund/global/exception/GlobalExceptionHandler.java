package io.github.authservice.crowdfund.global.exception;


import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice()
public class GlobalExceptionHandler {

    // 1. 일반적인 검증 에러 (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<@NonNull String> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse("입력값이 유효하지 않습니다.");
        return ResponseEntity.badRequest().body(message);
    }

    // 2. 타입 변환 에러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<@NonNull String> handleTypeMismatchException() {

        String message = "잘못된 요청값 입니다.";

        return ResponseEntity.badRequest().body(message);
    }

    // 3. 비즈니스 로직 예외(서비스에서 throw new IllegalArgumentException 사용 시 try catch 문 없이 에러 감지)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<@NonNull String> handleBusinessException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
