# Better Teleport

A better teleport command for minecraft. Intended features include:

* Manage permissions for use of the teleport command
* Ability to add, remove and query locations stored on a database
* Ability to teleport to locations given an alias
* Support multiple relational databases

Because this will be a server plugin, all of these features will be included in game. You won't have to have a separate
webpage full of that information; it will all be included in the UI.

## DB Table

The DB Table will look like this (subject to change):

| alias   |  x  |  y  |  z  | description |
|:-------:|:---:|:---:|:---:|:-----------:|
|string PK| int | int | int | string      |