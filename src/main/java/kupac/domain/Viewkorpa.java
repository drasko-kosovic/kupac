package kupac.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Viewkorpa.
 */
@Entity
@Table(name = "view_korpa")
public class Viewkorpa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "artikal")
    private String artikal;

    @Column(name = "cijena")
    private Double cijena;

    @Column(name = "ukupno")
    private Double ukupno;

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

    public Viewkorpa artikal(String artikal) {
        this.artikal = artikal;
        return this;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public Double getCijena() {
        return cijena;
    }

    public Viewkorpa cijena(Double cijena) {
        this.cijena = cijena;
        return this;
    }

    public void setCijena(Double cijena) {
        this.cijena = cijena;
    }

    public Double getUkupno() {
        return ukupno;
    }

    public Viewkorpa ukupno(Double ukupno) {
        this.ukupno = ukupno;
        return this;
    }

    public void setUkupno(Double ukupno) {
        this.ukupno = ukupno;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Viewkorpa)) {
            return false;
        }
        return id != null && id.equals(((Viewkorpa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Viewkorpa{" +
            "id=" + getId() +
            ", artikal='" + getArtikal() + "'" +
            ", cijena=" + getCijena() +
            ", ukupno=" + getUkupno() +
            "}";
    }
}
