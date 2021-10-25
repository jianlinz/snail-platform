package indi.jl.sp.auth;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.auth.jpa.domain.AuthUserDo;
import indi.jl.sp.security.api.encoder.Md5PasswordEncoder;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUserTest extends BaseSpringTest {


    @Sql("/indi/jl/sp/auth/sql/initUser.sql")
    @Test
    public void save() {
        AuthUserDo authUserDo = saveDo();
        authUserDo.setUsername("test");
        authUserDo.setName("test");
        authUserDo.setPassword("password");
        AuthUserDo save = post("/authUser", AuthUserDo.class, JsonUtil.toJsonString(authUserDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
        assert "test".equals(save.getName());
        assert "test".equals(save.getUsername());
        assert new Md5PasswordEncoder().encode("password").equals(save.getPassword());
    }

    @Test
    public void update() {
        AuthUserDo save = saveDo();
        AuthUserDo update = put("/authUser/" + save.getId(), AuthUserDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthUserDo> authUserDos = get("/authUser", AuthUserDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authUserDos);
        String ids = authUserDos.stream()
                .map(authUserDo -> authUserDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authUser?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authUser", AuthUserDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthUserDo> authUserDos = get("/authUser", AuthUserDo.class, List.class);
        assert authUserDos.size() > 3;

        PageChunk<AuthUserDo> pageSize1 = getPage("/authUser/page?size=1&page=0", AuthUserDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthUserDo> pageSize20 = getPage("/authUser/page?size=20&page=0", AuthUserDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthUserDo> pageSize20Page1 = getPage("/authUser/page?size=20&page=1", AuthUserDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }

    private AuthUserDo saveDo() {
        AuthUserDo authUserDo = new AuthUserDo();
        authUserDo.setUsername("test");
        authUserDo.setPassword("test");
        return post("/authUser", AuthUserDo.class, JsonUtil.toJsonString(authUserDo));
    }
}