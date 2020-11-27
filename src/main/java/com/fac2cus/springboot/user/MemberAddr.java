package com.fac2cus.springboot.user;

import lombok.Data;

@Data
public class MemberAddr {
    private String postcode;
    private String roadAddress;
    private String detailAddress;
}
