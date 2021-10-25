package indi.jl.sp.test.common.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDTO implements Serializable {

    private String name;

    private Integer age;

    private LocalDate birthday;

    private LocalDateTime cmt;

    private List<TestChildDTO> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<TestChildDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TestChildDTO> children) {
        this.children = children;
    }

    public void addChild(TestChildDTO testChildDTO) {
        if (null == this.children) {
            this.children = new ArrayList<>();
        }
        this.children.add(testChildDTO);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getCmt() {
        return cmt;
    }

    public void setCmt(LocalDateTime cmt) {
        this.cmt = cmt;
    }
}
