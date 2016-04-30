package se.andrisak.backprop.algo

import se.andrisak.backprop.rest.model.ClassificationRequest.TrainingDataItem

import scala.util.Random

/**
  * @author andrisak
  */
class BackPropagationAlgo(random: Random) {
  // controls how much weights are adjusted on each reverse pass
  private val TRAINING_RATE: Double = 0.35
  private val HIDDEN_NEURON_COUNT = 3
  private val INPUT_NEURON_COUNT = 2
  private val MAX_ITERATIONS = 5000

  def classify(iterations: Int, trainingData: Seq[TrainingDataItem], inputToClassify: List[Double]): Double = {
    val trainedNetwork = train(iterations, trainingData)
    forwardPropagation(trainedNetwork, inputToClassify)
  }

  private def train(iterations: Int, trainingData: Seq[TrainingDataItem]): Network = {
    val network = new Network(INPUT_NEURON_COUNT, HIDDEN_NEURON_COUNT, random)

    for (i <- 1 to Math.min(iterations, MAX_ITERATIONS)) {
      trainingData.foreach(trainingItem => {
        forwardPropagation(network, List(trainingItem.firstInput, trainingItem.secondInput))
        backPropagation(network, trainingItem.target)
        network.clearInputs()
      })
    }

    network
  }

  private def forwardPropagation(network: Network, inputs: List[Double]): Double = {
    network.initInputLayer(inputs)

    val inputLayer = network.getInputLayer
    val hiddenLayer = inputLayer.hiddenLayer
    val outputLayer = hiddenLayer.outputLayer

    inputLayer.neurons.foreach(inputNeuron => {
      inputNeuron.getNextLayerLinks.foreach(link => {
        val hiddenNeuron = link.to
        hiddenNeuron.addInput(inputNeuron.getInput * link.weight)
        hiddenNeuron.addInput(hiddenLayer.biasNeuron.output *
          hiddenLayer.biasNeuron.getLinkTo(hiddenNeuron).weight)
      })
    })

    hiddenLayer.neurons.foreach(hiddenNeuron => {
      val hiddenNeuronOutput = hiddenNeuron.output

      hiddenNeuron.getNextLayerLinks.foreach(link => {
        val outputNeuron = link.to
        outputNeuron.addInput(hiddenNeuronOutput * link.weight)
        outputNeuron.addInput(outputLayer.biasNeuron.output *
          outputLayer.biasNeuron.getLinkTo(outputNeuron).weight)
      })
    })

    network.computeOutput
  }

  private def backPropagation(network: Network, target: Double): Unit = {
    val outputNeuron = network.getOutputNode
    val finalOutput = outputNeuron.output
    val outputError = (target - finalOutput) * (1 - finalOutput) * finalOutput

    outputNeuron.getPreviousLayerNeurons.foreach(
      hiddenLayerNeuron => {
        hiddenLayerNeuron.getPreviousLayerNeurons.foreach(
          inputLayerNeuron => {
            val linkToInputNeuron = inputLayerNeuron.getLinkTo(hiddenLayerNeuron)
            val linkToOutputNeuron = hiddenLayerNeuron.getLinkTo(outputNeuron)
            val output = hiddenLayerNeuron.output
            val error = output * (1.0 - output) * outputError * linkToOutputNeuron.weight
            linkToInputNeuron.weight += TRAINING_RATE * error * inputLayerNeuron.getInput
          })

        val linkToOutputNeuron = hiddenLayerNeuron.getLinkTo(outputNeuron)
        linkToOutputNeuron.weight += TRAINING_RATE * outputError * hiddenLayerNeuron.output
      })
  }
}