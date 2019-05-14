package search;

import agentSearch.Problem;
import agentSearch.Solution;

public interface SearchMethod {

   Solution search(Problem problem);
   
   Statistics getStatistics();
   
   void stop();
   
   boolean hasBeenStopped();
}