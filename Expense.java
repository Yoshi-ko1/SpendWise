import java.time.LocalDate;

/**
 * Expense «abstract»
 * Money that the user spent. Parent class of FixedExpense and VariableExpense.
 * Stores the shared data and declares the abstract getDetails() method that
 * every child class must implement — this is where polymorphism applies.
 */
public abstract class Expense {
    private String expenseID;
    private double amount;
    private LocalDate date;
    private String description;
    private Category category;

    public Expense(String expenseID, double amount, LocalDate date, String description, Category category) {
        this.expenseID = expenseID;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Abstract method overridden by FixedExpense and VariableExpense.
     * Calling getDetails() on an Expense reference runs different logic
     * depending on the object's actual subclass (polymorphism).
     */
    public abstract String getDetails();
}
