package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.TypeSameException;
import com.nhnacademy.gw1.rate.*;
import com.nhnacademy.gw1.symbol.Symbol;
import com.nhnacademy.gw1.symbol.SymbolEUR;
import com.nhnacademy.gw1.symbol.SymbolKRW;
import com.nhnacademy.gw1.symbol.SymbolUSD;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BankTest {
    private Bank bank;
    private final List<ExchangeRate> exchangeRateList = List.of(
            new CurrencyKRW(),
            new CurrencyEUR(),
            new CurrencyUSD()
    );
    private final List<Symbol> symbolList = List.of(
            new SymbolEUR(),
            new SymbolKRW(),
            new SymbolUSD()
    );

    @BeforeEach
    void setup() {
        bank = new Bank(exchangeRateList);
    }

    @Test
    @DisplayName("원화를 달러로 환전 하는 테스트")
    void exchange_currency_KRW_to_USD() {
        Money money = new Money("1000원", symbolList);

        bank.exchangeCurrency(money, Currency.USD);
        assertThat(money.getCurrency()).isEqualTo(Currency.USD);
        assertThat(money.toString()).isEqualTo("1.00$");
    }

    @Test
    @DisplayName("달러를 원화로 환전 하는 테스트")
    void exchange_currency_USD_to_KRW() {
        Money money = new Money("5.25$", symbolList);

        bank.exchangeCurrency(money, Currency.KRW);
        assertThat(money.getCurrency()).isEqualTo(Currency.KRW);
        assertThat(money.toString()).isEqualTo("5250원");
    }

    @Test
    void exchange_currency_EqualType() {
        Money money = new Money("1$", symbolList);

        Assertions.assertThatThrownBy(() -> bank.exchangeCurrency(money, Currency.USD))
                .isInstanceOf(TypeSameException.class)
                .hasMessageContaining("same exchange currency type");
    }

    @Test
    void apply_exchange_rate_Money_KRW_to_USD() {
        Money money = new Money("5원", symbolList);
        bank.exchangeRateApply(money, Currency.USD);

        assertThat(money.getBalance()).isEqualTo(0.01);
    }

    @Test
    void apply_exchange_rate_Money_KRW_to_EUR() {
        Money money = new Money("1000원", symbolList);
        bank.exchangeRateApply(money, Currency.EUR);

        assertThat(money.getBalance()).isEqualTo(0.67);
    }

    @Test
    void apply_exchange_rate_Money_USD_to_KRW() {
        Money money = new Money("0.005$", symbolList);
        bank.exchangeRateApply(money, Currency.KRW);

        assertThat(money.getBalance()).isEqualTo(10);
    }

    @Test
    void apply_exchange_rate_Money_USD_to_EUR() {
        Money money = new Money("1$", symbolList);
        bank.exchangeRateApply(money, Currency.EUR);

        assertThat(money.getBalance()).isEqualTo(0.67);
    }

    @Test
    void apply_exchange_rate_Money_EUR_to_KRW() {
        Money money = new Money("1€", symbolList);
        bank.exchangeRateApply(money, Currency.KRW);

        assertThat(money.getBalance()).isEqualTo(1500);
    }

    @Test
    void apply_exchange_rate_Money_EUR_to_USD() {
        Money money = new Money("1€", symbolList);
        bank.exchangeRateApply(money, Currency.USD);

        assertThat(money.getBalance()).isEqualTo(1.5);
    }


    @Test
    void commission_exchanging_money() {
        Money money = new Money("1000원", symbolList);
        ExchangeFee exchangeFee = new ExchangeFee(money);

        assertThat(exchangeFee.getFee()).isEqualTo(50.0);
    }
}