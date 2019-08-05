package com.pension.investment

import com.pension.Account
import java.io.IOException
import java.math.BigDecimal
import java.util.Currency

trait InvestmentManagementService {

  @throws[IOException]
  def addFunds(account:Account,fundId:String,investmentAmount:BigDecimal,investCcy:Currency)

  @throws[IOException]
  def buyInvestmentFunc(account: Account,fundId:String,investmentAmount:BigDecimal):Boolean

  @throws[IOException]
  def sellInvestmentFund(account: Account,fundId:String,investmentAmount:BigDecimal):Boolean

}
