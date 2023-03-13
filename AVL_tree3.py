import random
import time
import sys


# Create a tree node
class Node(object):
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None
        self.height = 1

class AVLTree(object):
    def insert (self, key):
        new_node = Node (key)
        if not self.rootNode:
            self.rootNode = new_node
        else:
            if not self.find(key):
                self.elements_count += 1
                self.add_as_child (self.rootNode, new_node)

    def add_as_child (self, parent_node, child_node):
        node_to_rebalance = None
        if child_node.key < parent_node.key:
            if not parent_node.leftChild:
                parent_node.leftChild = child_node
                child_node.parent = parent_node
                if parent_node.height == 0:
                    node = parent_node
                    while node:
                        node.height = node.max_children_height() + 1
                        if not node.balance () in [-1, 0, 1]:
                            node_to_rebalance = node
                            break #we need the one that is furthest from the root
                        node = node.parent     
            else:
                self.add_as_child(parent_node.leftChild, child_node)
        else:
            if not parent_node.rightChild:
                parent_node.rightChild = child_node
                child_node.parent = parent_node
                if parent_node.height == 0:
                    node = parent_node
                    while node:
                        node.height = node.max_children_height() + 1
                        if not node.balance () in [-1, 0, 1]:
                            node_to_rebalance = node
                            break #we need the one that is furthest from the root
                        node = node.parent       
            else:
                self.add_as_child(parent_node.rightChild, child_node)
        
        if node_to_rebalance:
            self.rebalance (node_to_rebalance)
    


# Generate n random integers between 1 and 1000
n = 1000
numbers = [random.randint(1, 1000) for _ in range(n)]

# Insert each number into the AVL tree and measure the time taken for each insert
myTree = AVLTree()
root = None
times = []
for num in numbers:
    start = time.time()
    root = myTree.insert(root, num)
    end = time.time()
    times.append(end - start)

# Print the average time taken for each insert
print(f"Average time taken for each insert: {sum(times)/n:.10f} seconds")