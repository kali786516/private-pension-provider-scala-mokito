package com.pension.setup

import java.time.LocalDate

trait ReferenceIdsManager {
  def obtainId(firstName:String,lastName:String,taxId:String,dob:LocalDate):String
}
