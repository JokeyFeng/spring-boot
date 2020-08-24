定义业务需求：
用户注册后，系统需要给用户发送邮件告知用户注册成功，需要给用户初始化积分；
隐含的设计需求，用户注册后，后续需求可能会添加其他操作，
如再发送一条短信等等，希望程序具有扩展性，以及符合开闭原则。
如果不使用事件驱动，代码可能会像这样子：  

`
public class UserService {
  
    @Autowired
    EmailService emailService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    OtherService otherService;

    public void register(String name) {
        System.out.println("用户：" + name + "已注册！");
        emailService.sendEmail(name);
        scoreService.initScore(name);
        otherService.execute(name);
    }
}
`

要说有什么毛病，其实也不算有，因为可能大多数人在开发中都会这么写，喜欢写同步代码。
但这么写，实际上并不是特别的符合隐含的设计需求，假设增加更多的注册项 service，
我们需要修改 register 的方法，并且让 UserService 注入对应的 Service。
而实际上，register 并不关心这些“额外”的操作，如何将这些多余的代码抽取出去呢？
便可以使用 Spring 提供的 Event 机制。