package ccmello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import ccmello.home.HomeController;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = HomeController.class)
class SmokeTest extends IntegrationTestBaseWithoutDataSource {
    @Autowired
    private HomeController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
