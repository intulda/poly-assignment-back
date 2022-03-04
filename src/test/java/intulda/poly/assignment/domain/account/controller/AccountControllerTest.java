package intulda.poly.assignment.domain.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.AccountDTO;
import intulda.poly.assignment.domain.account.repository.AccountRepository;
import intulda.poly.assignment.global.configuration.jwt.model.JwtRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AccountControllerTest {

    Logger logger = LoggerFactory.getLogger(AccountControllerTest.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    AccountControllerTest() {
    }

    @BeforeEach
    void beforeEach() {
        JwtRequest jwtRequest = JwtRequest.builder()
                .username("intulda")
                .password("flqj0610")
                .build();

        AccountDTO accountDTO = AccountDTO.builder()
                .account(jwtRequest.getUsername())
                .accountPassword(jwtRequest.getPassword())
                .build();

        Account account = Account.builder()
                .accountDTO(accountDTO)
                .build();

        accountRepository.save(account);
    }

    @DisplayName(value = "내 정보 찾기")
    @Test
    void findMeTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account");
        MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();
        logger.info(response.getContentAsString());
    }

    @DisplayName(value = "회원가입")
    @Test
    void registerTest() throws Exception {
        for (int i = 0; i < 10; i++) {
            AccountDTO accountDTO = AccountDTO.builder()
                    .account(i + "a")
                    .accountPassword((i + (i * i)) + "")
                    .accountName("a" + i)
                    .build();
            Account account = Account.builder()
                    .accountDTO(accountDTO)
                    .build();

            RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/account")
                    .content(objectMapper.writeValueAsString(account))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                    .characterEncoding(StandardCharsets.UTF_8.displayName());

            MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();

            logger.info(response.getContentAsString());
        }
    }

    @DisplayName(value = "로그인 성공 후 토큰 반환")
    @Test
    void loginTest() throws Exception {
        JwtRequest jwtRequest = JwtRequest.builder()
                .username("intulda")
                .password("flqj0610")
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/account/authenticate")
                .content(objectMapper.writeValueAsString(jwtRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
                .characterEncoding(StandardCharsets.UTF_8.displayName());

        MockHttpServletResponse response = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        logger.info(response.getContentAsString());
    }
}