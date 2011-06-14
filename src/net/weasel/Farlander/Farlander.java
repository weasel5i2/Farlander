package net.weasel.Farlander;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Farlander extends JavaPlugin
{
	public String pluginName = "";
	public String pluginVersion = "";
	public static HashMap<Player,Location> returnLocations = null;
	
	@Override
	public void onDisable() 
	{
		logOutput( pluginName + " v" + pluginVersion + " disabled." );
	}

	@Override
	public void onEnable() 
	{
		returnLocations = new HashMap<Player,Location>();
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

	public static void genTargetChunks( Location loc )
    {
    	World world = loc.getWorld();
    	Block block = loc.getBlock();

    	Integer X = block.getX();
    	Integer Z = block.getZ();
    	
    	
    	// Force loading of the 3x3 chunk area player teleports to..
    	//
    	world.refreshChunk( X - 16, Z - 16 );
    	world.refreshChunk( X - 16, Z );
    	world.refreshChunk( X - 16, Z + 16 );
    	world.refreshChunk( X, Z - 16 ); 
    	world.refreshChunk( X, Z );
    	world.refreshChunk( X, Z + 16 );
    	world.refreshChunk( X + 16, Z - 16 );
    	world.refreshChunk( X + 16, Z );
    	world.refreshChunk( X + 16, Z + 16 );
    }
}
