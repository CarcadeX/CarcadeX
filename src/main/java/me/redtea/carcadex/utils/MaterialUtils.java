package me.redtea.carcadex.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaterialUtils {
    /**
     *  the material with the specified name or Material.AIR
     * @param s name of material
     * @return material with this name
     */
    @NotNull
    public static Material getMaterialFromString(@Nullable String s) {
        if(s == null || s.equals("")) return Material.AIR;
        Material material = Material.matchMaterial(s);
        if(material != null) return material;

        String[] spl = s.split(":");

        if (spl[0].equals("minecraft")) {
            material = Material.matchMaterial(spl[1]);
        } else {
            if(spl.length < 2) {
                material = Material.matchMaterial(spl[0]);
            } else {
                material = Material.matchMaterial(spl[0]+"_"+spl[1]);;
            }
        }

        return  material == null ? Material.AIR : material;
    }

    /**
     * Removes 'amount' items of Material from inventory
     * @param inventory - inventory from which we remove item
     * @param type material of item
     * @param amount amount of item
     */
    public void remove(@NotNull Inventory inventory, @NotNull Material type, int amount) {
        if (amount <= 0) return;
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }

    /**
     * Count of items with material 'type' in inventory
     * @param inventory inventory from which we count
     * @param type material of item
     * @return count of items with material 'type'
     */
    public int count(@NotNull Inventory inventory, @NotNull Material type) {
        int result = 0;
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                result += is.getAmount();
            }
        }
        return result;
    }
}
