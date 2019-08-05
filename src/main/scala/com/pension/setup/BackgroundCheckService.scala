package com.pension.setup

import com.pension.setup.BackgroundCheckResults
import java.io.IOException
import java.time.LocalDate


trait BackgroundCheckService {
  @throws[IOException]
  def confirm(firstName: String, lastName: String, taxId: String, dob: LocalDate): BackgroundCheckResults
}
