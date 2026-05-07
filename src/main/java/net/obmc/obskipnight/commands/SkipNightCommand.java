package net.obmc.obskipnight.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.obmc.obskipnight.OBSkipNight;
import net.obmc.obskipnight.config.PluginConfig;
import net.obmc.obskipnight.util.MessagingUtils;

public class SkipNightCommand implements BasicCommand {

	static Logger log = LoggerFactory.getLogger(OBSkipNight.class);

	private final OBSkipNight plugin;
	private final PluginConfig config;
	private BukkitTask skipTask = null;

	public SkipNightCommand(OBSkipNight plugin, PluginConfig config) {
		this.plugin = plugin;
		this.config = config;
	}
	
	@Override
	public void execute(CommandSourceStack source, String[] args) {

		if (!(source.getSender() instanceof Player)) {
			return;
		}
		Player sender = (Player) source.getSender();

	    boolean isOp = sender.isOp();
	    boolean ownerOnly = config.getOwnerOnly();


		    // start skipnight
		    if (args.length == 0) {
		        if (!isOp && ownerOnly) {
		            MessagingUtils.sendMessage( (Player)sender, Component.text("Sorry, command is reserved for server operators.").color(NamedTextColor.RED));
		        }
		        startSkipNight((Player) sender);
				return;
		    }

            // only server operators from here on
            if (!isOp) {
                MessagingUtils.sendMessage( (Player)sender,
                    Component.text("Sorry, command is reserved for server operators.").color(NamedTextColor.RED)
                    );
				return;
            }

		    // check provided argument
			switch (args[0].toLowerCase()) {

				// toggle all hostile warning and tracking
				case "owneronly":
					config.setOwnerOnly(!config.getOwnerOnly());
					MessagingUtils.sendMessage((Player)sender,
						Component.text("Flag 'owneronly' is now " + config.getOwnerOnly()).color(NamedTextColor.GREEN)
					);
					config.save();
					break;

				case "timer":
					if (args.length == 1) {
						MessagingUtils.sendMessage((Player)sender,
						    Component.text("Provide a value in seconds").color(NamedTextColor.RED));
					}
					try {
						config.setTimer(Integer.parseInt(args[1]));
						MessagingUtils.sendMessage((Player)sender,
							Component.text("timer is now " + config.getTimer() + " second" + (config.getTimer() != 1 ? "s" : "")).color(NamedTextColor.GREEN)
						);
						config.save();
					} catch (NumberFormatException e) {
						MessagingUtils.sendMessage((Player)sender,	Component.text("Provide a valid value in seconds").color(NamedTextColor.RED));
					}
					break;

				case "broadcast":
					config.setDoBroadcast(!config.getDoBroadcast());
					MessagingUtils.sendMessage((Player)sender,
						Component.text("Flag 'broadcast' is now " + config.getDoBroadcast()).color(NamedTextColor.GREEN)
					);
					config.save();
					break;

				// show current settings 
				case "settings":
					MessagingUtils.sendMessage((Player)sender, Component.text("Current settings:").color(NamedTextColor.YELLOW));
					MessagingUtils.sendMessage((Player)sender, Component.text("  owneronly: " + config.getOwnerOnly()).color(NamedTextColor.YELLOW));
					MessagingUtils.sendMessage((Player)sender, Component.text("  timer: " + config.getTimer() + " second" + (config.getTimer() != 1 ? "s" : "")).color(NamedTextColor.YELLOW));
					MessagingUtils.sendMessage((Player)sender, Component.text("  broadcast: " + config.getDoBroadcast()).color(NamedTextColor.YELLOW));
					break;

				case "help":
				default:
					Usage(sender);
					break;
			}
	}

    void Usage(Player sender) {
    	MessagingUtils.sendMessage((Player)sender,
    		Component.text("/skipnight owneronly - toggle only op can run skipnight").color(NamedTextColor.YELLOW)
    	);
    	MessagingUtils.sendMessage((Player)sender,
       		Component.text("/skipnight broadcast - toggle all player announcement of skip").color(NamedTextColor.YELLOW)
       	);
    	MessagingUtils.sendMessage((Player)sender,
           	Component.text("/skipnight timer - set the countdown time in seconds").color(NamedTextColor.YELLOW)
        );
    	MessagingUtils.sendMessage((Player)sender,
       		Component.text("/skipnight settings - shows current config").color(NamedTextColor.YELLOW)
       	);
    	MessagingUtils.sendMessage((Player)sender,
       		Component.text("/skipnight help - this usage").color(NamedTextColor.YELLOW)
       	);

    }

	private boolean isNight(World world) {
		long time = world.getTime(); // 0–23999
    	return time >= 13000 && time <= 23000;
	}

	public boolean startSkipNight(Player initiator) {

    	World world = initiator.getWorld();

	    if (!isNight(world)) {
    	    MessagingUtils.sendMessage(initiator, Component.text("You can only skip during night", NamedTextColor.RED));
        	return false;
    	}

    	if (skipTask != null) {
        	MessagingUtils.sendMessage(initiator, Component.text("A skip-night countdown is already running", NamedTextColor.RED));
        	return false;
    	}

		int seconds = config.getTimer();
		boolean doBroadcast = config.getDoBroadcast();
		MessagingUtils.sendSkipMessage(initiator, world, seconds, doBroadcast);

		skipTask = Bukkit.getScheduler().runTaskTimer(
            plugin,
            new Runnable() {
                int remaining = seconds;

                @Override
                public void run() {
                    if (remaining <= 0) {
                        doSkip(world);
                        skipTask.cancel();
                        skipTask = null;
                        return;
                    }

                    Bukkit.broadcastMessage("§eSkipping night in " + remaining + "…");
                    remaining--;
                }
            },
            20L, 20L
    	);

		return true;
	}

	private void doSkip(World world) {
    	world.setTime(0L);
    	world.setStorm(false);
    	world.setThundering(false);
	}
}