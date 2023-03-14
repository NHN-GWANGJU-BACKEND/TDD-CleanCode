package com.nhnacademy.gw1;

import com.nhnacademy.gw1.rate.CurrencyEUR;
import com.nhnacademy.gw1.rate.CurrencyKRW;
import com.nhnacademy.gw1.rate.CurrencyUSD;
import com.nhnacademy.gw1.rate.ExchangeRate;
import com.nhnacademy.gw1.symbol.Symbol;
import com.nhnacademy.gw1.symbol.SymbolEUR;
import com.nhnacademy.gw1.symbol.SymbolKRW;
import com.nhnacademy.gw1.symbol.SymbolUSD;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryMethodTest {
    private List<Symbol> symbolList = List.of(
            new SymbolEUR(),
            new SymbolKRW(),
            new SymbolUSD()
    );
    private List<ExchangeRate> exchangeRateList = List.of(
            new CurrencyEUR(),
            new CurrencyKRW(),
            new CurrencyUSD()
    );

    @Test
    void equal_Symbol() {
        assertThat(symbolList.get(0).getSymbol()).isEqualTo("€");
        assertThat(symbolList.get(1).getSymbol()).isEqualTo("원");
        assertThat(symbolList.get(2).getSymbol()).isEqualTo("$");
    }

    @Test
    void equal_exchange_rate() {
        assertThat(exchangeRateList.get(0).getCurrency()).isEqualTo(Currency.EUR);
        assertThat(exchangeRateList.get(1).getCurrency()).isEqualTo(Currency.KRW);
        assertThat(exchangeRateList.get(2).getCurrency()).isEqualTo(Currency.USD);
    }
}
