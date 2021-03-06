create table `UserConnection` (
    `userId` varchar(255) not null,
    `providerId` varchar(255) not null,
    `providerUserId` varchar(255),
    `rank` int not null,
    `displayName` varchar(255),
    `profileUrl` varchar(512),
    `imageUrl` varchar(512),
    `accessToken` varchar(255) not null,
    `secret` varchar(255),
    `refreshToken` varchar(255),
    `expireTime` bigint,
    primary key (`userId`, `providerId`, `providerUserId`))

  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create unique index `UserConnectionRank` on `UserConnection`(`userId`, `providerId`, `rank`);
create unique index `UserConnectionProviderUser` on `UserConnection`(`providerId`, `providerUserId`);
