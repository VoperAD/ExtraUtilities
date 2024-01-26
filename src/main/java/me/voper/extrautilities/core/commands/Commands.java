package me.voper.extrautilities.core.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.voper.extrautilities.ExtraUtilities;
import me.voper.extrautilities.core.Registry;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("%extrautilities")
@Description("Entry command for ExtraUtilities")
public class Commands extends BaseCommand {

    @Dependency
    ExtraUtilities plugin;
    @Dependency
    Registry registry;

    @HelpCommand
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("%restore")
    @Description("Restaura o storage que você estiver segurando.")
    @CommandPermission("extrautilities.anyone.restore")
    public void restore(CommandSender sender) {
        if (!(sender instanceof Player p))
            return;

        registry.getStorageHelpers().forEach(sh -> {
            final ItemStack item = p.getInventory().getItemInMainHand();
            if (sh.isStorage(item))
                sh.restore(item);
        });
    }

    @Subcommand("%restoreall")
    @Description("Restaura todos os storages encontrados no seu inventário.")
    @CommandPermission("extrautilities.anyone.restoreall")
    public void restoreAll(CommandSender sender) {
        if (!(sender instanceof Player p))
            return;

        registry.getStorageHelpers().forEach(sh -> {
            sh.restoreAll(p);
        });
    }

    @Subcommand("%setstorageamount")
    @Description("Altera a quantidade de items dentro do storage.")
    @CommandPermission("extrautilities.admin.setstorageamount")
    public void setStorageAmount(CommandSender sender, int amount) {
        if (!(sender instanceof Player p))
            return;

        registry.getStorageHelpers().forEach(sh -> {
            final ItemStack item = p.getInventory().getItemInMainHand();
            sh.setStorageAmount(item, amount, () -> {});
        });
    }

    @Subcommand("%setcontent")
    @Description("Altera o item armazenado no storage")
    @CommandPermission("extrautilities.admin.setcontent")
    @CommandCompletion("@itemid")
    public void setStorageContent(CommandSender sender, String itemid) {
        if (!(sender instanceof Player p))
            return;

        final ItemStack item = p.getInventory().getItemInMainHand();
        final ItemStack itemToStore = getItemToStore(itemid);

        if (itemToStore == null) {
            p.sendMessage(ChatColor.RED + "Invalid item");
            return;
        }

        registry.getStorageHelpers().forEach(sh -> sh.setStoredItem(item, itemToStore));
    }

    private ItemStack getItemToStore(String itemid) {
        SlimefunItem sfItem = SlimefunItem.getById(itemid);
        Material material = Material.matchMaterial(itemid.toUpperCase());

        if (sfItem != null) {
            return new ItemStack(sfItem.getItem());
        } else if (material != null) {
            return new ItemStack(material);
        }

        return null;
    }

}
