package cz.prm.repositories.common.query.filter;

import static cz.prm.domain.contacts.task.TaskStatus.IN_PROGRESS;
import static cz.prm.domain.contacts.task.TaskStatus.TO_DO;
import static cz.prm.domain.contacts.task.TaskStatus.WAITING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import cz.prm.domain.contacts.task.TaskStatus;
import org.junit.jupiter.api.Test;

class EnumFilterCriteriaTest {

    @Test
    void getEnumValues() {
        var fc = new EnumFilterCriteria(null, TaskStatus.class);
        assertThat(fc.getEnumValues()).isEmpty();

        fc = new EnumFilterCriteria("", TaskStatus.class);
        assertThat(fc.getEnumValues()).isEmpty();

        fc = new EnumFilterCriteria("TO_DO", TaskStatus.class);
        assertThat(fc.getEnumValues()).containsExactly(TO_DO);

        fc = new EnumFilterCriteria("TO_DO,  WAITING", TaskStatus.class);
        assertThat(fc.getEnumValues()).containsExactly(TO_DO, WAITING);

        fc = new EnumFilterCriteria("TO_DO,WAITING,IN_PROGRESS", TaskStatus.class);
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