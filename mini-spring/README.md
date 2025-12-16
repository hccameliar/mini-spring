# Mini-Spring 项目说明

## 📋 项目概述

**Mini-Spring** 是一个轻量级的 Spring 框架实现，旨在学习和理解 Spring 框架的核心原理。该项目实现了 Spring 的主要功能，包括
IoC 容器、AOP 框架、事件系统、XML 配置等。

### 项目信息

- **项目名称**: Mini-Spring
- **版本**: 1.0-SNAPSHOT
- **Java 版本**: 17
- **构建工具**: Maven

### 核心特性

✅ **IoC 容器** - Bean 的定义、注册、创建和管理  
✅ **依赖注入** - Setter 注入、构造函数注入、自动装配  
✅ **AOP 框架** - JDK 动态代理、CGLIB 代理、AspectJ 支持  
✅ **事件系统** - 应用事件发布和监听  
✅ **XML 配置** - 从类路径或文件系统加载配置  
✅ **类型转换** - 通用类型转换服务  
✅ **Bean 生命周期** - 初始化、销毁、后处理等

---

## 📁 项目结构

```
mini-spring/version2/
├── src/
│   ├── main/java/com/minispring/
│   │   ├── aop/                    # AOP 框架模块
│   │   ├── beans/                  # Bean 基础设施
│   │   ├── context/                # 应用上下文
│   │   ├── convert/                # 类型转换
│   │   ├── core/                   # 核心工具
│   │   ├── io/                     # 资源加载
│   │   ├── ioc/                    # IoC 容器核心
│   │   ├── util/                   # 工具类
│   │   ├── web/                    # Web 支持
│   │   └── xml/                    # XML 处理
│   └── test/java/com/minispring/   # 单元测试
├── pom.xml                         # Maven 配置
└── 文档文件                         # 各种说明文档
```

---

## 🏗️ 核心模块详解

### 1. **IoC 容器模块** (`ioc/`)

IoC（Inversion of Control）容器是 Spring 的核心，负责 Bean 的生命周期管理。

#### 子模块结构

```
ioc/
├── factory/                    # Bean 工厂接口
│   ├── BeanFactory            # 基础 Bean 工厂接口
│   ├── ConfigurableBeanFactory # 可配置的 Bean 工厂
│   ├── ListableBeanFactory    # 可列表的 Bean 工厂
│   ├── ConfigurableListableBeanFactory # 完整的可配置工厂
│   ├── HierarchicalBeanFactory # 层次结构的 Bean 工厂
│   └── ObjectFactory          # 对象工厂（用于解决循环依赖）
│
├── definition/                 # Bean 定义相关
│   ├── BeanDefinition         # Bean 定义接口
│   ├── BeanDefinitionRegistry # Bean 定义注册表
│   ├── BeanDefinitionReader   # Bean 定义读取器
│   ├── BeanReference          # Bean 引用
│   └── DependencyDescriptor   # 依赖描述符
│
├── support/                    # 支持实现
│   ├── AbstractBeanFactory    # 抽象 Bean 工厂
│   ├── AbstractAutowireCapableBeanFactory # 自动装配工厂
│   ├── DefaultListableBeanFactory # 默认实现
│   ├── DefaultSingletonBeanRegistry # 单例注册表
│   └── AbstractBeanDefinitionReader # 抽象定义读取器
│
├── instantiation/              # Bean 实例化
│   ├── InstantiationStrategy  # 实例化策略
│   ├── SimpleInstantiationStrategy # 简单实例化
│   ├── CglibSubclassingInstantiationStrategy # CGLIB 实例化
│   └── ConstructorResolver    # 构造函数解析
│
├── lifecycle/                  # Bean 生命周期
│   ├── BeanPostProcessor      # Bean 后处理器
│   ├── BeanFactoryPostProcessor # 工厂后处理器
│   ├── InitializingBean       # 初始化接口
│   └── DisposableBean         # 销毁接口
│
├── autowiring/                 # 自动装配
│   └── AutowireCapableBeanFactory # 自动装配工厂
│
└── scope/                      # Bean 作用域
    ├── Scope                  # 作用域接口
    ├── SingletonScope         # 单例作用域
    └── PrototypeScope         # 原型作用域
```

#### 核心流程

**Bean 创建流程**:

```
1. 获取 BeanDefinition
2. 实例化 Bean（选择合适的构造函数）
3. 填充属性（依赖注入）
4. 执行 Aware 接口方法
5. BeanPostProcessor 前置处理
6. 执行初始化方法
7. BeanPostProcessor 后置处理
8. 注册销毁回调
```

### 2. **AOP 框架模块** (`aop/`)

AOP（Aspect-Oriented Programming）框架提供面向切面编程支持。

#### 子模块结构

```
aop/
├── core/                       # AOP 核心概念
│   ├── Advice                 # 通知接口
│   ├── Advisor                # 通知器
│   ├── PointcutAdvisor        # 切点通知器
│   ├── Pointcut               # 切点
│   ├── ClassFilter            # 类过滤器
│   ├── MethodMatcher          # 方法匹配器
│   ├── MethodBeforeAdvice     # 前置通知
│   ├── AfterReturningAdvice   # 后置通知
│   └── TruePointcut           # 真切点
│
├── proxy/                      # 代理实现
│   ├── AopProxy               # AOP 代理接口
│   ├── ProxyFactory           # 代理工厂
│   ├── JdkDynamicAopProxy     # JDK 动态代理
│   ├── CglibAopProxy          # CGLIB 代理
│   ├── MethodInvocation       # 方法调用
│   └── ReflectiveMethodInvocation # 反射方法调用
│
├── support/                    # 支持类
│   ├── AdvisedSupport         # AOP 配置支持
│   ├── TargetSource           # 目标源
│   ├── SingletonTargetSource  # 单例目标源
│   └── DefaultPointcutAdvisor # 默认切点通知器
│
└── aspectj/                    # AspectJ 支持
    └── AspectJExpressionPointcut # AspectJ 表达式切点
```

#### 代理选择策略

```
ProxyFactory 自动选择代理方式：
├─ 如果目标类实现了接口 → JDK 动态代理
└─ 如果目标类没有实现接口 → CGLIB 代理
```

### 3. **Bean 基础设施模块** (`beans/`)

提供 Bean 属性处理和类型转换的基础设施。

```
beans/
├── BeanWrapper              # Bean 包装器（属性访问）
├── PropertyValue            # 属性值
├── PropertyValues           # 属性值集合
├── TypeConverter            # 类型转换器
├── SimpleTypeConverter      # 简单类型转换
├── BeansException           # Bean 异常
└── TypeMismatchException    # 类型不匹配异常
```

### 4. **应用上下文模块** (`context/`)

提供应用级别的容器和事件支持。

```
context/
├── ApplicationContext       # 应用上下文接口
├── ConfigurableApplicationContext # 可配置的应用上下文
├── support/
│   ├── AbstractApplicationContext # 抽象应用上下文
│   ├── AbstractRefreshableApplicationContext # 可刷新的上下文
│   └── AbstractXmlApplicationContext # XML 应用上下文
├── event/
│   ├── ApplicationEvent     # 应用事件
│   ├── ApplicationListener  # 事件监听器
│   ├── ApplicationEventMulticaster # 事件多播器
│   ├── SimpleApplicationEventMulticaster # 简单多播器
│   ├── ContextRefreshedEvent # 上下文刷新事件
│   └── ContextClosedEvent   # 上下文关闭事件
└── aware/
    ├── BeanNameAware        # Bean 名称感知
    ├── BeanFactoryAware     # 工厂感知
    └── ApplicationContextAwareProcessor # 上下文感知处理器
```

### 5. **XML 处理模块** (`xml/`)

处理 XML 配置文件的解析和 Bean 定义的加载。

```
xml/
├── XmlBeanDefinitionReader # XML Bean 定义读取器
├── BeanDefinitionDocumentReader # 文档读取器
├── DefaultBeanDefinitionDocumentReader # 默认实现
├── DocumentLoader           # 文档加载器
├── DefaultDocumentLoader    # 默认加载器
├── NamespaceHandler         # 命名空间处理器
├── NamespaceHandlerResolver # 命名空间解析器
├── DefaultNamespaceHandlerResolver # 默认解析器
├── ContextNamespaceHandler  # 上下文命名空间处理器
└── XmlBeanDefinitionStoreException # XML 异常
```

### 6. **资源加载模块** (`io/`)

处理资源的加载，支持类路径和文件系统资源。

```
io/
├── Resource                 # 资源接口
├── ResourceLoader           # 资源加载器
├── ClassPathResource        # 类路径资源
├── FileSystemResource       # 文件系统资源
└── DefaultResourceLoader    # 默认加载器
```

### 7. **类型转换模块** (`convert/`)

提供通用的类型转换服务。

```
convert/
├── Converter                # 转换器接口
├── ConverterFactory         # 转换器工厂
├── GenericConverter         # 通用转换器
├── ConversionService        # 转换服务
└── support/
    ├── GenericConversionService # 通用转换服务实现
    ├── StringToBooleanConverter # 字符串→布尔
    ├── StringToByteConverter    # 字符串→字节
    ├── StringToCharacterConverter # 字符串→字符
    ├── StringToDateConverter    # 字符串→日期
    ├── StringToDoubleConverter  # 字符串→双精度
    ├── StringToFloatConverter   # 字符串→浮点
    ├── StringToIntegerConverter # 字符串→整数
    ├── StringToLongConverter    # 字符串→长整数
    └── StringToShortConverter   # 字符串→短整数
```

### 8. **核心工具模块** (`core/`)

提供各种核心工具和辅助功能。

```
core/
├── ParameterNameDiscoverer # 参数名发现器
├── DefaultParameterNameDiscoverer # 默认实现
├── convert/                # 类型转换工具
└── io/                     # 资源加载工具
```

---

## 🔄 关键设计模式

### 1. **工厂模式** (Factory Pattern)

- `BeanFactory` - 创建和管理 Bean 的工厂
- `ProxyFactory` - 创建代理对象的工厂
- `ConverterFactory` - 创建类型转换器的工厂

### 2. **策略模式** (Strategy Pattern)

- `InstantiationStrategy` - Bean 实例化策略
- `MethodMatcher` - 方法匹配策略
- `ClassFilter` - 类过滤策略

### 3. **模板方法模式** (Template Method Pattern)

- `AbstractBeanFactory` - 定义 Bean 获取的模板
- `AbstractApplicationContext` - 定义应用上下文的模板

### 4. **适配器模式** (Adapter Pattern)

- `BeanWrapper` - 将任意对象适配为统一的属性访问接口
- `ConverterAdapter` - 将 Converter 适配为 GenericConverter

### 5. **观察者模式** (Observer Pattern)

- `ApplicationEventMulticaster` - 事件多播器
- `ApplicationListener` - 事件监听器

### 6. **代理模式** (Proxy Pattern)

- `JdkDynamicAopProxy` - JDK 动态代理
- `CglibAopProxy` - CGLIB 代理

### 7. **单例模式** (Singleton Pattern)

- `DefaultSingletonBeanRegistry` - 单例 Bean 注册表

---

## 🔗 依赖关系

### 主要依赖

```xml
<!-- XML 解析 -->
<dependency>
    <groupId>org.dom4j</groupId>
    <artifactId>dom4j</artifactId>
    <version>2.1.3</version>
</dependency>

<!-- CGLIB 代理 -->
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib-nodep</artifactId>
    <version>3.3.0</version>
</dependency>

<!-- AspectJ 支持 -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.9.1</version>
</dependency>

<!-- 日志框架 -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.11</version>
</dependency>

<!-- 测试框架 -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
```

---

## 🚀 快速开始

### 1. 编译项目

```bash
mvn clean compile
```

### 2. 运行测试

```bash
mvn clean test
```

### 3. 打包项目

```bash
mvn clean package
```

### 4. 基本使用示例

#### 创建 Bean

```java
// 1. 定义 Bean 类
public class UserService {
    private final String name;

    public UserService(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("Hello, " + name);
    }
}

// 2. 创建 XML 配置
<? xml version = "1.0"
encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans">
    <bean id="userService"class="com.example.UserService">
        <constructor-
arg value = "Mini-Spring" / >
    </bean>
</beans>

// 3. 加载配置并使用
ClassPathXmlApplicationContext context =
        new ClassPathXmlApplicationContext("beans.xml");
UserService userService = context.getBean("userService", UserService.class);
userService.

sayHello();
```

---

## 📊 Bean 生命周期

```
1. 实例化 (Instantiation)
   ↓
2. 属性填充 (Property Population)
   ↓
3. Aware 接口处理 (Aware Interfaces)
   - BeanNameAware.setBeanName()
   - BeanFactoryAware.setBeanFactory()
   ↓
4. BeanPostProcessor 前置处理 (postProcessBeforeInitialization)
   ↓
5. 初始化 (Initialization)
   - InitializingBean.afterPropertiesSet()
   - 自定义初始化方法
   ↓
6. BeanPostProcessor 后置处理 (postProcessAfterInitialization)
   ↓
7. 使用中 (In Use)
   ↓
8. 销毁 (Destruction)
   - DisposableBean.destroy()
   - 自定义销毁方法
```

---

## 🔍 循环依赖解决方案

Mini-Spring 使用**三级缓存**解决循环依赖问题：

```
一级缓存 (singletonObjects)
  ↑
  ├─ 存储完全初始化的 Bean 实例
  
二级缓存 (earlySingletonObjects)
  ↑
  ├─ 存储已实例化但未初始化的 Bean 实例
  
三级缓存 (singletonFactories)
  ↑
  ├─ 存储 Bean 工厂，用于创建代理对象
```

**解决流程**:

1. Bean A 依赖 Bean B
2. Bean B 依赖 Bean A
3. 创建 A 时，将 A 的工厂放入三级缓存
4. 创建 B 时，从三级缓存获取 A 的工厂
5. 完成循环依赖的解决

---

## 🎯 AOP 工作流程

```
1. 创建代理对象
   ├─ 检查目标类是否实现接口
   ├─ 实现接口 → JDK 动态代理
   └─ 未实现接口 → CGLIB 代理

2. 方法调用拦截
   ├─ 获取适用的 Advisor
   ├─ 构建拦截器链
   └─ 执行通知

3. 通知执行顺序
   ├─ 前置通知 (Before Advice)
   ├─ 目标方法执行
   ├─ 后置通知 (After Returning Advice)
   └─ 异常通知 (After Throwing Advice)
```

---

## 📝 配置文件示例

### XML 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context">
    
    <!-- 定义 Bean -->
    <bean id="userService" class="com.example.UserService">
        <property name="name" value="Mini-Spring"/>
    </bean>
    
    <!-- Bean 引用 -->
    <bean id="userController" class="com.example.UserController">
        <property name="userService" ref="userService"/>
    </bean>
    
    <!-- 构造函数注入 -->
    <bean id="orderService" class="com.example.OrderService">
        <constructor-arg ref="userService"/>
    </bean>
    
</beans>
```

---

## 🧪 测试

项目包含完整的单元测试，覆盖以下方面：

- ✅ Bean 创建和管理
- ✅ 依赖注入
- ✅ AOP 代理
- ✅ 事件系统
- ✅ XML 配置解析
- ✅ 类型转换
- ✅ 循环依赖解决

运行测试：

```bash
mvn clean test
```



