package com.pension.investment
import java.io.IOException
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.Currency

import com.pension.Account;

class ExternalInvestmentManagementService extends InvestmentManagementService{
  val MATH_CONTEXT = new MathContext(34, RoundingMode.DOWN)

  @throws[IOException]
  def executeInvestmentTrasnaction(fundId:String,investmentAmount:BigDecimal,direction:String) = {
    val externalBrokerLink:ExternalBrokerLink = new ExternalBrokerLink
    externalBrokerLink.executeInvestmentTransaction(fundId,investmentAmount,direction)
  }

  @Override
  override def addFunds(account: Account, fundId: String, investmentAmount: BigDecimal,investmentCcy:Currency): Unit = {
    if (investmentCcy != Currency.getInstance("USD")) {
      throw new IllegalArgumentException("Only USD accounts are supported.")
    }
    account.setAvailableCash(account.getAvailableCash.add(investmentAmount,MATH_CONTEXT))
  }

  @throws[IOException]
  override def buyInvestmentFunc(account: Account, fundId: String, investmentAmount: BigDecimal): Boolean = {
    if (account.getAvailableCash.compareTo(investmentAmount) < 0 ){
       throw new IllegalArgumentException("Not enough cash in account.")
    }
    if (executeInvestmentTrasnaction(fundId,investmentAmount,"BUY")) {
      account.setAvailableCash(account.getAvailableCash.subtract(investmentAmount,MATH_CONTEXT))
      account.getInvestments.add(fundId)
      true
    } else {
      false
    }
  }

  @throws[IOException]
  override def sellInvestmentFund(account: Account, fundId: String, investmentAmount: BigDecimal): Boolean = {
    if (!account.getInvestments.contains(fundId)) {
      throw new IllegalArgumentException("Account doesnt have any holdings in " + fundId)
    }
    if (executeInvestmentTrasnaction(fundId,investmentAmount,"SELL")){
      account.getInvestments.remove(fundId)
      account.setAvailableCash(account.getAvailableCash.add(investmentAmount,MATH_CONTEXT))
      true
    } else {
      false
    }
  }


}

