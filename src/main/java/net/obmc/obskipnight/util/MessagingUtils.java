package net.obmc.obskipnight.util;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MessagingUtils {

	static Logger log = Logger.getLogger("Minecraft");

	public static void sendSkipMessage(Player initiator, World world, int seconds, boolean doBroadcast) {

		Component skipMessage;
		if (doBroadcast) {
		    skipMessage = buildMessage(initiator.getName() + " has started a skipnight in " + seconds + " second" + ( seconds != 1 ? "s" : "" ) );
			sendAllMessage(skipMessage, world);
		} else {
            skipMessage = buildMessage("Skip night in " + seconds + " second" + ( seconds != 1 ? "s" : "" ) );		    
			sendMessage(initiator, skipMessage);
		}
	}

	public static void sendAllMessage(Component message, World world) {
	    Bukkit.getOnlinePlayers().forEach(p-> {
			if (p.getWorld().getName().equals(world.getName())) {
	        	p.sendActionBar(message);
			}
	    });
	}
	public static void sendMessage(Player initiator, Component message) {
		initiator.sendMessage(message);
	}

	private static Component buildMessage(String message) {
		return Component.text(message, NamedTextColor.YELLOW);
	}
}
