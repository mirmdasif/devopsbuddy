package net.asifhossain.devopsbuddy.web.controllers;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Role;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.User;
import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.UserRole;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import net.asifhossain.devopsbuddy.enums.RolesEnum;
import net.asifhossain.devopsbuddy.service.I18nService;
import net.asifhossain.devopsbuddy.service.PlanService;
import net.asifhossain.devopsbuddy.service.UserService;
import net.asifhossain.devopsbuddy.utils.PlansUtil;
import net.asifhossain.devopsbuddy.utils.UserUtils;
import net.asifhossain.devopsbuddy.web.domain.frontend.ProAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

/**
 * @author asif.hossain
 * @since 5/21/18
 */
@Controller
public class SignupController {

    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    public static final String SIGNUP_URL_MAPPING = "/signup";

    private static final String SIGNUP_VIEW_NAME= "registration/signup";

    public static final String PAYLOAD_MODEL_KEY = "payload";

    public static final String DUPLICATED_USERNAME_KEY = "duplicatedUsername";

    public static final String DUPLICATED_EMAIL_KEY = "duplicatedEmail";

    public static final String SIGNED_UP_MESSAGE_KEY = "signedUp";

    public static final String ERROR_MESSAGE_KEY = "message";

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @Autowired
    private I18nService i18nService;


    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signupGet(@RequestParam("planId") int planId, ModelMap model) {
        if (!PlansUtil.isValidPlan(planId)) {
            throw new IllegalArgumentException("Plan id not found");
        }

        model.addAttribute(PAYLOAD_MODEL_KEY, new ProAccountPayload());

        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.POST)
    public String signupPost(@Valid @ModelAttribute(PAYLOAD_MODEL_KEY) ProAccountPayload account, BindingResult result,
                             @RequestParam("planId") int planId, ModelMap model) {

        if(planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()) {
            result.reject("Plan Id Does not exists");
        } else {
            model.addAttribute("planId", planId);
        }

        checkForDuplicates(account, result);

        if (result.hasErrors()) {
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, false);
        } else {
            User user = UserUtils.formUserPayloadToDomainUser(account);
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, new Role(RolesEnum.getRoleForPlan(PlansEnum.getPlanById(planId)))));

            LOG.info("Creating user = {} with plan = {}", user, planId);
            userService.createUser(user, PlansEnum.getPlanById(planId), userRoles);
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, true);
        }

        return SIGNUP_VIEW_NAME;
    }

    private void checkForDuplicates(ProAccountPayload account, BindingResult result) {
        if(nonNull(userService.findUserByUsername(account.getUsername()))) {
            result.rejectValue("username", "signup.form.error.username.already.exists");
        }

        if (nonNull(userService.findUserByEmail(account.getEmail()))) {
            result.rejectValue("email", "signup.form.error.email.already.exists");
        }
    }
}
