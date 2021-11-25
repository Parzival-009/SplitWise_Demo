import java.util.*;

public class Transaction {
    private User paidBy;
    private double amount;
    // private Map<Integer, Double> splitBy;
    private List<Integer> splitBy;
    private List<Double> contributions;
    private String splitType;
    private boolean valid = false;

    Transaction(User paidBy, double amount, String splitType, List<Integer> splitBy, List<Double> contributions) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.splitBy = new ArrayList<Integer>();
        this.splitBy = splitBy;
        this.contributions = new ArrayList<Double>();
        this.splitType = splitType;
        this.contributions = contributions;
    }

    Transaction(User paidBy, double amount, String splitType, List<Integer> splitBy) {
        this.paidBy = paidBy;
        this.amount = amount;
        this.splitBy = new ArrayList<Integer>();
        this.splitBy = splitBy;
        this.contributions = new ArrayList<Double>();
        this.splitType = splitType;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSplitBy(List<Integer> splitBy) {
        this.splitBy = splitBy;
    }

    public void setContributions(List<Double> contributions) {
        this.contributions = contributions;
    }

    public void setValidity(boolean validity) {
        this.valid = validity;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public User getPaidBy() {
        return this.paidBy;
    }

    public double getAmount() {
        return this.amount;
    }

    public List<Integer> getSplitBy() {
        return this.splitBy;
    }

    public List<Double> getContributions() {
        return this.contributions;
    }

    public boolean getValidity() {
        return this.valid;
    }

    public String getSplitType() {
        return this.splitType;
    }

    public boolean validateTransaction() {
        double sum = 0;
        for (int k = 0; k < contributions.size(); k++) {
            sum += contributions.get(k);
        }
        switch (this.splitType) {
        case "EQUAL":
            this.valid = true;
            for (int i = 0; i < splitBy.size(); i++) {
                contributions.add(i, Math.floor((amount / splitBy.size()) * 100) / 100);
            }
            break;
        case "EXACT":
            if (sum == amount) {
                this.valid = true;
            } else {
                this.valid = false;
            }
            break;
        case "PERCENT":
            if (sum == 100) {
                this.valid = true;
                for (int i = 0; i < contributions.size(); i++) {
                    contributions.set(i, (contributions.get(i) * amount) / 100);
                }
            } else {
                this.valid = false;
            }
            break;
        default:
            System.out.println("Invalid Split!");
            break;
        }
        return this.valid;

    }

}
