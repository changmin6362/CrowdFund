package io.github.crowdfund.feature.pledgeaddress;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.useraddress.UserAddress;
import io.github.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceRequest;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceResponse;
import io.github.crowdfund.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PledgeAddressService {

    private final PledgeAddressRepository repository;
    private final UserAddressRepository userAddressRepository;
    private final PledgeRepository pledgeRepository;

    /**
     * 참여한 후원의 배송 정보 교체 도메인 로직
     */
    @Transactional
    public PledgeAddressReplaceResponse replace(SecurityUser securityUser, Long pledgeId, PledgeAddressReplaceRequest request) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 후원 정보를 찾을 수 없습니다."));

        if (securityUser.isOwner(pledge.userId())) {
            throw new IllegalArgumentException("본인의 후원 주소 정보만 교체할 수 있습니다.");
        }

        // 배송 정보 수정 가능 여부 확인 (이행 상태가 READY 인 경우만 가능)
        if (pledge.fulfillmentStatus() != FulfillmentStatus.READY) {
            throw new IllegalStateException("배송이 이미 시작되었거나 완료되어 주소를 변경할 수 없습니다.");
        }

        UserAddress userAddress = userAddressRepository.findById(request.addressId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주소 정보를 찾을 수 없습니다."));

        // 소유권 검증: 교체하려는 주소지가 후원자의 주소지인지 확인
        if (!Objects.equals(userAddress.userId(), pledge.userId())) {
            throw new IllegalArgumentException("자신의 배송지만 선택할 수 있습니다.");
        }

        PledgeAddress existingAddress = repository.findByPledgeId(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 후원의 주소 정보를 찾을 수 없습니다."));

        // 데이터 변경 여부 확인
        if (Objects.equals(existingAddress.recipientName(), userAddress.recipientName()) &&
                Objects.equals(existingAddress.phone(), userAddress.phone()) &&
                Objects.equals(existingAddress.postalCode(), userAddress.postalCode()) &&
                Objects.equals(existingAddress.addressMain(), userAddress.addressMain()) &&
                Objects.equals(existingAddress.addressDetail(), userAddress.addressDetail())) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        PledgeAddress newPledgeAddress = new PledgeAddress(
                existingAddress.id(),
                pledgeId,
                userAddress.userId(),
                userAddress.recipientName(),
                userAddress.phone(),
                userAddress.postalCode(),
                userAddress.addressMain(),
                userAddress.addressDetail(),
                existingAddress.createdAt(),
                LocalDateTime.now()
        );

        PledgeAddress saved = repository.save(newPledgeAddress);

        return new PledgeAddressReplaceResponse(mapToInfo(saved));
    }

    private PledgeAddressInfo mapToInfo(PledgeAddress address) {
        return new PledgeAddressInfo(
                address.id(),
                address.pledgeId(),
                address.userId(),
                address.recipientName(),
                address.phone(),
                address.postalCode(),
                address.addressMain(),
                address.addressDetail(),
                address.createdAt(),
                address.updatedAt()
        );
    }
}
