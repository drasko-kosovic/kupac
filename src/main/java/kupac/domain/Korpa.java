package kupac.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Korpa.
 */
@Entity
@Table(name = "korpa")
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "artikal", nullable = false)
    private String artikal;

    @NotNull
    @Column(name = "cijena", nullable = false)
    private Integer cijena;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtikal() {
        return artikal;
    }

    public Korpa artikal(String artikal) {
        this.artikal = artikal;
        return this;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public Integer getCijena() {
        return cijena;
    }

    public Korpa cijena(Integer cijena) {
        this.cijena = cijena;
        return this;
    }

    public void setCijena(Integer cijena) {
        this.cijena = cijena;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Korpa)) {
            return false;
        }
        return id != null && id.equals(((Korpa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Korpa{" +
            "id=" + getId() +
            ", artikal='" + getArtikal() + "'" +
            ", cijena=" + getCijena() +
            "}";
    }
}
