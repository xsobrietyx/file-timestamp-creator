# Description

Small utility that helps user to create timestamped files and folders on linux based machine.

## Usage
   ### Case A, build by yourself
    - Download and install JDK
    - Download and install Leiningen
    - Download sources
    - Open SOURCES_FOLDER
    - $ lein uberjar
    - Copy JAR (prefixed as standalone) from the SOURCES_FOLDER/target to the speceific folder on your lunix-based PC
    - Create alias (better to place it to your ~/.bashrc or ~/.zshrc and do not forget to source the file):
        $ alias ccf='java -jar /YOUR_JAR_LOCATION/UBERJAR_NAME.jar'
    - $ ccf file-name* -> will create file(s)/folder(s). Depends on file names.
   ### Case B, download executable jar
    - TBD
    
### Examples

    $ ccf hello.txt some-folder
    -> will create hello-TIMESTAMP.txt and some-folder-TIMESTAMP in users current path
    In negative cases:
    $ ccf hello.txt.abc
    $ ccf .bashrc
    -> You will receive message:
    File names with more than one dot is not allowed. Please enter a valid file/folder name.

## License

Copyright © 2020 Vadym Serdiuk

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
