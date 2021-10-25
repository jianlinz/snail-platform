package indi.jl.sp.auth.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.auth.jpa.domain.AuthRoleResourceDo;
import indi.jl.sp.auth.service.AuthRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "角色资源关联")
@RestController
@RequestMapping("/authRoleResource")
public class AuthRoleResourceController extends BaseController {

    private final AuthRoleResourceService authRoleResourceService;

    @Autowired
    public AuthRoleResourceController(AuthRoleResourceService authRoleResourceService) {
        this.authRoleResourceService = authRoleResourceService;
    }
    
    @PostMapping
    @ApiOperation("‍新增角色资源关联")
    public ResponseEntity<DataVO<AuthRoleResourceDo>> post(@RequestBody AuthRoleResourceDo authRoleResourceDo) {
        return responseOfPost(authRoleResourceService.save(authRoleResourceDo));
    }

    @DeleteMapping
    @ApiOperation("批量删除角色资源关联")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        authRoleResourceService.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新角色资源关联")
    public ResponseEntity<DataVO<AuthRoleResourceDo>> put(@PathVariable Long id, @RequestBody AuthRoleResourceDo authRoleResourceDo) {
        authRoleResourceDo.setId(id);
        return responseOfPut(authRoleResourceService.update(authRoleResourceDo));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询角色资源关联")
    public ResponseEntity<DataVO<AuthRoleResourceDo>> get(@PathVariable Long id) {
        return responseOfGet(authRoleResourceService.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合角色资源关联")
    public ResponseEntity<DataVO<List<AuthRoleResourceDo>>> findAll(AuthRoleResourceDo authRoleResourceDo) {
        return responseOfGet(authRoleResourceService.findAll(authRoleResourceDo));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询角色资源关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<AuthRoleResourceDo>>> findAll(AuthRoleResourceDo authRoleResourceDo,@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(authRoleResourceService.findAll(authRoleResourceDo, pageable));
    }
    
}