package com.example.spring_board.post.controller;

import com.example.spring_board.author.controller.AuthorController;
import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.service.PostService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;


//    새 게시글 작성
    @GetMapping("posts/new")
    public String authorsNew(){
        return "post/post-register";
    }

    @PostMapping("posts/new")
    public String postCreate(PostRequestDto postRequestDto) throws SQLException {
        postService.create(postRequestDto);
        return "redirect:/";
    }


//    게시판 목록 조회
    @GetMapping("posts")
    public String postFindAll(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("postList",posts);
        return "post/post-list";
    }


//   게시판 상세 조회
    @GetMapping("post")
    public String postFindById(@RequestParam(value = "id")Long myId, Model model) throws EntityNotFoundException {
        Post post1 = postService.findById(myId);
        model.addAttribute("post", post1);
        return "post/post-detail";
    }


    @PostMapping("post/update")
    public String postUpdate(
            PostRequestDto postRequestDto
    ) throws Exception {

        postService.update(postRequestDto);
        return "redirect:/";

    }



    @GetMapping("post/delete")
    public String deletePost(@RequestParam(value = "id")Long id) throws Exception {
        postService.delete(id);
        return "redirect:/posts";
    }


}
