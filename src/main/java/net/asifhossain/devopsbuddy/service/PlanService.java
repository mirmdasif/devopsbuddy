package net.asifhossain.devopsbuddy.service;

import net.asifhossain.devopsbuddy.backend.persistence.domain.backend.Plan;
import net.asifhossain.devopsbuddy.backend.persistence.repository.PlanRepository;
import net.asifhossain.devopsbuddy.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mirmdasif
 * since 6/26/2018
 */
@Service
@Transactional(readOnly = true)
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    /**
     * Find a plan for the given id or null
     * @param planId The plan id
     * @return The plan associated with the id or null
     */
    public Plan findPlanById(int planId) {
        return planRepository.findOne(planId);
    }

    /**
     * Creates a basic or pro plan
     * @param planId plan id
     * @return The created plan
     * @throws IllegalArgumentException if plan id is not 1 or 2
     */
    @Transactional
    public Plan createPlan(int planId) {
        return planRepository.save(new Plan(PlansEnum.getPlanById(planId)));
    }
}
