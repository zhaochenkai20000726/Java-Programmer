1.JVM 结构?运行时数据区域的包括哪些?
	1.1.程序计数器:(PC 寄存器)
		(1).是一块较小的内存空间,可以看作是当前线程所执行字节码的行号指示器;
		(2).每条线程都需要一个独立的程序计数器.
		(3).如果线程正在执行的是一个 Java 方法,计数器记录的是正在执行的虚拟机字节码指令的地址;
			如果正在执行的是 Native 方法,这个计数器的值为空.
		(4).程序计数器是唯一一个没有规定任何 OutOfMemoryError 的区域
	1.2.Java 虚拟机栈:
		(1).是线程私有的,生命周期与线程相同.
		(2).虚拟机栈描述的是 Java 方法执行的内存模型:每个方法被执行的时候都会创建一个栈帧(Stack Frame)存储:
			局部变量表、操作栈、动态链接、方法出口.
			每一个方法被调用到执行完成的过程,就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程;
		(3).这个区域有两种异常情况:
			StackOverflowError-线程请求的栈深度大于虚拟机所允许的深度
			OutOfMemoryError-虚拟机栈扩展到无法申请足够的内存时
	1.3.本地方法栈:线程私有
		(1).虚拟机栈为虚拟机执行 Java 方法(字节码)服务.
		(2).本地方法栈(Native Method Stacks)为虚拟机使用到的 Native 方法服务
	1.4.Java 堆:线程共享
		(1).Java 堆(Java Heap)是 Java 虚拟机中内存最大的一块.Java 堆在虚拟机启动时创建,被所有线程共享;
		(2).作用:存放对象实例.垃圾收集器主要管理的就是 Java 堆.Java 堆在物理上可以不连续,只要逻辑上连续即可.
	1.5.方法区:线程共享,非堆
		(1).用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据.
		(2).不需要连续的内存,可以选择固定的大小,更可以选择不实现垃圾收集;
	1.6.运行时常量池:
		是方法区的一部分.保存 Class 文件中的符号引用、翻译出来的直接引用.运行时常量池可以在运行期间将新的常量放入池中
	1.7.直接内存:
		其并不是虚拟机运行时数据区的一部分,也不是Java虚拟机规范中定义的内存区域.它直接从操作系统中分配
2.Java 对象是如何访问的?
	Object obj = new Object(); 最简单的访问,也会涉及到 Java 栈、Java 堆、方法区这三个最重要内存区域
	Object obj => 如果出现在方法体中,则上述代码会反映到 Java 栈的本地变量表中,作为 reference 类型数据出现
	new Object() => 反映到 Java 堆中,形成一块存储了 Object 类型所有对象实例数据值的内存.Java 堆中还包
					含对象类型数据的地址信息,这些类型数据存储在方法区中
3.如何判断对象是可回收的? 引用计数法? 根搜索算法?
	3.1.判断一个对象可以回收:
		(1).引用计数法
		(2).GC Roots(根搜索,可达性分析)
	3.2.引用计数法:
		(1).给对象中添加一个引用计数器,每当有一个地方引用他时,计数器就加 1;当引用失效时,引用计时器就减 1;
			任何时刻计数器为 0 的对象就是不可能再被使用;
		(2).缺点:很难解决对象之间的循环引用问题
			如父对象有一个对子对象的引用,子对象反过来引用父对象.这样,他们的引用计数永远不可能为 0;
	3.3.根搜索算法:
		(1).基本思路:通过一系列的成为"GC Roots"的对象作为起始点,从这些节点开始向下搜索,搜索所走过的路径成为
			引用链(Reference Chain),当一个对象的 GC Roots 没有任何引用链相连(用图论的话说,就是从 GC Roots
			到这个对象不可达)时,证明此对象是不可用的;
		(2).GC Roots 对象:
			虚拟机栈中的引用的对象 
			方法区中静态属性引用的对象,常量引用的对象 
			本地方法栈中JNI(即一般说的Native方法)引用的对象
4.Java 的4种引用方式?强引用,软引用,弱引用,虚引用
	4.1.Java 对引用的概念进行了扩充:
		强引用,软引用,弱引用,虚引用
	4.2.强引用(String Reference)
		Object obj = new Object();
		只要强引用还在,垃圾收集器永远不会回收掉被引用的对象.
	4.3.软引用(Soft Reference)
		用来描述一些还有用,但并非必须的对象.软引用所关联的对象,有在系统将要发生内存溢出异常之前
		将会把这些对象列进回收范围,并进行第二次回收
	4.4.弱引用(Weak Reference)
		描述非必须的对象,强度比软引用更弱一些,被弱引用关联的对象,只能生存到下一次垃圾收集发生前
	4.5.虚引用(Phantom Reference)
		它是最弱的一种引用关系;一个对象是否有虚引用的存在完全不会对其生存时间构成影响,也无法通过虚引用来取得一个对象实例;
		为一个对象设置虚引用关联的唯一目的:能在这个对象被垃圾收集器回收时收到一个系统通知;
5.垃圾收集算法:标记清除,复制算法,标记整理,分代收集?有哪些收集器?
	5.1.垃圾收集算法:参考文件:/Java知识点/Java/JavaSE/Java-JVM/Java-GC垃圾回收机制.java
	5.2.垃圾收集器:内存回收的具体实现,参考文件:/Java知识点/Java/JavaSE/Java-JVM/Java-GC垃圾回收机制.java
6.Minor GC 和 Full GC 有什么区别?
	6.1.Minor GC:从年轻代空间(包括 Eden 和 Survivor 区域)回收内存被称为 Minor GC.主要使用复制算法
		(1).当 JVM 无法为一个新的对象分配空间时会触发 Minor GC,比如当 Eden 区满了.所以分配率越高,越频繁执行 Minor GC;
		(2).执行 Minor GC 操作时,不会影响到永久代.从永久代到年轻代的引用被当成 GC roots,从年轻代到永久代的引用在标记阶段被直接忽略掉
		(3).所有的 Minor GC 都会触发"全世界的暂停(stop-the-world)"停止应用程序的线程.大部分 Eden 区中的对象都能被认为是垃圾,
			永远也不会被复制到 Survivor 区或者老年代空间.如果正好相反,Eden 区大部分新生对象不符合 GC 条件,
			Minor GC 执行时暂停的时间将会长很多;
	6.2.Full GC 和 Major GC --- Major GC 是清理永久代,Full GC 是清理整个堆空间—包括年轻代和永久代
		标记整理算法.
		速度一般比 Minor GC 慢10倍以上
7.Java 内存:为什么堆内存分区?堆内存分为哪几块?分代收集算法
	7.1.为什么堆内存要分区?
		对于一个大型的系统,当创建的对象及方法变量比较多时,即堆内存中的对象比较多,如果逐一分析对象是否该回收,效率很低.
		分区是为了进行模块化管理,管理不同的对象及变量,以提高 JVM 的执行效率;
	7.2.堆内存分为哪几块:
		Young Generation Space 新生区(也称新生代)
		Tenure Generation Space 养老区(也称老生代)
		Permanent Space 永久代
			==> 方法区,方法区和"PermGen Space"又有着本质的区别.前者是 JVM 的规范,而后者则是 JVM 规范的一种实现.
				并且只有 HotSpot 才有"PermGen Space"
	7.3.内存分配有哪些原则:
		(1).对象优先分配在 Eden
		(2).大对象直接进入老年代
		(3).长期存活的对象将进入老年代
		(4).动态对象年龄判定
		(5).空间分配担保
	7.4.
8.Class 字节码文件
	[/Java知识点/Java/JavaSE/Java-JVM/JVM-Java虚拟机.java]----5.Class 类文件结构
9.类加载器:类加载器的作用?有哪些类加载器?
	[/Java知识点/Java/JavaSE/Java-JVM/JVM-Java虚拟机.java]----6.虚拟机类加载机制:
10.类加载机制:双亲委派模型?为什么要使用双亲委派模型?
	[/Java知识点/Java/JavaSE/Java-JVM/JVM-Java虚拟机.java]----6.虚拟机类加载机制:
11.虚拟机和物理机的区别是什么?
12.运行时栈桢结构
13.Java 方法调用:
14.基于栈的指令集和基于寄存器的指令集
15.Javac 编译过程分为哪些步骤?
16.什么是即时编译器?
17.解释器和编译器分别是什么?
18.编译对象与触发条件:
19.经典的优化技术
20.如果对象不会逃逸到方法或线程外,可以做什么优化?
21.Java与C/C++的编译器对比
22.物理机如何处理并发问题?
23.Java 内存模型:什么是 Java 内存模型?Java 内存模型的目标?原子性,可见性,有序性
24.violate 关键字作用
26.虚拟机性能监控命令
	jps:虚拟机进程状况工具
	jstat:虚拟机统计信息监视工具
	jinfo:Java 配置信息
	jmap:Java 内存映像工具
	jhat:虚拟机堆转储快照分析工具
	jstack:Java 堆栈跟踪工具
27.虚拟机故障处理工具(可视化)
	JConsole:Java 监视与管理控制台 
	VisualVM:多合一故障处理工具
28.调优:Thread Dump,分析内存结构
29.JVM 各个版本的新特性
30.JVM 的启动参数
31.Java 的内存溢出与C++的内存溢出
32.自动内存管理机制
33.Java GC 是在什么时候,对什么东西,做了什么事情?
	33.1.什么时候:
		(1).系统空闲的时候
		(2).系统自身决定,不可预测的时间/调用 System.gc 的时候
		(3).说出新生代和老年代的结构,并说出MinorGC和FullGC
		(4).MinorGC 和 FullGC 触发的条件,OOM 触发的条件
	33.2.对什么东西:
		(1).不使用的对象
		(2).超出作用域的对象/引用计数为空的对象
		(3).从gc root开始搜索,搜索不到的对象
		(4).从root搜索不到,而且经过第一次标记、清理后,仍然没有复活的对象
	33.3.做了什么事情:
		(1).删除不使用的对象,释放内存空间
		(2).停止其他线程执行、运行finalize;
		(3).新生代做的是复制清理、from survivor、to survivor是干啥用的、老年代做的是标记清理、标记清理后碎片要不要整理、
			复制清理和标记清理有有什么优劣势
		(4).




