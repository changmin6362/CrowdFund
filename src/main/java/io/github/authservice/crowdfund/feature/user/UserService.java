package io.github.authservice.crowdfund.feature.user;

import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import io.github.authservice.crowdfund.feature.user.response.*;
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
    public GetUserNickNameResponse getUserNickName(Long userId) {
        String nickname = repository.findById(userId)
                .map(User::nickname)
                .orElse("알 수 없음");
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
                .map(user -> new UserDataInfo(
                        user.email(),
                        user.nickname(),
                        user.name(),
                        user.phone(),
                        user.role()
                ))
                .orElse(null);

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
        //  repository.updateUserData(userId, request);
        // return new UpdateUserDataResponse("내 정보 수정에 성공했습니다.");
        return new UpdateUserDataResponse("내 정보 수정 기능은 구현되지 않았습니다.");
    }

    /**
     * 회원 탈퇴
     *
     * @param userId 사용자 ID
     * @return message
     */
    public DeleteUserResponse deleteUser(Long userId) {
        // repository.deleteById(userId);

        // return new DeleteUserResponse("회원 탈퇴에 성공했습니다.");
        return new DeleteUserResponse("회원 탈퇴 기능은 구현되지 않았습니다.");
    }

    /**
     * 내가 후원한 프로젝트 목록 조회
     *
     * @param userId 사용자 ID
     * @return message, fundingList
     */
    public getMyFundingListResponse getMyFundingList(Long userId) {
        // TODO: PledgeRepository를 사용하여 실제 데이터 조회 및 UserPledgeResponse 변환 로직 구현 필요

        //         return new getMyFundingListResponse("내가 후원한 프로젝트 목록 조회에 성공했습니다.", repository.getMyFundingList(userId));
        return new getMyFundingListResponse("내가 후원한 프로젝트 목록 조회 기능은 구현되지 않았습니다.", null);
    }

}
