# Implementation of Binary Search Tree
According to R. Sedgewick's book Algorithms with described Symbol table operations.

Also added TreeUtil.printFmt to print a diagram of the graph.

Example output
```
A
B
C
E
min A 
max E 
A 1 
B 2 
C 3 
E 8 
Substree 4 
Floor D C 
Ceiling D E 
Select 2 Bst$Node@42a57993 
Rank B 1 
        
         A
           \  
             C
           /   \  
         B       E
After deleteMin()
        
         C
       /   \  
     B       E
After delete(C)
      
       E
     /
   B  
```