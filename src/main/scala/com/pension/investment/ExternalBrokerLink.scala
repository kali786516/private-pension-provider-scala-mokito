package com.pension.investment

import java.io.{BufferedInputStream, BufferedReader, IOException, InputStreamReader}
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL

import com.pension.investment.ExternalConnectionDetails


class ExternalBrokerLink {

  import java.io.IOException

  @throws[IOException]
  def create(url: URL): HttpURLConnection = url.openConnection.asInstanceOf[HttpURLConnection]

  @throws[IOException]
  def executeInvestmentTransaction(funId:String,investmentAmount:BigDecimal,direction:String) = {
    var url = new URL(ExternalConnectionDetails.INTERNAL_PROXY_URL)
    val con = create(url).asInstanceOf[HttpURLConnection]

    con.setRequestMethod("GET")
    con.setRequestProperty("DIRECTION",direction)
    con.setRequestProperty("FUND",funId)
    con.setRequestProperty("AMOUNT",investmentAmount.toPlainString)

    var sb = new StringBuffer()
    var result = ""

    var is = new BufferedInputStream(con.getInputStream)
    var br = new BufferedReader(new InputStreamReader(is))
    var inputLine = ""
    while ((inputLine = br.readLine()) != null) {
      sb.append(inputLine)
    }
    result = sb.toString

    java.lang.Boolean.valueOf(result)

  }

}
