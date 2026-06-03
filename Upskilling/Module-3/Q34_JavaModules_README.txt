Java Modules Exercise (Q34)
=============================

Folder structure to create manually:

    module34/
    ├── com.utils/
    │   ├── module-info.java
    │   └── com/utils/StringHelper.java
    └── com.greetings/
        ├── module-info.java
        └── com/greetings/Main.java

------------------------------------------------------------
File: com.utils/module-info.java
------------------------------------------------------------
module com.utils {
    exports com.utils;
}

------------------------------------------------------------
File: com.utils/com/utils/StringHelper.java
------------------------------------------------------------
package com.utils;

public class StringHelper {
    public static String greet(String name) {
        return "Hello, " + name + "!";
    }
}

------------------------------------------------------------
File: com.greetings/module-info.java
------------------------------------------------------------
module com.greetings {
    requires com.utils;
}

------------------------------------------------------------
File: com.greetings/com/greetings/Main.java
------------------------------------------------------------
package com.greetings;

import com.utils.StringHelper;

public class Main {
    public static void main(String[] args) {
        System.out.println(StringHelper.greet("World"));
    }
}

------------------------------------------------------------
Compile and Run Commands:
------------------------------------------------------------
javac --module-source-path module34 -d out --module com.utils,com.greetings
java --module-path out --module com.greetings/com.greetings.Main
