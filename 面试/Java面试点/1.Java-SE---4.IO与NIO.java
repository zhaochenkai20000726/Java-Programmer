1.序列化与反序列化:
	1.1.ArrayList 如何实现序列化
		(1).为什么 transient Object[] elementData;
			ArrayList 实际上是动态数组，每次在放满以后自动增长设定的长度值,如果数组自动增长长度
			设为100,而实际只放了一个元素,那就会序列化 99 个 null 元素.为了保证在序列化的时候不
			会将这么多 null 同时进行序列化,	ArrayList 把元素数组设置为 transient
		(2).为什么要写方法:writeObject and readObject
			前面提到为了防止一个包含大量空对象的数组被序列化，为了优化存储，所以，ArrayList 
			使用 transient 来声明elementData
			作为一个集合,在序列化过程中还必须保证其中的元素可以被持久化下来，
			所以,通过重写writeObject 和 readObject方法的方式把其中的元素保留下来
			writeObject方法把elementData数组中的元素遍历的保存到输出流（ObjectOutputStream）中。
			readObject方法从输入流（ObjectInputStream）中读出对象并保存赋值到elementData数组中
	1.2
2.InputStream、OutputStream、Reader、Writer 的继承体系
3.IO 框架主要用到什么设计模式:装饰模式
4.NIO 包有哪些结构?分别起到的作用?
5.NIO 针对什么情景会比 IO 有更好的优化?为什么使用NIO? NIO 有什么优势?
6.String 编码UTF-8 和GBK的区别?
7.什么时候使用字节流、什么时候使用字符流?
8.Java 中的 BIO,NIO,AIO 分别是什么?
	(1).BIO:同步并阻塞,服务器实现模式为一个连接一个线程,即客户端有连接请求时服务器端就需要启动一个线程进行处理,如果这个连接
		不做任何事情会造成不必要的线程开销,当然可以通过线程池机制改善
		BIO 方式适用于连接数目比较小且固定的架构,这种方式对服务器资源要求比较高,并发局限于应用中,JDK1.4以前的唯一选择
	(2).NIO:同步非阻塞,服务器实现模式为一个请求一个线程,即客户端发送的连接请求都会注册到多路复用器上,多路复用器轮询到连接
		有 I/O 请求时才启动一个线程进行处理;
		NIO 方式适用于连接数多且连接比较短的架构,比如聊天服务器,并发局限于应用中,编程较复杂,JDK1.4后开始支持;
	(3).AIO:异步非阻塞,服务器实现模式为一个有效请求一个线程,客户端的 I/O 请求都是由OS先完成了在通知服务器应用去启动线程进行处理;
		AIO 的方式适用于连接数多且连接比较长的架构,JDK7 开始支持
