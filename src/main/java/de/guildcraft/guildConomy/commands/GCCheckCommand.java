package de.guildcraft.guildConomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.avaje.ebean.EbeanServer;

import de.guildcraft.guildConomy.GCPlugin;
import de.guildcraft.guildConomy.persistence.Account;

public class GCCheckCommand extends GCSubcommand {

	public GCCheckCommand(GCPlugin plugin) {
		super(plugin);
		permission = "guildconomy.admin.check";
	}

	@Override
	public boolean execute(Player player, String[] args) {
		if(args.length > 2 || args.length < 1) {
			player.sendMessage(ChatColor.RED + "Bitte überprüfe die Schreibweise.");
			return true;
		}
		
		EbeanServer server = plugin.getDatabase();
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("vp")) {
				Account account = server.find(Account.class).where().ieq("username", args[1]).findUnique();
				if(account == null) {
					player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.WHITE + args[1] + ChatColor.RED + " existiert nicht.");
					return true;
				}
				
				player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + args[1] + "'s Votepoints: " + ChatColor.WHITE +
						String.valueOf(account.getVotepoints()));
				return true;
			}
		}
		
		Account account = server.find(Account.class).where().ieq("username", args[0]).findUnique();
		if(account == null) {
			player.sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.WHITE + args[0] + " existiert nicht.");
			return true;
		}
		
		player.sendMessage(ChatColor.GOLD + "[GuildConomy] " + args[0] + "'s Balance: " + ChatColor.WHITE +
				String.valueOf(account.getTaler()) + " Taler.");
		
		return true;
	}

}
