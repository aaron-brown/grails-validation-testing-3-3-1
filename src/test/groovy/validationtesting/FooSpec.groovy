package validationtesting

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class FooSpec extends Specification implements DomainUnitTest<Foo> {
    Foo foo

    def setup() {
        foo = new Foo()
    }

    @Unroll
    void 'Validate validate() == #expect (fooField: #fooFieldVerbose)'() {
        setup:
        foo.fooField = fooField

        expect:
        foo.validate() == expect
        foo.errors.getFieldError('fooField')?.code == fooFieldErrorCode

        where:
        expect << [
            true,

            false,
            false,
            false,
        ]

        [fooField, fooFieldErrorCode, fooFieldVerbose] << [
            ['foo', null, "'foo'"],

            [null, 'nullable', 'null'],
            ['', 'blank', "''"],
            [' ', 'blank', "' '"],
        ]
    }

    @Unroll
    void 'Validate validate() == #expect (fooField: #fooFieldVerbose) (Setting fooField via Constructor-args)'() {
        setup:
        foo = new Foo(fooField: fooField)

        expect:
        foo.validate() == expect
        foo.errors.getFieldError('fooField')?.code == fooFieldErrorCode

        where:
        expect << [
            true,

            false,
            false,
            false,
        ]

        [fooField, fooFieldErrorCode, fooFieldVerbose] << [
            ['foo', null, "'foo'"],

            [null, 'nullable', 'null'],
            ['', 'nullable', "''"],
            [' ', 'nullable', "' '"],
        ]
    }
}
