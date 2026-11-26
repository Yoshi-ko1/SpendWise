import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User
 * The student or young adult who owns the account and all of their
 * financial records (expenses and budgets).
 */
public class User {
    // Simple in-memory "database" of registered users, keyed by email.
    private static final Map<String, User> directory = new LinkedHashMap<>();

    private String userID;
    private String name;
    private String email;
    private String password;
    private List<Expense> expenses = new ArrayList<>();
    private List<Budget> budgets = new ArrayList<>();

    public User(String userID, String name, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Adds this user to the account directory. Returns false if the
     * email is already registered (Frame 1 - Register).
     */
    public boolean register() {
        if (directory.containsKey(email)) {
            return false;
        }
        directory.put(email, this);
        return true;
    }

    /**
     * Checks whether the given credentials match this user record.
     */
    public boolean login(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    /**
     * App-level helper: looks up a registered user by email/password
     * (Frame 1 - Login). Not part of the UML attribute list, but needed
     * to actually authenticate someone before a User object is on hand.
     */
    public static User authenticate(String email, String password) {
        User candidate = directory.get(email);
        if (candidate != null && candidate.login(email, password)) {
            return candidate;
        }
        return null;
    }

    public void addExpense(Expense e) {
        expenses.add(e);
    }

    public void removeExpense(String expenseID) {
        expenses.removeIf(e -> e.getExpenseID().equals(expenseID));
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setBudget(Budget b) {
        // Replace any existing budget for the same month + same category scope.
        budgets.removeIf(existing ->
                existing.getMonth().equals(b.getMonth()) &&
                        ((existing.getCategory() == null && b.getCategory() == null) ||
                                (existing.getCategory() != null && existing.getCategory().equals(b.getCategory()))));
        budgets.add(b);
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    /**
     * Builds a Report for the current month: total spent, remaining
     * budget against the overall monthly limit (if set), and a
     * category-by-category breakdown (Frame 5 - Report/Summary).
     */
    public Report generateReport() {
        String period = YearMonth.now().toString(); // e.g. 2026-11

        double totalSpent = 0.0;
        Map<Category, Double> breakdown = new LinkedHashMap<>();
        for (Expense e : expenses) {
            if (YearMonth.from(e.getDate()).toString().equals(period)) {
                totalSpent += e.getAmount();
                breakdown.merge(e.getCategory(), e.getAmount(), Double::sum);
            }
        }

        Budget overallBudget = null;
        for (Budget b : budgets) {
            if (b.getMonth().equals(period) && b.getCategory() == null) {
                overallBudget = b;
                break;
            }
        }

        double remaining = (overallBudget != null) ? overallBudget.getLimitAmount() - totalSpent : 0.0;

        String reportID = "RPT-" + userID + "-" + period;
        Report report = new Report(reportID, period, totalSpent, remaining);
        report.setCategoryBreakdown(breakdown);
        if (overallBudget != null) {
            report.setBudgetStatus(overallBudget.checkStatus(totalSpent));
        } else {
            report.setBudgetStatus("No overall budget set for " + period + ".");
        }
        return report;
    }
}
