package indi.jl.sp.core.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class JsonUtilSpec {

    public static class JsonTestObj {

        private String name;

        private String testNull;

        private LocalDateTime dateTime;

        private LocalDate date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTestNull() {
            return testNull;
        }

        public void setTestNull(String testNull) {
            this.testNull = testNull;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }

    @Test
    public void toJsonString() {
        JsonTestObj jsonTestObj = new JsonTestObj();
        jsonTestObj.setName("test");
        jsonTestObj.setDateTime(LocalDateTime.of(2020, 12, 1, 18, 20, 01, 9));
        jsonTestObj.setDate(LocalDate.of(2020, 1, 30));
        String jsonStr = JsonUtil.toJsonString(jsonTestObj);
        assert "{\"name\":\"test\",\"testNull\":null,\"dateTime\":\"2020-12-01 18:20:01\",\"date\":\"2020-01-30\"}".equals(jsonStr);
    }

    @Test
    public void parse() {
        String jsonStr = "{\"name\":\"test\",\"testNull\":null,\"dateTime\":\"2020-12-01 18:20:01\",\"date\":\"2020-01-30\"}";
        JsonTestObj jsonTestObj = JsonUtil.parse(jsonStr, JsonTestObj.class);
        assert "test".equals(jsonTestObj.getName());
        assert null == jsonTestObj.getTestNull();
        assert LocalDateTime.of(2020, 12, 1, 18, 20, 01).isEqual(jsonTestObj.getDateTime());
        assert LocalDate.of(2020, 1, 30).isEqual(jsonTestObj.getDate());
    }

    @Test
    public void convert() {
        ObjectNode objectNode = JsonUtil.getObjectMapper().createObjectNode();
        objectNode.put("name", "test");
        JsonTestObj jsonTestObj = JsonUtil.convert(objectNode, JsonTestObj.class);
        assert "test".equals(jsonTestObj.getName());
        assert null == jsonTestObj.getTestNull();
    }
}
