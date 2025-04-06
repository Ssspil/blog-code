package com.ssspil.websocket.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Table(name = "USER_INFO")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    public Integer userNo;          // 유저번호

    @Column(name = "USER_ID")
    public String userId;           // 유저ID

}
