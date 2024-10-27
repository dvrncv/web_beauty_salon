package beauty_salon.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private Long id;

    @Id
    @Column(insertable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
