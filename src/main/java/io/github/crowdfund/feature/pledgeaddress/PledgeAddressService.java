package io.github.crowdfund.feature.pledgeaddress;

import io.github.crowdfund.domain.pledgeaddress.PledgeAddress;
import io.github.crowdfund.domain.pledgeaddress.PledgeAddressRepository;
import io.github.crowdfund.domain.useraddress.UserAddress;
import io.github.crowdfund.domain.useraddress.UserAddressRepository;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceRequest;
import io.github.crowdfund.feature.pledgeaddress.dto.fetch.PledgeAddressFetchResponse;
import io.github.crowdfund.feature.pledgeaddress.dto.PledgeAddressInfo;
import io.github.crowdfund.feature.pledgeaddress.dto.replace.PledgeAddressReplaceResponse;
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
    public PledgeAddressFetchResponse fetch(Long pledgeId) {
        PledgeAddress address = repository.findByPledgeId(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 후원의 주소 정보를 찾을 수 없습니다."));

        return new PledgeAddressFetchResponse(mapToInfo(address));
    }

    /**
     * 후원 주소 교체 도메인 로직
     */
    @Transactional
    public PledgeAddressReplaceResponse replace(Long pledgeId, PledgeAddressReplaceRequest request) {
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
