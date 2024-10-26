package com.github.cichyvx.beginformatter;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public abstract class InputTrait {

    private static final String INPUT_HAPPY_PATH =
            """
            SELECT 1
            \tBEGIN
            \tEND
            """;

    private static final String EXPECTED_HAPPY_PATH =
            """
            SELECT 1
            BEGIN
            END
            """;

    private static final String INPUT_TEXT_AFTER_BEGIN =
            """
            SELECT 1
            \tBEGIN TRANSACTION
            \tEND
            """;

    private static final String EXPECTED_TEXT_AFTER_BEGIN =
            """
            SELECT 1
            BEGIN TRANSACTION
            END
            """;

    private static final String INPUT_TEXT_AFTER_END =
            """
            SELECT 1
            \tBEGIN
            \tEND;
            """;

    private static final String EXPECTED_TEXT_AFTER_END =
            """
            SELECT 1
            BEGIN
            END;
            """;

    private static final String INPUT_TEXT_AFTER_BEGIN_AND_END =
            """
            SELECT 1
            \tBEGIN TRANSACTION
            \tEND;
            """;

    private static final String EXPECTED_TEXT_AFTER_BEGIN_AND_END =
            """
            SELECT 1
            BEGIN TRANSACTION
            END;
            """;

    private static final String INPUT_TEXT_MULTIPLE_HAPPY_PATH =
            """
            SELECT 1
            \tBEGIN
            \tEND
                
            \tBEGIN
            \tEND
            \tBEGIN
            \tEND
            
            SELECT 1
            \tBEGIN
            \tEND
            """;

    private static final String EXPECTED_TEXT_MULTIPLE_HAPPY_PATH =
            """
            SELECT 1
            BEGIN
            END
                
            BEGIN
            END
            BEGIN
            END
            
            SELECT 1
            BEGIN
            END
            """;

    private static final String INPUT_TEXT_MULTIPLE_WITH_TEXT_AFTER =
            """
            SELECT 1
            \tBEGIN TRANSACTION
            \tEND;
                
            \tBEGIN TRANSACTION
            \tEND;
            \tBEGIN TRANSACTION
            \tEND;
            
            SELECT 1
            \tBEGIN TRANSACTION
            \tEND;
            """;

    private static final String EXPECTED_TEXT_MULTIPLE_WITH_TEXT_AFTER =
            """
            SELECT 1
            BEGIN TRANSACTION
            END;
                
            BEGIN TRANSACTION
            END;
            BEGIN TRANSACTION
            END;
            
            SELECT 1
            BEGIN TRANSACTION
            END;
            """;

    private static final String INPUT_TEXT_NEXTED_HAPPY_PATH =
            """
            SELECT 1
            \tBEGIN
            \t\tSELECT 2
            \t\t\tBEGIN
            \t\t\tEND
            \tEND
            """;

    private static final String EXPECTED_TEXT_NEXTED_HAPPY_PATH =
            """
            SELECT 1
            BEGIN
            \tSELECT 2
            \tBEGIN
            \tEND
            END
            """;

    private static final String INPUT_TEXT_NEXTED_WITH_TEXT_AFTER =
            """
            SELECT 1
            \tBEGIN TRANSACTION
            \t\tSELECT 2
            \t\t\tBEGIN TRANSACTION
            \t\t\tEND;
            \tEND;
            """;

    private static final String EXPECTED_TEXT_NEXTED_WITH_TEXT_AFTER =
            """
            SELECT 1
            BEGIN TRANSACTION
            \tSELECT 2
            \tBEGIN TRANSACTION
            \tEND;
            END;
            """;

    private static final String INPUT_TEXT_BETWEEN =
            """
            SELECT 1
            \tBEGIN TRAN
            \t\t SELECT 1
            \t\tFROM XXX
            \t\tWHERE XXX.ID > 3
            \tEND;
            """;

    private static final String EXPECTED_TEXT_BETWEEN =
            """
            SELECT 1
            BEGIN TRAN
            \tSELECT 1
            \tFROM XXX
            \tWHERE XXX.ID > 3
            END;
            """;


    public static Stream<Arguments> input() {
        return Stream.of(
                Arguments.argumentSet("Happy path", INPUT_HAPPY_PATH, EXPECTED_HAPPY_PATH),
                Arguments.argumentSet("Text after begin", INPUT_TEXT_AFTER_BEGIN, EXPECTED_TEXT_AFTER_BEGIN),
                Arguments.argumentSet("Text after end", INPUT_TEXT_AFTER_END, EXPECTED_TEXT_AFTER_END),
                Arguments.argumentSet("Text after both begin and end", INPUT_TEXT_AFTER_BEGIN_AND_END, EXPECTED_TEXT_AFTER_BEGIN_AND_END),
                Arguments.argumentSet("multiple statements - happy path", INPUT_TEXT_MULTIPLE_HAPPY_PATH, EXPECTED_TEXT_MULTIPLE_HAPPY_PATH),
                Arguments.argumentSet("multiple statements - text after", INPUT_TEXT_MULTIPLE_WITH_TEXT_AFTER, EXPECTED_TEXT_MULTIPLE_WITH_TEXT_AFTER),
                Arguments.argumentSet("nested statements - happy path", INPUT_TEXT_NEXTED_HAPPY_PATH, EXPECTED_TEXT_NEXTED_HAPPY_PATH),
                Arguments.argumentSet("nested statements - text after", INPUT_TEXT_NEXTED_WITH_TEXT_AFTER, EXPECTED_TEXT_NEXTED_WITH_TEXT_AFTER),
                Arguments.argumentSet("text between", INPUT_TEXT_BETWEEN, EXPECTED_TEXT_BETWEEN)
        );
    }

}
