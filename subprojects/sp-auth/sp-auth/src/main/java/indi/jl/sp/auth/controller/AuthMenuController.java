package indi.jl.sp.auth.controller;

import indi.jl.sp.auth.service.AuthMenuService;
import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.auth.jpa.domain.AuthMenuDo;
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

@RestController
@RequestMapping("/authMenu")
public class AuthMenuController extends BaseController {

    private final AuthMenuService authMenuService;

    @Autowired
    public AuthMenuController(AuthMenuService authMenuService) {
        this.authMenuService = authMenuService;
    }

    @PostMapping
    @ApiOperation("‍新增")
    public ResponseEntity<DataVO<AuthMenuDo>> post(@RequestBody AuthMenuDo authMenuDo) {
        return responseOfPost(authMenuService.save(authMenuDo));
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        authMenuService.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新")
    public ResponseEntity<DataVO<AuthMenuDo>> put(@PathVariable Long id, @RequestBody AuthMenuDo authMenuDo) {
        authMenuDo.setId(id);
        return responseOfPut(authMenuService.update(authMenuDo));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public ResponseEntity<DataVO<AuthMenuDo>> get(@PathVariable Long id) {
        return responseOfGet(authMenuService.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合")
    public ResponseEntity<DataVO<List<AuthMenuDo>>> findAll(AuthMenuDo authMenuDo) {
        return responseOfGet(authMenuService.findAll(authMenuDo));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<AuthMenuDo>>> findAll(AuthMenuDo authMenuDo,@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(authMenuService.findAll(authMenuDo, pageable));
    }
    
}