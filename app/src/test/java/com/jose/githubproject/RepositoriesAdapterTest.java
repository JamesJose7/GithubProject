package com.jose.githubproject;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RepositoriesAdapterTest {

    @Test
    public void callingALogoByNameReturnsId() throws Exception {
        int id = 0;
        com.jose.githubproject.model.LanguageLogo[] languageLogos = com.jose.githubproject.model.LanguageLogo.values();
        for (com.jose.githubproject.model.LanguageLogo logo : languageLogos) {
            if (logo.getName().equals("java")) {
                id = logo.getId();
            }
        }

        assertEquals(R.drawable.java_logo, id);
    }
}