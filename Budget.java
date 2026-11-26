/**
 * Budget
 * A spending limit the user sets for a month, optionally scoped to one Category.
 * A null category means the budget is an overall monthly limit.
 */
public class Budget {
    private String budgetID;
    private String month;      // e.g. "2026-11"
    private double limitAmount;
    private Category category; // null = overall budget for the month

    public Budget(String budgetID, String month, double limitAmount, Category category) {
        this.budgetID = budgetID;
        this.month = month;
        this.limitAmount = limitAmount;
        this.category = category;
    }

    public String getBudgetID() {
        return budgetID;
    }

    public String getMonth() {
        return month;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double amount) {
        this.limitAmount = amount;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Compares the given total spent against this budget's limit and
     * returns a plain-language status.
     */
    public String checkStatus(double totalSpent) {
        if (limitAmount <= 0) {
            return "No budget limit set.";
        }
        double ratio = totalSpent / limitAmount;
        if (ratio >= 1.0) {
            return String.format("OVER BUDGET by PHP %.2f", totalSpent - limitAmount);
        } else if (ratio >= 0.9) {
            return String.format("NEAR LIMIT (%.0f%% used)", ratio * 100);
        } else {
            return String.format("WITHIN BUDGET (%.0f%% used)", ratio * 100);
        }
    }

    @Override
    public String toString() {
        String scope = (category == null) ? "Overall" : category.getCategoryName();
        return String.format("%s budget for %s: PHP %.2f", scope, month, limitAmount);
    }
}
