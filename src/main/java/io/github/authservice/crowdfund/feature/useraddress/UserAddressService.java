package io.github.authservice.crowdfund.feature.useraddress;

import io.github.authservice.crowdfund.domain.useraddress.UserAddress;
import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.authservice.crowdfund.feature.useraddress.request.CreateUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.request.PatchUserAddressRequest;
import io.github.authservice.crowdfund.feature.useraddress.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAddressService {

    private final UserAddressRepository repository;

    /**
     * 내 배송지 등록 도메인 로직
     */
    @Transactional
    public CreateUserAddressResponse createUserAddress(Long userId, CreateUserAddressRequest request) {
        // 해당 유저의 첫 배송지인 경우 기본 배송지로 설정
        boolean isFirst = repository.findByUserId(userId).isEmpty();

        UserAddress userAddress = new UserAddress(
                null,
                userId,
                request.recipientName(),
                request.phone(),
                request.postalCode(),
                request.addressMain(),
                request.addressDetail(),
                isFirst,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        UserAddress saved = repository.save(userAddress);
        return new CreateUserAddressResponse(saved.id());
    }

    /**
     * 내 배송지 목록 조회 도메인 로직
     */
    @Transactional
    public GetUserAddressesResponse getUserAddresses(Long userId) {
        List<UserAddress> addresses = repository.findByUserId(userId);
        List<UserAddressInfo> infoList = addresses.stream()
                .map(this::mapToInfo)
                .collect(Collectors.toList());

        return new GetUserAddressesResponse(infoList);
    }

    /**
     * 내 배송지 수정 도메인 로직
     */
    @Transactional
    public PatchUserAddressResponse patchUserAddress(Long addressId, PatchUserAddressRequest request) {
        UserAddress existing = repository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));

        UserAddress updated = new UserAddress(
                existing.id(),
                existing.userId(),
                request.recipientName(),
                request.phone(),
                request.postalCode(),
                request.addressMain(),
                request.addressDetail(),
                existing.isDefault(),
                existing.createdAt(),
                LocalDateTime.now()
        );

        UserAddress saved = repository.save(updated);
        return new PatchUserAddressResponse(mapToInfo(saved));
    }

    /**
     * 기본 배송지 수정 도메인 로직
     */
    @Transactional
    public SetDefaultAddressResponse setDefaultAddress(Long addressId) {
        UserAddress target = repository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));

        // 기존 기본 배송지 해제
        repository.findByUserIdAndIsDefaultTrue(target.userId())
                .ifPresent(currentDefault -> {
                    UserAddress updatedDefault = new UserAddress(
                            currentDefault.id(),
                            currentDefault.userId(),
                            currentDefault.recipientName(),
                            currentDefault.phone(),
                            currentDefault.postalCode(),
                            currentDefault.addressMain(),
                            currentDefault.addressDetail(),
                            false,
                            currentDefault.createdAt(),
                            LocalDateTime.now()
                    );
                    repository.save(updatedDefault);
                });

        // 새로운 기본 배송지 설정
        UserAddress newDefault = new UserAddress(
                target.id(),
                target.userId(),
                target.recipientName(),
                target.phone(),
                target.postalCode(),
                target.addressMain(),
                target.addressDetail(),
                true,
                target.createdAt(),
                LocalDateTime.now()
        );

        UserAddress saved = repository.save(newDefault);
        return new SetDefaultAddressResponse(new DefaultAddressResult(saved.id(), saved.isDefault()));
    }

    /**
     * 내 배송지 삭제 도메인 로직
     */
    @Transactional
    public void deleteUserAddress(Long addressId) {
        UserAddress target = repository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배송지입니다."));

        if (target.isDefault()) {
            throw new IllegalStateException("기본 배송지는 삭제할 수 없습니다. 다른 배송지를 기본으로 설정한 후 삭제해주세요.");
        }

        repository.deleteById(addressId);
    }

    private UserAddressInfo mapToInfo(UserAddress address) {
        return new UserAddressInfo(
                address.id(),
                address.recipientName(),
                address.phone(),
                address.postalCode(),
                address.addressMain(),
                address.addressDetail(),
                address.isDefault(),
                address.createdAt(),
                address.updatedAt()
        );
    }
}
