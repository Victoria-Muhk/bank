public class BankAccount {
    private Balance balance;
    private final String accountNumber;
    private final String ownerName;
    private final long dailyWithdrawalLimit;
    private long totalWithdrawnToday;

    public BankAccount(Balance balance, String accountNumber, String ownerName, long dailyWithdrawalLimit) {
        if (balance == null) {
            throw new IllegalArgumentException("баланс не может быть null");
        }
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("номер счёта не может быть пустым");
        }
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("имя владельца не может быть пустым");
        }
        if (dailyWithdrawalLimit < 0) {
            throw new IllegalArgumentException("лимит не может быть отрицательным: " + dailyWithdrawalLimit);
        }
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.dailyWithdrawalLimit = dailyWithdrawalLimit;
        this.totalWithdrawnToday = 0;
    }

    public void transferTo(BankAccount target, long amount) {
        if (target == null) {
            throw new IllegalArgumentException("целевой счет не может быть null");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("сумма перевода не может быть отрицательной: " + amount);
        }
        if (amount == 0) {
            System.out.println("перевод на 0 не имеет смысла");
            return;
        }
        if (this.balance.getCurrency() != target.balance.getCurrency()) {
            throw new IllegalStateException("нельзя переводить между разными валютами");
        }
        if (this.totalWithdrawnToday + amount > this.dailyWithdrawalLimit) {
            throw new IllegalStateException(
                    "превышен дневной лимит: " + amount + " + " + this.totalWithdrawnToday
                            + " > " + this.dailyWithdrawalLimit
            );
        }
        this.balance = this.balance.subtract(amount);
        target.balance = target.balance.add(amount);
        this.totalWithdrawnToday += amount;
        System.out.println("перевод " + amount + " коп. от " + this.ownerName
                + " к " + target.ownerName + " выполнен");
    }

    public Balance getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getDailyWithdrawalLimit() {
        return dailyWithdrawalLimit;
    }

    public long getTotalWithdrawnToday() {
        return totalWithdrawnToday;
    }

    @Override
    public String toString() {
        return "счет " + accountNumber + " (" + ownerName + "): " + balance
                + ", лимит: " + dailyWithdrawalLimit + " коп., потрачено сегодня: " + totalWithdrawnToday + " коп.";
    }
}