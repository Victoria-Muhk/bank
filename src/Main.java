public class Main {
    public static void main(String[] args) {
        System.out.println("проверка Balance");

        Balance rubBalance = new Balance(10000, Currency.RUB);
        Balance usdBalance = new Balance(5000, Currency.USD);
        System.out.println("баланс в рублях: " + rubBalance);
        System.out.println("баланс в долларах: " + usdBalance);

        Balance newRubBalance = rubBalance.add(5000);
        System.out.println("после добавления 5000 коп.: " + newRubBalance);

        Balance afterSubtract = newRubBalance.subtract(3000);
        System.out.println("после вычитания 3000 коп.: " + afterSubtract);

        Balance convertedBalance = rubBalance.convertTo(Currency.USD);
        System.out.println("конвертация 10000 коп. RUB в USD: " + convertedBalance);

        try {
            rubBalance.subtract(20000);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("\n проверка BankAccount");
        BankAccount account1 = new BankAccount(
                new Balance(50000, Currency.RUB),
                "88005553535",
                "Виктория",
                30000
        );
        BankAccount account2 = new BankAccount(
                new Balance(20000, Currency.RUB),
                "676767676767",
                "Александр",
                25000
        );
        System.out.println("счет 1: " + account1);
        System.out.println("счет 2: " + account2);
        account1.transferTo(account2, 10000);
        System.out.println("после перевода:");
        System.out.println("счет 1: " + account1);
        System.out.println("счет 2: " + account2);
        try {
            account1.transferTo(account2, 25000);
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        try {
            account1.transferTo(null, 5000);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println("счет 1: " + account1);
        System.out.println("счет 2: " + account2);
    }
}