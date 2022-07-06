public class Main {
    public static void main(String[] args) {
/*
A
  \
   C
 /    \
B     E   
*/
        Bst<Character, Integer> t = new Bst<>();
        t.put('A', 1);
        t.put('C', 3);
        t.put('B', 2);
        t.put('E', 8);
        t.print();
        System.out.printf("min %s %n",t.min());
        System.out.printf("max %s %n",t.max());

        for(var k : t.keys()) {
            System.out.printf("%s %s %n", k, t.get(k));
        }
        System.out.printf("Substree %d %n",t.size());
        System.out.printf("Floor D %s %n",t.floor('D'));
        System.out.printf("Ceiling D %s %n",t.ceiling('D'));
        System.out.printf("Select 2 %s %n",t.select(2));
        System.out.printf("Rank B %s %n",t.rank('B'));
        
        new TreeUtil<Character, Integer>().printFmt(t);

        System.out.println("After deleteMin()");
        t.deleteMin();
        new TreeUtil<Character, Integer>().printFmt(t);
        
        System.out.println("After delete(C)");
        t.delete('C');
         new TreeUtil<Character, Integer>().printFmt(t);
        
    }
}