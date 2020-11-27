package com.fac2cus.springboot.user;

import org.springframework.data.jpa.repository.JpaRepository;


//CRUD함수를 JpaRepository가 들고있음
//@Repository라는 어노테이션이 업어도 IoC되요. 이유는 JpaRepository를 상속했기 때문에
public interface MemberRepository extends JpaRepository<Member,Integer> {
    //findBy규칙 -> Username문법
    //selelct * from user where username = ?
    Member findByUsername(String username); //Jpa Query method


}
