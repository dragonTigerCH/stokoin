package com.bsc.stokoin.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 812754706L;

    public static final QUser user = new QUser("user");

    public final com.bsc.stokoin.common.domain.QBaseEntity _super = new com.bsc.stokoin.common.domain.QBaseEntity(this);

    public final EnumPath<com.bsc.stokoin.user.domain.enums.AuthProvider> authProvider = createEnum("authProvider", com.bsc.stokoin.user.domain.enums.AuthProvider.class);

    public final StringPath birthday = createString("birthday");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final EnumPath<com.bsc.stokoin.user.domain.enums.Gender> gender = createEnum("gender", com.bsc.stokoin.user.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isInitProfile = createBoolean("isInitProfile");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath providerId = createString("providerId");

    public final EnumPath<com.bsc.stokoin.user.domain.enums.Role> role = createEnum("role", com.bsc.stokoin.user.domain.enums.Role.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

