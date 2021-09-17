```java
package com.example.knowledge_android.this_super_class;

public class A {

    public void x(){

        A a = A.this; //代表A实例化后对象的上下文

        if (a == this) {
            System.out.println("经过运行，二者是相等的。");
        }

        //Object o = A.super; //编译不通过,不能这样使用。

        String s = A.super.toString(); //调用父类Object的方法

        //以下两个是相同的
        Class<A> aClass = A.class;
        Class<? extends A> aClass1 = this.getClass();
        if (aClass == aClass1) {
            System.out.println("经过运行，二者是相等的。");
        }
    }
}
```

#########
this(xx);//指向其他的不同参构造器

super存在于有继承关系的子类里；
super相当于father.this；”super(xxx)”可以访问到父类中同参的father(xxx)构造方法；”super.xxx()”可以访问到父类中的xxx()方法；

this 相当于是指向当前对象本身。

this 代表当前类的上下文，A.this 代表A实例化后对象的上下文

###########
１.当在内部类中使用this指的就是内部类的对象, 为了访问外层类对象,就可以使用外层类名.this来访问。一般也只在这种情况下使用这种。

具体示例：http://www.cnblogs.com/hnrainll/archive/2012/01/13/2321193.html

2. 在java中，每个class都有一个相应的Class对象，当编写好一个类，编译完成后，在生成的.class文件中，就产生一个Class对象，用来表示这个类的类型信息。 

获得Class实例的三种方式：
1). 利用对象调用getClass()方法获取该对象的Class实例
2). 使用Class的静态方法forName()，用类的名字获取一个Class实例
3). 运用.class的方式获取Class实例，对基本数据类型的封装类，还可以采用.TYPE来获取对应的基本数据类型的Class实例。


##############
在运行期间，如果我们要产生某个类的对象，java虚拟机会检测该类型的Class对象是否已被加载。如果没有加载，java虚拟机会根据类的名称找到.class文件并加载它。
当new Point()的时候加载这个类，用forName构造实例的时候也加载该类，只加载一次。


##############
class用到反射里面，所以用的时候看到一句话：在运行期间，一个类，只有一个Class对象产生
Class对象到底是什莫？只能有一个嘛？比如：一个student类，那我可以创建多个stu1,stu2,stu3......这
些实例化对象和Class有神魔关系呢？
有共同属性的归为一类 class就是哪个类， 实例就是具体的一个； 实例化和Class联系的你和人类的关系。

java class 和 object的区别？
你可以这样来理解，类就是一类事物，而对象就是一个事物，
一类事物中包含若干的事物（而这其中的具体的事物就是对象），
比如说：车，你可能想到的是很多车，因为你没法具体到哪辆车，
这就是一个类的概念，而如果说，我家的那辆轿车，你肯定就可以想到那具体的一辆车，而这个具体的车就是对象的概念