package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.feature.useraddress.request.CreateUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.request.PatchUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserAddress", description = "사용자 배송지 관련 API")
@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService service;

    /**
     * 내 배송지 등록
     *
     * @param request 배송지 정보
     * @return message, addressId
     */
    @Operation(summary = "내 배송지 등록", description = "사용자의 배송지를 등록합니다.")
    @PostMapping("/address/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserAddressResponse createUserAddress(@PathVariable Long userId, @Valid @RequestBody CreateUserAddressRequest request) {
        return service.createUserAddress(request);
    }

    /**
     * 내 배송지 목록 조회
     *
     * @param userId 사용자 ID
     * @return message, addresses
     */
    @Operation(summary = "내 배송지 목록 조회", description = "사용자의 배송지 목록을 조회합니다.")
    @GetMapping("/addresses/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GetUserAddressesResponse getUserAddresses(@PathVariable Long userId) {
        return service.getUserAddresses(userId);
    }

    /**
     * 내 배송지 수정
     *
     * @param addressId 배송지 ID
     * @param request   배송지 정보
     * @return message, updatedAddress
     */
    @Operation(summary = "내 배송지 수정", description = "사용자의 특정 배송지를 수정합니다.")
    @PatchMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public PatchUserAddressResponse patchUserAddress(@PathVariable Long addressId, @Valid @RequestBody PatchUserAddressRequest request) {
        return service.patchUserAddress(addressId, request);
    }

    /**
     * 기본 배송지 변경
     *
     * @param addressId 배송지 ID
     * @return message, defaultAddressResult
     */
    @Operation(summary = "기본 배송지 변경", description = "사용자의 특정 배송지를 기본 배송지로 변경합니다.")
    @PatchMapping("/address/{addressId}/default")
    @ResponseStatus(HttpStatus.OK)
    public SetDefaultAddressResponse setDefaultAddress(@PathVariable Long addressId) {
        return service.setDefaultAddress(addressId);
    }

    /**
     * 내 배송지 삭제
     *
     * @param addressId 배송지 ID
     * @return message
     */
    @Operation(summary = "내 배송지 삭제", description = "사용자의 특정 배송지를 삭제합니다.")
    @DeleteMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteUserAddressResponse deleteUserAddress(@PathVariable Long addressId) {
        return service.deleteUserAddress(addressId);
    }
}
