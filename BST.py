class BSTNode:
    def __init__(self, val=None):
        self.left = None
        self.right = None
        self.val = val

    def insert(self, val):
        if not self.val:
            self.val = val
            return

        if self.val == val:
            return

        if val < self.val:
            if self.left:
                self.left.insert(val)
                return
            self.left = BSTNode(val)
            return

        if self.right:
            self.right.insert(val)
            return
        self.right = BSTNode(val)

    # Print the tree
    def PrintTree(self):
        if self.left:
            self.left.PrintTree()
        print( self.val),
        if self.right:
            self.right.PrintTree()

nums = [12, 6, 18, 19, 21, 11, 3, 5, 4, 24, 18]
my_tree = BSTNode()
for num in nums:
    my_tree.insert(num)

my_tree.PrintTree()