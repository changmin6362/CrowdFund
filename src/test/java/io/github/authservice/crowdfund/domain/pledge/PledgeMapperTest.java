package io.github.authservice.crowdfund.domain.pledge;

import io.github.authservice.crowdfund.domain.pledge.response.UserPledgeResponse;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PledgeMapperTest {

    @Autowired
    private PledgeMapper pledgeMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindUserByEmail() {
        // When
        var user = userRepository.findByEmail("admin@test.com");

        // Then
        System.out.println("[DEBUG_LOG] 이메일로 사용자 찾기: " + user);
        assertThat(user).isPresent();
    }

    @Test
    void testFindPledgesByUserId() {
        // When
        List<UserPledgeResponse> pledges = pledgeMapper.findPledgesByUserId(1L);

        // Then
        System.out.println("[DEBUG_LOG] 내가 참여한 펀딩 조회 userId=1: " + pledges);
        assertThat(pledges).isNotNull();
    }
}
