
import java.util.*;
public class Goal {
    private String name;
    private double target;
    private double currMoney;

    public Goal(String pName, double target, double currMoney) {
        name = pName;
        this.target = target;
        this.currMoney = currMoney;
    }

    public double calculateProgress() {
        if (target == 0) {
            return 0;
        } else {
            double percent = currMoney / target;
            return (percent * 100); // returns the percentage towards the goal
        }
    }

    public void updateProgress(double amount) {
        this.currMoney = this.currMoney + amount;
    }

    public String getName() {
        return name;
    }
}
