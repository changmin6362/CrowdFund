package io.github.crowdfund.global.error;


import io.github.crowdfund.global.common.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 전역 예외 처리 핸들러
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // API 요청 여부 확인
    private boolean isApiRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();
            return uri.startsWith("/api/");
        }
        return false;
    }

    // Controller의 @Validated 어노테이션으로 인한 @PathVariable과 @RequestParam 검증 에러 감지
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("@PathVariable이나 @RequestParam 검증 에러가 감지되었습니다. (에러 메시지 누락)");

        return createErrorResponse(message);
    }

    // Controller의 @Valid 어노테이션으로 인한 @RequestBody 검증 에러 감지
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse("@RequestBody 검증 에러가 감지되었습니다. (에러 메시지 누락)");

        return createErrorResponse(message);
    }

    // 타입 변환 에러 감지 (@PathVariable, @RequestParam 형식 오류)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleTypeMismatchException() {
        String message = "요청하신 엔드포인트의 경로 변수나 쿼리스트링이 잘못되었습니다.";
        return createErrorResponse(message);
    }

    // JSON 파싱 에러 감지 (@RequestBody 형식 오류)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException() {
        String message = "요청하신 데이터의 형식이 잘못되었거나 읽을 수 없습니다. (JSON 파싱 에러)";
        return createErrorResponse(message);
    }

    // IllegalArgumentException로 인한 비즈니스 로직 예외 에러 감지
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public Object handleBusinessException(RuntimeException e) {
        return createErrorResponse(e.getMessage());
    }

    /**
     * 에러 응답 생성 (API인 경우 JSON, SSR인 경우 전역 에러 페이지)
     */
    private Object createErrorResponse(String message) {
        if (isApiRequest()) {
            return ResponseEntity.badRequest().body(ApiResult.error(message));
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", message);
        mav.setViewName("error"); // 공통 에러 페이지로 이동
        return mav;
    }
}
