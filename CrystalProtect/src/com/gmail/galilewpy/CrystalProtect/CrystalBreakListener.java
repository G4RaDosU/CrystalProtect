package com.gmail.galilewpy.CrystalProtect;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * エンドクリスタルの破壊を検出， 保護ワールドに設定されていれば爆破をキャンセル
 *
 * @author G4RaDosU
 *
 */
public class CrystalBreakListener implements Listener {

	private CrystalProtect plugin;

	public CrystalBreakListener(CrystalProtect plugin) {
		this.plugin = plugin;
	}

	/**
	 * エンドクリスタルの爆破の検出
	 *
	 * @param event
	 */
	@EventHandler
	public void onEntityDamagedByCrystal(EntityDamageByEntityEvent event) {

		if (event.getEntityType().equals(EntityType.ENDER_CRYSTAL)) {
			Entity crystal = event.getEntity();
			Location loc = crystal.getLocation();
			World world = loc.getWorld();
			if (isProtectedWorld(world)) {
				event.setCancelled(true);
				crystal.remove();
				world.dropItem(loc, new ItemStack(Material.END_CRYSTAL));
			}
		}
	}

	/**
	 * @param world
	 * @return ワールドが保護ワールドに指定されているかどうか
	 */
	private boolean isProtectedWorld(World world) {
		String wname = world.getName();
		List<String> protectedWorlds = plugin.getProtectedWorlds();
		for (String w : protectedWorlds) {
			if (wname.equalsIgnoreCase(w)) {
				return true;
			}
		}
		return false;
	}

}
