package com.test.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.springboot.model.PostComment;

/**
 * @author Manas Mohanty
 * 
 * The Interface PostCommentRepository.
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
   
}