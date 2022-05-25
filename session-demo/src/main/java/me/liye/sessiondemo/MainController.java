package me.liye.sessiondemo;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.jdbc.ExtSessionRepo;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liye on 2022/05/16.
 */

@RestController
public class MainController {
    @Autowired(required = false)
    JdbcIndexedSessionRepository jdbcSessionRepo;

    @GetMapping("/health.json")
    @ResponseBody
    public Object checkPreload() {
        return new HashMap<String, Boolean>() {
            {
                put("success", new Boolean(true));
            }
        };
    }

    @GetMapping("write-session.json")
    @ResponseBody
    public Object writeSession(
            @RequestParam
            String key,
            @RequestParam
            String value, HttpServletRequest req) {
        req.getSession().setAttribute(key, value);
        return readSession(req);
    }

    @GetMapping("read-session.json")
    @ResponseBody
    public Object readSession(HttpServletRequest req) {
        Map kv = Maps.newHashMap();
        for (Enumeration<String> it = req.getSession().getAttributeNames(); it.hasMoreElements();
        ) {
            String k = it.nextElement();
            kv.put(k, req.getSession().getAttribute(k));
        }
        return kv;
    }

    @GetMapping("copy-session.json")
    @ResponseBody
    public Object copySession(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    ) {
        ExtSessionRepo.copySession(jdbcSessionRepo,from,to);
        return "success";
    }
}
