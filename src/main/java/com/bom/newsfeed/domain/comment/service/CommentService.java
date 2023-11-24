package com.bom.newsfeed.domain.comment.service;

import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.comment.repository.CommentRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;
import com.bom.newsfeed.global.exception.CommentAccessDeniedException;
import com.bom.newsfeed.global.exception.CommentNotFoundException;
import com.bom.newsfeed.global.exception.PostNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	@Transactional
	public Object createComment(Long postId, CommentRequestDto commentRequestDto, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = new Comment(commentRequestDto, member);
		Post post = findPost(postId);
		comment.initPost(post);
		commentRepository.save(comment);
		return null;
	}

	@Transactional
	public Object updateComment(Long commentId, CommentRequestDto commentRequestDto, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		comment.update(commentRequestDto);
		return null;
	}

	@Transactional
	public Object deleteComment(Long commentId, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		commentRepository.delete(comment);
		return null;
	}

	public Post findPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
	}

	private Comment findComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
	}

	private void checkUsername(Comment comment, Member member) {
		if (!comment.getMember().getUsername().equals(member.getUsername())) {
			throw new CommentAccessDeniedException();
		}
	}
}