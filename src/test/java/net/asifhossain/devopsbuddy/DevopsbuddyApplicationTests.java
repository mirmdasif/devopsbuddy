package net.asifhossain.devopsbuddy;

import net.asifhossain.devopsbuddy.service.I18nService;
import net.asifhossain.devopsbuddy.web.controllers.HelloController;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DevopsbuddyApplicationTests {

    @Autowired
    private HelloController helloController;

    @Autowired
    private I18nService i18nService;

    @Test
    public void contextLoads() {
        assertThat(helloController).isNotNull();
    }

    @Test
    public void localeTest() {
        String key = "index.main.callouts";
        String actual = i18nService.getMessage(key);
        String expected = "Bootstrap starter template";

        Assert.assertEquals(expected, actual);
    }
}
