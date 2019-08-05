package com.pension.investment

import java.net.HttpURLConnection
import org.scalatest.FunSuite
import org.mockito.Mock
import org.mockito.Spy
import java.math.BigDecimal
import java.net.URL
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.powermock.api.mockito.PowerMockito

@RunWith(classOf[JUnitRunner])
class ExternalBrokerLinkTest extends FunSuite {

  val TEST_FUND_ID = "FUND_ID"
  private val investmentAmount:BigDecimal = new BigDecimal(100000.0)

  @Spy
  val app: ExternalBrokerLink = null

  @Mock
  val connection: HttpURLConnection = null

  def testexecuteInvestmentTransactionStatus() ={
    var expected:Int = 200
    var url:URL = new URL(ExternalConnectionDetails.INTERNAL_PROXY_URL)
    var status = app.executeInvestmentTransaction(TEST_FUND_ID,investmentAmount,"SELL")
    //println(status)
    //assert(expected == status)
  }

  def urlAccessible() = {
    val u = PowerMockito.mock(classOf[URL])
    val url = "http://www.sdsgle.com"
    PowerMockito.whenNew(classOf[URL]).withArguments(url).thenReturn(u)
    val huc = PowerMockito.mock(classOf[Nothing])
    PowerMockito.when(u.openConnection).thenReturn(huc)
    //PowerMockito.when(huc.getResponseCode).thenReturn(200)
  }




  test("testexecuteInvestmentTransactionStatus") {
    println("testexecuteInvestmentTransactionStatus")
    testexecuteInvestmentTransactionStatus()
  }

}
