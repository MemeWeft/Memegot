# Memegot
Private Paper fork used for game plugin environments.

## Changes

### Branding
- Custom `/version` output (Memegot x.x)
- Custom `/memegot` info command
- Removed Paper onboarding message on first start
- Removed version check error message
- `/reload` command disabled

### File Structure
- Config files moved to `/config` (bukkit.yml, spigot.yml, commands.yml, server.properties, permissions.yml, help.yml, eula.txt, version_history.json, paper-global, paper-world-defaults)
- Player files moved to `/players` (ops.json, whitelist.json, banned-players.json, banned-ips.json, usercache.json)
- Memegot config in `/game` (game.yml, profiles.yml)

### Admin Commands
- `/setviewdistance <chunks>` — manually override view distance
- `/setslots <amount>` — manually override max player count

### User Commands
- `/ping [player]` — show player ping with color coding

### Server Defaults
- EULA auto-accepted
- Nether disabled
- End disabled
- Weather cycle disabled
- Daylight cycle disabled (time locked at 6000)
- Mob spawning disabled
- Fire spreading disabled
- Block updates disabled (random tick speed 0)
- Phantoms disabled
- Wandering traders disabled
- Advancement messages disabled
- Advancement sounds disabled

### Performance
- Mob AI disabled (configurable via `mob-ai` in game.yml)
- Player stats/advancements not saved (configurable via `player-stats` in game.yml)
- Advancement rewards skipped
- Block updates configurable via `block-updates` in game.yml
