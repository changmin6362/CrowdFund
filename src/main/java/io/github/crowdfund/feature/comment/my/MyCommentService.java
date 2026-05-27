package io.github.crowdfund.feature.comment.my;

import io.github.crowdfund.domain.comment.mapper.CommentMapper;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentsResponse;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCommentService {
    private final CommentMapper mapper;

    /**
     * 내 댓글 목록 조회 도메인 로직
     */
    @Transactional
    public MyCommentsResponse fetch(Long userId) {

        List<MyCommentInfo> myComments = mapper.findAllByUserId(userId);

        return new MyCommentsResponse(myComments);
    }
}