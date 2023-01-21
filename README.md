## Tinker's Middleearth - Bring Tinker's into Middleearth!
A compatability mod for Mevans [Lord of the Rings](http://lotrminecraftmod.wikia.com/wiki/The_Lord_of_the_Rings_Minecraft_Mod_Wiki) mod and [Tinker's Construct](https://github.com/SlimeKnights/TinkersConstruct) for Minecraft 1.7.10


### Releases
Releases can be found on [CurseForge](https://minecraft.curseforge.com/projects/tinkersmiddleearth).

This mod depends on [Tinker's Construct](https://www.curseforge.com/minecraft/mc-mods/tinkers-construct/files/2277012), [Lord of the Rings Mod: Legacy](https://www.curseforge.com/minecraft/mc-mods/the-lord-of-the-rings-mod-legacy/files) and [Mantle](https://www.curseforge.com/minecraft/mc-mods/mantle/files/2264244) for [Forge 1.7.10 - 10.13.4.1614](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.7.10.html).


### Setup for developers
1. Clone this repository, open it in Intellij IDEA as gradle project
2. Run `setupDecompWorkspace` gradle task
3. Download LotR mod and deobfuscate it using [Bon-2](https://ci.tterrag.com/job/BON2/15/). Place the deobfuscated file into the `libs` folder. **Never** upload this file!
4. Generate the source code of the deobfuscated LotR mod using [CFR](https://github.com/leibnitz27/cfr/releases/tag/0.152). **Don't** place it in the `libs` folder. **Never** upload this file!
5. Add `Dummy.jar` and the deobfuscated LotR mod as local dependencies to the project. Link the source for the LotR mod.
6. Execute `runClient` gradle task. If it launches, you've done well

#### Pitfalls
- If a "MCP config directory" file choosing dialog pops up while launching, direct it to `~/.gradle/caches/minecraft/net/minecraftforge/forge/1.7.10-10.13.4.1614-1.7.10/unpacked/conf/`
- Use Java 8