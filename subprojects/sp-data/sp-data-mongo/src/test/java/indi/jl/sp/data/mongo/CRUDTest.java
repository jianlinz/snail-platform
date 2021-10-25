package indi.jl.sp.data.mongo;

import indi.jl.sp.data.mongo.document.ChildDocument;
import indi.jl.sp.data.mongo.document.TestDocument;
import indi.jl.sp.test.common.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;

public class CRUDTest extends BaseSpringTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void save() {
        TestDocument testDocument = new TestDocument();
        testDocument.setCreateTime(LocalDateTime.now());
        testDocument.setKey1("11");
        ChildDocument childDocument = new ChildDocument();
        childDocument.setA("ad");
        testDocument.setChildDocument(childDocument);
        mongoTemplate.save(testDocument);
    }
}