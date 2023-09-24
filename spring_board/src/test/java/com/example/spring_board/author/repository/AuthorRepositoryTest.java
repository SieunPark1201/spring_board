package com.example.spring_board.author.repository;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//DataJpaTest를 사용하면 매 테스트코드가 종료되면 자동으로 DB가 원상복구(롤백)
@DataJpaTest
//test용의 configure를 사용하는 AutoConfigureTestDatabase.Replace가 기본 설정
//그런데, 기존의 configure(application.yml)을 사용하려면 NONE으로 세팅
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

//    테스트 시나리오
//    AuthorRepository가 제대로 기능하는지 확인하기 위해서는 어떤 테스트를 해야 하는가>
//    먼저 테스트 데이터를 save -> save된 데이터를 다시 조회해서 테스트데이터와 비교하면
//    repository의 save, find 기능이 정상인지 검증할 수 있다

//    테스트코드의 장점: 부족한 기능을 사전에 알아차릴 수 있다
//    -> 여기선 'test할때 뭘로 find해야하지? findbyid로는 불가(id를 모르기 떄문; 화면이 있어서 확인할 수 있었던 이 전 프로젝트와 다름)

    @Test
    public void authorSave(AuthorRequestDto authorRequestDto){
//        Author author1 = new Author();
//        author1.setName("hong");
//        author1.setEmail("hong@naver.com");
//        author1.setPassword("1234");

        Author author1 = Author.builder()
                .password("1234")
                .name("hong")
                .email("hong@naver.com")
                .build();

        authorRepository.save(author1);

//    저장된 데이터를 다시 조회해서, 입력한 테스트 데이터와 동일한지 검증
//    조회를 해야 하는데 id를 모르니 기본 메서c드인 findById를 사용할 수가 없으므로,
//    repository에 findByEmail 추가

        Author authorDB = authorRepository.findByEmail("hong@naver.com");






//        input값과 조회한 값이 같은지를 비교해 보는 방법
////        방법 1: print해본다
//        System.out.println("name : "+authorDB.getName());
//        System.out.println("email : "+authorDB.getEmail());
//        System.out.println("password : "+authorDB.getPassword());


//        방법 2: 실행 시 에러가 나면 빨간 불, 정상이면 파란 불로 구분할 수 있도록 JUnit의 기능 활용
//        JUnit이란 java/spring에서 테스트 용도의 tool로 가장 많이 사용되는 라이브러리
        Assertions.assertEquals(author1, authorDB);



    }

}
