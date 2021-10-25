package indi.jl.sp.auth;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.auth.jpa.domain.AuthRoleResourceDo;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthRoleResourceTest extends BaseSpringTest {

    @Test
    public void save() {
        AuthRoleResourceDo authRoleResourceDo = saveDo();
        AuthRoleResourceDo save = post("/authRoleResource", AuthRoleResourceDo.class, JsonUtil.toJsonString(authRoleResourceDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }
    
    @Test
    public void update() {
        AuthRoleResourceDo save = saveDo();
        AuthRoleResourceDo update = put("/authRoleResource/" + save.getId(), AuthRoleResourceDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthRoleResourceDo> authRoleResourceDos = get("/authRoleResource", AuthRoleResourceDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authRoleResourceDos);
        String ids = authRoleResourceDos.stream()
                .map(authRoleResourceDo -> authRoleResourceDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authRoleResource?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authRoleResource", AuthRoleResourceDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthRoleResourceDo> authRoleResourceDos = get("/authRoleResource", AuthRoleResourceDo.class, List.class);
        assert authRoleResourceDos.size() > 3;

        PageChunk<AuthRoleResourceDo> pageSize1 = getPage("/authRoleResource/page?size=1&page=0", AuthRoleResourceDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthRoleResourceDo> pageSize20 = getPage("/authRoleResource/page?size=20&page=0", AuthRoleResourceDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthRoleResourceDo> pageSize20Page1 = getPage("/authRoleResource/page?size=20&page=1", AuthRoleResourceDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }
    
    private AuthRoleResourceDo saveDo() {
        AuthRoleResourceDo authRoleResourceDo = new AuthRoleResourceDo();
        return post("/authRoleResource", AuthRoleResourceDo.class, JsonUtil.toJsonString(authRoleResourceDo));
    }
}