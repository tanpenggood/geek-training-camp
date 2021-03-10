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

hibernate-core 高版本中语法不兼容，故降低版本号为 5.1.0.Final

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
