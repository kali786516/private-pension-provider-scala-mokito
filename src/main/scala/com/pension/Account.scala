package com.pension

import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency
import java.util


class Account {
  private var id:String = null
  private var fistName:String = null
  private var lastName:String = null
  private var dob:LocalDate = null
  private var taxId:String = null
  private var totalInvestmentValue:BigDecimal = null
  private var ccy:Currency = null
  private var investments:util.Set[String] = null
  private var availableCash:BigDecimal = null
  private var expectedRetirement:LocalDate = null
  private var openingDate:LocalDate = null

  def getOpeningDate: LocalDate = openingDate

  def setOpeningDate(openingDate: LocalDate): Unit = {
    this.openingDate = openingDate
  }

  def getExpectedRetirement: LocalDate = expectedRetirement

  def getId: String = id

  def setId(id: String): Unit = {
    this.id = id
  }

  def getFistName: String = fistName

  def setFistName(fistName: String): Unit = {
    this.fistName = fistName
  }

  def getLastName: String = lastName

  def setLastName(lastName: String): Unit = {
    this.lastName = lastName
  }

  def getDob: LocalDate = dob

  def setDob(dob: LocalDate): Unit = {
    this.dob = dob
  }

  def getTaxId: String = taxId

  def setTaxId(taxId: String): Unit = {
    this.taxId = taxId
  }

  def getTotalInvestmentValue: BigDecimal = totalInvestmentValue

  def setTotalInvestmentValue(totalInvestmentValue: BigDecimal): Unit = {
    this.totalInvestmentValue = totalInvestmentValue
  }

  def getCcy: Currency = ccy

  def setCcy(ccy: Currency): Unit = {
    this.ccy = ccy
  }

  def getInvestments: util.Set[String] = investments

  def setInvestments(investments: util.Set[String]): Unit = {
    this.investments = investments
  }

  def getAvailableCash: BigDecimal = availableCash

  def setAvailableCash(availableCash: BigDecimal): Unit = {
    this.availableCash = availableCash
  }

  def setExpectedRetirement(expectedRetirement: LocalDate): Unit = {
    this.expectedRetirement = expectedRetirement
  }
}
