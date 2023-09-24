package com.example.spring_board.post.service;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostRequestDto;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
//db에 쿼리를 날릴 때 한꺼번에 묶어 보내고, 에러가 생기면 한꺼번에 rollback
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthorService authorService;

    public void create(PostRequestDto postRequestDto) {
        String my_appointment = null;
        LocalDateTime my_appointment_time = null;

        if(postRequestDto.getAppointment() != null
                && ! postRequestDto.getAppointment_time().isEmpty()
        ) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(postRequestDto.getAppointment_time(), dateTimeFormatter);
            LocalDateTime currentTime = LocalDateTime.now();

            if(currentTime.isBefore(localDateTime)==true){
                my_appointment_time = localDateTime;
                my_appointment = "Y";
            }

        }
        Author author1 = authorService.findByEmail(postRequestDto.getEmail());
//        System.out.println(localDateTime);
//        System.out.println(localDateTime.getClass());
//        System.out.println("author 조회");
//        System.out.println(postRequestDto.getAppointment());
//        System.out.println(postRequestDto.getAppointment_time());
        Post post1 = Post.builder()
                .title(postRequestDto.getTitle())
                .contents(postRequestDto.getContents())
//                post에는 author변수가 있으므로, post생성시 author 객체를 넘겨주면,
//                내부적으로 author 객체에서 author_id를 꺼내어 DB에 넣게 된다
                .author(author1)
                .appointment(my_appointment)
                .appointment_time(my_appointment_time)
                .build();

        System.out.println("postservice create");
        postRepository.save(post1);
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findByAppointment(null);
        return posts;
    }

    public Post findById(Long id) throws EntityNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public void update(PostRequestDto postRequestDto) throws Exception {
        Post post1 = postRepository.findById(Long.parseLong(postRequestDto.getId())).orElseThrow(Exception::new);

        if (post1 == null) {
            throw new Exception();
        } else {

            post1.setTitle(postRequestDto.getTitle());
            post1.setContents(postRequestDto.getContents());
            postRepository.save(post1);
        }
    }

    public void delete(Long id) {
        Post post = this.findById(id);
        postRepository.delete(post);
    }



}
