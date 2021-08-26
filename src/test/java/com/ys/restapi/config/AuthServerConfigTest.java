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

    @Test
    @TestDescription("인증 토큰 발급 받는 테스트 ")
    public void getAuthToken() throws Exception {

        String username = "youngsoo2@naver.com";
        String password = "youngsoo";
        Account youngsoo = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(youngsoo);

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(clientId, clientSecret)) // 시큐리티 테스트 디펜던시 가 있어야함
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password")
                ) // 기본적으로 핸들러가 적용이 된다
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("access_token").exists());
    }
}