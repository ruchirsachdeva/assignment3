package com.lnu.foundation.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Userconnection {

    @Id
    @Column(name="userid")
    private String userId;
    @Column(name="providerid")
    private String providerId;
    @Column(name="provideruserid")
    private String providerUserId;
    @Column(name="rank")
    private int rank;
    @Column(name="displayname")
    private String displayName;
    @Column(name="profileurl")
    private String profileUrl;
    @Column(name="imageurl")
    private String imageUrl;
    @Column(name="accesstoken")
    private String accessToken;
    @Column(name="secret")
    private String secret;
    @Column(name="refreshtoken")
    private String refreshToken;
    @Column(name="expiretime")
    private Long expireTime;

}
