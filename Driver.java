import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {

		Manager manager = new Manager();
		Scanner in = new Scanner(System.in);

		System.out.println("Welcome to SplitWise!!\nPlease add a user to continue:");

		do {
			System.out.println("Enter first your name:");
			String name = in.next();
			System.out.println("Enter id(6-digit number) and password:");
			int id = in.nextInt();
			String password = in.next();
			while (manager.getUserID().containsKey(id)) {
				System.out.println("ID is already taken. Please choose another ID");
				id = in.nextInt();
			}
			User user = new User(name, id, password);
			manager.addUser(user);
			System.out.println("New user " + user.getName() + " has been created. Your ID is " + user.getId()
					+ ".\nShare your ID with others so as to be involved in splitting.\nWould you like to add more user?(Type'NO' to quit)");
			String resp = in.next();
			if (resp.equals("NO"))
				break;
		} while (true);

		boolean toContinue = true;

		do {
			int action;
			System.out.println(
					"Please select the action to perform(Type the number associated with each action):\n1. Add Transaction\n2. Show balance(s)\n3. Show Transaction History\n4. Pay your balance\n5. User settings\n6. Quit App");
			action = in.nextInt();
			switch (action) {
			case 1:
				System.out.println("Enter the ID of user paying:");
				int id = in.nextInt();
				if (manager.getUserID().containsKey(id)) {
					System.out.println("Enter the amount to split:");
					double amount = in.nextDouble();
					System.out.println("Enter the number of people splitting:");
					int number = in.nextInt();
					System.out.println("Enter the IDs of users splitting");
					List<Integer> users = new ArrayList<Integer>();
					for (int i = 0; i < number; i++) {
						users.add(i, in.nextInt());
						if (manager.getUserID().containsKey(users.get(i)))
							continue;
						else {
							System.out.println("User not found. Please enter a valid user!");
							i--;
						}
					}
					System.out.println("Enter the type of split('EQUAL','EXACT' or 'PERCENT'):");
					String splitType = in.next();
					Transaction newTransaction;
					if (splitType.equals("EQUAL")) {
						newTransaction = new Transaction(manager.getUserID().get(id), amount, splitType, users);
						System.out.println("Please enter the ID and password of the user paying:");
						manager.addTransaction(newTransaction, in.nextInt(), in.next());
					} else {
						if (splitType.equals("EXACT") || splitType.equals("PERCENT")) {
							System.out.println("Enter the contribution of each user:");
							List<Double> contributions = new ArrayList<Double>();
							for (int i = 0; i < number; i++) {
								contributions.add(i, in.nextDouble());
							}
							newTransaction = new Transaction(manager.getUserID().get(id), amount, splitType, users,
									contributions);
							System.out.println("Please enter the ID and password of the user paying:");
							manager.addTransaction(newTransaction, in.nextInt(), in.next());
						} else {
							System.out.println("Invalid split type.");
							break;
						}

					}
				} else
					System.out.println("User not found.");

				break;

			case 2:
				System.out.println("Please select:\n1. All users\n2. Particular user");
				int no = in.nextInt();
				switch (no) {
				case 1:
					manager.showBalanceAll();
					break;

				case 2:
					System.out.println("Please enter the ID of the user to get balance:");
					int ID = in.nextInt();
					manager.showBalance(ID);
					break;

				default:
					System.out.println("Invalid input. Please try again!");
					break;
				}
				break;
			case 3:
				manager.showHistory();
				break;

			case 4:
				System.out.println("Enter your user ID:");
				Integer paidBy = in.nextInt();
				System.out.println("Enter your password:");
				String paidPass = in.next();
				if (manager.getUserID().get(paidBy).validatePassword(paidPass)) {
					System.out.println("Enter the amount:");
					Double paidAmount = in.nextDouble();
					System.out.println("Enter the ID of the reciever:");
					Integer paidTo = in.nextInt();
					manager.pay(paidBy, paidTo, paidAmount);
				} else {
					System.out.println("Invalid Login!");
				}
				break;

			case 5:
				System.out.println("Select the service: \n1. Change name\n2. Change password");
				Integer resp = in.nextInt();
				switch (resp) {
				case 1:
					System.out.println("Enter your ID:");
					Integer Id = in.nextInt();
					System.out.println("Enter your password:");
					String pass = in.next();
					if (manager.getUserID().get(Id).validatePassword(pass)) {
						System.out.println("Enter the new name you want:");
						String newName = in.next();
						String oldName = manager.getUserID().get(Id).getName();
						manager.nameChange(Id, newName);
						System.out.println("Name of " + Id + " has been successfully changed from " + oldName + " to "
								+ newName + ".");
					} else {
						System.out.println("Invalid login!");
					}
					break;
				case 2:
					System.out.println("Enter your ID:");
					Integer IdForPassChange = in.nextInt();
					System.out.println("Enter your password:");
					String oldPass = in.next();
					System.out.println("Enter the new password:");
					String newPass = in.next();
					manager.getUserID().get(IdForPassChange).setPassword(oldPass, newPass);
					break;
				default:
					System.out.println("Invalid Action");
					break;
				}
				break;

			case 6:
				System.out.println("Are you sure you want to quit?(Press 0 to confirm and any other number to cancel)");
				int confirm = in.nextInt();
				if (confirm == 0)
					toContinue = false;
				System.out.println("Goodbye!!");
				break;

			default:
				System.out.println("Invalid input please try again");
				break;
			}
		} while (toContinue);

		in.close();

	}

}
