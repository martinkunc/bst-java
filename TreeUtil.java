import java.util.HashMap;
import java.util.LinkedList;



public class TreeUtil<Key extends Comparable<Key>, Value> {
    public  void printFmt(Bst<Key,Value> bst) {
        var nodes = new LinkedList<GraphNode>();
        printFmt(bst.root, 0, 0, 0, null,nodes);

        int maxLevel = Integer.MIN_VALUE;
        int maxLeft = Integer.MIN_VALUE;
        int maxRight = Integer.MIN_VALUE;
        int maxKeyLen = Integer.MIN_VALUE;
        int maxNodes = Integer.MIN_VALUE;
        for (var n : nodes) {
            if (n.level > maxLevel) {
                maxLevel = n.level;
            }
            if (n.nodes.size() > maxNodes) {
                maxNodes = n.nodes.size();
            }
            for (var nn : n.nodes) {
                if (nn.leftSize > maxLeft) {
                    maxLeft = nn.leftSize;
                }
                if (nn.rightSize > maxRight) {
                    maxRight = nn.rightSize;
                }
                if (nn.node.key.toString().length() > maxKeyLen) {
                    maxKeyLen = nn.node.key.toString().length();
                }
            }
        }
        maxKeyLen = maxKeyLen * 2 ;
        
        for (var r = 0; r <= maxLevel; r++) {
            
            for (var rnl : nodes) {
                if (rnl.level == r) {

                    HashMap<Integer, GraphNode> pos2Node = new HashMap<>();
                    HashMap<Integer, GraphNode> slashPos2Node = new HashMap<>();
                    int maxPos = Integer.MIN_VALUE;

                    for(var rn : rnl.nodes) {
                        int rnPos = -1;
                        int slashPos = -1;
                        if (r == 0) {
                            rn.pos = maxNodes + 2;
                            rnPos = maxNodes + 2;
                            slashPos = maxNodes + 2;
                        } else {
                            if (rn.node == rn.parent.node.left) {
                                rn.pos = rn.parent.pos - 2;
                                rnPos = rn.parent.pos - 2;
                                slashPos = rn.parent.pos - 1;
                            } 
                            else if (rn.node == rn.parent.node.right) {
                                rn.pos = rn.parent.pos + 2;
                                rnPos = rn.parent.pos + 2;
                                slashPos = rn.parent.pos + 1;
                            }
                        }
                        if (rn.pos > maxPos) {
                            maxPos = rn.pos;
                        }
                        if (slashPos > maxPos) {
                            maxPos = slashPos;
                        }
                        pos2Node.put(rnPos, rn);
                        slashPos2Node.put(slashPos, rn);
                    }
                    int ml = maxLeft;
                    String row = "";

                    for (int i = 0; i <= maxPos; i++) {
                        var cn = slashPos2Node.get(i);
                        
                        if (cn != null && cn.parent != null) {
                            if (cn.parent.node.left == cn.node) {
                                row += padLeft("/", maxKeyLen);
                            } 
                            else if (cn.parent.node.right == cn.node) {
                                row += padLeft("\\", maxKeyLen);
                            } 
                        } else if (cn == null || cn.parent != null) {
                            row += padLeft("", maxKeyLen);
                        }
                    }
                    if (row != "") {
                        System.out.println(row);
                    }
                    row = "";
                    for (int i = 0; i <= maxPos; i++) {
                        var cn = pos2Node.get(i);
                        
                        if (cn != null) {
                            row += padLeft(cn.node.key.toString(), maxKeyLen);
                        } else if (cn == null) {
                            row += padLeft("", maxKeyLen);
                        }
                    }
                    System.out.println(row);
                    
                }
            }
        }
    }

    private String padLeft(String key, int padding) {
        String row = "";
        for(int k=0; k < padding - key.length(); k++) {
            row += " ";
        }
        return row + key;
    }

    private void printFmt(Bst<Key,Value>.Node x, int level, int leftSize, int rightSize, GraphNode parent, LinkedList<GraphNode> nodes) {
        if (x == null)
            return;
        var gn = new GraphNode();
        gn.level = level;
        gn.leftSize = leftSize;
        gn.rightSize = rightSize;
        gn.node = x;
        gn.parent = parent;
            
        printFmt(x.left, level + 1, leftSize + 1, rightSize, gn, nodes);
        boolean found = false;
        for (var n : nodes) {
            if (n.level == level) {
                
                if (n.nodes == null) {
                    n.nodes = new LinkedList<GraphNode>();
                }
                n.nodes.add(gn);
                found = true;
                break;
            }
        }
        if (!found) {
            
            var n = new GraphNode();
            n.level = level;
            n.leftSize = leftSize;
            n.rightSize = rightSize;
            n.node = x;
            n.parent = parent;
            if (n.nodes == null) {
                n.nodes = new LinkedList<GraphNode>();
            }
            n.nodes.add(gn);
            nodes.add(n);
        }

        printFmt(x.right, level + 1, leftSize, rightSize + 1, gn, nodes);
    }

    public class GraphNode {
        public int level;
        public int leftSize;
        public int rightSize;
        public Bst<Key, Value>.Node node;
        public int pos;
        public LinkedList<GraphNode> nodes;
        public GraphNode parent;

        public GraphNode() {
        }
    }
}