package kupac.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link kupac.domain.Viewkorpa} entity. This class is used
 * in {@link kupac.web.rest.ViewkorpaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /viewkorpas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ViewkorpaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter artikal;

    private DoubleFilter cijena;

    private DoubleFilter ukupno;

    public ViewkorpaCriteria() {
    }

    public ViewkorpaCriteria(ViewkorpaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.artikal = other.artikal == null ? null : other.artikal.copy();
        this.cijena = other.cijena == null ? null : other.cijena.copy();
        this.ukupno = other.ukupno == null ? null : other.ukupno.copy();
    }

    @Override
    public ViewkorpaCriteria copy() {
        return new ViewkorpaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getArtikal() {
        return artikal;
    }

    public void setArtikal(StringFilter artikal) {
        this.artikal = artikal;
    }

    public DoubleFilter getCijena() {
        return cijena;
    }

    public void setCijena(DoubleFilter cijena) {
        this.cijena = cijena;
    }

    public DoubleFilter getUkupno() {
        return ukupno;
    }

    public void setUkupno(DoubleFilter ukupno) {
        this.ukupno = ukupno;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViewkorpaCriteria that = (ViewkorpaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(artikal, that.artikal) &&
            Objects.equals(cijena, that.cijena) &&
            Objects.equals(ukupno, that.ukupno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        artikal,
        cijena,
        ukupno
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewkorpaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (artikal != null ? "artikal=" + artikal + ", " : "") +
                (cijena != null ? "cijena=" + cijena + ", " : "") +
                (ukupno != null ? "ukupno=" + ukupno + ", " : "") +
            "}";
    }

}
