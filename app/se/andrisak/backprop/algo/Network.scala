package se.andrisak.backprop.algo

import scala.util.Random

/**
 * @author andrisak
 */

case class InputLayer(layerName: String, neurons: List[Neuron], hiddenLayer: HiddenLayer)

case class HiddenLayer(layerName: String, biasNeuron: BiasNeuron, neurons: List[Neuron], outputLayer: OutputLayer)

case class OutputLayer(layerName: String, biasNeuron: BiasNeuron, neuron: Neuron)

class Network(inputLayerNeuronCount: Int, hiddenLayerNeuronCount: Int, rand: Random) {
  private val OUTPUT_LAYER_NEURON_COUNT = 1
  private val random = rand

  // create bias neurons
  private val hiddenBiasNeuron = new BiasNeuron("hiddenLayerBiasNode", random)
  private val outputBiasNeuron = new BiasNeuron("outputLayerBiasNode", random)

  // create the layers and init input layer
  private val outputLayer = createOutputLayer("outputLayerNeuron", OUTPUT_LAYER_NEURON_COUNT)
  private val hiddenLayer = createHiddenLayer("hiddenLayerNeuron", hiddenLayerNeuronCount, outputLayer)
  private val inputLayer = createInputLayer("inputLayerNeuron", inputLayerNeuronCount, hiddenLayer)

  // connect bias neurons to their respective layers
  hiddenBiasNeuron.connectToNeuronsInLayer(hiddenLayer.neurons)
  outputBiasNeuron.connectToNeuronsInLayer(List(outputLayer.neuron))

  // add connections between neurons in different layers
  connectLayers(inputLayer, hiddenLayer)
  connectLayers(hiddenLayer, outputLayer)

  def clearInputs(): Unit = {
    inputLayer.neurons.foreach(n => n.clearInput())
    hiddenLayer.neurons.foreach(n => n.clearInput())
    outputLayer.neuron.clearInput()
  }

  def getInputLayer: InputLayer = {
    inputLayer
  }

  def getHiddenLayer: HiddenLayer = {
    hiddenLayer
  }

  def getOutputNode: Neuron = {
    outputLayer.neuron
  }

  def computeOutput: Double = {
    getOutputNode.output
  }

  def initInputLayer(inputs: List[Double]) = {
    if (inputs.size != inputLayer.neurons.size) {
      throw new IllegalArgumentException("Invalid number of input values. " +
        "Must match input layer neuron count")
    }

    for (i <- inputLayer.neurons.indices) {
      inputLayer.neurons(i).addInput(inputs(i))
    }
  }

  private def createInputLayer(layerName: String, neuronCount: Int, hiddenLayer: HiddenLayer): InputLayer = {
    InputLayer(layerName, createNeuronLayer(layerName, neuronCount), hiddenLayer)
  }

  private def createHiddenLayer(layerName: String, neuronCount: Int, outputLayer: OutputLayer): HiddenLayer = {
    HiddenLayer(layerName, hiddenBiasNeuron, createNeuronLayer(layerName, neuronCount), outputLayer)
  }
  
  private def createOutputLayer(layerName: String, neuronCount: Int): OutputLayer = {
    OutputLayer(layerName, outputBiasNeuron, createNeuronLayer(layerName, neuronCount).head)
  }

  private def createNeuronLayer(layerName: String, neuronCount: Int): List[Neuron] = {
    var layer = List[Neuron]()
    1.to(neuronCount).foreach(i => layer = new Neuron(layerName + i, random) :: layer)

    layer
  }

  private def connectLayers(inputLayer: InputLayer, internalLayer: HiddenLayer): Unit = {
    connectLayers(inputLayer.neurons, internalLayer.neurons)
  }

  private def connectLayers(hiddenLayer: HiddenLayer, outputLayer: OutputLayer): Unit = {
    connectLayers(hiddenLayer.neurons, List(outputLayer.neuron))
  }

  private def connectLayers(layerA: List[Neuron], layerB: List[Neuron]) {
    layerA.foreach(neuron => neuron.connectToNeuronsInLayer(layerB))
    layerB.foreach(neuron => neuron.connectToNeuronsInPreviousLayer(layerA))
  }
}