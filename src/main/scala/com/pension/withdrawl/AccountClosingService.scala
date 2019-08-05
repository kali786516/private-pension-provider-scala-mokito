package com.pension.withdrawl

import com.pension.setup.BackgroundCheckService
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import com.pension.Account
import com.pension.setup.BackgroundCheckResults

case class AccountClosingService(var RETIREMENT_AGE:Int=65,var backgroundCheckService:BackgroundCheckService,
                                 var clock:Clock){

  @throws[IOException]
  def closeAccount(account:Account):AccountClosingResponse ={
    var accountHolderAge:Period = Period.between(account.getDob,LocalDate.now(clock))
    if (accountHolderAge.getYears < RETIREMENT_AGE) {
      new AccountClosingResponse(AccountClosingStatus.CLOSING_DENIED,LocalDateTime.now(clock))
    } else {
      var backgroundCheckResults:BackgroundCheckResults = backgroundCheckService.confirm(
        account.getFistName,account.getLastName,account.getTaxId,account.getDob
      )
      if (backgroundCheckResults == null ){
        AccountClosingResponse(AccountClosingStatus.CLOSING_PENDING,LocalDateTime.now(clock))
      } else {
        var riskProfile:String = backgroundCheckResults.riskProfile
        if (riskProfile.equals(AccountClosingStatus.CLOSING_DENIED,LocalDateTime.now(clock))) {
          AccountClosingResponse(AccountClosingStatus.CLOSING_DENIED,LocalDateTime.now(clock))
        } else {
          AccountClosingResponse(AccountClosingStatus.CLOSING_OK,LocalDateTime.now(clock))
        }
      }
    }
  }

}
