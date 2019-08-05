package com.pension

import java.time.LocalDate

import com.pension.setup.BackgroundCheckResults

trait AccountRepository {
def save(id:String,firstName:String,lastName:String,taxId:String,dob:LocalDate,backgroundCheckResults: BackgroundCheckResults)
}
