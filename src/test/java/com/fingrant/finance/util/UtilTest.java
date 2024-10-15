package com.fingrant.finance.util;

import com.shared.token.Tokenization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class UtilTest {

    @Mock
    Tokenization tokenization;

    @InjectMocks
    private Util util;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }


    @Test
    void tokenize() {

        try (MockedStatic<Tokenization> mockedTokenization = mockStatic(Tokenization.class)) {

            mockedTokenization.when(() -> Tokenization.obfuscate("sampleInput")).thenReturn("someweirdInput");
            final String result = Util.tokenize("sampleInput");

            assertEquals("someweirdInput", result);
        }

    }
}