package indi.jl.sp.auth;

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.auth.jpa.domain.AuthMenuDo;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthMenuTest extends BaseSpringTest {

    @Test
    public void save() {
        AuthMenuDo authMenuDo = saveDo();
        AuthMenuDo save = post("/authMenu", AuthMenuDo.class, JsonUtil.toJsonString(authMenuDo));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }
    
    @Test
    public void update() {
        AuthMenuDo save = saveDo();
        AuthMenuDo update = put("/authMenu/" + save.getId(), AuthMenuDo.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<AuthMenuDo> authMenuDos = get("/authMenu", AuthMenuDo.class, List.class);
        assert CollectionUtil.isNotEmpty(authMenuDos);
        String ids = authMenuDos.stream()
                .map(authMenuDo -> authMenuDo.getId().toString())
                .collect(Collectors.joining(","));
        delete("/authMenu?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/authMenu", AuthMenuDo.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<AuthMenuDo> authMenuDos = get("/authMenu", AuthMenuDo.class, List.class);
        assert authMenuDos.size() > 3;

        PageChunk<AuthMenuDo> pageSize1 = getPage("/authMenu/page?size=1&page=0", AuthMenuDo.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<AuthMenuDo> pageSize20 = getPage("/authMenu/page?size=20&page=0", AuthMenuDo.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<AuthMenuDo> pageSize20Page1 = getPage("/authMenu/page?size=20&page=1", AuthMenuDo.class);
        assert pageSize20Page1.getContent().size() == 0;
    }
    
    private AuthMenuDo saveDo() {
        AuthMenuDo authMenuDo = new AuthMenuDo();
        return post("/authMenu", AuthMenuDo.class, JsonUtil.toJsonString(authMenuDo));
    }
}