package com.example.spring_board.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

//AOP(Aspect Oriented Programming) : 관점지향 프로그래밍
//어떤 관점을 기준으로 >>공통된 로직<<을 따로 모듈화하는 방식의 프로그래밍

//어노테이션 중, Advice, Aspect 등의 키워드가 있으면,
//상시적으로 특정한 이벤트를 캐치하려고 기다리고 있는 AOP성 프로그램이라고 생각하면 된다
@ControllerAdvice
public class ExceptionHandlerAdvice {

//    findById에서 존재하지 않는 id로 페이지를 요청하는 경우
//    ExceptionHandler의 역할은 EntityNotFoundException이라는 예외 클래스가 발생했을 떄 catch하는 것에 있다.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notFound(Exception e){
        String context ="<header<h1>존재하지 않는 화면입니다.</h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.NOT_FOUND);
    }

//    create에서 중복된 이메일로 가입하려는 경우
//    SQLIntegrityConstraintViolationException
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> duplicateEmail(Exception e){
        String context ="<header<h1>중복된 이메일입니다.</h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.CONFLICT);
    }





}
