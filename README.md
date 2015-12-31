# EventBusAApplication
深入学习EventBus应用以及研究源码
==========================================
关于EventBus，有几个版本，用的比较多的是[greenrobot/EventBus](https://github.com/greenrobot/EventBus)<br />，这个库的优点是接口简洁，集成方便，但是限定了方法名，不支持注解
另一个库[square/otto](https://github.com/square/otto)<br />修改自 Guava，用的人也不少

--------------------------------------
这里首先根据greenrobot的EventBus来熟悉具体的用法，然后做出相应的简化
