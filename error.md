## org.apache.tomcat.util.bcel.classfile.ClassFormatException

```java
三月 10, 2021 1:52:58 下午 org.apache.catalina.startup.ContextConfig processAnnotationsJar
严重: Unable to process Jar entry [META-INF/versions/9/module-info.class] from Jar [jar:file:/Users/tanpeng/tp-file/小马哥训练营/geek-training-camp/.extract/webapps/ROOT/WEb/byte-buddy-1.10.21.jar!/] for annotations
org.apache.tomcat.util.bcel.classfile.ClassFormatException: Invalid byte tag in constant pool: 19
        at org.apache.tomcat.util.bcel.classfile.Constant.readConstant(Constant.java:133)
        at org.apache.tomcat.util.bcel.classfile.ConstantPool.<init>(ConstantPool.java:60)
        at org.apache.tomcat.util.bcel.classfile.ClassParser.readConstantPool(ClassParser.java:209)
        at org.apache.tomcat.util.bcel.classfile.ClassParser.parse(ClassParser.java:119)
        at org.apache.catalina.startup.ContextConfig.processAnnotationsStream(ContextConfig.java:2105)
        at org.apache.catalina.startup.ContextConfig.processAnnotationsJar(ContextConfig.java:1981)
        at org.apache.catalina.startup.ContextConfig.processAnnotationsUrl(ContextConfig.java:1947)
        at org.apache.catalina.startup.ContextConfig.processAnnotations(ContextConfig.java:1932)
        at org.apache.catalina.startup.ContextConfig.webConfig(ContextConfig.java:1326)
        at org.apache.catalina.startup.ContextConfig.configureStart(ContextConfig.java:878)
        at org.apache.catalina.startup.ContextConfig.lifecycleEvent(ContextConfig.java:369)
        at org.apache.catalina.util.LifecycleSupport.fireLifecycleEvent(LifecycleSupport.java:119)
        at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:90)
        at org.apache.catalina.core.StandardContext.startInternal(StandardContext.java:5179)
        at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:150)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1559)
        at org.apache.catalina.core.ContainerBase$StartChild.call(ContainerBase.java:1549)
        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)
```

**解决方案**

tomcat7 不能识别高版本 hibernate-core 中的JAVA语法，故降低版本号为 5.1.0.Final

## javax.persistence.PersistenceException

```java

Exception in thread "main" javax.persistence.PersistenceException: Unable to locate persistence units
	at org.hibernate.jpa.HibernatePersistenceProvider.getEntityManagerFactoryBuilderOrNull(HibernatePersistenceProvider.java:84)
	at org.hibernate.jpa.HibernatePersistenceProvider.getEntityManagerFactoryBuilderOrNull(HibernatePersistenceProvider.java:71)
	at org.hibernate.jpa.HibernatePersistenceProvider.createEntityManagerFactory(HibernatePersistenceProvider.java:52)
	at javax.persistence.Persistence.createEntityManagerFactory(Persistence.java:55)
	at com.itplh.projects.user.orm.jpa.JpaDemo.main(JpaDemo.java:21)
Caused by: javax.persistence.PersistenceException: Unrecognized persistence.xml version [2.2]
	at org.hibernate.jpa.boot.internal.PersistenceXmlParser.validate(PersistenceXmlParser.java:336)
	at org.hibernate.jpa.boot.internal.PersistenceXmlParser.loadUrl(PersistenceXmlParser.java:290)
	at org.hibernate.jpa.boot.internal.PersistenceXmlParser.parsePersistenceXml(PersistenceXmlParser.java:94)
	at org.hibernate.jpa.boot.internal.PersistenceXmlParser.doResolve(PersistenceXmlParser.java:84)
	at org.hibernate.jpa.boot.internal.PersistenceXmlParser.locatePersistenceUnits(PersistenceXmlParser.java:66)
	at org.hibernate.jpa.HibernatePersistenceProvider.getEntityManagerFactoryBuilderOrNull(HibernatePersistenceProvider.java:80)
	... 4 more
```

**解决方案**

`persistence.xml`协议头版本更换为2.1匹配当前 `org.hibernate:hibernate-core:5.1.0.Final`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
     http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="emf" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    </persistence-unit>
</persistence>
```

## javax.validation.ValidationException

Unable to load 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath

```java
三月 10, 2021 5:53:23 下午 org.hibernate.validator.internal.util.Version <clinit>
INFO: HV000001: Hibernate Validator 5.1.0.Final
Exception in thread "main" javax.validation.ValidationException: Unable to instantiate Configuration.
	at javax.validation.Validation$GenericBootstrapImpl.configure(Validation.java:279)
	at javax.validation.Validation.buildDefaultValidatorFactory(Validation.java:110)
	at com.itplh.projects.user.validator.bean.validation.BeanValidationDemo.main(BeanValidationDemo.java:15)
Caused by: javax.validation.ValidationException: HV000183: Unable to load 'javax.el.ExpressionFactory'. Check that you have the EL dependencies on the classpath
	at org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.<init>(ResourceBundleMessageInterpolator.java:172)
	at org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.<init>(ResourceBundleMessageInterpolator.java:118)
	at org.hibernate.validator.internal.engine.ConfigurationImpl.<init>(ConfigurationImpl.java:110)
	at org.hibernate.validator.internal.engine.ConfigurationImpl.<init>(ConfigurationImpl.java:86)
	at org.hibernate.validator.HibernateValidator.createGenericConfiguration(HibernateValidator.java:41)
	at javax.validation.Validation$GenericBootstrapImpl.configure(Validation.java:276)
	... 2 more
```

**解决方案**

引入依赖

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>3.0.1-b11</version>
</dependency>
```

## javax.el相关异常

```java
1. javax.validation.ValidationException Unable to load 'javax.el.ExpressionFactory'.

2. 信息: validateJarFile(/Users/tanpenggood/geek-training-camp/.extract/webapps/ROOT/WEB-INF/lib/javax.el-3.0.1-b11.jar) - jar not loaded. See Servlet Spec 2.on 9.7.2. Offending class: javax/el/Expression.class

3. have different Class objects for the type javax/el/ExpressionFactory used in the signature
```

**解决方案**

引入依赖，注意 scope 为 test。

即，仅在测试时需要，因为运行时tomcat内自带有相关依赖。

```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.el</artifactId>
    <version>3.0.1-b11</version>
    <scope>test</scope>
</dependency>
```
