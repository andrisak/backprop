package se.andrisak.backprop.controller.model

import play.api.libs.json.Json

/**
 * @author andrisak
 */
object ClassificationResponse {
  case class Classification(firstInput: Double, secondInput: Double, output: Double)

  implicit val classificationWrites = Json.writes[Classification]
}
