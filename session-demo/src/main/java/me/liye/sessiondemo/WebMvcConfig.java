package me.liye.sessiondemo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.MySqlJdbcIndexedSessionRepositoryCustomizer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liye on 2022/05/16.
 */
@EnableSpringHttpSession
@Configuration()
public class WebMvcConfig {
    final
    JdbcIndexedSessionRepository jdbcRepo;

    public WebMvcConfig(JdbcIndexedSessionRepository jdbcRepo) {
        this.jdbcRepo = jdbcRepo;
    }

//    @Bean
//    public MapSessionRepository sessionRepository() {
//        return new MapSessionRepository(
//                new ConcurrentHashMap<>()
//        );
//    }
}
