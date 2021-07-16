package com.example.morecomplexcontrollerdemo.support;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class MoneyFormatter implements Formatter<Money> {
    @Override
    public Money parse(String text, Locale locale) throws ParseException {
        if (NumberUtils.isParsable(text)) {
            return Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(text));
        } else if (StringUtils.isNotEmpty(text)) {
            var spit = StringUtils.split(text, " ");
            if (spit != null && spit.length == 2 && NumberUtils.isParsable(spit[1])) {
                return Money.of(CurrencyUnit.of(spit[0]), NumberUtils.createBigDecimal(spit[1]));
            }
            throw new ParseException(text, 0);
        }
        throw new ParseException(text, 0);
    }

    @Override
    public String print(Money money, Locale locale) {
        if (money == null) {
            return null;
        }
        return money.getCurrencyUnit().getCode() + " " + money.getAmount();
    }
}
