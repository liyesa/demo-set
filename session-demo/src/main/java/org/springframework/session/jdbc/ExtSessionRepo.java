package org.springframework.session.jdbc;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.springframework.security.util.FieldUtils;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;

import java.util.Map;

/**
 * Created by liye on 2022/05/16.
 */
public class ExtSessionRepo {

    @SneakyThrows
    public static void copySession(JdbcIndexedSessionRepository jdbcSessionRepo, String fromPrincipal, String toSid) {
        // find source session
        Map<String, JdbcIndexedSessionRepository.JdbcSession> sessionMap = jdbcSessionRepo.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, fromPrincipal);
        JdbcIndexedSessionRepository.JdbcSession jdbcSession = sessionMap.entrySet().stream().findFirst().map(Map.Entry::getValue).orElse(null);

        String str = JSON.toJSONString(jdbcSession, true);
        System.out.println(str);

        //copy session
        JdbcIndexedSessionRepository.JdbcSession copiedJdbcSession = jdbcSessionRepo.createSession();
        // set session id
        MapSession mapSession = (MapSession) FieldUtils.getFieldValue(copiedJdbcSession, "delegate");
        mapSession.setId(toSid);

        mockSessionValue(copiedJdbcSession);

        // set target principle
        // copiedJdbcSession.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, to);

//        jdbcSession.getAttributeNames().forEach(k -> {
//            copiedJdbcSession.setAttribute(k, jdbcSession.getAttribute(k));
//        });
//        jdbcSessionRepo.save(copiedJdbcSession);
    }

    private static void mockSessionValue(JdbcIndexedSessionRepository.JdbcSession jdbcSession) {
        jdbcSession.setAttribute("k1", "v1");
        jdbcSession.setAttribute("k2", "v2");
    }
}
