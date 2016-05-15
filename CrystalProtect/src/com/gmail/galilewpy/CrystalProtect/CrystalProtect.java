package com.gmail.galilewpy.CrystalProtect;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * spigot-API-1.9.2-R0.1で作成
 *
 * @author G4RaDosU
 *
 */
public class CrystalProtect extends JavaPlugin {

	/**
	 * Plugin Log
	 */
	private Logger log;

	/**
	 * 保護ワールドのリスト
	 */
	private List<String> worlds;

	// プラグイン起動時
	public void onEnable() {
		log = this.getLogger();
		this.setUp();
		log.info(this.getName() + " has been enabled!");
	}

	private void setUp() {
		// コンフィグファイルがなければ生成
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			log.info("config.ymlのファイルを確認できなかったため新たにに生成します.");
			this.saveDefaultConfig();
		}
		// リスナーの登録
		this.getServer().getPluginManager()
				.registerEvents(new CrystalBreakListener(this), this);

		// 保護ワールドの読み込み
		this.worlds = this.getConfig().getStringList("worlds");
	}

	// プラグイン停止時
	public void onDisable() {
		log.info(this.getName() + " has been disebled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length <= 0) {
			sender.sendMessage("[CrystalProtect]version 1.0");
			return true;
		}

		if (args[0].equalsIgnoreCase("reload")) {
			this.reloadConfig();
			this.worlds = this.getConfig().getStringList("worlds");
			sender.sendMessage("[CrystalProtect]コンフィグファイルをリロードしました.");
			return true;
		}

		return true;
	}

	/**
	 * @return 保護ワールドのリスト
	 */
	public List<String> getProtectedWorlds() {
		return this.worlds;
	}

}
