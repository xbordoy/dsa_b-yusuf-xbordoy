class BinaryTree:
    def __init__(self, val=None):
        self.left = None
        self.right = None
        self.val = val

    def insert(self, val):
        # Current value == null - nothing
        if not self.val:
            self.val = val
            return

        # New value == Current value
        if val == self.val:
            return

        # New value < current value - left child
        if val < self.val:
            if self.left:
                self.left.insert(val)
                return
            self.left = BinaryTree(val)
            return

        # New value > Current value - basically "else"
        if self.right:
            self.right.insert(val)
            return
        self.right = BinaryTree(val)
    
    def find(self, val):
        # current value is nothing
        if self == None:
            return
        
        # New value == Current value
        if val == self.val:
            return

    def delete(self, val):
        # current value is nothing
        if self == None:
            return self
        
        # value to be deleted < current node value
        if val < self.val:
            self.left = self.left.delete(val)
            return self
        
        # value to be deleted > current node value
        if val > self.val:
            self.right = self.right.delete(val)
            return self
        
        # does not contain a right child node
        if self.right == None:
            return self.left
        
        # does not contain a left child node
        if self.left == None:
            return self.right
        
        min_larger_node = self.right
        
        while min_larger_node.left:
            min_larger_node = min_larger_node.left
        
        self.val = min_larger_node.val
        self.right = self.right.delete(min_larger_node.val)
        return self
        
    # Print the tree
    def print(self):
        if self.left:
            self.left.print()
        print(self.val),
        if self.right:
            self.right.print()

if __name__ == '__main__':
    nums = [12, 6, 18, 19, 21, 11, 3, 5, 4, 24, 18]
    my_tree = BinaryTree()
    for num in nums:
        my_tree.insert(num)

    my_tree.print()