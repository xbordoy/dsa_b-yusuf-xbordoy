import pandas as pd
import matplotlib.pyplot as plt
import csv


class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None


class BinarySearchTree:
    def __init__(self):
        self.root = None

    def insert(self, value):
        if self.root is None:
            self.root = Node(value)
        else:
            self._insert(value, self.root)

    def _insert(self, value, node):
        if value < node.value:
            if node.left is None:
                node.left = Node(value)
            else:
                self._insert(value, node.left)
        else:
            if node.right is None:
                node.right = Node(value)
            else:
                self._insert(value, node.right)

    # Visualize the tree
    def visualize(self):
        # Initialize x and y lists for each node
        self.x = []
        self.y = []

        def traverse(node, depth=0, pos=0):
            if node:
                # Store the x and y coordinates for the current node
                self.x.append(pos)
                self.y.append(-depth)
                # Recursively traverse the left and right subtrees
                traverse(node.left, depth + 1, pos * 2)
                traverse(node.right, depth + 1, pos * 2 + 1)

        # Call the traverse function to populate x and y
        traverse(self.root)
        # Plot the nodes and edges
        fig, ax = plt.subplots()
        for i in range(len(self.x)):
            ax.annotate(self.root.value, xy=(self.x[i], self.y[i]), xytext=(self.x[i], self.y[i]))
            if i > 0:
                parent_x = self.x[(i - 1) // 2]
                parent_y = self.y[(i - 1) // 2]
                ax.plot([parent_x, self.x[i]], [parent_y, self.y[i]], color='k')
        ax.set_aspect('equal')
        plt.show()


# Construct the binary search tree
tree = BinarySearchTree()

# Open the CSV file and read the data
with open('data.csv', 'r') as file:
    reader = csv.reader(file)
    # next(reader)  # Skip the header row
    for row in reader:
        values = int(row[0])  # Assuming the value is in the first column
        tree.insert(values)

tree.visualize()
