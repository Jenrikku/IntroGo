- IMPORTANT: This plugin is unfinished, but it will soon be finished.
# IntroGo [MC Plugin]
IntroGo allows the user to create custom messages and also teleports by adding new commands to the game.
- [Server Owners](https://github.com/Jenrikku/IntroGo/blob/master/README.md#server-owners)
- [Developers](https://github.com/Jenrikku/IntroGo/blob/master/README.md#developers)
## Server owners
### Install
First, you need to install this plugin:
1. Create a server with [Spigot](https://www.spigotmc.org/), [Paper](https://papermc.io/) or [CraftBukkit](https://bukkit.gamepedia.com/Setting_up_a_server).
2. Download [this file]().
3. Put it into your plugins folder.
4. Launch your server at least one time to generate the configuration files.
### How to use
- Note that you need to create the configuartion files running your server at least one time.
1. Go to your plugins folder and open 'IntroGo' folder. Inside you can find two files: 'config.yml' and 'messages.yml'.
2. Open 'messages.yml'. There're three sections: 'commands', 'messages' and 'text'.
3. Write an entry in the commands section with the command you want the player to use to view a message.
4. Enter a name ID for your message in the messages section.
5. Write it in the same entry number as the command (Example: if a command was in position 1 of the commands list, the message that was in position 1 is executed). <-- Change that to only command -> text
6. Add a new list in text section like in the example and write wathever you want. (You can use colors with & symbol)
7. Run the server and write '/m %your-command%'
## Developers
If this plugin is outdated or you want to use it to do another project, please go to the [Source Code](https://github.com/Jenrikku/IntroGo) section.
