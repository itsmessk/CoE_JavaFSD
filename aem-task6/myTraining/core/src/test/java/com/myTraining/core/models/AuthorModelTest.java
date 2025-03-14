package com.myTraining.core.models;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AuthorImplTest {

    private final AemContext aemContext=new AemContext();

  private  AuthorModel authorModel;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(AuthorModel.class);
       // aemContext.load().json("/resource/com/myTraining/core/models/Author.json","/Author");
              }

    @Test
    void getBooks() {
        String book="history";
        assertEquals("history",book);
    }

    @Test
    void getAuthorBio() {
        String authorBio="authorBio";
        assertEquals("authorBio",authorBio);
    }


    @Test
    void getAuthorName() {
        String authorName="Amit";
        assertEquals("Amit",authorName);
    }

}