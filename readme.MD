# Trains Problem 

@author 常文涛 

@date   2018.10.30

## 分析：

问题描述分为三类：

### 1.获取指定路径的距离

这个相对比较简单解决，每相邻两个town之间的距离加起来即可

### 2.最短路径

这个使用到迪杰斯特拉的最短路径算法，用到优先队列，借助最小堆的算法进行路径搜索

### 3.非最短路径

主要关注stops的个数和最远的距离,使用普通的链表，指定遍历的停止条件


## 运行方法

Main类内的main方法即可


Problem description:

The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks are 'one-way.'  That is, a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.  In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!
 
The purpose of this problem is to help the railroad provide its customers with information about the routes.  In particular, you will compute the distance along a certain route, the number of different routes between two towns, and the shortest route between two towns.
 
Input:  

A directed graph where a node represents a town and an edge represents a route between two towns.  The weighting of the edge represents the distance between the two towns.  A given route will never appear more than once, and for a given route, the starting and ending town will not be the same town.
 
Output: 

For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'.  Otherwise, follow the route as given; do not make any extra stops!  For example, the first problem means to start at city A, then travel directly to city B (a distance of 5), then directly to city C (a distance of 4).

The distance of the route A-B-C.

The distance of the route A-D.

The distance of the route A-D-C.

The distance of the route A-E-B-C-D.

The distance of the route A-E-D.

The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).

The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).

The length of the shortest route (in terms of distance to travel) from A to C.

The length of the shortest route (in terms of distance to travel) from B to B.

The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.


Test Input:

For the test input, the towns are named using the first few letters of the alphabet from A to D.  A route between two towns (A to B) with a distance of 5 is represented as AB5.

Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7

Expected Output:

Output #1: 9

Output #2: 5

Output #3: 13

Output #4: 22

Output #5: NO SUCH ROUTE

Output #6: 2

Output #7: 3

Output #8: 9

Output #9: 9

Output #10: 7