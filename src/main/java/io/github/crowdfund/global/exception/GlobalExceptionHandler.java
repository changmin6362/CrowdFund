package io.github.crowdfund.global.exception;


import io.github.crowdfund.global.common.ApiResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 전역 예외 처리 핸들러
 */
@RestControllerAdvice()
public class GlobalExceptionHandler {

    // Controller의 @Validated 어노테이션으로 인한 @PathVariable과 @RequestParam 검증 에러 감지
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResult<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        // 내부 에러 메시지 중 첫 번째 메시지를 추출합니다.
        String message = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("@PathVariable이나 @RequestParam 검증 에러가 감지되었습니다. (에러 메시지 누락)");

        return ResponseEntity.badRequest().body(ApiResult.error(message));
    }

    // Controller의 @Valid 어노테이션으로 인한 @RequestBody 검증 에러 감지
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResult<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse("@RequestBody 검증 에러가 감지되었습니다. (에러 메시지 누락)");
        return ResponseEntity.badRequest().body(ApiResult.error(message));
    }

    // 타입 변환 에러 감지 (엔드포인트 파라미터 형식 오류)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResult<Void>> handleTypeMismatchException() {
        String message = "요청하신 엔드포인트 파라미터의 데이터 형식이 잘못되었습니다.";
        return ResponseEntity.badRequest().body(ApiResult.error(message));
    }

    // IllegalArgumentException로 인한 비즈니스 로직 예외 에러 감지
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiResult<Void>> handleBusinessException(RuntimeException e) {
        // HTTP 상태 코드는 400 Bad Request로, Body는 ApiResult 구조에 맞춰서 반환
        return ResponseEntity
                .badRequest()
                .body(ApiResult.error(e.getMessage()));
    }
}
