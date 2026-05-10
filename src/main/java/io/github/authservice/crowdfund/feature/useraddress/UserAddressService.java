package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.feature.useraddress.request.AddUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.request.UpdateUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository repository;

    /**
     * 내 배송지 등록 도메인 로직
     *
     * @param request 배송지 정보
     * @return message, addressId
     */
    public AddUserAddressResponse addUserAddress(AddUserAddressRequest request) {
        return new AddUserAddressResponse("주소 추가에 성공했습니다.", repository.addUserAddress(request));
    }

    /**
     * 내 배송지 목록 조회 도메인 로직
     *
     * @param userId 사용자 ID
     * @return message, addressList
     */
    public GetUserAddressListResponse getUserAddresses(Long userId) {
        return new GetUserAddressListResponse("주소를 성공적으로 조회했습니다.", repository.getUserAddresses(userId));
    }

    /**
     * 내 배송지 단일 조회
     *
     * @param addressId 배송지 ID
     * @return message, address
     */
    public GetUserAddressResponse getUserAddress(Long addressId) {
        return new GetUserAddressResponse("배송지 정보를 성공적으로 조회했습니다.", repository.getUserAddress(addressId));
    }

    /**
     * 내 배송지 수정
     *
     * @param addressId 배송지 ID
     * @param request 배송지 정보
     * @return message
     */
    public UpdateUserAddressResponse updateUserAddress(Long addressId, UpdateUserAddressRequest request) {
        repository.updateUserAddress(addressId, request);

        return new UpdateUserAddressResponse("주소 수정에 성공했습니다.");
    }

    /**
     * 내 배송지 삭제
     *
     * @param addressId 배송지 ID
     * @return message
     */
    public DeleteUserAddressResponse deleteUserAddress(Long addressId) {
        repository.deleteUserAddress(addressId);

        return new DeleteUserAddressResponse("주소 삭제에 성공했습니다.");
    }
}
