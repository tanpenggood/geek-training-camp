# week16

作业提交链接： https://jinshuju.net/f/WVgVmU

## 作业一

> 将 Spring Boot 应用打包 Java Native 应用，再将该应用通过 Dockerfile 构建 Docker 镜像，部署到 Docker 容器中，并且 成功运行，Spring Boot 应用的实现复杂度不做要求。

1. 项目结构

    ```
    my-native
        |- native-demo
            |- src
            |- Dockerfile
            |- apache-maven-3.8.1-bin.tar.gz
            |- graalvm-ce-java11-linux-amd64-21.1.0.tar.gz
            |- native-image-installable-svm-java11-linux-amd64-21.1.0.jar
    ```

    `Dockerfile`
    ```Dockerfile
    FROM centos:centos7
    #安装maven
    RUN mkdir /usr/local/maven
    WORKDIR /usr/local/maven/
    #从本地复制maven到容器,ADD命令会自动解压
    ADD apache-maven-3.8.1-bin.tar.gz /usr/local/maven/
    #设置MAVEN_HOME
    ENV MAVEN_HOME=/usr/local/maven/apache-maven-3.8.1/
    
    #安装graalvm
    RUN	mkdir /usr/local/graalvm
    #从本地复制graalvm及native-image到容器
    ADD graalvm-ce-java11-linux-amd64-21.1.0.tar.gz /usr/local/graalvm/
    ADD native-image-installable-svm-java11-linux-amd64-21.1.0.jar /usr/local/graalvm/
    WORKDIR /usr/local/graalvm/
    #设置JAVA_HOME
    ENV JAVA_HOME=/usr/local/graalvm/graalvm-ce-java11-21.1.0/
    #设置PATH
    ENV PATH=${JAVA_HOME}/bin:${MAVEN_HOME}/bin:$PATH
    #安装native-image
    RUN	gu install -L /usr/local/graalvm/native-image-installable-svm-java11-linux-amd64-21.1.0.jar
    #安装native-iamge运行时依赖gcc等环境
    RUN yum -y install gcc glibc-devel zlib-devel libstdc++-static
    
    #从gitee拉取代码并构建native-image
    RUN yum -y install git
    WORKDIR /usr/local/
    RUN git clone https://gitee.com/tanpenggood/geek-training-camp.git
    WORKDIR /usr/local/geek-training-camp/
    RUN git checkout v-week15
    WORKDIR /usr/local/geek-training-camp/my-native/native-demo
    RUN mvn -Pnative clean package -Dmaven.test.skip=true
    EXPOSE 8080
    CMD /usr/local/geek-training-camp/my-native/native-demo/target/native-demo
    ```
2. 测试

    1. 构建镜像
    
        ```
        docker build -t native-demo:v1 .
        ```
    
        ```
        [+] Building 1332.8s (21/22)                                                                                                                                                
         => [internal] load build definition from Dockerfile                                                                                                                   0.0s
         => => transferring dockerfile: 1.45kB                                                                                                                                 0.0s
         => [internal] load .dockerignore                                                                                                                                      0.0s
         => => transferring context: 2B                                                                                                                                        0.0s
         => [internal] load metadata for docker.io/library/centos:centos7                                                                                                     16.3s
         => [ 1/18] FROM docker.io/library/centos:centos7@sha256:0f4ec88e21daf75124b8a9e5ca03c37a5e937e0e108a255d890492430789b60e                                              0.0s
         => [internal] load build context                                                                                                                                      0.0s
         => => transferring context: 199B                                                                                                                                      0.0s
         => CACHED [ 2/18] RUN mkdir /usr/local/maven                                                                                                                          0.0s
         => CACHED [ 3/18] WORKDIR /usr/local/maven/                                                                                                                           0.0s
         => CACHED [ 4/18] ADD apache-maven-3.8.1-bin.tar.gz /usr/local/maven/                                                                                                 0.0s
         => [ 5/18] RUN rm -rf /usr/local/maven/apache-maven-3.8.1/conf/settings.xml                                                                                           0.5s
         => [ 6/18] RUN MKDIR /usr/local/graalvm                                                                                                                               0.5s
         => [ 7/18] ADD graalvm-ce-java11-linux-amd64-21.1.0.tar.gz /usr/local/graalvm/                                                                                       17.1s
         => [ 8/18] ADD native-image-installable-svm-java11-linux-amd64-21.1.0.jar /usr/local/graalvm/                                                                         0.2s
         => [ 9/18] WORKDIR /usr/local/graalvm/                                                                                                                                0.0s
         => [10/18] RUN GU install -L /usr/local/graalvm/native-image-installable-svm-java11-linux-amd64-21.1.0.jar                                                            1.6s
         => [11/18] RUN yum -y install gcc glibc-devel zlib-devel libstdc++-static                                                                                            41.9s
         => [12/18] RUN yum -y install git                                                                                                                                    22.9s
         => [13/18] WORKDIR /usr/local/                                                                                                                                        0.0s
         => [14/18] RUN git clone https://gitee.com/tanpenggood/geek-training-camp.git                                                                                         4.2s
         => [15/18] WORKDIR /usr/local/geek-training-camp/                                                                                                                     0.0s
         => [16/18] RUN git checkout v-week15                                                                                                                                  0.5s
         => [17/18] WORKDIR /usr/local/geek-training-camp/my-native/native-demo                                                                                                0.0s
         => [18/18] RUN mvn -Pnative clean package -Dmaven.test.skip=true                                                                                                   1226.8s
         => => # Downloading from central: https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter-params/5.7.2/junit-jupiter-params-5.7.2.jar                        
         => => # Downloaded from central: https://repo.maven.apache.org/maven2/org/junit/platform/junit-platform-commons/1.7.2/junit-platform-commons-1.7.2.jar (100 kB at 50 kB/s)
         => => # Downloading from central: https://repo.maven.apache.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.7.2/junit-jupiter-engine-5.7.2.jar                        
         => => # Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/commons/commons-compress/1.11/commons-compress-1.11.jar (426 kB at 199 kB/s)             
         => => # Progress (3): 119/176 kB | 6.4 kB | 49/213 kB                                                                                                                     
         => => # [output clipped, log limit 1MiB reached]
        ```
    
    2. 启动容器
    
        ```
        docker run -d -it -p 8080:8080 native-demo
        ```
    
    3. 访问 http://localhost:8080/helloworld
        - 响应数据 `Hello, Spring Native.`


## 离线安装包

### Mac

- GraalVM 
    - https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/graalvm-ce-java11-darwin-amd64-21.1.0.tar.gz
    
- native-image 
    - https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/native-image-installable-svm-java11-darwin-amd64-21.1.0.jar

### Linux

- GraalVM 
    - https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/graalvm-ce-java11-linux-amd64-21.1.0.tar.gz

- native-image 
    - https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/native-image-installable-svm-java11-linux-amd64-21.1.0.jar

- Maven 
    - https://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz
