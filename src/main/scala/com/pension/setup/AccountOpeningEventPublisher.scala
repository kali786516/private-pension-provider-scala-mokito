package com.pension.setup

trait AccountOpeningEventPublisher {

  def notify(accountId:String):Boolean

}
