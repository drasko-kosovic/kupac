package kupac.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kupac.web.rest.TestUtil;

public class KorpaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Korpa.class);
        Korpa korpa1 = new Korpa();
        korpa1.setId(1L);
        Korpa korpa2 = new Korpa();
        korpa2.setId(korpa1.getId());
        assertThat(korpa1).isEqualTo(korpa2);
        korpa2.setId(2L);
        assertThat(korpa1).isNotEqualTo(korpa2);
        korpa1.setId(null);
        assertThat(korpa1).isNotEqualTo(korpa2);
    }
}
