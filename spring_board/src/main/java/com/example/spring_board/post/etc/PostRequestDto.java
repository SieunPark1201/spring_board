package com.example.spring_board.post.etc;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostRequestDto {
    private String id;
    private String title;
    private String contents;
    private String email;
    private String appointment;
    private String appointment_time;


}
