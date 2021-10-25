package indi.jl.sp.data.jpa.domain;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Table(name = "jpa_test_t", indexes = {@Index(name = "uk_name", columnList = "name", unique = true), @Index(name = "index_name", columnList = "name")})
@Entity
public class JpaTestT extends BaseDo {

    private String name;

    public String testName;

    public String getName() {
        return name;
    }

    public JpaTestT setName(String name) {
        this.name = name;
        return this;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
