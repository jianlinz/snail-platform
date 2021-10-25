package indi.jl.sp.auth.controller;

import indi.jl.sp.auth.service.AuthUserService;
import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.auth.jpa.domain.AuthUserDo;
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
@RequestMapping("/authUser")
public class AuthUserController extends BaseController {

    private final AuthUserService authUserService;

    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }
    
    @PostMapping
    @ApiOperation("‍新增")
    public ResponseEntity<DataVO<AuthUserDo>> post(@RequestBody AuthUserDo authUserDo) {
        return responseOfPost(authUserService.save(authUserDo));
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        authUserService.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新")
    public ResponseEntity<DataVO<AuthUserDo>> put(@PathVariable Long id, @RequestBody AuthUserDo authUserDo) {
        authUserDo.setId(id);
        return responseOfPut(authUserService.update(authUserDo));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询")
    public ResponseEntity<DataVO<AuthUserDo>> get(@PathVariable Long id) {
        return responseOfGet(authUserService.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合")
    public ResponseEntity<DataVO<List<AuthUserDo>>> findAll(AuthUserDo authUserDo) {
        return responseOfGet(authUserService.findAll(authUserDo));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<AuthUserDo>>> findAll(AuthUserDo authUserDo,@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(authUserService.findAll(authUserDo, pageable));
    }
    
}