package Biocomputing.GPimpl;

import Biocomputing.Attributes;
import Biocomputing.Classifier;
import Biocomputing.Instance;
import Biocomputing.InstanceSet;
import org.epochx.epox.Node;
import org.epochx.epox.Variable;
import org.epochx.epox.math.*;
import org.epochx.epox.trig.*;
import org.epochx.gp.model.GPModel;
import org.epochx.gp.representation.GPCandidateProgram;
import org.epochx.life.GenerationAdapter;
import org.epochx.life.Life;
import org.epochx.op.selection.TournamentSelector;
import org.epochx.representation.CandidateProgram;
import org.epochx.stats.Stats;

import java.util.ArrayList;
import java.util.List;

import static org.epochx.stats.StatField.*;


public class GPClassifier extends Classifier {

    private GPWrapper wrapper = new GPWrapper();

    public GPClassifier(InstanceSet instanceSet)
    {
        this.wrapper.setInstanceSet(instanceSet);
        this.wrapper.buildClassifier();

    }

    @Override
    public int classifyInstance(Instance ins) {
        return wrapper.wapperClassifier(ins);
    }

    @Override
    public void printClassifier()
    {
        wrapper.print();
        System.out.println();
    }

    @Override
    public int classifierClass() {
        return wrapper.wrapperCC();
    }

    public class GPWrapper extends GPModel {

        private Variable[] variables;
        private InstanceSet instanceSet;

        public GPWrapper()
        {
            this.instanceSet = getInstanceSet();
            List<Node> syntax = new ArrayList<>();

            syntax.add(new SignumFunction());
            syntax.add(new SineFunction());
            syntax.add(new CosineFunction());
            //syntax.add(new ArcSecantFunction());
           // syntax.add(new AreaHyperbolicTangentFunction());
            syntax.add(new AbsoluteFunction());
            syntax.add(new AddFunction());
            //syntax.add(new DivisionProtectedFunction());
            syntax.add(new SubtractFunction());
            syntax.add(new CosecantFunction());

            variables = new Variable[Attributes.getNumAttributes()];

            for(int i=0; i< Attributes.getNumAttributes(); i++)
            {
                variables[i]=new Variable("ind"+i, Double.class);

                syntax.add(variables[i]);
            }
            setSyntax(syntax);


        }

        private void setInstanceSet(InstanceSet instanceSet) {
            this.instanceSet = instanceSet;
        }

        private InstanceSet getInstanceSet() {
            return instanceSet;
        }

        private GPWrapper buildClassifier()
        {
            setPopulationSize(600);
            setNoElites(10);
            setNoGenerations(30);
            setCrossoverProbability(0.3d);
            setReproductionProbability(0.03d);
            setMutationProbability(0.4);

            setProgramSelector(new TournamentSelector(this, 5));
           Life.get().addGenerationListener(new GenerationAdapter(){

                         public void onGenerationEnd() {
                     Stats.get().print(GEN_NUMBER, GEN_FITNESS_MIN, GEN_FITTEST_PROGRAM);
                 }
             });

            run();
            return this;
        }

        @Override
        public Class<?> getReturnType() {
            return Double.class;
        }

        @Override
        public double getFitness(CandidateProgram candidateProgram) {

            GPCandidateProgram p = (GPCandidateProgram) candidateProgram;
            double score = 0;
            for(Instance inst : this.instanceSet.getInstances())
            {
                for(int i=0; i<Attributes.getNumAttributes(); i++)
                {
                    variables[i].setValue(inst.getRealAttribute(i));
                }

                Double res = (Double) p.evaluate();
                if(res.intValue() == inst.getClassValue()) score++;


            }
            return (double) this.instanceSet.numInstances() - score;
        }

        private int wapperClassifier(Instance inst)
        {
            GPCandidateProgram p = (GPCandidateProgram) this.getProgramSelector().getProgram();

            for (int i=0; i<Attributes.getNumAttributes(); i++)
            {
                variables[i].setValue(inst.getRealAttribute(i));
            }

            return ((Double) p.evaluate()).intValue();
        }

        private int wrapperCC() {return -1;}

        private void print()
        {
            Stats.get().print(GEN_FITTEST_PROGRAM);
        }

    }

}
