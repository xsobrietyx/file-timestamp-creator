# As a user you should have the ability (kind of AC's):
- create a file or create a folder (all is files)
- pass params into app, params should be varargs, holding a file names
- then application creates a file(s) or folder(s) in current folder with name concatenated with current date and timestamp
- application should have bash alias which should allow application call from any folder
- solution should start as simple one and proceed with complexity afterwards

# Plan
- unit tests
- check arguments for . char
- if . then file else folder
- function that appends timestamp
- function that creates file(s) folder(s)
- main function with args
- alias should be documented

# Libs/deps
- joda time

