package com.example.spring_board.post.repository;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    jpa repository에서 customizing 메서드 생성
//    리턴타입 메서드명(매개변수)
    List<Post> findByAppointment(String appointment);
}
