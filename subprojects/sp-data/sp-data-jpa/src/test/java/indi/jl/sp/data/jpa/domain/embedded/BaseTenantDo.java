package indi.jl.sp.data.jpa.domain.embedded;

import indi.jl.sp.data.jpa.domain.AbstractDo;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

public class BaseTenantDo extends AbstractDo {

    @Id
    @EmbeddedId
    private TenantEmbeddedId tenantEmbeddedId;

    public TenantEmbeddedId getTenantEmbeddedId() {
        return tenantEmbeddedId;
    }

    public void setTenantEmbeddedId(TenantEmbeddedId tenantEmbeddedId) {
        this.tenantEmbeddedId = tenantEmbeddedId;
    }
}
