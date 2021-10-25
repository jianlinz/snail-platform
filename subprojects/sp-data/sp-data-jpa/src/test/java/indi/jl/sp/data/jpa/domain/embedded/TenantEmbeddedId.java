package indi.jl.sp.data.jpa.domain.embedded;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TenantEmbeddedId implements Serializable {

    private Long id;

    private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
