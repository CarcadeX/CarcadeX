# CarcadeX
Powerful library for bukkit development and other...

Messages
---------------
messages.yml in resource (jar) folder:
<pre>

error: "<red>Error!"
someRoot:
      message1: "<green>Message"
      message2:
          - "<green>Message start!"
          - "<green>End!"
 </pre>
 
Using in plugin:
 
<pre>
@Override
public void onEnable() {
      Messages messages = Messages.of(this); //Init Messages
      CommandSender sender = ...; //Getting CommandSender
      messages.get("error").send(sender); //Getting error message
      message.get("someRoot.message1").send(sender); //Getting someRoot.message1 message
      message.get("someRoot.message2").send(sender); //Getting someRoot.message2 message
}
</pre>

Ways of getting Message object:
1. Load messages.yml from jar by default: <pre>Messages messages = Messages.of(this);</pre>
2. Load custom file from jar by default: <pre>Messages messages = Messages.of(this, "filename.yml");</pre>
3. Load using ConfigurationSection: <pre>Messages messages = Messages.of(getConfig().getConfigurationSection("messages"));</pre>

Supporting
---------------
<p>**Paper 1.16.x or longer**</p>
<p>**Java 8 or longer**</p>
