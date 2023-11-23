package com.bom.newsfeed.domain.comment.service;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.comment.repository.CommentRepository;
import com.bom.newsfeed.domain.member.dto.MemberDto;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;

	// 댓글 작성
	@Transactional
	public void  createComment(Long postId, CommentRequestDto commentRequestDto, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = new Comment(commentRequestDto, member);
		Post post = findPost(postId);
		comment.initPost(post);
		commentRepository.save(comment);
	}

	// 댓글 수정
	@Transactional
	public void updateComment(Long commentId, CommentRequestDto commentRequestDto, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		comment.update(commentRequestDto);
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long commentId, MemberDto memberDto) {
		Member member = memberDto.toEntity();
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		commentRepository.delete(comment);
	}


	public Post findPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(() ->
			new IllegalArgumentException("존재하지 않는 게시물입니다."));
	}

	private Comment findComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 댓글입니다."));
	}

	private void checkUsername(Comment comment, Member member) {
		if (!comment.getMember().getUsername().equals(member.getUsername())) {
			throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
		}
	}
}