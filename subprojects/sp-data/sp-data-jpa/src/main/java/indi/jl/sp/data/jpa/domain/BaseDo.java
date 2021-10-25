package indi.jl.sp.data.jpa.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * DO基类
 */
@MappedSuperclass
public class BaseDo extends AbstractDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
