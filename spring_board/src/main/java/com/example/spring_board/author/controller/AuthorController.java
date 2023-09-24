package com.example.spring_board.author.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import com.example.spring_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("author/login")
    public String authorLogin(){
        return "author/login";
    }

    @GetMapping("authors/new")
    public String authorsNew(){
        return "author/author-register";
    }

    @PostMapping("authors/new")
    public String authorCreate(
//                               @RequestParam(value = "name")String myName,
//                               @RequestParam(value ="email")String myEmail,
//                               @RequestParam(value = "password")String myPassword,
//                               @RequestParam(value = "role")String myRole
            AuthorRequestDto authorRequestDto
    ) throws SQLException {


//////        방법 1. setter 방식 : 최초시점 이외의 다른 클래스에서 객체값을 변경 가능함으로서 정확성 저하, 유지보수에 어려움 생김
//        author1.setName(myName);
//        author1.setEmail(myEmail);
//        author1.setPassword(myPassword);
//        author1.setRole(myRole);
//        author1.setName(authorRequestDto.getName());
//        author1.setEmail(authorRequestDto.getEmail());
//        author1.setPassword(authorRequestDto.getPassword());
//        author1.setRole(authorRequestDto.getRole());
////        실무에서는 setter를 최대한 배제.
//          setter를 통해 class 곳곳에서 값을 재세팅할 수 있기 떄문
//          최초 객체 생성시점 뿐만 아니라 여러 군데에서 setter를 통해 객체값을 변경가능하기 때문에
//          데이터의 정확성을 보장하기 어렵고, 유지보수가 어렵다.
//        author1.setCreateDate(LocalDateTime.now());

//////        방법 2. 생성자 방식 : 반드시 매개변수의 순서를 맞추어줘야 함. 매개변수가 많아지게 되면 개발에 어려움이 생김
//        Author author1 = new Author(
//                authorRequestDto.getName(),
//                authorRequestDto.getEmail(),
//                authorRequestDto.getPassword(),
//                authorRequestDto.getRole()
//        );

//        방법 3. builder 패턴: 매개변수의 순서와 상관없이 객체 생성 가능
        Author author1 = Author.builder()
                        .password(authorRequestDto.getPassword())
                        .name(authorRequestDto.getName())
                        .email(authorRequestDto.getEmail())
                        .role(authorRequestDto.getRole())
                        .build();


        authorService.create(author1);
        return "redirect:/";

    }



    //    회원목록조회
    @GetMapping("authors")
    public String authorFindAll(Model model){
        List<Author> authors = authorService.findAll();
        model.addAttribute("authorList",authors);
        return "author/author-list";
    }


    //회원 단건 조회
    // ... author?id=5 형식의 파라미터로 조회
    @GetMapping("author")
    public String authorFindById(@RequestParam(value = "id")Long myId, Model model) throws EntityNotFoundException {
        Author author1 = authorService.findById(myId);
        model.addAttribute("author", author1);
        return "author/author-detail";
    }

    @PostMapping("author/update")
    public String authorUpdate(
//                               @RequestParam(value = "id")String myId,
//                               @RequestParam(value ="name")String myName,
//                               @RequestParam(value ="email")String myEmail,
//                               @RequestParam(value = "password")String myPassword,
//                               @RequestParam(value = "role")String role
                               AuthorRequestDto authorRequestDto
    ) throws Exception {

        authorService.update(authorRequestDto);
        return "redirect:/";

    }



//    DeleteMapping을 사용할 수도 있지만, DeleteMapiing은 form 태그에서는 자원하지 않는다
//    form태그에서 DeleteMapping을 지원하지 않는다는 얘기는 action = "delete"를 줄 수 없다는 뜻
//    그래서 react나 vue/js와 같은 프론트엔드의 특정한 기술을 통해서 delete 요청을 일반적으로 하므로,
//    rest api 형식의 개발(json)에서는 deletemapping이 가능하다
    @GetMapping("author/delete")
    public String deleteAuthor(@RequestParam(value = "id")Long id) throws Exception {
        authorService.delete(id);
        return "redirect:/authors";
    }
//0528 PostMapping을 GetMapping으로 변환; postmapping같은 경우 뒤에 데이터를 숨겨서 들여오고, getmapping의 경우 대부분 ?형식으로 뒤에 달고 들어옴


}
