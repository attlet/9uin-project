package com.inProject.in.domain.Board.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Retention(RetentionPolicy.RUNTIME)  //해당 어노테이션의 정보가 어느 범위까지 유효하도록 할 지. runtime은 컴파일 후에도 jvm에서 참조 가능.
//@WithSecurityContext(factory = WithCustomUserSecurityFactory.class)
//public @interface WithAuth {
//    String value();
//}
