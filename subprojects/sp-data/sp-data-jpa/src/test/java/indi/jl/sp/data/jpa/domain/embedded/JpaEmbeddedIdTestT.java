package indi.jl.sp.data.jpa.domain.embedded;

//@Table(name = "jpa_embedded_id_test_t")
//@Entity
public class JpaEmbeddedIdTestT extends BaseTenantDo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
