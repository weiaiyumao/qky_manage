package com.zqfinance.system.common;

import java.math.BigDecimal;

public class DatePageSumMoneyMessage extends DataPageMessage {

	private BigDecimal sumMoney;

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}
}
