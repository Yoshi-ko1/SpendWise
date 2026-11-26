import java.time.LocalDate;

/**
 * VariableExpense extends Expense
 * An irregular expense that changes day to day (e.g. food, fare).
 */
public class VariableExpense extends Expense {
    private boolean isEssential;

    public VariableExpense(String expenseID, double amount, LocalDate date, String description,
                            Category category, boolean isEssential) {
        super(expenseID, amount, date, description, category);
        this.isEssential = isEssential;
    }

    public boolean isEssential() {
        return isEssential;
    }

    public void setEssential(boolean value) {
        this.isEssential = value;
    }

    @Override
    public String getDetails() {
        return String.format("[Variable] %s - PHP %.2f | Category: %s | %s | %s",
                getDescription(), getAmount(), getCategory(),
                isEssential ? "Essential" : "Non-essential", getDate());
    }
}
