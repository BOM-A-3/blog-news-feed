package com.bom.newsfeed.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bom.newsfeed.domain.comment.dto.CommentRequestDto;
import com.bom.newsfeed.domain.comment.dto.CommentResponseDto;
import com.bom.newsfeed.domain.comment.entity.Comment;
import com.bom.newsfeed.domain.comment.repository.CommentRepository;
import com.bom.newsfeed.domain.member.entity.Member;
import com.bom.newsfeed.domain.post.entity.Post;
import com.bom.newsfeed.domain.post.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostRepository postRepository;


	// 댓글 조회
	public List<CommentResponseDto> getFindAll() {
		return null;
	}

	// 댓글 작성
	public void createComment(Long postId, CommentRequestDto commentRequestDto, Member member) {
		Comment comment = new Comment(commentRequestDto, member);
		Post post = findPost(postId);
		post.addComment(comment);
		commentRepository.save(comment);
	}



	// 댓글 수정
	@Transactional
	public void updateComment(Long commentId, CommentRequestDto commentRequestDto, Member member) {
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		comment.update(commentRequestDto);
	}



	// 댓글 삭제
	public void deleteComment(Long commentId, Member member) {
		Comment comment = findComment(commentId);
		checkUsername(comment, member);
		commentRepository.delete(comment);
	}


	// ----------------


	private Post findPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(() ->
			new IllegalArgumentException("존재하지 않는 게시물입니다."));
	}

	private Comment findComment(Long commentId) {
		return commentRepository.findById(commentId).orElseThrow(()->
			new IllegalArgumentException("존재하지 않는 댓글입니다."));
	}

	private void checkUsername(Comment comment, Member member) {
		String username = member.getUsername();
		if (!comment.getMember().getUsername().equals(username)) {
			throw new IllegalArgumentException("작성자가 아닙니다.");
		}
	}
}