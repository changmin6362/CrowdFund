package io.github.crowdfund.domain.user.mapper;

import io.github.crowdfund.feature.user.dto.update.UserUpdateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int updateUserData(@Param("userId") Long userId, @Param("request") UserUpdateRequest request);

    int deactivateUser(@Param("userId") Long userId);
}
