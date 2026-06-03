package io.github.crowdfund.feature.useraddress;

import io.github.crowdfund.feature.useraddress.dto.create.UserAddressCreateRequest;
import io.github.crowdfund.feature.useraddress.dto.update.UserAddressUpdateRequest;
import io.github.crowdfund.feature.useraddress.dto.create.UserAddressCreateResponse;
import io.github.crowdfund.feature.useraddress.dto.fetch.UserAddressesFetchResponse;
import io.github.crowdfund.feature.useraddress.dto.update.UserAddressUpdateResponse;
import io.github.crowdfund.feature.useraddress.dto.set.UserAddressSetResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Validated
@Tag(name = "UserAddress", description = "사용자 배송지 관련 API")
public class UserAddressController {

    private final UserAddressService service;

    /**
     * 내 배송지 등록
     *
     * @param request 배송지 정보
     * @return message, createdAddressId
     */
    @Operation(summary = "내 배송지 등록")
    @ApiResponse(responseCode = "201", description = "배송지 등록 성공 응답 예시")
    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<UserAddressCreateResponse> create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @RequestBody UserAddressCreateRequest request) {

        return ApiResult.success("배송지 등록에 성공했습니다.", service.create(securityUser.getUserId(), request));
    }

    /**
     * 내 배송지 목록 조회
     *
     * @return message, addresses
     */
    @Operation(summary = "내 배송지 목록 조회")
    @ApiResponse(responseCode = "200", description = "배송지 목록 조회 성공 응답 예시")
    @GetMapping("/addresses")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserAddressesFetchResponse> fetch(@AuthenticationPrincipal SecurityUser securityUser) {
        return ApiResult.success("배송지 목록 조회에 성공했습니다.", service.fetch(securityUser.getUserId()));
    }

    /**
     * 내 배송지 수정
     *
     * @param addressId 배송지 ID
     * @param request   배송지 정보
     * @return message, updatedAddress
     */
    @Operation(summary = "내 배송지 수정")
    @ApiResponse(responseCode = "200", description = "배송지 수정 성공 응답 예시")
    @PatchMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserAddressUpdateResponse> update(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long addressId,
            @Valid @RequestBody UserAddressUpdateRequest request) {
        return ApiResult.success("배송지 수정에 성공했습니다.", service.update(securityUser.getUserId(), addressId, request));
    }

    /**
     * 기본 배송지 변경
     *
     * @param addressId 배송지 ID
     * @return message, defaultAddressResult
     */
    @Operation(summary = "기본 배송지 변경")
    @ApiResponse(responseCode = "200", description = "기본 배송지 변경 성공 응답 예시")
    @PatchMapping("/address/{addressId}/default")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserAddressSetResponse> set(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long addressId) {
        return ApiResult.success("기본 배송지 변경에 성공했습니다.", service.set(securityUser.getUserId(), addressId));
    }

    /**
     * 내 배송지 삭제
     *
     * @param addressId 배송지 ID
     * @return message
     */
    @Operation(summary = "내 배송지 삭제")
    @ApiResponse(responseCode = "200", description = "배송지 삭제 성공 응답 예시")
    @DeleteMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long addressId) {
        service.delete(securityUser.getUserId(), addressId);

        return ApiResult.success("배송지 삭제에 성공했습니다.");
    }
}
