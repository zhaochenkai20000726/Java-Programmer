<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**目录**

- [三、XML](#%E4%B8%89xml)
- [四、XML解析:](#%E5%9B%9Bxml%E8%A7%A3%E6%9E%90)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# 三、XML			
	1.什么是XML
		(1).Extensible Markup Language,可扩展的标记语言,用于定义文档,使其具有结构化的特征;
			原意是用于数据传输,目前常用作配置文件;
		(2).背景:
			①.HTML,超文本标记语言,由万维网之父博纳斯.李创造,具有固定格式的文本语言;
			②.基于HTML的思想,扩展设计出XML,用户可以定义XML文件的格式,具有很高的灵活性;
			③).根据XML设计理念,重新的定义了HTML,即XHTML;		
		(3).用途:描述数据和用作配置文件;		
	2.XML语法:(可以使用IE验证XML)
		(1).xml文档声明:
			①.简单的声明:<?xml version="1.0" ?>
			②.encoding指定编码:<?xml version="1.0" encoding="utf-8" ?> //注意文档的编码
			③.standalone
		(2).XML元素:指xml文件中出现的标签,分开始和结束标签,标签可以不包含标签体;
			对XML标签中出现的空格和换行,XML解析程序都会当作标签内容进行处理;
		(3).属性:一个标签可以有多个属性,每个属性都有自己的名称和取值;
		(4).注释: <!-- 注释 -->
		(5).CDATA区:对于其内的内容,XML解析程序不会处理,而是直接输出:
			语法:<![CDATA[<....>]]>
		(6).转义字符:
			& -- &amp;
			< -- &lt;
			> -- &gt;
			" -- &quto;
			' -- &apos;
		(7).处理指令:用来指挥解析引擎如何解析XML文件,处理执行必须以"<?"作为开头,以"?>"来作为结尾;
	3.XML约束
		(1).在xml技术里,可以编写一个文档来约束一个xml文档的书写规范,称之为XML约束
		(2).常用约束技术:
			◆XML DTD
			◆XML Schema
	4.XML DTD约束技术(Document Type Definition),文档类型定义,可在XML文件内编写
		(1).语法:
			在XML文件内部定义DTD时:
				<!DOCTYPE 根元素 [元素声明]>
			◆元素定义:在DTD文档中使用ELEMENT声明一个XML元素
				<!ELEMENT 元素名称 元素类型>
				元素类型可以是元素内容或类型:
					①如为元素:需用()括起来如:
						<!ELEMENT 书架(书+)>
						<!ELEMENT 书(书名,作者,售价)>
					②如为元素类型,则直接书写,DTD规范定义了如下几种类型
					◆EMPTY:用于定义空元素,如<br/>
					◆ANY:表示元素内容为任意类型;
		(2).引用DTD约束:
			①当引用的文件在本地时:<!DOCTYPE 文档根节点 SYSTEM "DTD文件的url">,如:
				<!DOCTYPE 书架 SYSTEM "book.dtd">
			②当引用的文件是一个公共的文件时：<!DOCTYPE 文档根节点 PUBLIC "DTD名称" "DTD文件的URL">
			
# 四、XML解析:
	★补充知识点:调整JVM大小
		①JVM默认占用64M内存,超过将内存溢出;
		②运行Java程序,在Eclipse中,Run Configurations中,选择Arguments下的VM arguments,加上如下选项:
			Xmx80m //调整JVM内存到80M
	◆DOM解析与SAX解析比较:
		①DOM解析是对文档的CRUD操作方便,缺点是占用内存比较大;
		②SAX解析优点占用内存少,解析速度快,缺点是只适合做文档读取
	1.DOM解析:Document Object Model,文档对象模型
		DOM解析往文档中添加数据时注意中文乱码问题：
		解决方案：
		①指定写编码
			XMLWriter writer = 
				new XMLWriter(new OutputStreamWriter(new FileOutputStream("src/book.xml"), "UTF-8"));
		②指定输出格式:
			OutputFormat format = new OutputFormat();
			format.setEncoding("utf-8");
			//这里不要用FileWriter字符流,而是直接使用字节流
			//XMLWriter writer = new XMLWriter(new FileWriter("src/book.xml"), format);
			XMLWriter writer = new XMLWriter(new FileOutputStream("src/book.xml"), format);
			writer.write(document);
			writer.close();
		
	2.SAX解析:Simple API for XML
	3.XML解析包:
		(1).JAXP:是J2SE的一部分,java.xml.parser
			
	4.XPath		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			