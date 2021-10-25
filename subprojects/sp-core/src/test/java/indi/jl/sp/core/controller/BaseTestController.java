package indi.jl.sp.core.controller;

import indi.jl.sp.core.exception.SpBusinessException;
import indi.jl.sp.core.exception.SpSystemException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseTestController extends BaseController {

    @GetMapping(path = "/businessEx")
    public ResponseEntity businessEx() {
        throw new SpBusinessException("00000", "TEST_ERROR");
    }

    @GetMapping(path = "/systemEx")
    public ResponseEntity systemEx() {
        throw new SpSystemException("TEST_SYSTEM_ERROR");
    }

    @GetMapping(path = "/unKnowEx")
    public ResponseEntity unKnowEx() {
        throw new RuntimeException("TEST_UN_KNOW_ERROR");
    }
}
