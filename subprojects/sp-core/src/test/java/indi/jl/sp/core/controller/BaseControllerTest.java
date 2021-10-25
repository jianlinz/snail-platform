package indi.jl.sp.core.controller;

import indi.jl.sp.core.SpCoreProperties;
import indi.jl.sp.core.vo.DataVO;
import indi.jl.sp.test.common.BaseSpringTest;
import indi.jl.sp.test.exception.ResponseErrorExceptionSp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseControllerTest extends BaseSpringTest {

    @Autowired
    private SpCoreProperties spCoreProperties;

    @Test
    public void businessExTest() {
        try {
            get("/businessEx", String.class);
        } catch (ResponseErrorExceptionSp e) {
            DataVO errorVO = buildErrorVO(e.getMessage());
            assert "A_00000".equals(errorVO.getErrCode());
            assert "TEST_ERROR".equals(errorVO.getErrMsg());
        }
    }

    @Test
    public void systemExTest() {
        try {
            get("/systemEx", String.class);
        } catch (ResponseErrorExceptionSp e) {
            DataVO errorVO = buildErrorVO(e.getMessage());
            assert spCoreProperties.getResponseSystemCode().equals(errorVO.getErrCode());
            assert spCoreProperties.getResponseSystemMsg().equals(errorVO.getErrMsg());
        }
    }

    @Test
    public void unKnowExTest() {
        try {
            get("/unKnowEx", String.class);
        } catch (ResponseErrorExceptionSp e) {
            DataVO errorVO = buildErrorVO(e.getMessage());
            assert spCoreProperties.getResponseUnKnowCode().equals(errorVO.getErrCode());
            assert spCoreProperties.getResponseUnKnowMsg().equals(errorVO.getErrMsg());
        }
    }
}
