# HarcoreMultiplayer

## server_info.json

The death events are recorded by the plugin.

The death count is updated by `start.py`.

When the server shuts down, the python script checks for any new death events. If the number of death events has exceeded the previous total, a new death has occured. Move the world files into the archives and regenerate the world.