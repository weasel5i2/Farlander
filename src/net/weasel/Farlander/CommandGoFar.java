package net.weasel.Farlander;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGoFar implements CommandExecutor
{
	public Farlander instance;
	
	public CommandGoFar( Farlander i ) 
	{
		instance = i;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) 
	{
		if( arg0 instanceof Player )
		{
			if( arg1.getName() == "far" )
			{
				double playerX = 0;
				double playerY = 0;
				double playerZ = 0;
				
				double destX = 0;
				double destY = 0;
				double destZ = 0;
				
				Player player = (Player)arg0;
				World world = player.getWorld();
				Location target = null;
				BlockFace dir = getPlayerDirection( player );
				
				playerX = player.getLocation().getX();
				playerY = player.getLocation().getY();
				playerZ = player.getLocation().getZ();
				
				if( dir == BlockFace.NORTH )
				{
					destX = -12550821;
					destY = playerY;
					destZ = playerZ;
	
					player.sendMessage( "Sending you off to the far lands.." );
					target = new Location(world, destX, destY, destZ );
					player.teleport( target );
					genTargetChunks( target );
					
					return true;
				}
	
				else if( dir == BlockFace.EAST )
				{
					destX = playerX;
					destY = playerY;
					destZ = -12550821;
	
					player.sendMessage( "Sending you off to the far lands.." );
					target = new Location(world, destX, destY, destZ );
					player.teleport( target );
					genTargetChunks( target );
	
					return true;
				}
				else if( dir == BlockFace.SOUTH )
				{
					destX = 12550821;
					destY = playerY;
					destZ = playerZ;
	
					player.sendMessage( "Sending you off to the far lands.." );
					target = new Location(world, destX, destY, destZ );
					player.teleport( target );
					genTargetChunks( target );
	
					return true;
				}
				else
				{
					destX = playerX;
					destY = playerY;
					destZ = 12550821;
	
					player.sendMessage( "Sending you off to the far lands.." );
					target = new Location(world, destX, destY, destZ );
					player.teleport( target );
					genTargetChunks( target );
	
					return true;
				}
			}
			else if( arg1.getName() == "return" )
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
	
	public static BlockFace getPlayerDirection( Player player )
	{
		BlockFace retVal = null;
		float yaw = player.getLocation().getYaw();
		
		if( yaw < 0 ) yaw += 360;
		if( yaw > 360 ) yaw -= 360;
		
		if( yaw > 45 && yaw < 135 ) 
			retVal = BlockFace.NORTH;
		else if( yaw > 135 && yaw < 225 ) 
			retVal = BlockFace.EAST;
		else if( yaw > 225 && yaw < 315 ) 
			retVal = BlockFace.SOUTH;
		else if( yaw > 315 || yaw < 45 ) 
			retVal = BlockFace.WEST;

		return retVal;
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
    
    private void genTargetChunks( Location loc )
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
