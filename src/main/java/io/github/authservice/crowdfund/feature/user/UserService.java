package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import io.github.authservice.crowdfund.feature.user.response.DeleteUserResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserDataResponse;
import io.github.authservice.crowdfund.feature.user.response.GetUserNickNameResponse;
import io.github.authservice.crowdfund.feature.user.response.UpdateUserDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * 내 닉네임 조회 도메인 로직
     *
     * @param userId 사용자 ID
     * @return message, nickname
     */
    public GetUserNickNameResponse getUserNickName(String userId) {
        return new GetUserNickNameResponse("닉네임 조회에 성공했습니다.", repository.getUserNickName(userId));
    }

    /**
     * 내 정보 조회 도메인 로직
     *
     * @param userId 사용자 ID
     * @return message, nickname, email, phone
     */
    public GetUserDataResponse getUserData(String userId) {
        return new GetUserDataResponse("내 정보 조회에 성공했습니다.", repository.getUserData(userId));
    }

    /**
     * 내 정보 수정 도메인 로직
     *
     * @param userId  사용자 ID
     * @param request 수정할 사용자 정보
     * @return message, nickname, email, phone
     */
    public UpdateUserDataResponse updateUserData(String userId, UserUpdateRequest request) {
        repository.updateUserData(userId, request);

        return new UpdateUserDataResponse("내 정보 수정에 성공했습니다.");
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    public DeleteUserResponse deleteUser(String userId) {
        repository.deleteUser(userId);

        return new DeleteUserResponse("회원 탈퇴에 성공했습니다.");
    }
}
