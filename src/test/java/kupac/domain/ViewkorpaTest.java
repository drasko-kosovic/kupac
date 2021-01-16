package kupac.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kupac.web.rest.TestUtil;

public class ViewkorpaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Viewkorpa.class);
        Viewkorpa viewkorpa1 = new Viewkorpa();
        viewkorpa1.setId(1L);
        Viewkorpa viewkorpa2 = new Viewkorpa();
        viewkorpa2.setId(viewkorpa1.getId());
        assertThat(viewkorpa1).isEqualTo(viewkorpa2);
        viewkorpa2.setId(2L);
        assertThat(viewkorpa1).isNotEqualTo(viewkorpa2);
        viewkorpa1.setId(null);
        assertThat(viewkorpa1).isNotEqualTo(viewkorpa2);
    }
}
