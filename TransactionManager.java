import java.util.*;
public class TransactionManager
{
    private Deque<Transaction>  transOrganizer;

    public TransactionManager()
    {
        transOrganizer = new ArrayDeque<>();
    }

    public void addTransactionAtStart(Transaction trans)
    {
        transOrganizer.addFirst(trans);
    }

    public void addTransactionAtEnd(Transaction trans)
    {
        transOrganizer.addLast(trans);
    }

    //Running out of time but wanted to add a remove button for transactions as well
    public void removeTransactionAtStart()
    {
        transOrganizer.pollFirst();
    }

    public void removeTransactionAtEnd()
    {
        transOrganizer.pollLast();
    }

}
