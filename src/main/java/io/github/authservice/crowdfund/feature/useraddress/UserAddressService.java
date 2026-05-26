package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.authservice.crowdfund.feature.useraddress.request.CreateUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.request.PatchUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final UserAddressRepository repository;

    /**
     * 내 배송지 등록 도메인 로직
     */
    public CreateUserAddressResponse createUserAddress(CreateUserAddressRequest request) {
        // UserAddress saved = repository.save(request);
        // return new AddUserAddressResponse("주소 추가에 성공했습니다.", repository.addUserAddress(request));
        return new CreateUserAddressResponse("주소 추가 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 배송지 목록 조회 도메인 로직
     */
    public GetUserAddressesResponse getUserAddresses(Long userId) {
        //         return new GetUserAddressListResponse("주소를 성공적으로 조회했습니다.", repository.getUserAddresses(userId));
        return new GetUserAddressesResponse("내 배송지 목록 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 배송지 수정 도메인 로직
     */
    public PatchUserAddressResponse patchUserAddress(Long addressId, PatchUserAddressRequest request) {
        // repository.updateUserAddress(addressId, request);

        // return new PatchUserAddressResponse("주소 수정에 성공했습니다.", updatedAddress);
        return new PatchUserAddressResponse("내 배송지 수정 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 기본 배송지 수정 도메인 로직
     */
    public SetDefaultAddressResponse setDefaultAddress(Long addressId) {
        // repository.setDefaultAddress(addressId);
        // return new SetDefaultAddressResponse("기본 배송지 변경에 성공했습니다.", updatedAddress);
        return new SetDefaultAddressResponse("기본 배송지 변경 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 배송지 삭제 도메인 로직
     */
    public DeleteUserAddressResponse deleteUserAddress(Long addressId) {
        // repository.deleteById(addressId);

        // return new DeleteUserAddressResponse("주소 삭제에 성공했습니다.");
        return new DeleteUserAddressResponse("내 배송지 삭제 기능은 구현되지 않았습니다.");
    }
}
