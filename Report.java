import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Report
 * A summary of the user's spending for a chosen period (month).
 */
public class Report {
    private String reportID;
    private String period;
    private double totalSpent;
    private double remainingBudget;
    private Map<Category, Double> categoryBreakdown = new LinkedHashMap<>();
    private String budgetStatus;

    public Report(String reportID, String period, double totalSpent, double remainingBudget) {
        this.reportID = reportID;
        this.period = period;
        this.totalSpent = totalSpent;
        this.remainingBudget = remainingBudget;
    }

    public String getReportID() {
        return reportID;
    }

    public String getPeriod() {
        return period;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setCategoryBreakdown(Map<Category, Double> breakdown) {
        this.categoryBreakdown = breakdown;
    }

    public Map<Category, Double> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public void setBudgetStatus(String status) {
        this.budgetStatus = status;
    }

    /**
     * Builds the printable summary shown on the Report / Summary screen (Frame 5).
     */
    public String generateSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SpendWise Report: ").append(period).append(" =====\n");
        sb.append(String.format("Total Spent:      PHP %.2f%n", totalSpent));
        sb.append(String.format("Remaining Budget: PHP %.2f%n", remainingBudget));
        if (budgetStatus != null) {
            sb.append("Budget Status:    ").append(budgetStatus).append("\n");
        }
        sb.append("\nSpending per category:\n");
        if (categoryBreakdown.isEmpty()) {
            sb.append("  (no expenses recorded this period)\n");
        } else {
            for (Map.Entry<Category, Double> entry : categoryBreakdown.entrySet()) {
                sb.append(String.format("  - %-15s PHP %.2f%n", entry.getKey().getCategoryName(), entry.getValue()));
            }
        }
        return sb.toString();
    }
}
