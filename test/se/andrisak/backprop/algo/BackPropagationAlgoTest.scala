package se.andrisak.backprop.algo

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}
import se.andrisak.backprop.rest.model.ClassificationRequest.TrainingDataItem

import scala.util.Random

/**
  * @author andrisak.
  */
class BackPropagationAlgoTest extends FunSuite with BeforeAndAfterEach with Matchers with MockitoSugar {
  val ITERATIONS = 1
  val TRAINING_DATA_FIRST_INPUT: Double = 1.0
  val TRAINING_DATA_SECOND_INPUT: Double = 1.0
  val TARGET: Double = 1.0
  val RANDOM = 0.5
  val EXPECTED_OUTPUT = 0.9439400108508628
  var trainingData: TrainingDataItem = null

  override protected def beforeEach(): Unit = {
    trainingData = TrainingDataItem(TRAINING_DATA_FIRST_INPUT, TRAINING_DATA_SECOND_INPUT, TARGET)
  }

  test("test classify") {
    val random = mock[Random]
    when(random.nextDouble()).thenReturn(RANDOM)
    val bp = new BackPropagationAlgo(random)

    val output = bp.classify(ITERATIONS, Seq(trainingData), List(1.0, 1.0))

    output should equal(EXPECTED_OUTPUT)
  }

}
