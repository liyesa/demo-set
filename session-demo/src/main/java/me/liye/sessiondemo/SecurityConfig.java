/*
 * Copyright 2014-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.liye.sessiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.session.jdbc.ExtSessionRepo;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;

import javax.servlet.http.HttpServletRequest;


/**
 * Spring Security configuration.
 *
 * @author Rob Winch
 * @author Vedran Pavic
 */
@Slf4j
@Configuration("securityConfig")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    JdbcIndexedSessionRepository jdbcSessionRepo;

    @Override
    public void configure(WebSecurity web) {
//		web
//			.ignoring().requestMatchers(PathRequest.toH2Console());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((authorize) -> authorize.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()
                                .anyRequest()
//                                .authenticated()
                                .access("@securityConfig.checkLoginState(authentication, request)")
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.invalidSessionStrategy(
                                        (req, resp) -> {
                                            System.out.println(req.getRequestedSessionId());
                                            //load session value form remote
                                            req.getSession().setAttribute("mockKey", "mockValue");
                                            resp.sendRedirect("/read-session.json");
//
//                                    ExtSessionRepo.copySession(jdbcSessionRepo, "admin",
//                                            ((HttpServletRequest) req).getSession().getId()
//                                    );
                                        }
                                )

                )
//                .formLogin((formLogin) -> formLogin.permitAll())
        ;

    }

    public boolean checkLoginState(Authentication authentication, HttpServletRequest request) {
        return true;
    }

}
