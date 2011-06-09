package net.weasel.Farlander;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReturn implements CommandExecutor
{
	public Farlander instance;
	
	public CommandReturn( Farlander i ) 
	{
		instance = i;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) 
	{
		if( arg0 instanceof Player )
		{
			if( arg1.getName() == "return" )
			{
				Player player = (Player)arg0;
				World world = player.getWorld();
				Block spawnBlock = getTopBlock( world, 0, 0 );
				
				player.sendMessage( "Returning you to your homeland.." );
				player.teleport( spawnBlock.getLocation() );
				genTargetChunks( spawnBlock.getLocation() );
			}
		}
		
		return false;
	}
	
    public static Block getTopBlock( World world, double X, double Z )
    {
    	Block retVal = null;
    	double Y = 127;
    	boolean exitLoop = false;
    	Block currentBlock = null;
    	
    	while( Y > 0 && exitLoop == false )
    	{
    		try
    		{
    			currentBlock = world.getBlockAt((int)X,(int)Y,(int)Z);
    		}
    		catch( Exception e )
    		{
    			retVal = null;
    			exitLoop = true;
    			break;
    		}
    		
    		if( currentBlock.getTypeId() != 0 
    		&& currentBlock.getTypeId() != 17 
    		&& currentBlock.getTypeId() != 18 
    		&& currentBlock.getTypeId() != 78 )
    		{
    			retVal = currentBlock;
    			exitLoop = true;
    		}
    		else
    		{
    			Y--;
    		}
    	}
    	
    	return retVal;
    }

    public static void genTargetChunks( Location loc )
    {
    	World world = loc.getWorld();
    	Block block = loc.getBlock();

    	Integer X = block.getX();
    	Integer Z = block.getZ();
    	
    	
    	// Force loading of the 3x3 chunk area player teleports to..
    	//
    	world.regenerateChunk( X - 16, Z - 16 );
    	world.regenerateChunk( X - 16, Z );
    	world.regenerateChunk( X - 16, Z + 16 );
    	world.regenerateChunk( X, Z - 16 ); 
    	world.regenerateChunk( X, Z );
    	world.regenerateChunk( X, Z + 16 );
    	world.regenerateChunk( X + 16, Z - 16 );
    	world.regenerateChunk( X + 16, Z );
    	world.regenerateChunk( X + 16, Z + 16 );
    }
}
