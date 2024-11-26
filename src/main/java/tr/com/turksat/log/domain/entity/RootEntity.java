package tr.com.turksat.log.domain.entity;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.io.Serializable;

/**
 * @author scinkir
 * @since 23.05.2024
 */
@MappedSuperclass
public abstract class RootEntity extends PanacheEntityBase implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract boolean isNew();

    public abstract String getPrimaryId();

    @Transient
    public Kullanici getOlusturanKullanici() {
        return null;
        // ihtiyaç dahilinde override edilecek
    }

    public void setOlusturanKullanici(Kullanici ktvKullanici) {
        // ihtiyaç dahilinde override edilecek
    }
}
