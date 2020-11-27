package com.fac2cus.springboot.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    private int id;
    @Column(unique = true, nullable = false,length = 20)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    private String address;
    @Column(nullable = false)
    private String role;//Enum을 쓰는게 좋다. //admin,user,manager

    private String provider;
    private String providerId;
    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public Member(String username, String password, String name, String email, String address, String role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
