1.大数据的四则运算(不适用 BigInteger)


2.一个字节输出流转换为字节输入流
	Singleton singleton = Singleton.getInstance();
	
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ObjectOutputStream oo = new ObjectOutputStream(out);
	oo.writeObject(singleton);
	
	ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
	ObjectInputStream oi = new ObjectInputStream(in);

	Singleton two = (Singleton) oi.readObject();

3.求一个整数的二进制中1的个数:
	把一个整数减去1,再和原整数做与运算,会把该整数最右边1一个1变成0.
	那么一个整数的二进制表示中有多少个1,就可以进行多少次这样的操作.从而可以减少比较的次数
	public static int count1Number(int n){
        int count = 0;
        while (n != 0){
            n = n & (n -1);
            count++;
        }
        return count;
    }













