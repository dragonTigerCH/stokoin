package com.bsc.stokoin.post.infrastructure.repository;

import com.bsc.stokoin.category.domain.enums.CategoryEnums;
import com.bsc.stokoin.post.domain.Post;
import com.bsc.stokoin.post.domain.QPost;
import com.bsc.stokoin.post.domain.enums.PostEnums;
import com.bsc.stokoin.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QPost qPost = QPost.post;

    @Transactional(readOnly = true)
    public Page<Post> findByTitleContainsOrderByCreatedDateDesc(
            String title, Pageable pageable, PostEnums postEnums, CategoryEnums categoryEnums, Long userId) {
        List<Post> results = new ArrayList<>();
        Long totalCount = 0L;

        if (postEnums == PostEnums.TYPE_USER) {
            results = jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.title.contains(title).and(qPost.user.id.eq(userId)))
                    .orderBy(qPost.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            totalCount = jpaQueryFactory.select(qPost.count())
                    .from(qPost)
                    .where(qPost.title.contains(title).and(qPost.user.id.eq(userId)))
                    .fetchOne();
        } else if (postEnums == PostEnums.TYPE_CATEGORY) {
            results = jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.title.contains(title).and(qPost.category.categoryCode.eq(categoryEnums)))
                    .orderBy(qPost.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            totalCount = jpaQueryFactory.select(qPost.count())
                    .from(qPost)
                    .where(qPost.title.contains(title).and(qPost.category.categoryCode.eq(categoryEnums)))
                    .fetchOne();
        } else {
            results = jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.title.contains(title))
                    .orderBy(qPost.createdDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            totalCount = jpaQueryFactory.select(qPost.count())
                    .from(qPost)
                    .where(qPost.title.contains(title))
                    .fetchOne();
        }

        return new PageImpl<>(results, pageable, totalCount);
    }
}

















