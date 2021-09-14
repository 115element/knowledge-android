#①进入java文件所在目录
》》 cd G:\WorkStation\knowledge-android\app\src\main\java\com\example\knowledge_android\jnistudy>

#②执行javac编译命令,在当前目录生成class文件
》》 javac .\JniTest.java

#③切换到类根路径
》》 cd G:\WorkStation\knowledge-android\app\src\main\java\

#④执行javah命令，将在类根路径下生成 com_example_knowledge_android_jnistudy_JniTest.h 头文件
》》 javah com.example.knowledge_android.jnistudy.JniTest


注意：javah命令后边的全类路径包名 + 类根路径名 = 这个Class文件的绝对路径





