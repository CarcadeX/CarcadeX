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

Repo
---------------
<p>You can use Repo.<K, V>builder() to create the repository you need.<p>
<h3>Binary repository</h3>
<p>This repository requires the least effort to initialize it.<p>
<p>You need only class, that implements Serializable and contains fields that types are primitive or implements Serializable</p>
<p>For example:</p>

```java
@Data
class User implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private final String name;
    private int age;
    private List<String> children;
}
```

<p>Usage of builder:</p>

```java
MutableRepo<String, User> repo = Repo.<String, User> builder()
        .dir(Paths.get("data")
        .binary()
        .build();
```

<p>Warning! This method can be quite unstable! It is not recommended to use :(</p>
<p>Also it is recommended to set the serialVersionUID to the class as it was done in the example:</p>

```java
private static final long serialVersionUID = 6529685098267757690L;
```

<h3>Serializator repository</h3>
<p>To use this type of repository, you must create your own implementation of CommonSerializer<T>.</p>
<p>For example:</p>

```java
public record Purchase(int idOnForum,
                       String command,
                       String[] allowedServers,
                       String[] allowedWorlds,
                       XMaterial icon) {}
                     
public class PurchaseSerializer implements CommonSerializer<Purchase> {
    @Override
    public String serialize(Purchase purchase) {
        return purchase.idOnForum() +
                "\n" +
                purchase.command() +
                "\n" +
                String.join(",", purchase.allowedServers()) +
                "\n" +
                String.join(",", purchase.allowedWorlds()) +
                "\n" +
                purchase.icon().parseMaterial().name();
    }

    @Override
    public Purchase deserialize(String s) {
        String[] args = s.split("\n");
        return new Purchase(
                Integer.parseInt(args[0]),
                args[1],
                args[2].split(","),
                args[3].split(","),
                XMaterial.valueOf(args[4])
        );
    }
}
```


<p>Now you can create repo using builder:</p>

```java
MutableRepo<String, User> repo = Repo.<String, User> builder()
        .dir(Paths.get("data")
        .serializer(new PurchaseSerializer())
        .build();
```

<h3>Schema repo</h3>
<p>If you need a complete customization of the process of saving to memory (for example, if you want to use a database), then you can create a class that implements SchemaStrategy and use the builder from the example.</p>

```java
//using mysql class from https://github.com/Huskehhh/MySQL
@Data
class User {
    private final int id;
    private final String name;
    private int age;
}

@RequiredArgsConstructor
class UserSchema implements SchemaStrategy<Integer, User> {
    private final MySQL mysql;

    @Override
    public void init() {
        mysql.query("CREATE TABLE IF NOT EXISTS table (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL," +
                "age INT NOT NULL" +
                ");");
    }

    @Override
    public void close() {
        mySQL.closeConnection();
    }

    @Override
    public Collection<User> all() {
        List<User> result = new ArrayList<>();
        mysql.query("SELECT * from table;",results->{
            if(results != null)
            while(results.next()){
                result.add(new User(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getInt("age")
                ));
            }
        });
        return result;
    }

    @Override
    public User get(Integer key) {
        mysql.query("SELECT * from table WHERE id = "+key+";",results->{
            if(results != null)
                if(results.next()){
                    return new User(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getInt("age")
                    );
                }
        });
        throw new RuntimeException("not found");
    }

    @Override
    public void insert(Integer key, User value) {
        mysql.update("INSERT INTO `table` (`id`, `name`, `age`) VALUES ('"+key+"','"+
                value.getName()
                +"',"+
                value.getAge()
                +");");

    }

    @Override
    public void remove(Integer key) {
        mysql.query("DELETE FROM table WHERE id =" + key + ";");
    }
}
```

<p>Creating repo:</p>

```java
MySQL mysql = ...;
Repo.<Integer, User> builder().
                .schema(new UserSchema(mysql))
                .build();
```

<h3>Customizing builder</h3>
<p>In addition to directly installing the repository folder, you can:<p>

```java
//A) Set name of dir (the path to it is the root folder of the program)
Repo.builder()
        .dir("data-dir")
        .serializer(new PurchaseSerializer())
        .build();


//B) Set bukkit plugin and name of dir (the path to it is the folder of your bukkit plugin)
@Override
public void onEnable() {
    Repo.builder()
            .dir("data-dir")
            .plugin(this)
            .serializer(new PurchaseSerializer())
            .build();
}


//C) Set only plugin (the folder name "saved-data-carcadex" will be used in plugin folder)
@Override
public void onEnable() {
    Repo.builder()
            .plugin(this)
            .serializer(new PurchaseSerializer())
            .build();
}


//D) implementation (the folder name "saved-data-carcadex" will be used in root folder)
Repo.builder().serializer(new PurchaseSerializer()).build();
```
  
<p>You can set repo autoupdate:</p>

```java
Repo.builder()
          .autoupdate(1000*60*60*10) //in miliseconds
          .serializer(new PurchaseSerializer())
          .build();
```

<p>You can set repo as threadSafe:</p>

```java
Repo.builder()
          .threadSafe()
          .serializer(new PurchaseSerializer())
          .build();
```

<p>You can set logger for repo:</p>

```java
Repo.builder()
          .logging() //Default slf4j logger
          /* .logging(org.slf4j.Logger) other logger
             .debugLogging(org.slf4j.Logger) only on debug enable logger
             or else .debugLogging() using default slf4j logger
          */
          .serializer(new PurchaseSerializer())
          .build();
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
    implementation("io.github.iredtea:carcadex:1.0.5") //or add it to plugin.yml: libs and set compileOnly
}
```
**Maven:**
```xml
<dependency>
    <groupId>io.github.iredtea</groupId>
    <artifactId>carcadex</artifactId>
    <version>1.0.5</version>
</dependency>
```
