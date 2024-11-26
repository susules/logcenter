package tr.com.turksat.log.domain.entity;

import jakarta.persistence.*;

/**
 * @author scinkir
 * @since 23.05.2024
 */
@Entity
@Table(schema = "ktv", name = "tb_kullanici")
public class Kullanici {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = -1L;

    public Kullanici() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
