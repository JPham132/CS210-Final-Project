public class Transaction {
    private double amount;
    private String category;
    private String description;
    private String date;

    public Transaction(String day, double cost, String category, String desc)
    {
        date = day;
        amount = cost;
        this.category = category;
        description = desc;
    }

    // Meant to create a object for transactions that can be added into hashmaps and linked list
    // All getters below
    public double getAmount()
    {
        return amount;
    }

    public String getDate()
    {
        return date;
    }

    public String getCategory()
    {
        return this.category;
    }

    public String getDescription()
    {
        return description;
    }
}
