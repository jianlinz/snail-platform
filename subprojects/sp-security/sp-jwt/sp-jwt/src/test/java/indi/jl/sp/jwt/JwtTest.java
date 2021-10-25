package indi.jl.sp.jwt;

import indi.jl.sp.security.api.pojo.SecurityResource;
import indi.jl.sp.security.api.pojo.SecurityRole;
import indi.jl.sp.security.api.pojo.SecurityUserDetail;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import java.util.*;

public class JwtTest extends BaseSpringTest {

    @Test
    public void generateUserToken() {
        Map<String, Object> extMsg = new HashMap<>();
        extMsg.put("test", new ArrayList<>());

        List<SecurityRole> roles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SecurityRole securityRole = new SecurityRole();
            securityRole.setRoleId((long) i);
            securityRole.setName("i" + i);
            for (int j = 0; j < 10; j++) {
                securityRole.addSecurityResource(new SecurityResource("POST", "GET:/test/daqwetdqwqrqrqrgerqrqwsxvdghttjehb/daqwetdqwqrqrqrgerqrqwsxvdghttjehb/sdadddddddddd/dddddddddddddd/daqwetdqwqrqrqrgerqrqwsxvdghttjehb/sdadddddddddd/dddddddddddddd/daqwetdqwqrqrqrgerqrqwsxvdghttjehb/sdadddddddddd" + i));
            }
            roles.add(securityRole);
        }
        SecurityUserDetail securityUserDetail = new SecurityUserDetail(1L, "test", "password", "测试账号",
                roles, extMsg,
                true, true, true, true);
        String token = JwtUtil.generateAccessToken(securityUserDetail);
        assert "test".equals(JwtUtil.getPayload(token).getUserName());
        assert "测试账号".equals(JwtUtil.getPayload(token).getName());
        assert 1L == JwtUtil.getPayload(token).getUserId();
        assert 10 == JwtUtil.getPayload(token).getRoles().size();
        assert 100 == JwtUtil.getPayload(token).getResources().size();
        assert 0 == ((List) JwtUtil.getPayload(token).getExtMessage().get("test")).size();
    }

    @Test
    public void validToken() {
        SecurityUserDetail securityUserDetail = new SecurityUserDetail(2L,
                "testValid", "password", "测试账号",
                new ArrayList<>(),
                new HashMap<>(), true, true, true, true);

        String token = JwtUtil.generateAccessToken(securityUserDetail);
        JwtUtil.verify(token);
        try {
            JwtUtil.verify(token + "a");
        } catch (PreAuthenticatedCredentialsNotFoundException e) {
            assert "Token is invalid!".equals(e.getMessage());
        }
        try {
            JwtUtil.verify("q.b.c");
        } catch (PreAuthenticatedCredentialsNotFoundException e) {
            assert e.getMessage().startsWith("Token format error!");
        }
    }

}