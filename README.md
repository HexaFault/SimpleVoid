# 🌌 SimpleVoid — Custom Void Dimensions & Portals for Paper 1.20–1.26

SimpleVoid is a lightweight Paper plugin that lets you create **true void dimensions**, set a **hub world**, manage **custom portals**, and control **first‑join & respawn behavior** — all without needing external world‑generation plugins.

Perfect for hub servers, minigames, skyblock, creative void worlds, or clean lobby dimensions.

---

## ✨ Features

- 🕳️ **Create true void dimensions** using Minecraft 1.20–1.26 datapacks
- 🌀 **Custom portals** that teleport between worlds
- 🏠 **Set a hub world** and teleport players with `/hub`
- 👶 **First‑join teleport** to hub world
- 💀 **Configurable respawn behavior**
- 🔄 **Reload config without restarting**
- 📦 Zero dependencies — fully standalone

---

## 📥 Installation

1. Download the latest SimpleVoid JAR
2. Place it in your server’s `plugins/` folder
3. Start the server
4. Edit `config.yml` if needed
5. Restart or `/reload`

---

## 🧭 Commands

### `/simplevoid`
Base command. Shows help menu.

### `/simplevoid createworld <name>`
Creates a **new void dimension** using a datapack.

After running this command, you **must** run:

```
/reload
```

Minecraft will then generate the new dimension.

### `/simplevoid hub`
Teleports you to the configured hub world.

### `/simplevoid sethub`
Sets your **current world** as the hub world.

### `/simplevoid reload`
Reloads the plugin configuration.

### `/simplevoid portal create <name> [height] [width]`
Creates a custom portal at your location.

### `/simplevoid portal delete <name>`
Deletes a portal.

### `/simplevoid portal list`
Lists all portals.

---

## 🌍 Creating a Void Dimension

SimpleVoid uses **datapacks** to create dimensions (the correct method for Minecraft 1.20+).

### 1. Create the dimension
```
/simplevoid createworld mydimension
```

### 2. Reload datapacks
```
/reload
```

### 3. Teleport into it
```
/execute in mydimension run tp @s 0 100 0
```

### 4. (Optional) Set it as hub
```
/simplevoid sethub
```

---

## ⚙️ Configuration (`config.yml`)

```yaml
hub-world: "hub"
respawn-mode: "same-world"
```

### `hub-world`
The world players teleport to when using `/hub` or on first join.

### `respawn-mode`
Controls where players respawn:

- `same-world` — respawn in the world they died in
- `hub` — always respawn in the hub world

---

## 🔌 First Join Behavior

SimpleVoid detects first‑time players and automatically teleports them to the **hub world**.

This works correctly on **Minecraft 1.26**, where `hasPlayedBefore()` is no longer reliable.

---

## 🌀 Portals

SimpleVoid includes a simple but powerful portal system.

- Create portals of any size
- Name them
- Link them to worlds
- Delete or list them easily

Portals are saved automatically and restored on restart.

---

## 📁 Dimension Datapack Structure

SimpleVoid generates datapacks in:

```
world/datapacks/simplevoid_<name>/
```

Each datapack includes:

- `pack.mcmeta`
- `data/<name>/dimension_type/<name>.json`
- `data/<name>/dimension/<name>.json`

These define a **true void dimension** using:

```json
"generator": {
  "type": "minecraft:noise",
  "settings": "minecraft:the_void"
}
```

---

## 🧩 Compatibility

- ✔ Paper 1.20 – 1.26
- ✔ Works with all world managers
- ✔ No dependencies
- ✔ Safe to use on production servers

---

## 🛠 Developer Notes

SimpleVoid avoids deprecated APIs and uses correct 1.26‑compatible dimension creation via datapacks.  
All worlds are created safely without touching `Bukkit.createWorld()`.

---

## ❤️ Support

If you enjoy SimpleVoid, consider starring the repository or sharing feedback.  
Issues and feature requests are welcome!

