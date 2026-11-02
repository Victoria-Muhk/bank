import java.util.Objects;

public final class Balance {
    private final long amountInKopecks;
    private final Currency currency;

    public Balance(long amountInKopecks, Currency currency) {
        if (currency == null) {
            throw new IllegalArgumentException("Валюта не может быть null");
        }
        if (amountInKopecks < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной: " + amountInKopecks);
        }
        this.amountInKopecks = amountInKopecks;
        this.currency = currency;
    }

    public Balance add(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Нельзя прибавить отрицательную сумму: " + amount);
        }
        return new Balance(this.amountInKopecks + amount, this.currency);
    }

    public Balance subtract(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Нельзя вычесть отрицательную сумму: " + amount);
        }
        if (amount > this.amountInKopecks) {
            throw new IllegalStateException(
                    "Недостаточно средств: " + amount + " > " + this.amountInKopecks
            );
        }
        return new Balance(this.amountInKopecks - amount, this.currency);
    }

    public Balance convertTo(Currency targetCurrency) {
        if (targetCurrency == null) {
            throw new IllegalArgumentException("Целевая валюта не может быть null");
        }
        if (targetCurrency == this.currency) {
            return this;
        }
        // Конвертируем: копейки исходной валюты -> копейки рублей -> копейки целевой валюты
        long amountInRublesKopecks = this.amountInKopecks * this.currency.getPriceInRublesKopecks()
                / 100; // делим на 100, т.к. RUB = 100 копеек
        long targetAmount = amountInRublesKopecks * 100 / targetCurrency.getPriceInRublesKopecks();
        return new Balance(targetAmount, targetCurrency);
    }

    public long getAmountInKopecks() {
        return amountInKopecks;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return currency.getSymbol() + " " + amountInKopecks / 100.0 + " " + currency.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return amountInKopecks == balance.amountInKopecks && currency == balance.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInKopecks, currency);
    }
}