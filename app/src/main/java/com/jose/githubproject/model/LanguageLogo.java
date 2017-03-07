package com.jose.githubproject.model;

import static com.jose.githubproject.R.drawable.*;

/**
 * Created by jeeps on 3/6/2017.
 */

public enum LanguageLogo {

    JAVA("java", java_logo),
    PYTHON("python", python_logo),
    JAVASCRIPT("javascript", javascript_logo),
    C("c", c_logo),
    CPP("c++", cpp_logo),
    RUBY("ruby", ruby_logo),
    PHP("php", php_logo),
    CSHARP("c#", csharp_logo),
    HTML("html", html_logo),
    CSS("css", css3_logo),
    OBJ_C("objective-c", objective_c_logo),
    RL("r", r_logo),
    NONE("r", image_missing);

    private String name;
    private int id;

    LanguageLogo(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


}
