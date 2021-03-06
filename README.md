# IntroGo [MC Plugin]
IntroGo is a simple plugin that allows you to create a custom welcome message and a custom spawn.
## Install
First, you need to install the plugin:
1. Create a server with [Spigot](https://www.spigotmc.org/) or [Paper](https://papermc.io/). (CraftBukkit is no longer supported)
2. Download [this file](https://github.com/Jenrikku/IntroGo/releases/download/1.1a/IntroGo.jar).
3. Put it into your plugins folder (%YOUR_SERVER%\plugins\).
4. Launch your server at least one time to generate the configuration files.
## How to use
- Note that you need to create the configuration files by running your server at least one time.
### Welcome message
1. Go to your plugins folder and open 'IntroGo' folder. Inside you can find a file named 'config.yml'.
2. Open 'config.yml'.
3. Set initialMessage/enabled to true.
4. Write a welcome message like in the example.
#### Constants
- %player% -> Name of the player that logs in.
- %online% -> Number of players that are in the server.
- %max% -> Maximum number of players that can be connected at the same time in the server.
- %available% -> Number of players that can still join.
### Custom Spawn
1. Run your server.
2. Join as a player.
3. Use '/go-adm setspawn' to set the spawn point.
4. OPTIONAL - If you want, you can set enableWhenRespawn to true to teleport players to the initial spawn when they die. _You need to run /go-adm reload after it._
### Misc.
- /go-adm reload
	Reloads 'config.yml'. Useful if you want to modify the welcome message without restarting the server.
- /go-adm info
    Returns the current version installed.
