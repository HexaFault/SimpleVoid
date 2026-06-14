SimpleVoid
[Looks like the result wasn't safe to show. Let's switch things up and try something else!]
https://img.shields.io/badge/Java-17-orange
https://img.shields.io/badge/Paper-1.20.6-blue
https://github.com/HexaFault/SimpleVoid/releases
https://img.shields.io/github/downloads/HexaFault/SimpleVoid/total
SimpleVoid is a lightweight Paper plugin that makes it easy to:
• Create void worlds using datapacks
• Configure a hub world for first‑time players
• Control respawn behavior (bed → bed, else → hub)
• Create simple rectangular portals (height + width)
• Teleport players between worlds without extra plugins
• Provide a standalone /hub command
Everything is designed to be simple, intuitive, and kid‑friendly.
---
✨ Features
• /simplevoid createworld  — generate a void world via datapack
• Hub world support
◦ First‑time players spawn in the hub
◦ Respawn at bed if it exists
◦ Otherwise respawn in the hub
• /hub — teleport to the hub world
• /simplevoid sethub — set the current world as the hub
• Easy portal creation:
◦ Stand on the bottom‑left corner
◦ Choose height + width
◦ Teleport to a target world
• /simplevoid portal list — view all portals
• /simplevoid portal delete  — remove a portal
• No dependencies
• Works on Paper 1.20+
---
📦 Commands
Create a void world:
/simplevoid createworld
Teleport to the hub:
/hub
Set the hub world:
/simplevoid sethub
Portal commands:
/simplevoid portal create  [height] [width]
/simplevoid portal delete
/simplevoid portal list
---
⚙️ Configuration
hub-world: “hub”
portals: {}
Respawn behavior:
• If the player has a valid bed, they respawn at their bed
• If not, they respawn in the hub world
• If the hub world does not exist, SimpleVoid falls back to vanilla behavior
---
🧱 Void World Generation
Void worlds are created using a datapack placed into:
world/datapacks/simplevoid_/
Note: Void world generation is not yet complete and does not automatically create a fully void world. The datapack structure is created correctly, but the world must be reloaded or restarted to activate.
---
🧭 Portal System
Portals are simple rectangular regions defined by:
• Base X, Y, Z (where you stand when creating)
• Height
• Width
• Target world + coordinates
Players teleport when entering any block inside the region.
Portals are saved in config.yml.
---
🛠 Requirements
• Java 17+
• Paper 1.20.6+
• No external dependencies
---
📄 License
This project is licensed under the MIT License.
See the LICENSE file for details.
