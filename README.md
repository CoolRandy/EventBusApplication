# EventBusAApplication
深入学习EventBus应用以及研究源码
==========================================
关于EventBus，有几个版本，用的比较多的是[greenrobot/EventBus](https://github.com/greenrobot/EventBus)<br />，这个库的优点是接口简洁，集成方便，但是限定了方法名，不支持注解
另一个库[square/otto](https://github.com/square/otto)<br />修改自 Guava，用的人也不少

--------------------------------------
这里首先根据greenrobot的EventBus来熟悉具体的用法，然后做出相应的简化

1、当方法名以onEvent开头，就表示想要订阅一个事件，比如onEventMainThread就表示订阅者将会在UI线程中调用
同理其他方法类似

2、onEventMainThread触发时机是在new Thread()执行完成之后，也就是说当事件发布后onEventMainThread就执行了，那他是如何知道的呢？其实这里和参数有关，也就是说发布事件类型和onEventMainThread接收的参数类型必须是一样的才会执行，这个是一一对应的

3、这里先简单理解整个执行过程，后面会详细针对源码进行解析：在onCreate里面执行	EventBus.getDefault().register(this);意思是让EventBus扫描当前类，把所有onEvent开头的方法记录下来，如何记录呢？使用Map，Key为方法的参数类型，Value中包含我们的方法。
这样在onCreate执行完成以后，我们的onEventMainThread就已经以键值对的方式被存储到EventBus中了。
然后当子线程执行完毕，调用EventBus.getDefault().post(new ItemListEvent(Item.ITEMS))时，EventBus会根据post中实参的类型，去Map中查找对于的方法，于是找到了我们的onEventMainThread，最终调用反射去执行我们的方法

4、根据EventBus Guide描述可知EventBus有4种ThreadMode：PostThread、MainThread、BackgroundThread和Async。具体的用法就是在前面添加onEvent，分别代表的含义可以具体参考文档说明：这里简要说明：

onEventMainThread代表这个方法会在UI线程执行

onEventPostThread代表这个方法会在当前发布事件的线程执行

BackgroundThread这个方法，如果在非UI线程发布的事件，则直接执行，和发布在同一个线程中。如果在UI线程发布的事件，则加入后台任务队列，使用线程池一个接一个调用。

Async 加入后台任务队列，使用线程池调用，注意没有BackgroundThread中的一个接一个

至此，简单的使用已经介绍完了，接下来就是深入研究一下源码，看一看它的实现原理，这样将会更加有利于我们熟练的运用

####EventBus源码解析####
