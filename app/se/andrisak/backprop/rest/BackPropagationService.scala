package se.andrisak.backprop.rest

import javax.inject.Inject

import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import se.andrisak.backprop.algo.BackPropagationAlgo
import se.andrisak.backprop.rest.model.ClassificationRequest.ClassificationInput
import se.andrisak.backprop.rest.model.ClassificationResponse.Classification

import scala.util.Random

/**
 * @author andrisak
 */
class BackPropagationService @Inject()(random: Random) extends Controller {
  def classify = Action(BodyParsers.parse.json) { request =>
    val r = request.body.validate[ClassificationInput]

    r.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      classification => {
        val iterations = classification.valuesToClassify.iterations
        val trainingData = classification.trainingData
        val bp = new BackPropagationAlgo(random)
        val firstInput = classification.valuesToClassify.firstInput
        val secondInput = classification.valuesToClassify.secondInput
        val output = bp.classify(iterations, trainingData, List(firstInput, secondInput))

        Ok(Json.toJson(Classification(firstInput, secondInput, output)))
      })
  }
}
