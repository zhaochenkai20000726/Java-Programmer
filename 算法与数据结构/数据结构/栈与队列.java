一.基础知识:
1.栈和队列的基本性质:
	(1).栈:先进后出
	(2).队列:先进先出
	(3).栈和队列在实现结构上可以有数组和链表两种形式
		①.数组结构实现比较容易
		②.链表结构复杂,因为牵扯到很多指针操作
2.栈结构的基本操作:
	(1).pop:弹出栈顶元素
	(2).top或peek:只访问不弹出;
	(3).push:从栈顶压入元素
	(4).size:栈元素的个数
3.队列的基本操作:
	与栈操作不同,push 操作为在队列头部加入元素,而pop元素在队列尾部弹出一个元素
	操作时间复杂度为常数,即 O(1)

4.深度优先遍历(DFS)与宽度优先遍历(BFS):
	(1).深度优先遍历:可以使用栈来实现遍历;
	(2).宽度优先遍历:可以使用队列来实现遍历

二.实例:
1.定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数(?????)

2.双栈队列:
	(1).编写一个类,只能使用两个栈结构来实现队列,支持队列的基本操作(add,poll,peek);
	(2).两个栈结构:一个是Push栈,即数据压入栈都是该栈,push往pop中倒入栈时必须一次倒入
		如果pop弹栈中存在数据,则不能将push的数据倒入pop栈
	(3).实现:
		public class TwoStack<E> {
			private Stack<E> stackPush;
			private Stack<E> stackPop;
			public TwoStack() {
				stackPush = new Stack<E>();
				stackPop = new Stack<E>();
			}
			public void push(E value) {
				while(!stackPop.empty()){
					stackPush.push(stackPop.pop());
				}
				stackPush.push(value);
			}
			public E pop() {
				while(!stackPush.empty()){
					stackPop.push(stackPush.pop());
				}
				return stackPop.pop();
			}
			public int[] twoStack(int[] ope, int n) {
				if (ope == null || ope.length == 0) {
					return null;
				}
				int[] arr = new int[ope.length];
				int len = 0;
				TwoStack<Integer> two = new TwoStack<Integer>();
				for (int i = 0; i < ope.length; i++) {
					if(ope[i] > 0){
						two.push(ope[i]);
					}else{
						arr[len++] = two.pop();
					}
				}
				return Arrays.copyOf(arr, len);
			}
		}
3.反转栈:
	

4.双栈排序练习题


5.滑动窗口练习题:双端队列
	5.1.描述:有一个整型数组 arr 和一个大小为 w 的窗口从数组的最左边滑到最右边,窗口每次向右边滑一个位置。 
		返回一个长度为n-w+1的数组res，res[i]表示每一种窗口状态下的最大值。 以数组为[4,3,5,4,3,3,6,7]，w=3为例。
		因为第一个窗口[4,3,5]的最大值为5，第二个窗口[3,5,4]的最大值为5，第三个窗口[5,4,3]的最大值为5。
		第四个窗口[4,3,3]的最大值为4。第五个窗口[3,3,6]的最大值为6。第六个窗口[3,6,7]的最大值为7。所以最终返回[5,5,5,4,6,7]。
		给定整形数组arr及它的大小n，同时给定w，请返回res数组。保证w小于等于n，同时保证数组大小小于等于500
		[4,3,5,4,3,3,6,7],8,3 ==> [5,5,5,4,6,7]
	5.2.思路:
		(1).双端队列,qmax,双端队列存放着数组中的下标值
		(2).假设当前数为arr[i],放入规则:
			如果qmax为空,直接把下标i放入队列;
			如果qmax不为空,取出当前qmax队尾存放的下标j,如果 arr[j]>arr[i],直接把下标i放入qmax的队尾;
			如果arr[j]<=arr[i],则一直从qmax的队尾弹出下标,直到某个下标在qmax中对应的值大于arr[i], 把i放入qmax的队尾
		(3).弹出规则:
			如果qmax的队头的下标等于 i-w,弹出qmax当前队头下标
	5.3.实现:
		public int[] slide(int[] arr, int w) {
			if (arr == null || arr.length == 0) {
				return arr;
			}
			int len = arr.length;
			LinkedList<Integer> qmax = new LinkedList<Integer>();
			int[] ret = new int[len - w + 1];
			int index = 0;

			for (int i = 0; i < arr.length; i++) {
				while(!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]){
					qmax.pollLast();
				}
				qmax.addLast(i);
				if(qmax.peekFirst() == i-w){
					qmax.pollFirst();
				}
				if(i >= w - 1){
					ret[index++] = arr[qmax.peekFirst()];
				}
			}
			return ret;
		}			
6.数组变树练习题





