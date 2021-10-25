package indi.jl.sp.data.jpa.domain;

import indi.jl.sp.data.jpa.listener.DefaultDoLifeCycleListener;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({DefaultDoLifeCycleListener.class})
public class AbstractDo implements Serializable {

    @Column(updatable = false, nullable = false)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @Column(updatable = false, nullable = false)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @Column(updatable = false, nullable = false)
    @ApiModelProperty("创建人账号")
    private String createUserName;

    @Column(updatable = false, nullable = false)
    @ApiModelProperty("更新人账号")
    private String updateUserName;

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

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }
}
