import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main
 * Demo driver that creates objects from each class and calls their
 * methods, using values typed in by the user at runtime (via Scanner)
 * instead of hardcoded values.
 *
 * This class is not part of the UML class diagram; it only shows the
 * other classes — Category, Expense, FixedExpense, VariableExpense,
 * Budget, User, Report — being used.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final List<Category> categories = new ArrayList<>();
    private static int expenseCounter = 1;

    public static void main(String[] args) {
        seedCategories();

        // 1. Register
        System.out.println("=== Register ===");
        System.out.print("Full name: ");
        String name = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = new User("U-" + Math.abs(email.hashCode() % 10000), name, email, password);
        boolean registered = user.register();
        System.out.println("Registered: " + registered);

        // 2. Login
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String loginEmail = sc.nextLine().trim();
        System.out.print("Password: ");
        String loginPassword = sc.nextLine().trim();

        User loggedIn = User.authenticate(loginEmail, loginPassword);
        if (loggedIn == null) {
            System.out.println("Invalid email or password. Exiting.");
            return;
        }
        System.out.println("Welcome, " + loggedIn.getName() + "!");

        // 3. Add expenses
        System.out.println("\n=== Add Expenses ===");
        System.out.print("How many expenses do you want to add? ");
        int count = readInt();
        for (int i = 1; i <= count; i++) {
            System.out.println("\n-- Expense " + i + " --");
            Expense expense = readExpense();
            loggedIn.addExpense(expense);
            System.out.println("Saved: " + expense.getDetails());
        }

        // 4. Set a budget
        System.out.println("\n=== Set Budget ===");
        System.out.print("Month (yyyy-mm, blank = current month): ");
        String month = sc.nextLine().trim();
        if (month.isEmpty()) {
            month = YearMonth.now().toString();
        }
        System.out.print("Overall monthly budget (PHP): ");
        double limit = readDouble();
        Budget budget = new Budget("BUD-" + month, month, limit, null);
        loggedIn.setBudget(budget);
        System.out.println("Saved: " + budget);

        // 5. View all expenses.
        //    Polymorphism: getDetails() is called the same way on every
        //    Expense, but each subclass (FixedExpense / VariableExpense)
        //    runs its own version of the method.
        System.out.println("\n=== Expense List ===");
        for (Expense e : loggedIn.getExpenses()) {
            System.out.println(e.getDetails());
        }

        // 6. Generate and print the report
        System.out.println("\n=== Report ===");
        Report report = loggedIn.generateReport();
        System.out.println(report.generateSummary());
    }

    private static void seedCategories() {
        categories.add(new Category("CAT-1", "Food"));
        categories.add(new Category("CAT-2", "Transportation"));
        categories.add(new Category("CAT-3", "School Needs"));
        categories.add(new Category("CAT-4", "Load"));
        categories.add(new Category("CAT-5", "Bills"));
        categories.add(new Category("CAT-6", "Others"));
    }

    private static Expense readExpense() {
        Category category = chooseCategory();

        System.out.print("Fixed or Variable? (F/V): ");
        String type = sc.nextLine().trim().toUpperCase();

        System.out.print("Amount (PHP): ");
        double amount = readDouble();

        System.out.print("Description: ");
        String description = sc.nextLine().trim();

        System.out.print("Date (yyyy-mm-dd, blank = today): ");
        LocalDate date = readDate(LocalDate.now());

        String expenseID = "EXP-" + (expenseCounter++);

        if (type.startsWith("F")) {
            System.out.print("Due date (yyyy-mm-dd, blank = same as date): ");
            LocalDate dueDate = readDate(date);
            System.out.print("Is this recurring? (y/n): ");
            boolean recurring = sc.nextLine().trim().equalsIgnoreCase("y");
            return new FixedExpense(expenseID, amount, date, description, category, dueDate, recurring);
        } else {
            System.out.print("Is this essential? (y/n): ");
            boolean essential = sc.nextLine().trim().equalsIgnoreCase("y");
            return new VariableExpense(expenseID, amount, date, description, category, essential);
        }
    }

    private static Category chooseCategory() {
        System.out.println("Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + categories.get(i).getCategoryName());
        }
        System.out.print("Choose a category number: ");
        int idx = readInt();
        while (idx < 1 || idx > categories.size()) {
            System.out.print("Invalid. Choose a category number: ");
            idx = readInt();
        }
        return categories.get(idx - 1);
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount: ");
            }
        }
    }

    private static LocalDate readDate(LocalDate fallback) {
        String input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return fallback;
        }
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Could not read that date, using " + fallback + " instead.");
            return fallback;
        }
    }
}
