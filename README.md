
SimpleVoid is a lightweight Paper plugin that makes it easy to:

- Create **void worlds**
- Configure a **hub world** for first-time players
- Control **respawn behavior**
- Create **void worlds** using datapacks
- Configure a **hub world** for first‑time players
- Control **respawn behavior** (bed → bed, else → hub)
- Create simple **rectangular portals** (height + width)
- Teleport players between worlds without extra plugins
- Provide a standalone `/hub` command

Everything is designed to be simple, intuitive, and kid‑friendly.

---

## ✨ Features

- `/simplevoid createworld <name>` — instantly generate a void world
- Configurable **hub world** for new players
- Configurable **respawn mode** (`same-world` or `hub`)
- `/simplevoid createworld <name>` — generate a void world via datapack
- **Hub world support**
  - First‑time players spawn in the hub
  - Respawn at bed if it exists
  - Otherwise respawn in the hub
- `/hub` — teleport to the hub world
- `/simplevoid sethub` — set the current world as the hub
- Easy **portal creation**:
  - Stand on the bottom-left corner
  - Stand on the bottom‑left corner
- Choose height + width
  - Teleport to hub spawn by default
- `/simplevoid portal list` to view all portals
- `/simplevoid portal delete <name>` to remove portals
  - Teleport to a target world
- `/simplevoid portal list` — view all portals
- `/simplevoid portal delete <name>` — remove a portal
- No dependencies
- Works on **Paper 1.20+**

---

## 📦 Commands

### Create a void world
### Create a void world
/simplevoid createworld
Creates a datapack‑based void dimension with a safe spawn platform.

---

### Teleport to the hub
/hub

---

### Set the hub world
/simplevoid sethub
Sets the hub world to the player’s current world and updates `config.yml`.

---

### Portal commands
/simplevoid portal create  [height] [width]
/simplevoid portal delete
/simplevoid portal list

---

## ⚙️ Configuration

```yaml
hub-world: "hub"

portals: {}

Respawn behavior
• If the player has a valid bed, they respawn at their bed
• If not, they respawn in the hub world
• If the hub world does not exist, SimpleVoid falls back to vanilla behavior

🧱 Void World Generation
Void worlds are created using a datapack placed into:
world/datapacks/simplevoid_<worldname>/

Void world generation is not complete and does not create a void world.

🧭 Portal System
Portals are simple rectangular regions defined by:
• Base X, Y, Z (where you stand when creating)
• Height
• Width
• Target world + coordinates
Players teleport when entering any block inside the region.
Portals are saved in config.yml.