package indi.jl.sp.gradle.plugin.task.project

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import indi.jl.sp.gradle.plugin.task.module.BaseInitModule
import org.gradle.api.tasks.TaskAction

class InitJpaProjectFile extends BaseInitJpaProject {

    static final String DESCRIPTION = "生成JPA项目文件"

    InitJpaProjectFile() {
        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {
        mkConfiguration()
        mkDomain()
        mkJpaRepository()
        mkDao()
        mkService()
        mkImpl()
        mkController()
        mkTest()
    }

    def mkDomain() {
        File file = project.file("${getProjectDomainPath()}/${getProjectDomainName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectDomainPackage()};

import indi.jl.sp.data.jpa.domain.BaseDo;
import indi.jl.sp.core.util.JsonUtil;
import javax.persistence.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "${getModuleDesc()}")
@Entity
@Table(name = "${getTableName()}")
public class ${getProjectDomainName()} extends BaseDo {

    public ${getProjectDomainName()}(){}

    @Column
    @ApiModelProperty("测试字段")
    private Integer test;

    public Integer getTest() {
        return test;
    }

    public ${getProjectDomainName()} setTest(Integer test) {
        this.test = test;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}""")
        }
    }

    def mkJpaRepository() {
        File file = project.file("${getProjectRepositoryPath()}/${getProjectRepositoryName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectRepositoryPackage()};

import ${getProjectDomainPackage()}.${getProjectDomainName()};
import indi.jl.sp.data.jpa.repository.BaseJpaRepository;

public interface ${getProjectRepositoryName()} extends BaseJpaRepository<${getProjectDomainName()}> {

}""")
        }
    }

    def mkDao() {
        File file = project.file("${getProjectDaoPath()}/${getProjectDaoName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectDaoPackage()};

import com.querydsl.core.types.dsl.EntityPathBase;
import indi.jl.sp.data.jpa.annotation.SpDao;
import indi.jl.sp.data.jpa.dao.BaseDao;
import ${getProjectDomainPackage()}.${getProjectDomainName()};
import ${getProjectDomainPackage()}.${getProjectDomainQName()};
import indi.jl.sp.data.jpa.repository.AbstractJpaRepository;
import ${getProjectRepositoryPackage()}.${getProjectRepositoryName()};
import org.springframework.beans.factory.annotation.Autowired;

@SpDao
public class ${getProjectDaoName()} extends BaseDao<${getProjectDomainName()}> {

    private final ${getProjectRepositoryName()} ${toFirstLowerCase(getProjectRepositoryName())};

    @Autowired
    public ${getProjectDaoName()}(${getProjectRepositoryName()} ${toFirstLowerCase(getProjectRepositoryName())}) {
        this.${toFirstLowerCase(getProjectRepositoryName())} = ${toFirstLowerCase(getProjectRepositoryName())};
    }
    
    @Override
    protected AbstractJpaRepository<${getProjectDomainName()}, Long> getRepository() {
        return ${toFirstLowerCase(getProjectRepositoryName())};
    }

    @Override
    protected EntityPathBase<${getProjectDomainName()}> getEntityPathBase() {
        return ${getProjectDomainQName()}.${toFirstLowerCase(getProjectDomainName())};
    }
}""")
        }
    }

    def mkService() {
        File file = project.file("${getProjectServicePath()}/${getProjectServiceName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectServicePackage()};

import ${getProjectDomainPackage()}.${getProjectDomainName()};
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ${getProjectServiceName()} {
   
   
    /**
     * 保存
     *
     * @param ${toFirstLowerCase(getProjectDomainName())} 保存对象
     * @return 保存后结果
     */
    @Transactional
    ${getProjectDomainName()} save(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())});

    /**
     * 更新
     *
     * @param ${toFirstLowerCase(getProjectDomainName())} 更新对象
     * @return 更新后结果
     */
    @Transactional
    ${getProjectDomainName()} update(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())});

    /**
     * 根据id集合删除
     *
     * @param ids ids
     */
    @Transactional
    void deleteByIds(List<Long> ids);
    
    /**
     * 根据id 查询 
     *
     * @param id 主键
     * @return 查询结果
     */
    Optional<${getProjectDomainName()}> get(Long id);

    /**
     * 查询
     *
     * @param ${toFirstLowerCase(getProjectDomainName())} 查询条件
     * @return 查询结果集
     */   
    List<${getProjectDomainName()}> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())});

    /**
     * 分页查询
     *
     * @param ${toFirstLowerCase(getProjectDomainName())}   查询条件
     * @param pageable 分页参数
     * @return 查询结果集
     */
    PageChunk<${getProjectDomainName()}> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}, Pageable pageable);
}""")
        }
    }

    def mkImpl() {
        File file = project.file("${getProjectImplPath()}/${getProjectImplName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectImplPackage()};

import ${getProjectDaoPackage()}.${getProjectDaoName()};
import ${getProjectDomainPackage()}.${getProjectDomainName()};
import ${getProjectServicePackage()}.${getProjectServiceName()};
import org.springframework.beans.factory.annotation.Autowired;
import indi.jl.sp.core.domain.PageChunk;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ${getProjectImplName()} implements ${getProjectServiceName()} {

    private final ${getProjectDaoName()} ${toFirstLowerCase(getProjectDaoName())};

    @Autowired
    public ${getProjectImplName()}(${getProjectDaoName()} ${toFirstLowerCase(getProjectDaoName())}) {
        this.${toFirstLowerCase(getProjectDaoName())} = ${toFirstLowerCase(getProjectDaoName())};
    }

    @Override
    public Optional<${getProjectDomainName()}> get(Long id) {
        return ${toFirstLowerCase(getProjectDaoName())}.getById(id);
    }

    @Override
    public ${getProjectDomainName()} save(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        return ${toFirstLowerCase(getProjectDaoName())}.save(${toFirstLowerCase(getProjectDomainName())});
    }

    @Override
    public ${getProjectDomainName()} update(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        return ${toFirstLowerCase(getProjectDaoName())}.update(${toFirstLowerCase(getProjectDomainName())});
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        ${toFirstLowerCase(getProjectDaoName())}.deleteByIds(ids);
    }
    
    @Override
    public List<${getProjectDomainName()}> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        return ${toFirstLowerCase(getProjectDaoName())}.findAll(${toFirstLowerCase(getProjectDomainName())});
    }

    @Override
    public PageChunk<${getProjectDomainName()}> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}, Pageable pageable) {
        return ${toFirstLowerCase(getProjectDaoName())}.findAll(${toFirstLowerCase(getProjectDomainName())}, pageable);
    }

}""")
        }
    }

    def mkController() {
        File file =project.file("${getProjectControllerPath()}/${getProjectControllerName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectControllerPackage()};

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.util.StringUtil;
import indi.jl.sp.core.vo.DataVO;
import ${getProjectDomainPackage()}.${getProjectDomainName()};
import ${getProjectServicePackage()}.${getProjectServiceName()};
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

@Api(tags = "${getModuleDesc()}")
@RestController
@RequestMapping("/${toFirstLowerCase(getJavaModuleName())}")
public class ${getProjectControllerName()} extends BaseController {

    private final ${getProjectServiceName()} ${toFirstLowerCase(getProjectServiceName())};

    @Autowired
    public ${getProjectControllerName()}(${getProjectServiceName()} ${toFirstLowerCase(getProjectServiceName())}) {
        this.${toFirstLowerCase(getProjectServiceName())} = ${toFirstLowerCase(getProjectServiceName())};
    }
    
    @PostMapping
    @ApiOperation("‍新增${getModuleDesc()}")
    public ResponseEntity<DataVO<${getProjectDomainName()}>> post(@RequestBody ${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        return responseOfPost(${toFirstLowerCase(getProjectServiceName())}.save(${toFirstLowerCase(getProjectDomainName())}));
    }

    @DeleteMapping
    @ApiOperation("批量删除${getModuleDesc()}")
    public ResponseEntity<DataVO<String>> delete(@RequestParam @ApiParam(value = "id集合 逗号分隔") String ids) {
        ${toFirstLowerCase(getProjectServiceName())}.deleteByIds(StringUtil.convertLong(ids));
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    @ApiOperation("更新${getModuleDesc()}")
    public ResponseEntity<DataVO<${getProjectDomainName()}>> put(@PathVariable Long id, @RequestBody ${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        ${toFirstLowerCase(getProjectDomainName())}.setId(id);
        return responseOfPut(${toFirstLowerCase(getProjectServiceName())}.update(${toFirstLowerCase(getProjectDomainName())}));
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询${getModuleDesc()}")
    public ResponseEntity<DataVO<${getProjectDomainName()}>> get(@PathVariable Long id) {
        return responseOfGet(${toFirstLowerCase(getProjectServiceName())}.get(id).orElse(null));
    }

    @GetMapping
    @ApiOperation("根据条件查询集合${getModuleDesc()}")
    public ResponseEntity<DataVO<List<${getProjectDomainName()}>>> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())}) {
        return responseOfGet(${toFirstLowerCase(getProjectServiceName())}.findAll(${toFirstLowerCase(getProjectDomainName())}));
    }

    @GetMapping("/page")
    @ApiOperation("根据条件分页查询${getModuleDesc()}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "当前页数,默认0", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "每页条数,默认10", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "排序 默认根据创建时间倒叙")
    })
    public ResponseEntity<DataVO<PageChunk<${getProjectDomainName()}>>> findAll(${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())},@PageableDefault(sort = DEFAULT_SORT, direction = Sort.Direction.DESC) @ApiIgnore Pageable pageable) {
        return responseOfGet(${toFirstLowerCase(getProjectServiceName())}.findAll(${toFirstLowerCase(getProjectDomainName())}, pageable));
    }
    
}""")
        }
    }

    def mkTest() {
        File file =project.file("${getProjectTestPath()}/${getTestName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getTestPackage()};

import indi.jl.sp.core.domain.PageChunk;
import indi.jl.sp.core.util.CollectionUtil;
import indi.jl.sp.core.util.JsonUtil;
import ${getProjectDomainPackage()}.${getProjectDomainName()};
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ${getTestName()} extends BaseSpringTest {

    @Test
    public void save() {
        ${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())} = saveDo();
        ${getProjectDomainName()} save = post("/${toFirstLowerCase(getJavaModuleName())}", ${getProjectDomainName()}.class, JsonUtil.toJsonString(${toFirstLowerCase(getProjectDomainName())}));
        assert null != save.getId();
        assert null != save.getCreateTime();
        assert null != save.getUpdateTime();
    }
    
    @Test
    public void update() {
        ${getProjectDomainName()} save = saveDo();
        ${getProjectDomainName()} update = put("/${toFirstLowerCase(getJavaModuleName())}/" + save.getId(), ${getProjectDomainName()}.class, JsonUtil.toJsonString(save));
        assert save.getId() == update.getId();
        assert null != update.getCreateTime();
        assert null != update.getUpdateTime();
    }

    @Test
    public void delete() {
        saveDo();
        saveDo();
        Collection<${getProjectDomainName()}> ${toFirstLowerCase(getProjectDomainName())}s = get("/${toFirstLowerCase(getJavaModuleName())}", ${getProjectDomainName()}.class, List.class);
        assert CollectionUtil.isNotEmpty(${toFirstLowerCase(getProjectDomainName())}s);
        String ids = ${toFirstLowerCase(getProjectDomainName())}s.stream()
                .map(${toFirstLowerCase(getProjectDomainName())} -> ${toFirstLowerCase(getProjectDomainName())}.getId().toString())
                .collect(Collectors.joining(","));
        delete("/${toFirstLowerCase(getJavaModuleName())}?ids=" + ids);
        assert CollectionUtil.isEmpty(get("/${toFirstLowerCase(getJavaModuleName())}", ${getProjectDomainName()}.class, List.class));
    }

    @Test
    public void find() {
        saveDo();
        saveDo();
        saveDo();
        saveDo();
        Collection<${getProjectDomainName()}> ${toFirstLowerCase(getProjectDomainName())}s = get("/${toFirstLowerCase(getJavaModuleName())}", ${getProjectDomainName()}.class, List.class);
        assert ${toFirstLowerCase(getProjectDomainName())}s.size() > 3;

        PageChunk<${getProjectDomainName()}> pageSize1 = getPage("/${toFirstLowerCase(getJavaModuleName())}/page?size=1&page=0", ${getProjectDomainName()}.class);
        assert pageSize1.getTotalElements() > 3;
        assert pageSize1.getContent().size() == 1;

        PageChunk<${getProjectDomainName()}> pageSize20 = getPage("/${toFirstLowerCase(getJavaModuleName())}/page?size=20&page=0", ${getProjectDomainName()}.class);
        assert pageSize20.getContent().size() > 3;

        PageChunk<${getProjectDomainName()}> pageSize20Page1 = getPage("/${toFirstLowerCase(getJavaModuleName())}/page?size=20&page=1", ${getProjectDomainName()}.class);
        assert pageSize20Page1.getContent().size() == 0;
    }
    
    private ${getProjectDomainName()} saveDo() {
        ${getProjectDomainName()} ${toFirstLowerCase(getProjectDomainName())} = new ${getProjectDomainName()}();
        return post("/${toFirstLowerCase(getJavaModuleName())}", ${getProjectDomainName()}.class, JsonUtil.toJsonString(${toFirstLowerCase(getProjectDomainName())}));
    }
}""")
        }
    }

    def mkConfiguration() {
        File file = project.file("${getProjectMainPath()}/${getProjectConfigurationName()}.java")
        if(fileExist(file)){
            return
        }
        file.withWriter(BaseInitModule.charset) {
            it.write("""package ${getProjectPackage()};

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ${getProjectConfigurationName()}{

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket ${toFirstLowerCase(getJavaModuleName())}Docket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("${toFirstLowerCase(getJavaModuleName())}")
                .select()
                .paths(PathSelectors.ant("/${toFirstLowerCase(getJavaModuleName())}/**"))
                .build();
    }

}""")
        }
    }
}
