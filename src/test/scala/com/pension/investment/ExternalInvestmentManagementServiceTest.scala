package com.pension.investment

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.time.LocalDate
import java.util.HashSet

import com.pension.Account
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doReturn, mock, when}
import com.pension.investment.ExternalInvestmentManagementService
import org.mockito.Spy
import java.io.IOException

@RunWith(classOf[JUnitRunner])
class ExternalInvestmentManagementServiceTest extends FunSuite {

  private var testAccount:Account = mock(classOf[Account])

  val TEST_FUND_ID = "FUND_ID"
  @Spy
  var underTest:ExternalInvestmentManagementService = null


  @Test
  @throws[IOException]
  def shouldBeAbleToBuyPensionFundInvestmentIfEnoughCashInAccount(): Unit = {
    when(underTest.executeInvestmentTrasnaction(anyString, any(classOf[Nothing]), anyString))
      .thenReturn(true)

    //doReturn(true).when(underTest).executeInvestmentTrasnaction(anyString, any(classOf[Nothing]), anyString)

    testAccount.setInvestments(new HashSet())

    val startingAccountBalance = new BigDecimal(1000000)

    testAccount.setAvailableCash(startingAccountBalance)

    val desiredInvestmentAmount = new BigDecimal(100000)

    underTest.buyInvestmentFunc(testAccount, TEST_FUND_ID, desiredInvestmentAmount)

    assertEquals(testAccount.getAvailableCash, startingAccountBalance.subtract(desiredInvestmentAmount))
    assertTrue(testAccount.getInvestments.contains(TEST_FUND_ID))
  }






}
