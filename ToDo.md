# Roadmap
- Option to disable LotR bronze crafting and alloy furnaces
- Update books
- Fix lotR recipes not working with tinker's items (missing OreDict?)
- Look into how Mantle and TC handle plugins. There seems to be a system in place already for submodding, in some capacity
- Look into compatability with [Iguanas Tinker Tweaks](https://www.curseforge.com/minecraft/mc-mods/iguanas-tinker-tweaks)
- Add NEI compatability
- Re-balance mod
- Add new smelting recipes for bird cages, obsidian bricks and walls, orc platings, mugs and plates, mithril mail
- Add recipes for armor, weapons and tool smelting, depending on durability

## Smeltery Update
- Make smelteries more faction-specific
  - Require alignment for interaction
  - Limit recipes/alloys/fuels per faction
  - Add more faction smelteries
  - Add seared faction bricks
    - More grout recipes
    - Melt faction rock
  - Special structure for each faction/race that grants bonuses
- Fuel overhaul
  - new block type: "Smeltery heat source", burns normal fuel
  - The lower the burn time, the lower the max temperature the smeltery will operate at
    - Maximum temperature depends on fuel. Doesn't heat instantly, instead needs time to pre-heat
    - After pre-heating the real smelting starts, but rather quick
    - Can only move fluids if heat is above their melting point, otherwise stuck at bottom
    - Alloying only happens over specific temperature (higher than melting components)
    - Loses heat while not getting fueled
    - Sounds like bad gameplay tho
  - The bigger the smeltery, the quicker fuel will be consumed
  - Add new fuel types (research needed)
  - Perhaps remove lava tanks as heat source? Only keep for dwarves and orcs?
  - Bellows
    - Increases heat and maximum temperature, but fuel will be consumed quicker
    - Operate by hand? Have smith NPC help you???
- Better steel processing
  - Remove liquefied coal
  - Perhaps using coal as fuel in "Smeltery heat source" will yield steel?
  - Add slag as byproduct
    - can be cast to scorched stone?
    - Mix with other stuff to create mordor rock?
  - More research needed
- Allow multiple fluid outputs per Item
  - Melt LotR and Tinkers tools back into metals
  - Melt trimmed armour
  - Melt Gilded iron


## Tool tinkering update
- Scrolls are modifier
  - Different levels stack, but not past the max scroll level
  - Need gems to apply them
- Banes apply to tinker's weapons and stack (will take longer to acquire exponentially, while effect grows linearly)
- New Scrolls
  - "United": The higher your alignment with a specific faction, the higher the chance to not consume durability. Caps at 90% (durability x10).
    - Durability boost is x1 at 2500, x2 at 5000 and x10 at 25000 alignment
    - Very very very rare mob drop from elite units (black uruk, swan knight, etc.)
      - To get the drop, you need at least -5000 with the mob faction, so slaughtering your allies won't do the trick
    - You need at least 2500+ alignment with your faction to apply it
    - Replaces "Durability" modifier
- Change color/texture of specific parts (see "Dyer" branch)
- Parts
  - Faction-specific texture, model and properties (glowing elven blade)
    - Faction specific workstation/part builder/etc
      - material restriction
    - Crafting a part takes multiple steps
      - Use faction workbench for those recipes?
      - "tinkering intensifies" achievement if player modifies 100 parts
      - Handle
        - Could be treated with leather/fur
        - Wood types give different properties
      - Binding
        - Gems
      - Extra
        - Gems again
        - Skulls?
- Special loot: Find weapons with special modifiers/parts hidden in middle earth
- Add new weapons, crossbow, etc

### Ideas for gem <> scroll mapping
| Gem      | Melee Weapons | Armour     | Tools             | Ranged Weapons |
|----------|---------------|------------|-------------------|----------------|
| Diamond  | Looting       | True       | Efficiency (x0.5) |                |
| Emerald  | Swift         | Falling    | Silken \| Fortune | Punch          |
| Ruby     | Sharpness     | Protection |                   | Power          |
| Opal     | Durability    | Durability | Durability        | Durability     |
| Amethyst | Knockback     |            | Efficiency        |                |
| Topaz    | Long          |            |                   |                |


## Armor update
- Add armor casting
- Add trimming to all factions
- New modifier: Autoforge (ultra rare, utumno lava level-only scroll modifier, taking a bath in molten metal repairs your armor)