import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Manager {

    private Map<Integer, Map<Integer, Double>> balances;
    private List<Transaction> transactionsList;
    private Map<Integer, User> usersId;

    Manager() {
        this.transactionsList = new ArrayList<Transaction>();
        this.balances = new HashMap<Integer, Map<Integer, Double>>();
        this.usersId = new HashMap<Integer, User>();
    }

    public Map<Integer, User> getUserID() {
        return this.usersId;
    }

    public void addUser(User user) {
        this.balances.put(user.getId(), new HashMap<Integer, Double>());
        this.usersId.put(user.getId(), user);
    }

    public void showBalanceAll() {
        boolean isEmpty = true;
        for (Map.Entry<Integer, Map<Integer, Double>> allBalances : balances.entrySet()) {
            for (Map.Entry<Integer, Double> userBalance : allBalances.getValue().entrySet()) {
                if (userBalance.getValue() != 0.0) {
                    isEmpty = false;
                    if (allBalances.getKey() == userBalance.getKey())
                        continue;
                    System.out.println(usersId.get(allBalances.getKey()).getName() + " owes "
                            + usersId.get(userBalance.getKey()).getName() + " : " + userBalance.getValue());
                }
            }
        }

        if (isEmpty) {
            System.out.println("No balances");
        }

    }

    public void showBalance(Integer userId) {
        boolean isEmpty = true;
        for (Map.Entry<Integer, Double> userBalance : balances.get(userId).entrySet()) {
            if (userBalance.getValue() != 0.0) {
                isEmpty = false;
                if (userId == userBalance.getKey())
                    continue;
                System.out.println(usersId.get(userId).getName() + " owes "
                        + usersId.get(userBalance.getKey()).getName() + " : " + userBalance.getValue());
            }
        }

        if (isEmpty) {
            System.out.println("No balances");
        }
    }

    public void addTransaction(Transaction transaction, Integer id, String password) {
        if (transaction.getPaidBy().getId() == id && transaction.getPaidBy().validatePassword(password)) {
            if (transaction.validateTransaction()) {
                this.transactionsList.add(transaction);
                // Map<Integer, Double> userBalance =
                // balances.get(transaction.getPaidBy().getId());
                int payerID = transaction.getPaidBy().getId();

                for (int j = 0; j < transaction.getSplitBy().size(); j++) {
                    int contributorID = transaction.getSplitBy().get(j);
                    if (!balances.get(contributorID).containsKey(payerID)) {
                        balances.get(contributorID).put(payerID, 0.0);
                    }
                    if (contributorID == payerID)
                        continue;
                    balances.get(contributorID).replace(payerID,
                            balances.get(contributorID).get(payerID) + transaction.getContributions().get(j));
                }

                System.out.println("Transaction added!");
            } else
                System.out.println("Invalid Transaction. Please try again!");
        } else {
            System.out.println("Invalid Login!");
        }
    }

    public void pay(Integer paidBy, Integer paidTo, Double paidAmount) {
        balances.get(paidBy).replace(paidTo, balances.get(paidBy).get(paidTo) - paidAmount);
        System.out.println("Payment successful!");
    }

    public void nameChange(Integer id, String name) {
        usersId.get(id).setName(name);
    }

    public void showHistory() {

        try {
            ListIterator<Transaction> itr = transactionsList.listIterator();
            int transactionNo = 1;
            while (itr.hasNext()) {
                Transaction currentTransaction = itr.next();
                System.out.println(transactionNo + ". " + currentTransaction.getPaidBy().getName() + " paid Rupees "
                        + currentTransaction.getAmount() + ", which was splitted by:");
                for (int i = 0; i < currentTransaction.getSplitBy().size(); i++) {
                    System.out.println(" " + usersId.get(currentTransaction.getSplitBy().get(i)).getName()
                            + " contributing Rupees " + currentTransaction.getContributions().get(i));
                }
                transactionNo++;
            }
        } catch (Exception e) {
            System.out.println("Sorry there was some error.");
        }
    }

}
