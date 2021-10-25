package indi.jl.sp.data.mongo.document;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SP_TEST_MONGO")
public class TestDocument extends BaseDocument {

    private String key1;

    private ChildDocument childDocument;

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public ChildDocument getChildDocument() {
        return childDocument;
    }

    public void setChildDocument(ChildDocument childDocument) {
        this.childDocument = childDocument;
    }
}
