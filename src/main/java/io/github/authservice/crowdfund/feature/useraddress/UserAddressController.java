package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.feature.useraddress.request.AddUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.request.UpdateUserAddressRequest;
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
    @Operation(summary = "배송지 등록", description = "사용자의 배송지를 등록합니다.")
    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public AddUserAddressResponse addUserAddress(@Valid @RequestBody AddUserAddressRequest request) {
        return service.addUserAddress(request);
    }

    /**
     * 내 배송지 목록 조회
     *
     * @param userId 사용자 ID
     * @return message, addressList
     */
    @Operation(summary = "배송지 목록 조회", description = "사용자의 배송지 목록을 조회합니다.")
    @GetMapping("/addresses/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GetUserAddressListResponse getUserAddresses(@PathVariable Long userId) {
        return service.getUserAddresses(userId);
    }

    /**
     * 내 배송지 단일 조회
     *
     * @param addressId 배송지 ID
     * @return message, address
     */
    @Operation(summary = "배송지 단일 조회", description = "사용자의 특정 배송지를 조회합니다.")
    @GetMapping("/addresses/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public GetUserAddressResponse getUserAddress(@PathVariable Long addressId) {
        return service.getUserAddress(addressId);
    }

    /**
     * 내 배송지 수정
     *
     * @param addressId 배송지 ID
     * @param request   배송지 정보
     * @return message
     */
    @Operation(summary = "배송지 수정", description = "사용자의 특정 배송지를 수정합니다.")
    @PutMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateUserAddressResponse updateUserAddress(@PathVariable Long addressId, @Valid @RequestBody UpdateUserAddressRequest request) {
        return service.updateUserAddress(addressId, request);
    }

    /**
     * 내 배송지 삭제
     *
     * @param addressId 배송지 ID
     * @return message
     */
    @Operation(summary = "배송지 삭제", description = "사용자의 특정 배송지를 삭제합니다.")
    @DeleteMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteUserAddressResponse deleteUserAddress(@PathVariable Long addressId) {
        return service.deleteUserAddress(addressId);
    }
}
