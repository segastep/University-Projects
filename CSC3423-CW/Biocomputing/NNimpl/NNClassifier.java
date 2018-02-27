package Biocomputing.NNimpl;

import Biocomputing.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;


import java.util.Arrays;

public class NNClassifier extends Classifier {


    private final TransferFunctionType ACTIVATION_FUNCTION = TransferFunctionType.SIGMOID;
    private final int HIDDEN = 15;
    private final int LAYERS = 1;
    private final int MAX_ITER = 10000;
    private final double MAX_ERROR = 0.01d;
    private final double L_RATE = 0.1d;

    private MultiLayerPerceptron mLP;
    private BackPropagation backProp;

    private int prediction;

    public NNClassifier(InstanceSet trainingSet)
    {
        /*Prepare and normalize data*/
        DataSet data = instanceToDataSet(trainingSet);
        //MaxMinNormalizer normalizer = new MaxMinNormalizer();
        // Huge negative impact on accuracy
        //normalizer.normalize(data);

        /*
         * Set up the Neural network
         */

        mLP = new MultiLayerPerceptron(ACTIVATION_FUNCTION,
                Attributes.getNumAttributes(), HIDDEN, 10, LAYERS);

        /***
         * Please, clarify something for me if possible, look at the
         * commented code below, if I first set the maxErr, learning rate and Maxiters,
         * to the mLP object and then pass it to backProp, I observed that I get
         * lower accuracy, when it theory it should be the same, could you explain
         * why this is happening, if whomever marks that is familiar with the
         * framework I am using of course, there was a 3% accuracy drift with the
         * commented out implementation.
         */
        //mLP.getLearningRule().setMaxError(MAX_ERROR);
        //mLP.getLearningRule().setLearningRate(L_RATE);
        //mLP.getLearningRule().setMaxIterations(MAX_ITER);
        backProp = mLP.getLearningRule();
        backProp.setMaxError(MAX_ERROR);
        backProp.setLearningRate(L_RATE);
        backProp.setMaxIterations(MAX_ITER);
        mLP.learn(data);
    }

    /**
     * Convers instance to Neuroph readable format, strips off last attribute
     * from the original instance as it's not needed.
     * @param instance target instance to convert
     * @return DataSetRow
     */
    public DataSetRow instanceToRow(Instance instance)
    {
        double inputs[] = Arrays.copyOfRange(instance.getRealAttributes(),
                0,instance.getRealAttributes().length-1);

        DataSetRow thisRow = new DataSetRow(inputs, new double[]{instance.getClassValue()});
        return thisRow;
    }
    /**
     * Create dataset for neural network from instance object,
     * strips off the class attribute, as it doesn't count.
     * @param trainData
     * @return DataSet
     */

    public DataSet instanceToDataSet(InstanceSet trainData)
    {
        int dataRows = Attributes.getNumAttributes();
        DataSet ds = new DataSet(dataRows,1);
        for(Instance s : trainData.getInstances())
            ds.addRow(instanceToRow(s));
        //System.out.println(ds.toString());
        return ds;
    }

    @Override
    public int classifyInstance(Instance ins) {

        int numAttr = Attributes.getNumAttributes();
        // Here we have an extra attribute so we strip it off
        double[] attr = Arrays.copyOfRange(ins.getRealAttributes(),
                0,ins.getRealAttributes().length-1);

        this.mLP.setInput(attr);

        // Calculate outputs
        this.mLP.calculate();
        double[] res = this.mLP.getOutput();

        // Deal with predictions
        this.prediction = (int) Math.round(res[0]);
        return prediction < 0 || prediction > numAttr ? -1 : prediction;
    }

    /**
     * Classifier will print mLP's topology to string,
     * not including weights as they can be arbitrary long
     * string depending on the dataset size
     */
    @Override
    public void printClassifier() {

        StringBuilder sb = new StringBuilder();
        sb.append("MultiLayerPerceptionNN:[");
        sb.append(" LayerCount: ").append(this.mLP.getLayersCount()).append(", Layers info: ");
        for (int i=0; i < this.mLP.getLayersCount(); i++)
        {
            if(i>0) sb.append(",");
            int bias = 0;
            for (Neuron neuron : this.mLP.getLayerAt(i).getNeurons())
            {
                if (neuron instanceof BiasNeuron) bias++;
            }
            sb.append("{layerID: ").append(i+1).append(", Neurons Count: ")
                    .append(this.mLP.getLayerAt(i).getNeuronsCount())
                    .append(", Bias Neurons: ").append(bias)
                    .append("}");

        }
        sb.append("]");
        System.out.println(sb.toString());
        System.out.println("Neural Network learning completed after " + backProp.getCurrentIteration()+ " iterations.");

    }

    @Override
    public int classifierClass() {
        return this.prediction;
    }

}
