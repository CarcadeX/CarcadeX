# CarcadeX
Powerful library for bukkit development and other...

Messages
---------------

<a href="https://docs.advntr.dev/minimessage/format.html">Using MiniMessages format</a>

messages.yml in resource (jar) folder:
```yaml
error: "<red>Error!</red>"
someRoot:
  message1: "<green>Hi, %name%!</green>"
  message2:
    - "<green>Message start!</green>"
    - "<green>End!</green>"
```
 
Using in plugin:
 
```java
@Override
public void onEnable() {
      Messages messages = Messages.of(this); //Init Messages
      CommandSender sender = ...; //Getting CommandSender
      messages.get("error").send(sender); //Getting error message
      messages.get("someRoot.message1").replaceAll("%name%", sender.getName()).send(sender); //Getting someRoot.message1 message
      messages.get("someRoot.message2").send(sender); //Getting someRoot.message2 message
}
```

Ways of getting Message object:
1. Load messages.yml from jar by default: 
```java
Messages messages = Messages.of(this);
```
2. Load custom file from jar by default:
```java
Messages messages = Messages.of(this, "filename.yml");
```
3. Load using ConfigurationSection: 
```java
Messages messages = Messages.of(getConfig().getConfigurationSection("messages"));
```

Supporting
---------------
<p>Paper 1.16.x or higher</p>
<p>Java 8 or higher</p>

Starting use
---------------
**Gradle:**
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.iredtea:carcadex:1.1.0") //or add it to plugin.yml: libs and set compileOnly
}
```
**Maven:**
```xml
<dependency>
    <groupId>io.github.iredtea</groupId>
    <artifactId>carcadex</artifactId>
    <version>1.1.0</version>
</dependency>
```
