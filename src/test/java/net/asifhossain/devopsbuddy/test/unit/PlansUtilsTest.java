package net.asifhossain.devopsbuddy.test.unit;

import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.utils.PlansUtil;
import org.junit.Assert;
import org.junit.Test;

public class PlansUtilsTest {

    @Test
    public void testIsValidPlanId() {
        Assert.assertTrue(PlansUtil.isValidPlan(PlansEnum.BASIC.getId()));
        Assert.assertTrue(PlansUtil.isValidPlan(PlansEnum.PRO.getId()));
        Assert.assertFalse(PlansUtil.isValidPlan(3));
    }
}
