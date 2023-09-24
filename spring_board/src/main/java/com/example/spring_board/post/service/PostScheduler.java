package com.example.spring_board.post.service;

import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostScheduler {

    @Autowired
    private PostRepository postRepository;


//   스케쥴러는 초, 분, 시, 일, 월, 주(요일) 등 시간 옵션을 줘야
//    n분에 한 번, n분에 한 번과 같이 옵션 시간을 주기로 돌아간다

//    cron: 리눅스, 스프링, 그 외 각종 프레임워크에서 스케쥴러를 의미한다
    @Scheduled(cron = "0 0/1 * * * *")
    public void postSchedule(){
//        System.out.println("스케쥴러입니다");

        List<Post> posts = postRepository.findByAppointment("Y");

        for(Post post: posts){
            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isAfter(post.getAppointment_time()) == true) {
                post.setAppointment(null);
                postRepository.save(post);

            }
        }



    }

}
