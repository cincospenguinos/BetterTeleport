# Better Teleport

A better teleport command for minecraft. Intended features include:

* Ability to add, remove and query locations stored on a database
* Ability to teleport to locations given an alias
* Support multiple relational databases
* Support server-side use of the /loc and /tele commands

Because this will be a server plugin, all of these features will be included in game. You won't have to have a separate
webpage full of that information; it will all be included in the UI.

## How to use it

Upon installing it for the first time, the db configuration file will be generated in the BetterTeleport directory
of your plugins directory of your server. The plugin will disable itself immediately after, and in order for it to
work you will need to update the dbconfig.yml file that is generated with the proper information to connect to your
mysql database. After updating it, simply run the server again/reload all the plugins again and it should connect
up properly.

### The /loc command

The /loc command is the heart of the plugin. You can add, remove and query locations by using it. To add, you simply
type in `/loc add` followed by either the alias you want this location to be recognized by, or the coordinates that
the location should be known by. If no coordinates are provided, then the command will grab the coordinates of the
player typing in the command. After the coordinates, the alias must be provided, and an optional description can
be given as well, surrounded by quotes ("). Below is an example:

```
/loc add village1 # Adds a new location with the coordinates of the player and calls it village1
/loc add village1 "This is a cool village" # Same as above with a description
/loc add village1 0 100 0 "This is a cool village" # Same as above, but with coordinates (0, 100, 0) instead of the player's
```

To remove a location, it's simply `/loc remove` followed by the alias of the location you want to remove. If you have
multiple locations with the same name in different dimensions (The End, Nether, or Overworld), only the location
matching the alias you give it in the dimension you are currently in will be removed. Example: you have a location
with alias "village" in both the nether and the overworld. If you are in the nether and you input the command
`/loc remove village`, only the location *in the nether* will be removed, *not* the one in the overworld.

To view information on a location, use `/loc list`. It will list all the locations in your current dimension.

### The /tele command

To teleport to a location, simply use `/tele ` followed by either the alias of the location you want to teleport to or
the player you want to teleport to. Note that aliases have precedence over player names, so if you have a player named
"joe" and a location named "joe", `/tele joe` will teleport you to the *location* named joe, **NOT** the player named
joe.

## DB Table

The DB Table will look like this (subject to change):

| alias   | dimension |  x  |  y  |  z  | description |
|:-------:|:---------:|:---:|:---:|:---:|:-----------:|
|string PK| string PK | int | int | int | string      |

## Things to Implement

* Support for multiple databases
* Support server-side use of the /loc and /tele commands
* [Auto completion](https://bukkit.org/threads/easy-no-api-setting-up-custom-tab-completion.299956/)