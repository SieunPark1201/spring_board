package com.example.spring_board.author.etc;


import lombok.Getter;
import lombok.Setter;

//input 태그에 name=""로 넘겨주는 name을 그대로 RegisterForm 클래스에서 사용해야 한다
//user와 주고 받는 dota Form 클래스를 일반적으로 DTO(Data Tranfer Object)라고 부른다
@Getter
//내부적으로 setter를 사용해 html의 input값을 꺼내 담으므로 setter가 필요
@Setter
public class AuthorRequestDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;

//    Author 클래스에 있는 걸 그대로 사용하면 안 되냐? -> author클래스는 db와 연동. 이 클래스는 화면에 연결.
//    화면에 연결되는 값과 db에 들어가는 값이 일치하지 않는 경우가 꽤 있기 떄문에 보통 따로 만든다

}
