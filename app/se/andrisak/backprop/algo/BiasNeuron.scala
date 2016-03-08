package se.andrisak.backprop.algo

import scala.util.Random

/**
  * @author andrisak.
  */
class BiasNeuron(neuronName: String, random: Random) extends Neuron(neuronName, random) {
  override def output: Double = 1.0
}
