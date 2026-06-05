package io.github.crowdfund.feature.user;

import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.domain.user.mapper.UserMapper;
import io.github.crowdfund.feature.user.dto.fetch.UserDataInfo;
import io.github.crowdfund.feature.user.dto.fetch.UserFetchResponse;
import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import io.github.crowdfund.feature.user.dto.view.UserViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    /**
     * 내 닉네임 조회 도메인 로직 (이메일 기반)
     */
    @Transactional
    public UserViewResponse viewByEmail(String email) {
        String nickname = repository.findByEmail(email)
                .map(User::nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new UserViewResponse(nickname);
    }

    /**
     * 내 정보 조회 도메인 로직 (이메일 기반)
     */
    @Transactional
    public UserFetchResponse fetchByEmail(String email) {
        UserDataInfo userData = repository.findByEmail(email)
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
     * 내 정보 수정 도메인 로직 (이메일 기반)
     */
    @Transactional
    public UserFetchResponse updateByEmail(String email, UserUpdateRequest request) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (user.nickname().equals(request.nickname()) &&
                user.name().equals(request.name()) &&
                user.phone().equals(request.phone())) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        int affectedRows = userMapper.updateUserData(user.id(), request);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("수정에 실패했습니다.");
        }

        return fetchByEmail(email);
    }

    /**
     * 회원 탈퇴 도메인 로직 (이메일 기반)
     */
    @Transactional
    public void deleteByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        int affectedRows = userMapper.deactivateUser(user.id());

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않거나 이미 탈퇴한 사용자입니다.");
        }
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
    public UserFetchResponse update(Long userId, UserUpdateRequest request) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (user.nickname().equals(request.nickname()) &&
                user.name().equals(request.name()) &&
                user.phone().equals(request.phone())) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        int affectedRows = userMapper.updateUserData(userId, request);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        return fetch(userId);
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
