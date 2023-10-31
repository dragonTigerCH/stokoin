package com.bsc.stokoin.comment.domain.recomment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReComment is a Querydsl query type for ReComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReComment extends EntityPathBase<ReComment> {

    private static final long serialVersionUID = 582517903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReComment reComment = new QReComment("reComment");

    public final com.bsc.stokoin.common.domain.QBaseEntity _super = new com.bsc.stokoin.common.domain.QBaseEntity(this);

    public final com.bsc.stokoin.comment.domain.comment.QComment comment;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.bsc.stokoin.user.domain.QUser user;

    public QReComment(String variable) {
        this(ReComment.class, forVariable(variable), INITS);
    }

    public QReComment(Path<? extends ReComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReComment(PathMetadata metadata, PathInits inits) {
        this(ReComment.class, metadata, inits);
    }

    public QReComment(Class<? extends ReComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.bsc.stokoin.comment.domain.comment.QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.bsc.stokoin.user.domain.QUser(forProperty("user")) : null;
    }

}

