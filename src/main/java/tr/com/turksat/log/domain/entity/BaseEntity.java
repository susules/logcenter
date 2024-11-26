package tr.com.turksat.log.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by scinkir on 25.11.2024.
 */
@MappedSuperclass
public abstract class BaseEntity extends RootEntity {

    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(generator = "id_gen")
    @GenericGenerator(name = "id_gen", strategy = "uuid2")
    @Column(length = 64, unique = true, nullable = false)
    private String id;

    @Column(name = "olusturulma_tarihi",nullable = false,updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss.SSS", timezone = "GMT+3")
    private LocalDateTime olusturulmaTarihi = LocalDateTime.now();

    @Version
    @Column(name = "versiyon", columnDefinition = "integer DEFAULT 0", nullable = false)
    protected int versiyon = 0;

    @Column(name = "son_guncelleme_tarihi")
    private LocalDateTime sonGuncellemeTarihi;

    @Column(name = "son_guncelleyen_kullanici")
    private String sonGuncelleyenKullanici;

    @Transient
    private String tempId;

    public int getVersiyon() {
        return versiyon;
    }

    public void setVersiyon(int versiyon) {
        this.versiyon = versiyon;
    }

    public void setOlusturulmaTarihi(LocalDateTime olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public LocalDateTime getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }
    public LocalDateTime getSonGuncellemeTarihi() {
        return sonGuncellemeTarihi;
    }

    public void setSonGuncellemeTarihi(LocalDateTime sonGuncellemeTarihi) {
        this.sonGuncellemeTarihi = sonGuncellemeTarihi;
    }

    public String getSonGuncelleyenKullanici() {
        return sonGuncelleyenKullanici;
    }

    public void setSonGuncelleyenKullanici(String sonGuncelleyenKullanici) {
        this.sonGuncelleyenKullanici = sonGuncelleyenKullanici;

    }

    public String getPrimaryId() {
        return getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }



    public String getTempId() {
        if (tempId == null) {
            if (!isNew()) {
                setTempId(id);
            } else {
                String uuid = UUID.randomUUID().toString();
                setTempId(uuid);
            }
        }
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    @Override
    public int hashCode() {
        return (getId() != null ? getId().hashCode() : super.hashCode()) + (getOlusturulmaTarihi() != null ? getOlusturulmaTarihi().hashCode() : getClass().hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj == this) return true;

        if (!(obj instanceof BaseEntity)) return false;
        
        BaseEntity be = (BaseEntity) obj;
        return this.hashCode() == be.hashCode();
    }

    public boolean isNew() {
        return id == null || id.equals("");
    }

    @Override
    public String toString() {
        return this.id;
    }
}
