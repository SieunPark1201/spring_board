package com.example.spring_board.author.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


//@Getter @Setter 어노테이션을 붙이면 우리가 generate를 이용해 일일이 붙였던 getter, setter가 알아서 생성됨
@Getter
//@Setter
@Entity
//기본생성자를 만들어주는 어노테이션. builder를 사용하면 기본생성자가 부재하기 때문에 에러가 발생하는데, 이 어노테이션을 붙여주면 된다
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(length=50)
    private String name;
    @Setter
    @Column(length=50, unique = true)
    private String email;

    @Setter
    @Column(length=255)
    private String password;

    @Column(length=20)
    private String role;


//    mysql에서는 datetime형식으로 컬럼 생성
    @Column
    private LocalDateTime createDate;





//    생성자 방식과 builder패턴
//    ... 을 사용하기 위에 위의 setter 어노테이션 주석처리
    @Builder
    public Author(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = LocalDateTime.now();
    }

}
