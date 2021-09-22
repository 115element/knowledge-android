
在没有supportV4之前，android开发界面时一直使用是Activity+View的组合。但Android运行在各种各样的设备中，
有小屏幕的手机，超大屏的平板甚至电视。针对屏幕尺寸的差距，很多情况下，都是先针对手机开发一套app，然后拷贝一份，
修改布局以适应什么超级大屏的。在这种背景下Fragment自然而然的出现了，你可以把Fragment当成Activity一个界面的一部分，
甚至Activity的界面由完全不同的Fragment组成，Fragment会同步当前Activity的声明周期和接收、处理用户的事件，
这样Activity内部的逻辑控制会分散到Fragment减少业务逻辑耦合，和增加Fragment的重用。并且动态的添加、替换、移除某个Fragment。
至于楼主说的Fragment用View来代替，在我们日常的Android开发中可以这么实现，
但是使用View去布局一些复杂，碎片化的页面的时候它的性能与代码质量就会比使用Fragment差好多。
因为比较Fragment出现是为了更好的开发出优雅的界面的。我觉得如果把Fragment要和View或者Activity做类比，
感觉它和Activity更亲近，因为它更像是Activity的孩子。



1.1 Fragment 解决了什么问题？（过去）
Fragment 可以将 Activity 视图拆分为多个区块进行模块化地管理 ，避免了 Activity 视图代码过度臃肿混乱。
虽然自定义 View 或 Window 也可以在一定程度拆分 Activity 界面，但事实上它们的职责不同：View / Window 的职责是封装某种视图功能，
而 Fragment 是在更高的层次使用控制自定义 View。
此外，Fragment 还可以更方便地管理生命周期和事务（虽然我们会通过 MVP 或 MVVM 模式分离业务逻辑，但是对于复杂页面，
我们还是无法避免 Activity 视图代码演化的非常臃肿混乱）。

需要注意的是，Fragment 不能脱离 Activity 独立存在，必须由 Activity 或另一个 Fragment 托管，
Fragment#onCreateView 实例化的视图最终会被嵌入到宿主的视图树中。


1.2 Fragment 存在什么问题？（现在）
Fragment 的最初设计理念是 “一个微型 Activity” 的角色，正所谓 “欲戴王冠，必受其重”，
很多专门为 Activity 设计 的 API 也需要添加到 Fragment 中，比如运行时权限，多窗口模式切换等新 API。
这无疑是在无限制地扩充 Fragment 的职责边界，也在增大 Fragment 设计的复杂度，要知道 Fragment 的本质思想是界面模块化而已。


1.3 Fragment 2.0（未来）
Google 正在重新构思 Fragment 的定位，随着 AndroidX Fragment 版本 陆续更新，
新版 Fragment 正在渐渐走进我们的视野，我们称新版 Fragment 为 Fragment 2.0，已知的新特性包括：
FragmentScenario：Fragment 的测试框架；
FragmentFactory：统一的 Fragment 实例化组件；
FragmentContainerView：Fragment 专属视图容器；
OnBackPressedDispatcher：在 Fragment 或其他组件中处理返回按钮事件。




要点如下：
FragmentActivity 是 Activity 支持 Fragment 的基础，其中持有一个 FragmentController 中间类，
它是 FragmentActivity 和 FragmentManager 的中间桥接者，对 Fragment 的操作最终是分发到 FragmentManager 来处理；


FragmentManager 承载了 Fragment 的核心逻辑，负责对 Fragment 执行添加、移除或替换等操作，以及添加到返回堆栈。
它的实现类 FragmentManagerImpl 是我们主要的分析对象；


FragmentHostCallback 是 FragmentManager 向 Fragment 宿主的回调接口，Activity 和 Fragment 中都有内部类实现该接口，
所以 Activity 和 Fragment 都可以作为另一个 Fragment 的宿主（Fragment 宿主和 FragmentManager 是 1 : 1 的关系）；


FragmentTransaction 是 Fragment 事务抽象类，它的实现类 BackStackRecord 是事务管理的主要分析对象。

