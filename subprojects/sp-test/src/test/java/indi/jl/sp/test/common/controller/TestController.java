package indi.jl.sp.test.common.controller;

import indi.jl.sp.core.controller.BaseController;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.test.common.model.TestChildDTO;
import indi.jl.sp.test.common.model.TestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @GetMapping
    public ResponseEntity<DataVO<TestDTO>> test() {
        TestDTO testDTO = new TestDTO();
        testDTO.setAge(12);
        testDTO.setName("name");
        testDTO.setBirthday(LocalDate.of(2020, 1, 30));
        testDTO.setCmt(LocalDateTime.of(2020, 12, 1, 18, 20, 01));
        TestChildDTO testChildDTO = new TestChildDTO();
        testChildDTO.setChildName("child");
        testDTO.addChild(testChildDTO);
        return responseOfGet(testDTO);
    }

    @PostMapping
    public ResponseEntity<DataVO<TestDTO>> post(@RequestBody TestDTO testDTO) {
        return responseOfPost(testDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DataVO<TestDTO>> put(@PathVariable String id, @RequestBody TestDTO testDTO) {
        return responseOfPut(testDTO);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<DataVO<String>> delete(@PathVariable String id) {
        return responseOfDelete();
    }
}
