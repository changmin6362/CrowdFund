package io.github.crowdfund.feature.user;

import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.domain.user.mapper.UserMapper;
import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import io.github.crowdfund.feature.user.dto.fetch.UserFetchResponse;
import io.github.crowdfund.feature.user.dto.view.UserViewResponse;
import io.github.crowdfund.feature.user.dto.fetch.UserDataInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    /**
     * 내 닉네임 조회 도메인 로직
     */
    @Transactional
    public UserViewResponse view(Long userId) {
        String nickname = repository.findById(userId)
                // User에서 nickname을 가져옴
                .map(User::nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new UserViewResponse(nickname);
    }

    /**
     * 내 정보 조회 도메인 로직
     */
    @Transactional
    public UserFetchResponse fetch(Long userId) {
        UserDataInfo userData = repository.findById(userId)
                // 받아온 User 형태의 데이터를 UserDataInfo 형태로 가공
                .map(user -> new UserDataInfo(
                        user.email(),
                        user.nickname(),
                        user.name(),
                        user.phone(),
                        user.role()
                ))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        return new UserFetchResponse(userData);
    }

    /**
     * 내 정보 수정 도메인 로직
     */
    @Transactional
    public void update(Long userId, UserUpdateRequest request) {
        int affectedRows = userMapper.updateUserData(userId, request);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
    }

    /**
     * 회원 탈퇴 도메인 로직
     */
    @Transactional
    public void delete(Long userId) {
        int affectedRows = userMapper.deactivateUser(userId);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않거나 이미 탈퇴한 사용자입니다.");
        }
    }
}
