package net.asifhossain.devopsbuddy.enums;

public enum PlansEnum {
    BASIC(1, "Basic"),
    PRO(2, "Pro");

    int id;
    String planName;

    PlansEnum(int id, String planName) {
        this.id = id;
        this.planName = planName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public static PlansEnum getPlanById(int planId) {

        if (planId == 1) {
            return BASIC;
        } else if (planId == 2) {
            return PRO;
        }

        throw new IllegalArgumentException();
    }
}
