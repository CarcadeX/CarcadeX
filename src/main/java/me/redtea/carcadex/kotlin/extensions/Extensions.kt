package me.redtea.carcadex.kotlin.extensions

import me.redtea.carcadex.utils.ItemStackUtils
import me.redtea.carcadex.utils.MaterialUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.Cancellable
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable

val console = Bukkit.getConsoleSender()

fun Cancellable.cancel() {
    this.isCancelled = true
}

fun consoleCommand(command: String) = Bukkit.dispatchCommand(console, command)

fun matchMaterial(material: String): Material = MaterialUtils.getMaterialFromString(material)

fun Inventory.remove(material: Material, amount: Int) = MaterialUtils.remove(this, material, amount)
fun Inventory.count(material: Material): Int = MaterialUtils.count(this, material)

fun ItemStack.durability(durability: Int) = ItemStackUtils.setDurability(this, durability)
fun ItemStack.durability(): Int = ItemStackUtils.getDurability(this)

fun runnableX(task: () -> Unit): BukkitRunnable = object : BukkitRunnable() {
    override fun run() {
        task()
    }
}

fun Plugin.print(s: String) = logger.info(s)
fun Plugin.print(a: Any) = print(a.toString())

