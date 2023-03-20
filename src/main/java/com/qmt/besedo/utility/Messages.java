package com.qmt.besedo.utility;

import com.qmt.besedo.model.message.Message;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.function.Function;

import static com.qmt.besedo.utility.Strings.GET_SIZE;
import static io.vavr.control.Validation.*;

/**
 * Utility for {@link Messages} objects.
 */
@UtilityClass
public class Messages {

    private static final Function<Message, Validation<Seq<String>, Message>> REQUIRE_NON_NULL_MESSAGE =
            message -> null == message ? invalid(List.of("Message cannot be null")) : valid(message);
    private static final Function3<String, Integer, Integer, Validation<String, String>> REQUIRE_VALUE_SIZE_BETWEEN =
            (value, min, max) -> {
                var size = GET_SIZE.applyAsInt(value);
                boolean isSizeValid = size < min || size > max;
                if (isSizeValid) {
                    return invalid("id size is not valid, it should be between %d and %d (current size: %d)".formatted(min, max, size));
                } else {
                    return valid(value);
                }
            };

    private static final Function2<String, Integer, Validation<String, String>> REQUIRE_VALUE_SIZE_NOT_EXCEEDING =
            (value, max) -> {
                var size = GET_SIZE.applyAsInt(value);
                if (size > max) {
                    return invalid("id size is not valid, it should not exceed " + max);
                } else {
                    return valid(value);
                }
            };


    /**
     * Test if mail is non-blank and if it is valid.
     */
    private static final Function<String, Validation<String, String>> REQUIRE_VALID_EMAIL = mail -> {
        Function<String, Validation<String, String>> validMail = m -> {
            if (EmailValidator.getInstance().isValid(mail)) {
                return valid(mail);
            } else {
                return invalid("Mail is not valid");
            }
        };

        return Strings.GET_NON_EMPTY_VALUE
                .apply(mail)
                .toValidation("Mail is mandatory and cannot be blank")
                .flatMap(validMail);
    };


    /**
     * <p>Validate the {@link Message} testing all its field.</p>
     * <ul>
     *     <li>id length must be between 1 and 100 (this makes it mandatory as null id is considered as length equals to 0</li>
     *     <li>Title or body must be existing and non blank</li>
     *     <li>mail is mandatory and must be valid</li>
     * </ul>
     */
    public static final Function<Message, Validation<Seq<String>, Message>> REQUIRE_VALID_MESSAGE = message ->
            REQUIRE_NON_NULL_MESSAGE
                    .apply(message)
                    .flatMap(mess -> combine(REQUIRE_VALUE_SIZE_BETWEEN.apply(mess.id(), 1, 100),
                            Strings.REQUIRE_ONE_NON_BLANK_FROM_TWO.apply(mess.title(), mess.body(), "title and body are not defined, one of them must be defined at least"),
                            REQUIRE_VALUE_SIZE_NOT_EXCEEDING.apply(mess.title(), 300),
                            REQUIRE_VALUE_SIZE_NOT_EXCEEDING.apply(mess.body(), 10000),
                            REQUIRE_VALID_EMAIL.apply(mess.email()))
                            .ap((id, titleAndBody, title, body,  email) -> mess));


}
