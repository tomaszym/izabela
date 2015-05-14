package org.tejo.model.google

import java.net.URL

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.gdata.client.spreadsheet.SpreadsheetService
import com.google.gdata.data.spreadsheet._

import scala.collection.JavaConversions._

case class Spreadsheet(worksheetFeedUrl: URL)(implicit config: AccountConfig) {
  private val spreadsheetFeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full")

  protected lazy val service: SpreadsheetService = {
    val service = new SpreadsheetService("MySpreadsheetIntegration")
    service.setProtocolVersion(SpreadsheetService.Versions.V3)

    val JSON_FACTORY = JacksonFactory.getDefaultInstance
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential = new GoogleCredential.Builder()
      .setTransport(httpTransport)
      .setJsonFactory(JSON_FACTORY)
      .setServiceAccountId(config.serviceAccountId)
      .setServiceAccountPrivateKeyFromP12File(config.p12PrivateKey)
      .setServiceAccountScopes(
        List(
          "https://www.googleapis.com/auth/drive",
          "https://spreadsheets.google.com/feeds"
        ))
      .build()

    service.setOAuth2Credentials(credential)
    service
  }

  def insertRow(data: Map[String, String], worksheetTitle: String) = {
    val feed = service.getFeed(spreadsheetFeedUrl, classOf[SpreadsheetFeed])

    val worksheetFeed: WorksheetFeed = service.getFeed(worksheetFeedUrl, classOf[WorksheetFeed])

    val worksheet = worksheetFeed.getEntries.find(_.getTitle.getPlainText == worksheetTitle).getOrElse{
      throw new Exception("worksheet not found!")
    }

    val listFeedUrl = worksheet.getListFeedUrl()
    val listFeed = service.getFeed(listFeedUrl, classOf[ListFeed])

    var row = new ListEntry()
    data.foreach{
      case (k, v) => {
        row.getCustomElements.setValueLocal(k, v)
      }
    }
    service.insert(listFeedUrl, row)
  }

  def readRows[T](
    worksheetTitle: String
                 )(
    parseRow: ((String) => Option[String]) => T
               ): List[T] = {
    val feed = service.getFeed(spreadsheetFeedUrl, classOf[SpreadsheetFeed])

    val worksheetFeed: WorksheetFeed = service.getFeed(worksheetFeedUrl, classOf[WorksheetFeed])

    val worksheet = worksheetFeed.getEntries.find(_.getTitle.getPlainText == worksheetTitle).getOrElse{
      throw new Exception("worksheet not found!")
    }

    val listFeedUrl = worksheet.getListFeedUrl()
    val listFeed = service.getFeed(listFeedUrl, classOf[ListFeed])
    val entries = listFeed.getEntries

    val r = for (row <- entries) yield {
      def getCell(col: String): Option[String] = Option(row.getCustomElements.getValue(col))
      parseRow(getCell)
    }

    r.toList
  }
}
