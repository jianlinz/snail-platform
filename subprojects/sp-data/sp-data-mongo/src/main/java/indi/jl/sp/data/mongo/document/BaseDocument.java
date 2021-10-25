package indi.jl.sp.data.mongo.document;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaseDocument implements Serializable {

    /**
     * 主键  对应mongodb的objectId
     */
    @Id
    private String id;

    @ApiModelProperty("创建时间")
    @Field(value = "CT",targetType = FieldType.TIMESTAMP)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @Field("UT")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人Id")
    @Field("CU")
    private Long createUserId;

    @ApiModelProperty("更新人id")
    @Field("UU")
    private Long updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}
