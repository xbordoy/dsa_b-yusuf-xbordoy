# Datastructures and algorithms

# 1) Introduction

In this assignment, we will be implementing, testing and comparing the results from four diffrent types of data structures. The programing language for the implementations will be in python. 

# 2) Objective:
The primary objective of the task is to: 
 > ***Implement and then compare 4 implementations of data structures in terms of the effectiveness of insert, delete and search operations in different situations***
 
 Also, In the technical documentation, your task is to 
 > **document all implemented data structures and provide detailed testing scenarios, based on which you have found out in which situations which of these implementations are more effective.**

And finally, it is also required to 
> **submit a program that is used to test and measure the effectiveness of these implementations as a single source file (it contains the main function).**

# 3) Implementation of the AVL Tree
The AVL-Tree is a "self-balancing" binary search tree, the major difference between a typical binary search tree is that, if we create a tree  from the values [3, 2, 1], we would end up with a highly skewed tree
```java
        3
       /
      2
     /
    1
```
whereas in the AVL tree this would result in 
```java
      2
     / \
    1   3
```
