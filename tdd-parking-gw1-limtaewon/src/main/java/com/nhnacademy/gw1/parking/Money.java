package com.nhnacademy.gw1.parking;

import com.nhnacademy.gw1.parking.exception.CannotExitException;

public class Money {
    private long money;

    public Money(int money) {
        this.money = money;
    }

    public long getMoney() {
        return money;
    }

    public void subMoney(Money money) {
        subInvalidMoney(money);
        this.money -= money.getMoney();
    }

    private void subInvalidMoney(Money money) {
        if (this.money - money.money < 0) {
            throw new CannotExitException(this.money, money.getMoney());
        }
    }
}
