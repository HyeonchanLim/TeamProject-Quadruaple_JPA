package com.green.project_quadruaple.user;

import com.green.project_quadruaple.common.MyFileUtils;
import com.green.project_quadruaple.common.config.CookieUtils;
import com.green.project_quadruaple.common.config.jwt.TokenProvider;
import com.green.project_quadruaple.common.config.security.AuthenticationFacade;
import com.green.project_quadruaple.user.model.DuplicateEmailResult;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceParentTest {
    @Mock protected UserMapper userMapper;
    @Mock protected PasswordEncoder passwordEncoder;
    @Mock protected MyFileUtils myFileUtils;
    @Mock protected DuplicateEmailResult duplicateEmailResult;
    @Mock protected TokenProvider tokenProvider;
    @Mock protected CookieUtils cookieUtils;
    @Mock protected AuthenticationFacade authenticationFacade;
    @Mock protected JavaMailSender javaMailSender;

    @InjectMocks
    protected UserService userService;

    protected final String FROM_ADDRESS = "quadrupleart@gmail.com";
    protected final long SIGNED_USER_ID = 112L;
}
