/**
 * Copyright (c) 2013,
 * Tomasz Choma, Olgierd Grodzki, Łukasz Potępa, Monika Rakoczy, Paweł Synowiec, Łukasz Szarkowicz
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package forecasting;

import forecasting.model.Chromosome;
import forecasting.model.SlidingTimeWindow;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.Year;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeSeriesForecast extends AbstractForecast{

    private static final int RANGE_MIN = 0;
    private static final int RANGE_MAX = 1;
    private double percentOfKeptFromSelection = 0.4;
    private double percentOfKeptFromCrossover = 0.4;
    private double percentOfKeptFromMutation = 0.2;

    @Autowired
    private AbstractGeneticAlgorithmOperation selection;

    @Autowired
    private AbstractGeneticAlgorithmOperation crossover;

    @Autowired
    private AbstractGeneticAlgorithmOperation mutation;

    @Autowired
    private AbstractFitnessCalculator fitnessCalculator;

    @Autowired
    private AbstractForecastCalculator forecastCalculator;

    private TimeSeries timeSeries;
    private int populationSize;
    private SlidingTimeWindow slidingTimeWindow;
    private int numOfIterations;
    private int numOfDataPoints;

    private boolean doForecast = false;
    private Chromosome globalBest;

    private List<GAObserver> obs = new ArrayList<>();

    public void initializeForecast(int numOfDataPoints){
        this.numOfDataPoints = numOfDataPoints;
        this.doForecast = true;
    }

    /**
     * 初始化遗传算法
     */
    public void initializeGeneticAlgorithm(TimeSeries timeSeries,
                                           int populationSize,
                                           SlidingTimeWindow slidingTimeWindow,
                                           int numOfIterations,
                                           double crossoverProbability,
                                           double mutationProbability,
                                           double percentOfKeptFromSelection ,
                                           double percentOfKeptFromCrossover,
                                           double percentOfKeptFromMutation){

        this.populationSize = populationSize;
        this.slidingTimeWindow = slidingTimeWindow;
        this.numOfIterations = numOfIterations;
        this.percentOfKeptFromSelection = percentOfKeptFromSelection;
        this.percentOfKeptFromCrossover = percentOfKeptFromCrossover;
        this.percentOfKeptFromMutation = percentOfKeptFromMutation;
        crossover.setProbability(crossoverProbability);
        mutation.setProbability(mutationProbability);
        this.timeSeries = timeSeries;

    }

    /**
     * 初始化染色体
     * @param populationSize
     * @param stw
     * @return
     */
    private Chromosome[] initializePopulation(int populationSize, SlidingTimeWindow stw){
    	System.out.println("populationsize:"+populationSize+"slidingtimewindow:"+slidingTimeWindow);

        Chromosome[] population = new Chromosome[populationSize];

        int chromosomeSize = stw.getLength() + 1;

        if(forecastCalculator instanceof ARMAForecastCalculator){
            chromosomeSize += stw.getLength();
        }

        Random random = new Random();

        for(int i = 0; i < population.length; i++){

            double[] genes = new double[chromosomeSize];

            for(int j = 0; j < genes.length; j++){
                genes[j] = RANGE_MIN + (RANGE_MAX - RANGE_MIN) * random.nextDouble();
            }

            population[i] = new Chromosome(genes);
        }
        return population;
    }

    /**
     * 计算每个染色体的适应值
     * @param population
     * @param timeSeries
     * @param window
     */
    private void calculateFitnessForPopulation(Chromosome[] population,
                                               TimeSeries timeSeries,
                                               SlidingTimeWindow window){
   // 	System.out.println("染色体个数："+population.length);
        for(Chromosome chromosome : population){
   //     	System.out.println("每个染色体中的基因:"+Arrays.toString(chromosome.getGenes()));
        	for(int i=0;i<window.getLength();i++){
     //   	      	System.out.print("window："+window.getValueAt(i));
        	}
        	double fitness = fitnessCalculator.calculateFitness(timeSeries, window, chromosome);
            //System.out.println("Fitness:"+fitness);
            chromosome.setFitness(fitness);
          //  System.out.println("----------------------------------------");
        }
    }

    /**
     * 获取最小的适应值
     * @param population
     * @return
     */
    private Chromosome findBestInPopulation(Chromosome[] population){
        Chromosome best = population[0];
        for(int i = 1; i < population.length; i++){
            if(population[i].getFitness() < best.getFitness()){
                best = population[i];
            }
        }
        return best;
    }

    /**
     * 通过遗传算法获得最佳适应值那个染色体
     */
    @Override
    protected Chromosome doInBackground() throws Exception {

        globalBest = null;

        //初始化染色体
        Chromosome[] population = initializePopulation(populationSize, slidingTimeWindow);
        calculateFitnessForPopulation(population, timeSeries, slidingTimeWindow);

        for(int i = 0; i < numOfIterations; i++){

            calculateFitnessForPopulation(population, timeSeries, slidingTimeWindow);

            Chromosome[] selectionPopulation = selection.performGeneticOperation(population);//通过轮盘获得的新染色体集合
           // System.out.println("通过轮盘选择获得的染色体集合：");
            for (int sn=0;sn<selectionPopulation.length;sn++){
            	//System.out.println(sn+" "+Arrays.toString(selectionPopulation[sn].getGenes()));
            }
            calculateFitnessForPopulation(selectionPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(selectionPopulation);

            Chromosome[] crossoverPopulation = crossover.performGeneticOperation(population);
            //System.out.println("通过交叉获得的染色体集合：");
            for (int sn=0;sn<crossoverPopulation.length;sn++){
            //System.out.println(sn+" "+Arrays.toString(crossoverPopulation[sn].getGenes()));
            }
            calculateFitnessForPopulation(crossoverPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(crossoverPopulation);

            Chromosome[] mutationPopulation = mutation.performGeneticOperation(population);
         //   System.out.println("通过变异后获得的染色体集合：");
            for (int sn=0;sn<mutationPopulation.length;sn++){
            	//System.out.println(sn+" "+Arrays.toString(mutationPopulation[sn].getGenes()));
            }
            calculateFitnessForPopulation(mutationPopulation, timeSeries, slidingTimeWindow);
            Arrays.sort(mutationPopulation);

            int numOfKeptFromSelection = (int) (percentOfKeptFromSelection * populationSize);

            int numOfKeptFromCrossover = (int) (percentOfKeptFromCrossover * populationSize);

            int numOfKeptFromMutation = (int) (percentOfKeptFromMutation * populationSize);

            while(numOfKeptFromSelection +
                    numOfKeptFromCrossover +
                    numOfKeptFromMutation > populationSize){
                numOfKeptFromSelection--;
            }

            while(numOfKeptFromSelection +
                    numOfKeptFromCrossover +
                    numOfKeptFromMutation < populationSize){
                numOfKeptFromSelection++;
            }

            System.arraycopy(selectionPopulation, 0, population, 0, numOfKeptFromSelection);

            System.arraycopy(crossoverPopulation, 0, population, numOfKeptFromSelection, numOfKeptFromCrossover);

            System.arraycopy(mutationPopulation, 0, population, numOfKeptFromSelection + numOfKeptFromCrossover, numOfKeptFromMutation);
           // System.out.println("通过遗传算法后获得的染色体集合：");
            for (int sn=0;sn<population.length;sn++){
            //	System.out.println(sn+" "+Arrays.toString(population[sn].getGenes()));
            }
            Chromosome best = findBestInPopulation(population);
            
            if(globalBest == null || globalBest.getFitness() > best.getFitness()){
                globalBest = best;
            }

            for(GAObserver observer : obs){
                observer.update(best.getFitness(), best.getGenes(), i);
            }
        }

        return globalBest;
    }

    @Override
    public TimeSeries doForecast(TimeSeries timeSeries,
                                 SlidingTimeWindow slidingTimeWindow,
                                 int numOfDataPoints,
                                 double[] genes){

        Chromosome chromosome = new Chromosome(genes);
        Date date =timeSeries.getNextTimePeriod().getEnd();

        for(int i = 0; i < numOfDataPoints; i++){

            double forecast = forecastCalculator.calculateForecast(timeSeries,
                    slidingTimeWindow,
                    chromosome,
                    timeSeries.getItemCount());
            System.out.println("当前时间点:"+timeSeries.getNextTimePeriod().getEnd().toString());
            Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.add(Calendar.MONTH, 1);
			Date nextDate=calendar.getTime();
			date =nextDate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			System.out.println("预测时间"+sdf.format(nextDate)+"预测值："+forecast);
			timeSeries.add(new Day(nextDate), forecast);
        }
        return timeSeries;
    }

    public void addObserver(GAObserver o){
        obs.add(o);
    }

    /**
     * 预测开始
     */
    @Override
    protected void done() {

        if(doForecast){
            doForecast(timeSeries, slidingTimeWindow, numOfDataPoints, globalBest.getGenes());
        }

        for(GAObserver observer : obs){
            observer.done(timeSeries);
            observer.done(globalBest.getGenes());
        }
    }
}
