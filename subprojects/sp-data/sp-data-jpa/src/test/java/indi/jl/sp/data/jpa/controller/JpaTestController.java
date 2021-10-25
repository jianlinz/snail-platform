package indi.jl.sp.data.jpa.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.data.jpa.domain.JpaTestT;
import indi.jl.sp.data.jpa.service.JpaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpaTest")
public class JpaTestController extends BaseController {

    @Autowired
    private JpaTestService jpaTestService;

    @PostMapping
    public ResponseEntity<DataVO<JpaTestT>> post(@RequestBody JpaTestT jpaTestT) {
        jpaTestT = jpaTestService.save(jpaTestT);
        return responseOfPost(jpaTestT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        jpaTestService.delete(id);
        return responseOfDelete();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataVO<JpaTestT>> put(@PathVariable Long id, @RequestBody JpaTestT jpaTestT) {
        jpaTestT.setId(id);
        return responseOfPut(jpaTestService.update(jpaTestT));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataVO<JpaTestT>> get(@PathVariable Long id) {
        JpaTestT jpaTestT = jpaTestService.get(id).orElse(null);
        return responseOfGet(jpaTestT);
    }

    @GetMapping
    public ResponseEntity<DataVO<List<JpaTestT>>> findAll() {
        List<JpaTestT> jpaTests = jpaTestService.findAll();
        return responseOfGet(jpaTests);
    }
}
