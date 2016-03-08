package se.andrisak.backprop.algo

import scala.util.Random

/**
 * @author andrisak
 */

case class ForwardLink(val from: Neuron, val to: Neuron, var weight: Double)

case class ReverseLink(val to: Neuron)

class Neuron(neuronName: String, rand: Random) {
  private val random = rand
  private val name = neuronName

  // forward connections to nodes in next layer
  var forwardLinks: List[ForwardLink] = List()
  // reverse connections to nodes in previous layer
  var reverseLinks: List[ReverseLink] = List()
  // inputs from all neurons in previous layer
  private var inputs: List[Double] = List()

  def addInput(input: Double): Unit = {
    inputs = input :: inputs
  }

  def getInput: Double = {
    inputs.sum
  }

  def clearInput(): Unit = {
    inputs = List()
  }

  def getNextLayerLinks: List[ForwardLink] = {
    forwardLinks
  }

  def getLinkTo(other: Neuron): ForwardLink = {
    // only one link between each pair of neurons
    forwardLinks.filter(p => p.to == other).head
  }

  def getPreviousLayerNeurons: List[Neuron] = {
    reverseLinks.map(r => r.to)
  }

  def connectToNeuronsInLayer(neurons: List[Neuron]): Unit = {
    neurons.foreach(neuron =>
      createForwardConnectionTo(neuron)
    )
  }

  def connectToNeuronsInPreviousLayer(neurons: List[Neuron]): Unit = {
    neurons.foreach(neuron =>
      createReverseConnectionTo(neuron)
    )
  }

  private def createForwardConnectionTo(other: Neuron): ForwardLink = {
    val weight = random.nextDouble()
    val link = new ForwardLink(this, other, weight)
    forwardLinks = link :: forwardLinks

    link
  }

  private def createReverseConnectionTo(other: Neuron): ReverseLink = {
    val rpLink = new ReverseLink(other)
    reverseLinks = rpLink :: reverseLinks

    rpLink
  }

  /**
   * sigmoid function used for computing output
 *
   * @return
   */
  def output: Double = {
    1.0 / (1.0 + math.exp(-inputs.sum))
  }

  override def toString: String = {
    name
  }
}