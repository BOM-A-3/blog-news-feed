package com.bom.newsfeed.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 35089989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.bom.newsfeed.global.common.entity.QBaseEntity _super = new com.bom.newsfeed.global.common.entity.QBaseEntity(this);

    public final com.bom.newsfeed.domain.category.entity.QCategory category;

    public final ListPath<com.bom.newsfeed.domain.comment.entity.Comment, com.bom.newsfeed.domain.comment.entity.QComment> comments = this.<com.bom.newsfeed.domain.comment.entity.Comment, com.bom.newsfeed.domain.comment.entity.QComment>createList("comments", com.bom.newsfeed.domain.comment.entity.Comment.class, com.bom.newsfeed.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.bom.newsfeed.domain.like.entity.Likes, com.bom.newsfeed.domain.like.entity.QLikes> likes = this.<com.bom.newsfeed.domain.like.entity.Likes, com.bom.newsfeed.domain.like.entity.QLikes>createList("likes", com.bom.newsfeed.domain.like.entity.Likes.class, com.bom.newsfeed.domain.like.entity.QLikes.class, PathInits.DIRECT2);

    public final com.bom.newsfeed.domain.member.entity.QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final ListPath<com.bom.newsfeed.domain.postfile.entity.PostFile, com.bom.newsfeed.domain.postfile.entity.QPostFile> postFiles = this.<com.bom.newsfeed.domain.postfile.entity.PostFile, com.bom.newsfeed.domain.postfile.entity.QPostFile>createList("postFiles", com.bom.newsfeed.domain.postfile.entity.PostFile.class, com.bom.newsfeed.domain.postfile.entity.QPostFile.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.bom.newsfeed.domain.category.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.bom.newsfeed.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

