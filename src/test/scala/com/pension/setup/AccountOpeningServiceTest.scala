package com.pension.setup

import java.io.IOException

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import java.time.LocalDate

import com.pension.AccountRepository
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.when
import org.scalatest.Assertions._

@RunWith(classOf[JUnitRunner])
class AccountOpeningServiceTest extends FunSuite with BeforeAndAfterEach {

  private val FIRST_NAME = "John"
  private val LAST_NAME  = "Smith"
  private val DOB = LocalDate.of(1990,1,1)
  private val TAX_ID = "132323"
  private var underTest:AccountOpeningService               = null
  private var backgroundCheckService:BackgroundCheckService = mock(classOf[BackgroundCheckService])
  private var referenceIdsManager:ReferenceIdsManager       = mock(classOf[ReferenceIdsManager])
  private var accountRepository:AccountRepository           = mock(classOf[AccountRepository])
  private var accountOpeningEventPublisher:AccountOpeningEventPublisher = mock(classOf[AccountOpeningEventPublisher])

  override def beforeEach() {
    underTest = new AccountOpeningService(AccountOpeningStatus.UNACCEPTABLE_RISK_PROFILE,backgroundCheckService,referenceIdsManager,accountRepository,accountOpeningEventPublisher)
  }


  @throws[IOException]
  def shouldOpenAccount()={
    when(backgroundCheckService.confirm(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn(new BackgroundCheckResults("Some Value","0"))

    when(referenceIdsManager.obtainId(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn("some_id")

    var accountOpenningStatus:String = underTest.openAccount(FIRST_NAME,LAST_NAME,TAX_ID,DOB)
    assert(AccountOpeningStatus.opened == accountOpenningStatus)
  }

  @throws[IOException]
  def shouldDeclineIfUnnaceptableRiskProfileAccount()={
    when(backgroundCheckService.confirm(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn(new BackgroundCheckResults(AccountOpeningStatus.UNACCEPTABLE_RISK_PROFILE,"0"))
    var accountOpenningStatus:String = underTest.openAccount(FIRST_NAME,LAST_NAME,TAX_ID,DOB)
    assert(AccountOpeningStatus.declined == accountOpenningStatus)
  }

  @throws[IOException]
  def shouldDeclineIfNullBackgroundCheckResponseReceived()={
    when(backgroundCheckService.confirm(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn(null)
    var accountOpenningStatus:String = underTest.openAccount(FIRST_NAME,LAST_NAME,TAX_ID,DOB)
    assert(AccountOpeningStatus.declined == accountOpenningStatus)
  }

  @throws[IOException]
  def shouldThrowIfBackgroundCheckServiceThrows(): Unit ={
    when(backgroundCheckService.confirm(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenThrow(new IOException())
    //assertThrows(classOf[IOException],underTest.openAccount(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
  }

  @throws[IOException]
  def shouldThrowIfAccountRepositoryThrows() ={
    var backgroundCheckResults:BackgroundCheckResults = new BackgroundCheckResults("some","0")
    when(backgroundCheckService.confirm(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn(backgroundCheckResults)
    when(referenceIdsManager.obtainId(FIRST_NAME,LAST_NAME,TAX_ID,DOB))
      .thenReturn("Some ID")
    when(accountRepository.save("Some ID",FIRST_NAME,LAST_NAME,TAX_ID,DOB,backgroundCheckResults))
      .thenThrow(new IOException)
    //assertThrows(classOf[RuntimeException],underTest.openAccount(FIRST_NAME,LAST_NAME,TAX_ID,DOB))

  }


  test("Intialize AccountOpeningService"){
    println("Intialize AccountOpeningService")
    beforeEach()
  }

  test("Should Open Account"){
    println("Should Open Account")
    shouldOpenAccount()
  }

  test("shouldDeclineAccount backgroundCheckService When"){
    println("shouldDeclineAccount backgroundCheckService When")
    shouldDeclineIfUnnaceptableRiskProfileAccount()
  }

  test("shouldDeclineIfNullBackgroundCheckResponseReceived backgroundCheckService response recieved"){
    println("shouldDeclineIfNullBackgroundCheckResponseReceived backgroundCheckService response recieved false")
    shouldDeclineIfNullBackgroundCheckResponseReceived()
  }

  test("shouldThrowIfBackgroundCheckServiceThrows Test"){
    println("shouldThrowIfBackgroundCheckServiceThrows Test")
    assertThrows[IOException] {
      shouldThrowIfAccountRepositoryThrows
    }
  }

  test("shouldThrowIfAccountRepositoryThrows Test"){
    println("shouldThrowIfAccountRepositoryThrows Test")

  }



}
