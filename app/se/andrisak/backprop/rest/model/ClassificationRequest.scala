package se.andrisak.backprop.rest.model

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

/**
 * @author andrisak
 */
object ClassificationRequest {
  case class ClassificationInput(trainingData: Seq[TrainingDataItem], valuesToClassify: ValuesToClassify)
  case class TrainingDataItem(firstInput: Double, secondInput: Double, target: Double)
  case class ValuesToClassify(firstInput: Double, secondInput: Double, iterations: Int)

  implicit val valuesToClassifyReads: Reads[ValuesToClassify] = (
      (__ \ "firstInput").read[Double] ~
      (__ \ "secondInput").read[Double] ~
      (__ \ "iterations").read[Int]
    )(ValuesToClassify.apply _)

  implicit val trainingDataItemReads: Reads[TrainingDataItem] = (
      (__ \ "firstInput").read[Double] ~
      (__ \ "secondInput").read[Double] ~
      (__ \ "target").read[Double]
    )(TrainingDataItem.apply _)

  implicit val classificationInputReads: Reads[ClassificationInput] = (
      (__ \ "classificationInput" \ "trainingData").read[Seq[TrainingDataItem]] ~
      (__ \ "classificationInput" \ "valuesToClassify").read[ValuesToClassify]
    )(ClassificationInput.apply _)
}