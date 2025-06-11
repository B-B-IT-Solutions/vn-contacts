package cz.prm.repositories.common.query.filter;

import static cz.prm.domain.task.Status.IN_PROGRESS;
import static cz.prm.domain.task.Status.TO_DO;
import static cz.prm.domain.task.Status.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import cz.prm.domain.task.Status;
import org.junit.jupiter.api.Test;

class EnumFilterCriteriaTest {

    @Test
    void getEnumValues() {
        var fc = new EnumFilterCriteria(null, Status.class);
        assertThat(fc.getEnumValues()).isEmpty();

        fc = new EnumFilterCriteria("", Status.class);
        assertThat(fc.getEnumValues()).isEmpty();

        fc = new EnumFilterCriteria("TO_DO", Status.class);
        assertThat(fc.getEnumValues()).containsExactly(TO_DO);

        fc = new EnumFilterCriteria("TO_DO,  WAITING", Status.class);
        assertThat(fc.getEnumValues()).containsExactly(TO_DO, WAITING);

        fc = new EnumFilterCriteria("TO_DO,WAITING,IN_PROGRESS", Status.class);
        assertThat(fc.getEnumValues()).containsExactly(TO_DO, WAITING, IN_PROGRESS);
    }

    @Test
    void getEnumValues_throwsException() throws Exception {
        final var fc1 = new EnumFilterCriteria("NON_EXISTANT", DummyClass1.class);
        var exception = assertThrows(IllegalArgumentException.class, () -> fc1.getEnumValues());
        assertThat(exception.getCause()).isInstanceOf(NoSuchFieldException.class);

        final var fc2 = new EnumFilterCriteria("inaccessibleField", DummyClass1.class);
        exception = assertThrows(IllegalArgumentException.class, () -> fc2.getEnumValues());
        assertThat(exception.getCause()).isInstanceOf(IllegalAccessException.class);
    }

    private static class DummyClass1 {

        private static DummyClass1 inaccessibleField = new DummyClass1();
        public static DummyClass1 accessibleField = new DummyClass1();
    }
}