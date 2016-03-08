package se.andrisak.backprop.algo

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}

import scala.util.Random

/**
  * @author andrisak.
  */
class NeuronTest extends FunSuite with BeforeAndAfterEach with Matchers with MockitoSugar {
  val NEURON_NAME = "neuron"
  val NEURON_NAME2 = "neuron2"
  val RANDOM_VALUE = 0.53
  val random = mock[Random]
  when(random.nextDouble()).thenReturn(RANDOM_VALUE)
  val neuron = new Neuron(NEURON_NAME, random)

  test("input should be stored and be cleared") {
    val input = 0.543
    neuron.addInput(input)

    neuron.getInput should equal (input)
    neuron.clearInput()
    neuron.getInput should equal (0)
  }

  test("neurons should be connected with a ForwardLink") {
    val neuron2 = new Neuron(NEURON_NAME2, random)

    neuron.connectToNeuronsInLayer(List(neuron2))

    val link = neuron.getLinkTo(neuron2)
    link.to should equal(neuron2)
    link.weight should equal(RANDOM_VALUE)
    link should equal(neuron.getNextLayerLinks.head)
  }

  test("neurons should be connected with a ReverseLink") {
    val neuron2 = new Neuron(NEURON_NAME2, random)

    neuron.connectToNeuronsInPreviousLayer(List(neuron2))

    neuron.getPreviousLayerNeurons.head should equal(neuron2)
  }

  test("test the sigmoid computation") {
    neuron.output should equal(0.5)
  }

}
