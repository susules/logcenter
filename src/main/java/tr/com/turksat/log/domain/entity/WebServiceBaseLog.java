package tr.com.turksat.log.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkiverse.hibernate.types.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


/**
 * @author scinkir
 * @since 04.02.2020
 */
@Entity
@Table(schema = "ttvae", name = "tb_web_service_sebeke_log",
        indexes = {
                @Index(name = "idx1_tb_web_service_sebeke_log", columnList = "metot_adi"),
                @Index(name = "idx2_tb_web_service_sebeke_log", columnList = "olusturan_kullanici_id"),
                @Index(name = "idx3_tb_web_service_sebeke_log", columnList = "olusturulma_tarihi"),
                @Index(name = "idx4_tb_web_service_sebeke_log", columnList = "dtype"),
                @Index(name = "idx5_tb_web_service_sebeke_log", columnList = "transaction_id")
        }
)
@Getter
@Setter
public  class WebServiceBaseLog extends BaseEntity {

    @Column(name = "dtype", nullable = false, updatable = false,length = 100)
    protected String dtype;
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "sure")
    private int sure;

    @Column(name = "metot_adi",columnDefinition = "text", nullable = false)
    private String metotAdi;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "items", columnDefinition = "jsonb", insertable = false, updatable = false)
    private String items;
    @JsonProperty("giris_parametre")
    @Column(name = "giris_parametre", columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String giris;
    @JsonProperty("cikis_parametre")
    @Column(name = "cikis_parametre",columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private String cikis;


   // @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "olusturan_kullanici_id", referencedColumnName = "id") //nullable
    //private Kullanici olusturanKullanici;
    //
   @Column(name = "olusturan_kullanici_id")
    private Long olusturanKullaniciId;

    @Column(name = "aciklama",columnDefinition = "text")
    private String aciklama;

    @Column(name = "hizmet_id")
    private String hizmetId;

    public WebServiceBaseLog() {
    }

    public WebServiceBaseLog(String metotAdi, String giris, String cikis, Long olusturanKullaniciId) {
        this.metotAdi = metotAdi;
        this.giris = giris;
        this.cikis = cikis;
        this.olusturanKullaniciId = olusturanKullaniciId;
    }

}
