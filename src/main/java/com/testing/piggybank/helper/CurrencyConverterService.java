package com.testing.piggybank.helper;

import com.testing.piggybank.model.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyConverterService {
    // One EUR is worth:
    BigDecimal USD = new BigDecimal("1.10");
    BigDecimal GBP = new BigDecimal("0.80");
    BigDecimal EUR = new BigDecimal("1.00");
    BigDecimal AUD = new BigDecimal("1.50");

    /**
     * Convert foreign currency to EURO.
     *
     * @param chosenCurrency chosen currency
     * @param amount         The given amount
     * @return {@link BigDecimal} the foreign currency converted to EURO.
     */
    public BigDecimal toEuro(final Currency chosenCurrency, final BigDecimal amount) {
        return amount;
    }
}
