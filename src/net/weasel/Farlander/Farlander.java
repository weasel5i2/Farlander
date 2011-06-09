package net.weasel.Farlander;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Farlander extends JavaPlugin
{
	public String pluginName = "";
	public String pluginVersion = "";
	
	@Override
	public void onDisable() 
	{
		logOutput( pluginName + " v" + pluginVersion + " disabled." );
	}

	@Override
	public void onEnable() 
	{
		pluginName = getDescription().getName();
		pluginVersion = getDescription().getVersion();
		
		CommandExecutor cmdGoFar = new CommandGoFar(this);
		CommandExecutor cmdReturn = new CommandReturn(this);
		
		getCommand( "far" ).setExecutor( cmdGoFar );
		getCommand( "return" ).setExecutor( cmdReturn );

		logOutput( pluginName + " v" + pluginVersion + " enabled." );
	}

	public void logOutput( String message )
	{
		System.out.println( "[" + pluginName + "] " + message );
	}
}
