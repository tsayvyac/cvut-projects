package cz.cvut.fel.nss.budgetmanager.Dispatcher.model;

public enum Currency {
    EUR("EUR"),
    CZK("CZK"),
    USD("USD");

    Currency(String currency) {
        this.currency = currency;
    }

    private final String currency;

    @Override
    public String toString() {
        return currency;
    }
}
