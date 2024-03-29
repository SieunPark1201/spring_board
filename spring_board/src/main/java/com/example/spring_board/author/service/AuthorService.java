package com.example.spring_board.author.service;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import com.example.spring_board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class AuthorService implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepository;

//    의존성 주입(dependency injection -- DI)
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void create(Author author) throws SQLException {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
    }

    public List<Author> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author findById(Long id) throws EntityNotFoundException {
//        authorRepository.findById(id)의 리턴 타입: optional객체
//        optional 객체는 null일 수도 있고 없을 수도 있음. => null일 때 곤란해지므로 예외 처리를 해줘야함
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return author;
    }

    public void update(AuthorRequestDto authorRequestDto) throws Exception {
//        Author author1 = authorRepository.findById(author.getId()).orElse(null);     //Author author로 받는 경우
        Author author1 = authorRepository.findById(Long.parseLong(authorRequestDto.getId())).orElseThrow(Exception::new);

        if (author1 == null) {
            throw new Exception();
        } else {
//            author1.setName(author.getName());
//            author1.setEmail(author.getEmail());
//            author1.setPassword(author.getPassword());

            author1.setName(authorRequestDto.getName());
            author1.setEmail(authorRequestDto.getEmail());
            author1.setPassword(authorRequestDto.getPassword());
            authorRepository.save(author1);
        }
    }


    public void delete(Long id) {
//       먼저 db에서 조회 후에 author객체를 가져온다
//        해당 author 객체로 jpa가 delete할 수 있도록 전달해준다

//        Author author = authorRepository.findById(id).orElse(null);
//        위의 findById를 this로 호출
        Author author = this.findById(id);
        authorRepository.delete(author);
//        한 줄로 쓸 수도 있음         authorRepository.delete(this.findById(id));
    }


    public Author findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }




//    중요!
//    doLogin이라는 spring 내장 메서드가 실행될 떄,
//    UserDetailsService를 구현한 클래스의 loadUserByUsername이라는 메서드를 찾는 걸로 약속

    @Override
//    String username은 사용자가 화면에 입력한 email주소를 스프링이 받아서 loadUserByUsername에 넣어준다
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        doLogin 내장 기능이 정상 실행되려면, DB에서 조회한 id/pw를 return해줘야 한다
        Author author = authorRepository.findByEmail(username);
        if(author==null){
//            이 부분은 다음 주에
        }
//        DB에서 조회한 email, password, 권한을 return. 권한이 없다면 emptyList로 return.
        return new User(author.getEmail(), author.getPassword(), Collections.emptyList());
    }

}
