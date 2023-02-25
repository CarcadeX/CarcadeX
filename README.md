# CarcadeX
Powerful library for bukkit development and other...

Messages
---------------
messages.yml in resource (jar) folder:
```
error: "<red>Error!"
someRoot:
      message1: "<green>Message"
      message2:
          - "<green>Message start!"
          - "<green>End!"
```
 
Using in plugin:
 
```
@Override
public void onEnable() {
      Messages messages = Messages.of(this); //Init Messages
      CommandSender sender = ...; //Getting CommandSender
      messages.get("error").send(sender); //Getting error message
      message.get("someRoot.message1").send(sender); //Getting someRoot.message1 message
      message.get("someRoot.message2").send(sender); //Getting someRoot.message2 message
}
```

Ways of getting Message object:
1. Load messages.yml from jar by default: <pre>Messages messages = Messages.of(this);</pre>
2. Load custom file from jar by default: <pre>Messages messages = Messages.of(this, "filename.yml");</pre>
3. Load using ConfigurationSection: <pre>Messages messages = Messages.of(getConfig().getConfigurationSection("messages"));</pre>

Supporting
---------------
<p>Paper 1.16.x or longer</p>
<p>Java 8 or longer</p>

Starting use
---------------
**Gradle:**
```
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.iredtea:carcadex:1.0.0") //or add it to plugin.yml: libs and set compileOnly
}
```
**Maven:**
```
<dependency>
    <groupId>io.github.iredtea</groupId>
    <artifactId>carcadex</artifactId>
    <version>1.0.0</version>
</dependency>
```
