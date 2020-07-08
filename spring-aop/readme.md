Spring Aop底层原理详解（利用spring后置处理器实现AOP）

对于一个java程序员来说，相信绝大多数都有这样的面试经历，
面试官问：你知道什么是aop吗？谈谈你是怎么理解aop的？等等诸如此类关于aop的问题。
当然对于一些小白可能会一脸懵逼；对于一些工作一两年的，可能知道，哦！aop就是面向切面变成，打印日志啊等等；
要是有点学习深度的呢可能会说aop底层实现利用了jdk动态代理，cglib啊什么的，很多时候可能面试就到此打住了。
当然，然后也就没有然后了（客气点的来句：回头有消息我会通知你的！）。

今天，通过这篇文章，我想带大家先了解下什么是Spring后置处理器，
然后利用Spring的后置处理器我们自己来手写一个SpringAop，来完成和Spring Aop一样的功能！让你可以对你的面试官说：你精通AOP！

Spring扩展点和后置处理器

今天呢我跟大家介绍的后置处理器呢，有三个
BeanFactoryPostProcessor：可以插手beanFactory的生命周期
BeanPostProcessor：可以插手bean的生命周期
ImportSelector：借助@Import注解，可以动态实现将一个类是否交由spring管理，常用作开关操作

1：BeanFactoryPostProcessor（BeanDefinitionRegistryPostProcessor 有兴趣的可以自行查阅spring源码）
`
@FunctionalInterface
public interface BeanFactoryPostProcessor {   
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
`
可以看到，该接口只定义了一个方法，具体实现的执行时机呢，我们可以通过spring源码得知：
在我们AnnotationConfigApplicationContext.refresh();的时候，从下源码可知，在我们beanFactory被创建出来后，相关准备工作做完后，
会去执行invokeBeanFactoryPostProcessors(beanFactory);也就是去执行我们的BeanFactoryPostProcessor.

2：BeanPostProcessor：可以看出，该接口定义了两个方法，分别在bean实例化之后放到我们的容器之前和之后去执行，方法的返回值为一个object，
这个object呢就是我们存在于容器的对象了（所以这个位置我们是不是可以对我们的bean做一个动态的修改，替换等等操作，
所以这也是我们spring的扩展点之一，后面结合我么自己手写aop来详细讲解这个扩展点的应用）	

`
public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException { /* compiled code */ }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException { /* compiled code */ }
}
`
3：ImportSelector
在讲ImportSelector之前呢，我想先讲一下@Import这个注解。在spring处理我们的java类的时候，会分成四种情况去处理
1）普通类：就是我们家里@Component，@Service，@Repository等等的类
2）处理我们的import进来的类：
这里呢，又分为三种情况：
a）import一个普通类：@Import（A.class）
b）import一个Registrar：比如我们的aop @Import(AspectJAutoProxyRegistrar.class)
c）import一个ImportSelector：具体妙用见下文
对于普通类，spring在doScan的时候，就将扫描出来的java类转换成我们的BeanDefinition，然后放入一个BeanDefinitionMap中去。
对于@import的三种情况，处理就在我们的ConfigurationClassPostProcessor（该类是我们BeanDefinitionRegistryPostProcessor后置处理器的一个实现，
同时这也是我们spring内部自己维护的唯一实现类（排除内部类）），具体处理import的核心代码如下，if-else 很容易可以看出spring对于我们import三种类型的处理。


拟我们的SpringAop，如果有点基础的可能应该会知道，Spring是基于我们的动态代理实现的（先不考虑是cglib还是jdk动态代理），
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