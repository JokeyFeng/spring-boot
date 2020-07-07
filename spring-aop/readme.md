拟我们的springAop，如果有点基础的可能应该会知道，spring是基于我们的动态代理实现的（先不考虑是cglib还是jdk动态代理），
结合我们aop使用（没用过的好去百度了），那么我们就需要解决如下几个问题：
a）我们知道开启和关闭aop需要注解@EnableAspectJAutoProxy，如何实现，结合上文，我们可以使用@import(ImportSelector.class)
b）如何确定代理关系，即哪些是我们需要代理的目标对象和其中的目标方法，以及哪些方法是要增强到目标对象的目标方法上去的？
c）如何实现目标对象的替换，就是我们在getBean的时候，如何根据目标对象来获取到我们增强后的代理对象？

如上问题都解决了，那么也就实现了我们的AOP.


具体工程说明如下：
annotation：放我们所有的自定义注解
holder:自定义数据结构，具体类后面说
processor:放我们所有的后置处理器及代理相关
selector:放我们的ImportSelector的实现
util:工具类

要模拟aop，那么我们就要结合我们怎么去使用aop：
对于AOP,我们知道有一个开关注解类 @EnableAspectJAutoProxy（同样我们定义个@EnableAop），
注解@Aspect，@Before，@After。。。（注意这些都不是spring的注解，是Aspectj的注解，只是我们spring直接引用了而已，
同样我们也对于新建自定义注解@AopJ，@BeforeMe，@AfterMe，@AroundMe。。。）

针对问题2，由于BeanFactoryPostProcessor的所有实现会在beanFactory完成对由于bean的扫描后，
在实例化之前执行，所以我们可以新建一类，实现这个接口，然后实现方法里面主要完成对有BeanDefinition的扫描，
找出我们所有的通知类，然后循环里面的方法，找到所有的通知方法，然后根据注解判断切入类型（也就是前置，后置还是环绕），
最后解析注解的内容，扫描出所有的目标类，放入我们定义好的容器中。