package Biocomputing;

import Biocomputing.*;

import org.jenetics.DoubleChromosome;
import org.jenetics.DoubleGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;
import org.jenetics.TournamentSelector;
import org.jenetics.SinglePointCrossover;
import org.jenetics.Mutator;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.engine.limit;
import java.util.ArrayList;


public class GAwrapper 
{
	static private int numAtts;
	static private InstanceSet is;

	public GAwrapper(InstanceSet pIs) {
		numAtts=Attributes.getNumAttributes();
		is=pIs;
	}

	static private double evaluate(Genotype<DoubleGene> gt) {
		Classifier cs = buildClassifier(gt);
		return cs.computeFitness(is);
	}

	static private Classifier buildClassifier(Genotype<DoubleGene> gt) {
		double []values = gt.stream().mapToDouble(c -> c.getGene().doubleValue()).toArray();

		int index = 0;
		double []center = new double[numAtts];
		for(int i=0;i<numAtts;i++) {
			center[i] = values[index++];
		}
		double radius = values[index++];
		int classOfSphere = (int)values[index];
	
		return new ClassifierSphere(center,radius,classOfSphere);
	}


	public Classifier generateClassifier() {
		//First we need to construct the genotype definition. What type and range of values each gene has
		ArrayList<DoubleChromosome> chromosomes = new ArrayList<DoubleChromosome>();
		int index;
		double maxD = 0;
		// Genes to represent the center of the sphere. One attribute per dimension in the problem
		for(index=0;index<numAtts;index++) {
			Attribute att = Attributes.getAttribute(index);
			chromosomes.add(DoubleChromosome.of(att.minAttribute(), att.maxAttribute()));
			double size = att.maxAttribute()-att.minAttribute();
			if(size>maxD) {
				maxD = size;
			}
		}
		// Gene to represent the radius of the sphere
		chromosomes.add(DoubleChromosome.of(0,maxD));
		// Gene to represent the problem class associated to this sphere
		chromosomes.add(DoubleChromosome.of(0,Attributes.numClasses));

		// We create the factory: the generator of individuals
		Factory<Genotype<DoubleGene>> gtf = Genotype.of(chromosomes);

		// We create the execution environment.
		Engine<DoubleGene, Double> engine = Engine
			.builder(GAwrapper::evaluate, gtf)
			.populationSize(1000)
			.survivorsSize(5)
			.offspringSelector(new TournamentSelector<>(5))
			.alterers(
				new Mutator<>(0.20),
				new SinglePointCrossover<>(0.90))
			.build();

		final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

		// And we finally run the GA
		Genotype<DoubleGene> result = engine.stream()
			.limit(25)
			// Uncomment to print detailed statistics of each generation of the GA
			.peek(statistics)
			.peek(r -> System.out.println(statistics))
			.collect(EvolutionResult.toBestGenotype());

		// Uncomment to print the raw genotype of the best individual of the run
		System.out.println(result);

		return buildClassifier(result);
	}

}
