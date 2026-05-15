package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.PledgeMapper;
import io.github.authservice.crowdfund.domain.pledge.response.UserPledgeResponse;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserMapper;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import io.github.authservice.crowdfund.feature.user.response.*;
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
    private final PledgeMapper pledgeMapper;

    /**
     * 내 닉네임 조회 도메인 로직
     *
     * @param userId 사용자 ID
     * @return message, nickname
     */
    public GetUserNickNameResponse getUserNickName(Long userId) {
        String nickname = repository.findById(userId)
                // User에서 nickname을 가져옴
                .map(User::nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new GetUserNickNameResponse("닉네임 조회에 성공했습니다.", nickname);
    }

    /**
     * 내 정보 조회 도메인 로직
     *
     * @param userId 사용자 ID
     * @return message, user
     */
    public GetUserDataResponse getUserData(Long userId) {
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

        return new GetUserDataResponse("내 정보 조회에 성공했습니다.", userData);
    }

    /**
     * 내 정보 수정 도메인 로직
     *
     * @param userId  사용자 ID
     * @param request 수정할 사용자 정보
     * @return message
     */
    public UpdateUserDataResponse updateUserData(Long userId, UserUpdateRequest request) {
        int affectedRows = userMapper.updateUserData(userId, request);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        return new UpdateUserDataResponse("내 정보 수정에 성공했습니다.");
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    public DeleteUserResponse deactivateUser(Long userId) {
        int affectedRows = userMapper.deactivateUser(userId);

        if (affectedRows == 0) {
            throw new IllegalArgumentException("존재하지 않거나 이미 탈퇴한 사용자입니다.");
        }

        return new DeleteUserResponse("회원 탈퇴에 성공했습니다.");
    }

    /**
     * 내가 후원한 프로젝트 목록 조회
     *
     * @param userId 사용자 ID
     * @param status 후원 상태 필터 (null인 경우 모든 상태 조회)
     * @return message, pledgeList
     */
    public GetMyPledgeListResponse getMyPledgeList(Long userId, FulfillmentStatus status) {
        List<UserPledgeResponse> pledgeList = pledgeMapper.findPledgesByUserId(userId, status);

        return new GetMyPledgeListResponse("내가 후원한 프로젝트 목록 조회에 성공했습니다.", pledgeList);
    }

}
