# Maven Dependencies #

To pull Instinct as a Maven dependency, add the following to your `pom.xml`.

```
<dependencies>
  <dependency>
    <groupId>com.googlecode.instinct</groupId>
    <artifactId>instinct-core</artifactId>
    <version>0.1.9</version>
  </dependency>
<dependencies>
```

# Repositories #

Instinct 0.1.9 is available from the central Maven repository.

The latest release is always available from the Instinct Maven repository by manually adding the Instinct repository to your `pom.xml`:

```
<project>
  ...
  <repositories>
    <repository>
      <id>instinct-repository</id>
      <url>http://instinct.googlecode.com/svn/artifacts/maven/</url>
    </repository>
  </repositories>
  ...
</project>
```