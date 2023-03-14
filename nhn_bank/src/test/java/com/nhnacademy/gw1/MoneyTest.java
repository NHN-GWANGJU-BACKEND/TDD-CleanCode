package com.nhnacademy.gw1;

import com.nhnacademy.gw1.exception.InvalidMoneyException;
import com.nhnacademy.gw1.exception.TypeNotSameException;
import com.nhnacademy.gw1.symbol.Symbol;
import com.nhnacademy.gw1.symbol.SymbolEUR;
import com.nhnacademy.gw1.symbol.SymbolKRW;
import com.nhnacademy.gw1.symbol.SymbolUSD;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    private final List<Symbol> symbolList = List.of(
            new SymbolEUR(),
            new SymbolKRW(),
            new SymbolUSD()
    );

    @Test
    void add_success() {
        Money money = new Money("5.25$", symbolList);
        Money money2 = new Money("5.25$", symbolList);

        money.add(money2);

        assertThat(money.toString()).isEqualTo("10.50$");
    }

    @Test
    void add_invalid_money_failed() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("-2000원", symbolList);

        Assertions.assertThatThrownBy(() -> money.add(money2))
                .isInstanceOf(InvalidMoneyException.class)
                .hasMessageContaining("invalid money");
    }

    @Test
    void add_currency_failed() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("2000$", symbolList);

        Assertions.assertThatThrownBy(() -> money.add(money2))
                .isInstanceOf(TypeNotSameException.class)
                .hasMessageContaining("not same currency");
    }

    @Test
    void subtract_success() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("1000원", symbolList);

        money.subtract(money2);

        assertThat(money.getBalance()).isEqualTo(0);
    }

    @Test
    void subtract_failed() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("2000원", symbolList);

        Assertions.assertThatThrownBy(() -> money.subtract(money2))
                .isInstanceOf(InvalidMoneyException.class)
                .hasMessageContaining("invalid money");
    }

    @Test
    void subtract_currency_failed() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("1000$", symbolList);

        Assertions.assertThatThrownBy(() -> money.subtract(money2))
                .isInstanceOf(TypeNotSameException.class)
                .hasMessageContaining("not same currency");
    }

    @Test
    void settingTypeAndMoney() {
        Money money = new Money("1000원", symbolList);

        assertThat(money.getBalance()).isEqualTo(1000);
        assertThat(money.getCurrency()).isEqualTo(Currency.KRW);
    }

    @Test
    void money_equals_success() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("1000원", symbolList);

        assertThat(money.equals(money2)).isTrue();
    }

    @Test
    void money_equals_failed_balance() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("1100원", symbolList);

        assertThat(money.equals(money2)).isFalse();
    }

    @Test
    void money_equals_failed_Currency() {
        Money money = new Money("1000원", symbolList);
        Money money2 = new Money("1000$", symbolList);

        assertThat(money.equals(money2)).isFalse();
    }
}