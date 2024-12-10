import java.util.*;
public class GoalManager
{
    private Map<String, Goal> goalGraph;

    public GoalManager()
    {
        goalGraph = new HashMap<>();
    }

    public void addGoal(Goal goal)
    {
        if(!goalGraph.containsKey(goal.getName()))
        {
            goalGraph.put(goal.getName(), goal);
        }
    }

    public void removeGoal(String goalName)
    {
        if(goalGraph.containsKey(goalName))
        {
            goalGraph.remove(goalName);
        }
    }

    public Goal getGoal(String goalName)
    {
        if(goalGraph.containsKey(goalName))
        {
            return goalGraph.get(goalName);
        }
        return null;
    }

    public Collection<Goal> getGoals()
    {
        return goalGraph.values();
    }
}
