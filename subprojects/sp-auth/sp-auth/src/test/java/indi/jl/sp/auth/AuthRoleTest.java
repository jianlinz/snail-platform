package indi.jl.sp.auth;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.auth.jpa.domain.AuthRoleDo;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthRoleTest extends BaseSpringTest {

    @Test
    public void save() {
        AuthRoleDo authRoleDo = saveDo();
        AuthRoleDo save = post("/authRole", AuthRoleDo.class, JsonUtil.toJsonString(authRoleDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }
    
    @Test
    public void update() {
        AuthRoleDo save = saveDo();
        AuthRoleDo update = put("/authRole/" + save.getId(), AuthRoleDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthRoleDo> authRoleDos = get("/authRole", AuthRoleDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authRoleDos);
        String ids = authRoleDos.stream()
                .map(authRoleDo -> authRoleDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authRole?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authRole", AuthRoleDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthRoleDo> authRoleDos = get("/authRole", AuthRoleDo.class, List.class);
        assert authRoleDos.size() > 3;

        PageChunk<AuthRoleDo> pageSize1 = getPage("/authRole/page?size=1&page=0", AuthRoleDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthRoleDo> pageSize20 = getPage("/authRole/page?size=20&page=0", AuthRoleDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthRoleDo> pageSize20Page1 = getPage("/authRole/page?size=20&page=1", AuthRoleDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }
    
    private AuthRoleDo saveDo() {
        AuthRoleDo authRoleDo = new AuthRoleDo();
        return post("/authRole", AuthRoleDo.class, JsonUtil.toJsonString(authRoleDo));
    }
}