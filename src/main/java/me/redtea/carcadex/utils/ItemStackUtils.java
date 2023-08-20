package me.redtea.carcadex.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtils {
    public static void setDurability(ItemStack itemStack, int damage) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta instanceof Damageable) {
                Damageable damageMeta = (Damageable) itemMeta;damageMeta.setDamage(damage);
            }
            itemStack.setItemMeta(itemMeta);
        }
    }

    public static int getDurability(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta instanceof Damageable) {
                Damageable damageMeta = (Damageable) itemMeta;
                return ((Damageable) itemMeta).getDamage();
            }
            throw new UnsupportedOperationException("This item stack has not durability");
        }
        throw new UnsupportedOperationException("This item stack has not durability");
    }
}
