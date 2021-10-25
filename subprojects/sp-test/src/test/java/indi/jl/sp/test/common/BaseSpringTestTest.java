package indi.jl.sp.test.common;

import indi.jl.sp.core.util.JsonUtil;
import indi.jl.sp.test.common.model.TestChildDTO;
import indi.jl.sp.test.common.model.TestDTO;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BaseSpringTestTest extends BaseSpringTest {

    @Test
    public void getPrefixTest() {
        assert null != getPrefix();
    }

    @Test
    public void testGet() {
        TestDTO testDTO = get("/test", TestDTO.class);
        assert "name".equals(testDTO.getName());
        assert 12 == testDTO.getAge();
        assert LocalDate.of(2020, 1, 30).isEqual(testDTO.getBirthday());
        assert LocalDateTime.of(2020, 12, 1, 18, 20, 01).isEqual(testDTO.getCmt());
        List<TestChildDTO> testChildren = testDTO.getChildren();
        assert 1 == testChildren.size();
        assert "child".equals(testChildren.get(0).getChildName());
    }


    @Test
    public void testPost() {
        LocalDateTime cmt = LocalDateTime.now();
        //返回的时候只精确到秒 前端通常到秒就可以了
        LocalDateTime cmtReturn = LocalDateTime.of(cmt.getYear(), cmt.getMonth(), cmt.getDayOfMonth(), cmt.getHour(), cmt.getMinute(), cmt.getSecond());
        TestDTO testDTO = new TestDTO();
        testDTO.setAge(12);
        testDTO.setName("name");
        testDTO.setBirthday(LocalDate.of(2020, 1, 30));
        testDTO.setCmt(cmt);
        TestChildDTO testChildDTO = new TestChildDTO();
        testChildDTO.setChildName("child");
        testDTO.addChild(testChildDTO);
        TestDTO testDTOReturn = post("/test", TestDTO.class, JsonUtil.toJsonString(testDTO));
        assert "name".equals(testDTOReturn.getName());
        assert 12 == testDTOReturn.getAge();
        assert LocalDate.of(2020, 1, 30).isEqual(testDTOReturn.getBirthday());
        assert cmtReturn.isEqual(testDTOReturn.getCmt());
        List<TestChildDTO> testChildren = testDTOReturn.getChildren();
        assert 1 == testChildren.size();
        assert "child".equals(testChildren.get(0).getChildName());
    }

    @Test
    public void testPut() {
        TestDTO testDTO = new TestDTO();
        testDTO.setAge(12);
        testDTO.setName("name");
        put("/test/id", TestDTO.class, JsonUtil.toJsonString(testDTO));
    }


    @Test
    public void testDelete() {
        delete("/test/id");
    }
}
