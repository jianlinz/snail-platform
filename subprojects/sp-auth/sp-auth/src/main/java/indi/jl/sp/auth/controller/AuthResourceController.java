package indi.jl.sp.auth.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.auth.jpa.domain.AuthResourceDo;
import indi.jl.sp.auth.service.AuthResourceService;
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

@Api(tags = "资源")
@RestController
@RequestMapping("/authResource")
public class AuthResourceController extends BaseController {

    private final AuthResourceService authResourceService;

    @Autowired
    public AuthResourceController(AuthResourceService authResourceService) {
        this.authResourceService = authResourceService;
    }
    
    @PostMapping
    @ApiOperation("‍新增资源")
    public ResponseEntity<DataVO<AuthResourceDo>> post(@RequestBody AuthResourceDo authResourceDo) {
        return responseOfPost(authResourceService.save(authResourceDo));
    }

    @DeleteMapping
    @ApiOperation("批量删除资源")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        authResourceService.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新资源")
    public ResponseEntity<DataVO<AuthResourceDo>> put(@PathVariable Long id, @RequestBody AuthResourceDo authResourceDo) {
        authResourceDo.setId(id);
        return responseOfPut(authResourceService.update(authResourceDo));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询资源")
    public ResponseEntity<DataVO<AuthResourceDo>> get(@PathVariable Long id) {
        return responseOfGet(authResourceService.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合资源")
    public ResponseEntity<DataVO<List<AuthResourceDo>>> findAll(AuthResourceDo authResourceDo) {
        return responseOfGet(authResourceService.findAll(authResourceDo));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<AuthResourceDo>>> findAll(AuthResourceDo authResourceDo,@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(authResourceService.findAll(authResourceDo, pageable));
    }
    
}