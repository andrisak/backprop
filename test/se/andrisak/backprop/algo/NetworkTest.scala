package se.andrisak.backprop.algo

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}

import scala.util.Random

/**
  * @author andrisak.
  */
class NetworkTest extends FunSuite with BeforeAndAfterEach with Matchers with MockitoSugar {
  val RANDOM_VALUE = 0.2
  val INPUT_LAYER_NEURON_COUNT = 1
  val HIDDEN_LAYER_NEURON_COUNT = 1
  val INPUT = 0.528593

  val random = mock[Random]
  when(random.nextDouble()).thenReturn(RANDOM_VALUE)

  test("test that clearInput clears all node input") {
    val network = new Network(INPUT_LAYER_NEURON_COUNT, HIDDEN_LAYER_NEURON_COUNT, random)
    network.getInputLayer.neurons.head.addInput(0.534543)
    network.getHiddenLayer.neurons.head.addInput(0.6854543)
    network.clearInputs()

    network.getInputLayer.neurons.head.getInput should equal(0.0)
    network.getHiddenLayer.neurons.head.getInput should equal(0.0)
  }

  test("init of input layer should add the input to input neurons") {
    val network = new Network(INPUT_LAYER_NEURON_COUNT, HIDDEN_LAYER_NEURON_COUNT, random)
    network.initInputLayer(List(INPUT))

    network.getInputLayer.neurons.head.getInput should equal(INPUT)
  }

  test("adding more input values than input neurons should throw an exception") {
    val network = new Network(INPUT_LAYER_NEURON_COUNT, HIDDEN_LAYER_NEURON_COUNT, random)

    intercept[IllegalArgumentException] {
      network.initInputLayer(List(INPUT, INPUT))
    }
  }
}
