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
			Player player = (Player)arg0;

			if( Farlander.returnLocations.containsKey(player) )
			{
				Location loc = Farlander.returnLocations.get( player );
				Farlander.genTargetChunks( loc );
				player.sendMessage( "Returning you to your homeland.." );
				player.teleport( loc );
			}
			else
			{
				player.sendMessage( "Unable to send you back home!" );
				Location loc = getTopBlock( player.getWorld(), 0, 0 ).getLocation();
				Farlander.genTargetChunks( loc );
				player.sendMessage( "Sending you to respawn point.." );
				
				player.teleport( loc );
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
}
