package io.github.authservice.crowdfund.feature.pledgeaddress;

import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.authservice.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.authservice.crowdfund.domain.useraddress.UserAddress;
import io.github.authservice.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.authservice.crowdfund.feature.pledgeaddress.request.ReplacePledgeAddressRequest;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.GetPledgeAddressResponse;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.PledgeAddressInfo;
import io.github.authservice.crowdfund.feature.pledgeaddress.response.ReplacePledgeAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PledgeAddressService {

    private final PledgeAddressRepository repository;
    private final UserAddressRepository userAddressRepository;

    /**
     * 후원 주소 조회 도메인 로직
     */
    public GetPledgeAddressResponse getPledgeAddress(Long pledgesId) {
        PledgeAddress address = repository.findByPledgeId(pledgesId)
                .orElseThrow(() -> new IllegalArgumentException("해당 후원의 주소 정보를 찾을 수 없습니다."));

        return new GetPledgeAddressResponse(
                "후원 주소 조회가 완료되었습니다.",
                mapToInfo(address)
        );
    }

    /**
     * 후원 주소 교체 도메인 로직
     */
    @Transactional
    public ReplacePledgeAddressResponse replacePledgeAddress(Long pledgeId, ReplacePledgeAddressRequest request) {
        UserAddress userAddress = userAddressRepository.findById(request.addressId())
                .orElseThrow(() -> new IllegalArgumentException("해당 주소 정보를 찾을 수 없습니다."));

        PledgeAddress existingAddress = repository.findByPledgeId(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 후원의 주소 정보를 찾을 수 없습니다."));

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

        return new ReplacePledgeAddressResponse(
                "후원 주소가 성공적으로 교체되었습니다.",
                mapToInfo(saved)
        );
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
