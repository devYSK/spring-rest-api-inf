package com.ys.restapi.config;

import com.ys.restapi.accounts.Account;
import com.ys.restapi.accounts.AccountRole;
import com.ys.restapi.accounts.AccountService;
import com.ys.restapi.common.BasicControllerTests;
import com.ys.restapi.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BasicControllerTests {

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Test
    @TestDescription("인증 토큰 발급 받는 테스트 ")
    public void getAuthToken() throws Exception {

        // 이 메서드만 테스트 할때는 어카운트 세이브 과정을 지워야함.

        Account youngsoo = Account.builder()
                .email(appProperties.getUserUsername())
                .password(appProperties.getUserPassword())
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(youngsoo);

        this.mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret())) // 시큐리티 테스트 디펜던시 가 있어야함
                        .param("username", appProperties.getUserUsername())
                        .param("password", appProperties.getUserPassword())
                        .param("grant_type", "password")
                ) // 기본적으로 핸들러가 적용이 된다
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("access_token").exists());
    }
}