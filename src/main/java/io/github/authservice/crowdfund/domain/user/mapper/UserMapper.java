package io.github.authservice.crowdfund.domain.user.mapper;

import io.github.authservice.crowdfund.feature.user.request.UserUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int updateUserData(@Param("userId") Long userId, @Param("request") UserUpdateRequest request);

    int deactivateUser(@Param("userId") Long userId);
}
