package indi.jl.sp.auth;

import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthResourceTest extends BaseSpringTest {

    @Test
    public void save() {
        AuthResourceDo authResourceDo = saveDo();
        AuthResourceDo save = post("/authResource", AuthResourceDo.class, JsonUtil.toJsonString(authResourceDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }

    @Test
    public void update() {
        AuthResourceDo save = saveDo();
        AuthResourceDo update = put("/authResource/" + save.getId(), AuthResourceDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthResourceDo> authResourceDos = get("/authResource", AuthResourceDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authResourceDos);
        String ids = authResourceDos.stream()
                .map(authResourceDo -> authResourceDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authResource?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authResource", AuthResourceDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthResourceDo> authResourceDos = get("/authResource", AuthResourceDo.class, List.class);
        assert authResourceDos.size() > 3;

        PageChunk<AuthResourceDo> pageSize1 = getPage("/authResource/page?size=1&page=0", AuthResourceDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthResourceDo> pageSize20 = getPage("/authResource/page?size=20&page=0", AuthResourceDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthResourceDo> pageSize20Page1 = getPage("/authResource/page?size=20&page=1", AuthResourceDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }

    private AuthResourceDo saveDo() {
        AuthResourceDo authResourceDo = new AuthResourceDo();
        authResourceDo.setName("test");
        authResourceDo.setMethod("POST");
        authResourceDo.setUrl("/test");
        return post("/authResource", AuthResourceDo.class, JsonUtil.toJsonString(authResourceDo));
    }
}