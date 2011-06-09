package net.weasel.Farlander;

import org.bukkit.Location;
import org.bukkit.World;
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

				if( Farlander.returnLocations.containsKey(player) )
					Farlander.returnLocations.remove( player );
				
				Farlander.returnLocations.put( player, player.getLocation() );
				player.sendMessage( "Sending you off to the far lands.." );
				target = new Location(world, destX, destY, destZ );
				player.teleport( target );
				Farlander.genTargetChunks( target );

				return true;
			}

			else if( dir == BlockFace.EAST )
			{
				destX = playerX;
				destY = playerY;
				destZ = -12550821;

				if( Farlander.returnLocations.containsKey(player) )
					Farlander.returnLocations.remove( player );
				
				Farlander.returnLocations.put( player, player.getLocation() );
				player.sendMessage( "Sending you off to the far lands.." );
				target = new Location(world, destX, destY, destZ );
				player.teleport( target );
				Farlander.genTargetChunks( target );

				return true;
			}
			else if( dir == BlockFace.SOUTH )
			{
				destX = 12550821;
				destY = playerY;
				destZ = playerZ;

				if( Farlander.returnLocations.containsKey(player) )
					Farlander.returnLocations.remove( player );
				
				Farlander.returnLocations.put( player, player.getLocation() );
				player.sendMessage( "Sending you off to the far lands.." );
				target = new Location(world, destX, destY, destZ );
				player.teleport( target );
				Farlander.genTargetChunks( target );

				return true;
			}
			else
			{
				destX = playerX;
				destY = playerY;
				destZ = 12550821;

				if( Farlander.returnLocations.containsKey(player) )
					Farlander.returnLocations.remove( player );
				
				Farlander.returnLocations.put( player, player.getLocation() );
				player.sendMessage( "Sending you off to the far lands.." );
				target = new Location(world, destX, destY, destZ );
				player.teleport( target );
				Farlander.genTargetChunks( target );

				return true;
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
}
