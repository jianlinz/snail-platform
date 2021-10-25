package indi.jl.sp.auth;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.auth.jpa.domain.AuthUserRoleDo;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUserRoleTest extends BaseSpringTest {

    @Test
    public void save() {
        AuthUserRoleDo authUserRoleDo = saveDo();
        AuthUserRoleDo save = post("/authUserRole", AuthUserRoleDo.class, JsonUtil.toJsonString(authUserRoleDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }
    
    @Test
    public void update() {
        AuthUserRoleDo save = saveDo();
        AuthUserRoleDo update = put("/authUserRole/" + save.getId(), AuthUserRoleDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthUserRoleDo> authUserRoleDos = get("/authUserRole", AuthUserRoleDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authUserRoleDos);
        String ids = authUserRoleDos.stream()
                .map(authUserRoleDo -> authUserRoleDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authUserRole?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authUserRole", AuthUserRoleDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthUserRoleDo> authUserRoleDos = get("/authUserRole", AuthUserRoleDo.class, List.class);
        assert authUserRoleDos.size() > 3;

        PageChunk<AuthUserRoleDo> pageSize1 = getPage("/authUserRole/page?size=1&page=0", AuthUserRoleDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthUserRoleDo> pageSize20 = getPage("/authUserRole/page?size=20&page=0", AuthUserRoleDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthUserRoleDo> pageSize20Page1 = getPage("/authUserRole/page?size=20&page=1", AuthUserRoleDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }
    
    private AuthUserRoleDo saveDo() {
        AuthUserRoleDo authUserRoleDo = new AuthUserRoleDo();
        return post("/authUserRole", AuthUserRoleDo.class, JsonUtil.toJsonString(authUserRoleDo));
    }
}