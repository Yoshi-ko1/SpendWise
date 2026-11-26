import java.time.LocalDate;

/**
 * FixedExpense extends Expense
 * A recurring bill with a due date (e.g. rent, subscriptions).
 */
public class FixedExpense extends Expense {
    private LocalDate dueDate;
    private boolean isRecurring;

    public FixedExpense(String expenseID, double amount, LocalDate date, String description,
                         Category category, LocalDate dueDate, boolean isRecurring) {
        super(expenseID, amount, date, description, category);
        this.dueDate = dueDate;
        this.isRecurring = isRecurring;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate date) {
        this.dueDate = date;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    @Override
    public String getDetails() {
        return String.format("[Fixed] %s - PHP %.2f | Category: %s | Due: %s | %s | %s",
                getDescription(), getAmount(), getCategory(), dueDate,
                isRecurring ? "Recurring" : "One-time", getDate());
    }
}
