import com.sun.source.tree.Tree;

import java.util.*;
public class CategoryManager {
    private Map<String, TreeSet<Transaction>> categories;

    public CategoryManager() {
        this.categories = new TreeMap<>();
    }

    public void addItem(String category, Transaction trans) {
        if (categories.containsKey(category)) {
            TreeSet<Transaction> tranSet = categories.get(category);
            tranSet.add(trans);
        } else {
            TreeSet<Transaction> newSet = new TreeSet<>(
                    Comparator.comparing(Transaction::getDescription)
                            .thenComparing(Transaction::getDate) // Use date as a secondary comparison
                            .thenComparingDouble(Transaction::getAmount) // Optionally add amount for uniqueness
            );
            newSet.add(trans);
            categories.put(category, newSet);
        }
    }

    public Set<Transaction> getTransByCategory(String category)
    {
        if (categories.containsKey(category))
        {
            return categories.get(category);
        }
        else
        {
            return null;
        }
    }

    class CategoryMaker {
        private Map<String, CategoryMaker> subs = new HashMap<>();
        private List<Transaction> trans = new ArrayList<>();
        private String name;

        public CategoryMaker(String info)
        {
            name = info;
        }

        public void addSubCategory(String subCat) //Didn't really use this or the other method as other methods provided access
        {
            subs.putIfAbsent(subCat, new CategoryMaker(subCat));
        }

        public void addTrans(Transaction transaction)
        {
            trans.add(transaction);
        }
    }
}

