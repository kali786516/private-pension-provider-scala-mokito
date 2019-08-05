package com.pension.setup

import java.io.IOException
import java.time.LocalDate
import com.pension.setup.AccountOpeningStatus

import com.pension.AccountRepository

case class AccountOpeningService(val UNACCEPTABLE_RISK_PROFILE:String=AccountOpeningStatus.UNACCEPTABLE_RISK_PROFILE,
val backgroundCheckService:BackgroundCheckService,
val referenceIdsManager:ReferenceIdsManager,
val accountRepository:AccountRepository,
val eventPublisher: AccountOpeningEventPublisher){

  @throws[IOException]
  def openAccount(firstName:String,lastName:String,taxId:String,dob:LocalDate):String = {

     var backgroundCheckResults:BackgroundCheckResults = backgroundCheckService.confirm(firstName,lastName,taxId,dob)

    if (backgroundCheckResults == null || backgroundCheckResults.riskProfile.equals(AccountOpeningStatus.UNACCEPTABLE_RISK_PROFILE)) {
       AccountOpeningStatus.declined;
    } else {
      var id:String = referenceIdsManager.obtainId(firstName, lastName, taxId, dob);
      if (id != null) {
        accountRepository.save(id, firstName, lastName, taxId, dob, backgroundCheckResults);
        eventPublisher.notify(id)
        AccountOpeningStatus.opened;
      } else {
        AccountOpeningStatus.declined;
      }
    }


  }






}
