package com.qmt.besedo.model.message;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.ToIntFunction;

import static io.vavr.control.Validation.*;

@UtilityClass
public class MessageValidation {

    private static final Function<String, Option<String>> GET_NON_EMPTY_VALUE = value ->
            Option.of(value)
                    .flatMap(v -> v.isBlank() ? Option.none() : Option.of(v));

    public static Validation<Seq<String>, Message> requireValidMessage(Message message) {
        Validation<Seq<String>, Message> mailValidator = combine(requireIsNullOrIsSizeBetween(message.id(), 1, 100),
                requireTitleOrBody(message.title(), message.body()),
                requireValidEmail(message.email()))
                .ap((id, mailAndBody, email) -> message);
        if (mailValidator.isValid()) {
            return valid(message);
        } else {
            return invalid(mailValidator.getError());
        }
    }

    private static Validation<String, Tuple2<String, String>> requireTitleOrBody(String title, String body) {
        Option<String> titleOpt = GET_NON_EMPTY_VALUE.apply(title);
        Option<String> bodyOpt = GET_NON_EMPTY_VALUE.apply(body);

        if (titleOpt.isEmpty() && bodyOpt.isEmpty()) {
            return invalid("title and body are not defined, one of them must be defined at least");
        } else {
            return valid(Tuple.of(title, body));
        }
    }


    private static Validation<String, String> requireIsNullOrIsSizeBetween(String value, int min, int max) {
        ToIntFunction<String> getSize = v -> v == null ? 0 : v.length();
        var size = getSize.applyAsInt(value);
        boolean b = size < min || size > max;
        if (b) {
            return invalid("id size is not valid, it should be between %d and %d (current size: %d)".formatted(min, max, size));
        } else {
            return valid(value);
        }
    }

    private static Validation<String, String> requireValidEmail(String mail) {
        return GET_NON_EMPTY_VALUE
                .apply(mail)
                .fold(() -> invalid("Mail is mandatory"), Validation::valid);
    }
}
