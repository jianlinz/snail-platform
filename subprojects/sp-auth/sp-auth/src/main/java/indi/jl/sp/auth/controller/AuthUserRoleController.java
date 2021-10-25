package indi.jl.sp.auth.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.auth.jpa.domain.AuthUserRoleDo;
import indi.jl.sp.auth.service.AuthUserRoleService;
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

@Api(tags = "用户角色关联")
@RestController
@RequestMapping("/authUserRole")
public class AuthUserRoleController extends BaseController {

    private final AuthUserRoleService authUserRoleService;

    @Autowired
    public AuthUserRoleController(AuthUserRoleService authUserRoleService) {
        this.authUserRoleService = authUserRoleService;
    }
    
    @PostMapping
    @ApiOperation("‍新增用户角色关联")
    public ResponseEntity<DataVO<AuthUserRoleDo>> post(@RequestBody AuthUserRoleDo authUserRoleDo) {
        return responseOfPost(authUserRoleService.save(authUserRoleDo));
    }

    @DeleteMapping
    @ApiOperation("批量删除用户角色关联")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        authUserRoleService.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新用户角色关联")
    public ResponseEntity<DataVO<AuthUserRoleDo>> put(@PathVariable Long id, @RequestBody AuthUserRoleDo authUserRoleDo) {
        authUserRoleDo.setId(id);
        return responseOfPut(authUserRoleService.update(authUserRoleDo));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询用户角色关联")
    public ResponseEntity<DataVO<AuthUserRoleDo>> get(@PathVariable Long id) {
        return responseOfGet(authUserRoleService.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合用户角色关联")
    public ResponseEntity<DataVO<List<AuthUserRoleDo>>> findAll(AuthUserRoleDo authUserRoleDo) {
        return responseOfGet(authUserRoleService.findAll(authUserRoleDo));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询用户角色关联")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<AuthUserRoleDo>>> findAll(AuthUserRoleDo authUserRoleDo,@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(authUserRoleService.findAll(authUserRoleDo, pageable));
    }
    
}