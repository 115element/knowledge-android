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
